import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;

public class FenetreTrajectoires {
	private float scale=1;
	private ArrayList<Molecule> molecules;


    public FenetreTrajectoires(ArrayList<Molecule> mols) {
    	setMols(mols);
    	
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new JScrollPane(new TestPane()));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
               
            }
        });
    }

    public class TestPane extends JPanel {

        private float scale = 1;

        public TestPane() {
        	 setBackground(Color.black);
             setForeground(Color.black);
            addMouseWheelListener(new MouseAdapter() {

                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    double delta = 0.05f * e.getPreciseWheelRotation();
                    scale += delta;
                    revalidate();
                    repaint();
                }

            });
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension size = new Dimension(200, 200);
            size.width = Math.round(size.width * scale);
            size.height = Math.round(size.height * scale);
            return size;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            AffineTransform at = new AffineTransform();
            at.scale(scale, scale);
            g2d.setTransform(at);

            for (Molecule mol : molecules){
   	   		 //g2.setPaint(Color.gray);
   	   		 //to get rainbow, pastel colors
   			Random random = new Random();
   			final float hue = random.nextFloat();
   			final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
   			final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
   			Color color = Color.getHSBColor(hue, saturation, luminance);
   			g2d.setColor(color);
   			g2d.setStroke(new BasicStroke(0));
   			System.out.println("mol \n");
   			
   	   		ListIterator <CaracteristiqueTemporelle> it = mol.positions().listIterator();
   	   		CaracteristiqueTemporelle tmp = new CaracteristiqueTemporelle();
   	   		 if(it.hasNext())	{
   			 		 tmp=it.next();
   			 		 CaracteristiqueTemporelle tmp2 = new CaracteristiqueTemporelle();
   						while (it.hasNext()) {
   								tmp2=it.next();
   						/*		tmp2.setX(tmp2.x());
   								tmp2.setY( tmp2.y());*/
   								System.out.println("x " + tmp.x()+ " y "+ tmp.y()+ " next x " + tmp2.x()+ " next y "+tmp2.y() +"\n");
   								g2d.draw(new Line2D.Double(tmp.x(), tmp.y(), tmp2.x(), tmp2.y()));
   								tmp=tmp2;
   							
   		   				}
   			 		 
   			 		 }
   	   		 }
            g2d.dispose();
        }
    }
    
    public static void main(String[] args) {

		final Controleur controleur = new Chercheur();
		Parseur p = new Parseur();
		try {
			p.lireFichierAnalyse("./data/fichiersTests/analyseTest3.txt");
		}
		catch (IOException e) {
		}
		try {	
			System.out.println("Lecture du 2e fichier");
			p.lireFichierTrajectoire("./data/fichiersTests/trajectoiresTest3.trc");
		}
		catch (IOException e) {
		}
		try {	
			System.out.println("Lecture du fichier de timbres");
			p.lectureTimbre("./data/listeInstruments.txt");
		}
		catch (IOException e) {
		}
		controleur.initMolecules(p);
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new FenetreTrajectoires(controleur.molecules());			
					
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
}
