import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class FenetreTrajectoires extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Molecule> molecules;
	private Dimension preferredSize;   
	private int largeurInitiale, hauteurInitiale;


	public static void main(String[] args) { 

		try {
			Controleur.initMolecules("./data/trajectoires.trc","./data/analyse.txt", "./data/listeInstruments.txt");
		}
		catch (IOException e) {
		}


		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dimension d = Controleur.maxDimension();
					JFrame jf = new JFrame("test");
					jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					jf.setSize(300, 300);
					jf.add(new JScrollPane(new FenetreTrajectoires(Controleur.molecules(), d.width + d.width/10 ,d.height + d.height/10)));
					jf.setVisible(true);	

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});


	}    

	public ArrayList<Molecule> getMols()
	{
		return this.molecules;
	}

	public void setMols(ArrayList<Molecule> mols){
		molecules=mols;
	}
	public FenetreTrajectoires(ArrayList<Molecule> mols) {
		largeurInitiale=ControleurFenetres.getLargeurVideo();
		hauteurInitiale=ControleurFenetres.getHauteurVideo();
		molecules=mols;
		init();

	}

	public FenetreTrajectoires(ArrayList<Molecule> mols, int w, int h) {
		largeurInitiale=w;
		hauteurInitiale=h;
		this.molecules=mols;
		init();

	}

	private void init(){
		preferredSize= new Dimension(largeurInitiale,hauteurInitiale); 

		setBackground(Color.black);
		setForeground(Color.black);

		for (Molecule mol : molecules){
			Random random = new Random();
			final float hue = random.nextFloat();
			final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
			final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
			Color color = Color.getHSBColor(hue, saturation, luminance);
			mol.setCouleur(color);
		}
		// mouse listener to detect scrollwheel events
		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				updatePreferredSize(e.getWheelRotation(), e.getPoint());
			}
		});


	}

	private void updatePreferredSize(int wheelRotation, Point stablePoint) {
		double scaleFactor = findScaleFactor(wheelRotation);
		scaleBy(scaleFactor);
		Point offset = findOffset(stablePoint, scaleFactor);
		offsetBy(offset);
		getParent().doLayout();
	}

	private double findScaleFactor(int wheelRotation) {
		double d = wheelRotation * 1.08;
		return (d > 0) ? 1 / d : -d;
	}

	private void scaleBy(double scaleFactor) {
		int w = (int) (getWidth() * scaleFactor);
		int h = (int) (getHeight() * scaleFactor);
		preferredSize.setSize(w, h);
	}

	private Point findOffset(Point stablePoint, double scaleFactor) {
		int x = (int) (stablePoint.x * scaleFactor) - stablePoint.x;
		int y = (int) (stablePoint.y * scaleFactor) - stablePoint.y;
		return new Point(x, y);
	}

	private void offsetBy(Point offset) {
		Point location = getLocation();
		setLocation(location.x - offset.x, location.y - offset.y);
	}
	public Dimension getPreferredSize() {
		return preferredSize;
	}

	public int getHauteurInit()
	{
		return this.hauteurInitiale;
	}

	public int getLargeurInit()
	{
		return this.largeurInitiale;
	}




	private Point2D.Float n1, n2;


	public void paint(Graphics g) {
		super.paint(g);
		ArrayList<Molecule> molecules= this.getMols();
		Graphics2D g2 = (Graphics2D) g;
		for (Molecule mol : molecules){

			if(mol.positions().size()>0){   //si la molecule a plus de 50 positions on l'affiche

				ListIterator <CaracteristiqueTemporelle> it = mol.positions().listIterator();
				CaracteristiqueTemporelle tmp = new CaracteristiqueTemporelle();
				g2.setColor(mol.getCouleur());

				if(it.hasNext())	{
					tmp=it.next();

					CaracteristiqueTemporelle tmp2 = new CaracteristiqueTemporelle();
					//traçage d'une ligne entre chaque paire de point de la molécule
					while (it.hasNext()) {		

						float w = (float)getWidth()/(float)getLargeurInit();
						float h = (float)getHeight()/(float)getHauteurInit();
						tmp2=it.next();

						if(it.nextIndex()==2){ 					//on affiche le numero de molécule seulement entre les deux premiers points de sa trajectoire
							this.n1 = new Point2D.Float(tmp.x()*w,tmp.y()*h);
							this.n2 = new Point2D.Float(tmp2.x()*w, tmp2.y()*h);
							//double d = n1.distance(n2);
							g2.drawString(String.valueOf(mol.numero()),
									(n1.x + n2.x) / 2, (n1.y + n2.y) / 2);
						}
						//System.out.println(getWidth() + "  "+ getHeight());
						//System.out.println(w+" "+h+" "+"x " + tmp.x()*w + " y "+ tmp.y()*h+ " next x " + tmp2.x()*w+ " next y "+tmp2.y()*h +"\n");
						g2.draw(new Line2D.Float(tmp.x()*w, tmp.y()*h, tmp2.x()*w, tmp2.y()*h));
						tmp=tmp2;

					}

				}
			}       
		}
	}
}
