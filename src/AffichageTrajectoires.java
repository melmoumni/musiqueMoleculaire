import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.JFrame;

class AffichageTrajectoires extends JPanel{

    /*
     * Classe servant à afficher les trajectoires pour la fenetre de choix du compositeur
     */

    private float hauteur;
    private float largeur;
    private int delaiRafraichissement;
    private int etapeActuelle;
    private AbstractMap <Molecule, Path2D> trajectoires = new HashMap<Molecule, Path2D>();
    private List<Molecule> selectionnees = new ArrayList<Molecule>();
    private List<Molecule> nonFinies = new ArrayList<Molecule>();
    private AbstractMap <Molecule, ListIterator<CaracteristiqueTemporelle>> caracActuelle = new HashMap<Molecule, ListIterator<CaracteristiqueTemporelle>>();

    AffichageTrajectoires(float largeur, float hauteur, int delaiRafraichissement, List<Molecule> molecules){
	this.hauteur = hauteur;
	this.largeur = largeur;
	this.delaiRafraichissement = delaiRafraichissement;
	this.etapeActuelle = 0;
	for(Molecule m : molecules)
	    ajoutMolecule(m);
	setBackground(Color.BLACK);
	setForeground(Color.BLACK);
	setPreferredSize(new Dimension((int)largeur, (int)hauteur));

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
		getPreferredSize().setSize(w, h);
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

    public boolean aFini(){
	return nonFinies.size() == 0;
    }

    private void ajoutMolecule(Molecule m){
	Path2D trajectoire = new Path2D.Float();
	Color couleur = couleurAleatoire();

	trajectoires.put(m, trajectoire);
	m.setCouleur(couleur);
	nonFinies.add(m);
	caracActuelle.put(m, m.positions().listIterator());
    }

    private Color couleurAleatoire(){
	Random rand = new Random();
	int r = rand.nextInt(256);
	int g = rand.nextInt(256);
	int b = rand.nextInt(256);
	
	return new Color(r, g, b);
    }

    private Point2D creerPoint(CaracteristiqueTemporelle carac){
	int hauteurComponent = getHeight();
	int largeurComponent = getWidth();
	double x = largeurComponent * carac.x / largeur;
	double y = hauteurComponent * (hauteur - carac.y) / hauteur;
	
	return new Point2D.Double(x, y);
    }

    public void dessinerTrajectoiresCompletes(){
	Set<Map.Entry<Molecule, Path2D>> trajectoiresEntrySet = trajectoires.entrySet();
	for (Map.Entry<Molecule, Path2D> entry : trajectoiresEntrySet){
	    Molecule m = entry.getKey();
	    Path2D trajectoire = entry.getValue();
	    List<CaracteristiqueTemporelle> postemp = m.positions();

	    boolean isFirst = true;
	    for (CaracteristiqueTemporelle carac : postemp){
		Point2D p = creerPoint(carac);
		if(isFirst){
		    trajectoire.moveTo(p.getX(), p.getY());
		    isFirst = false;
		}
		else
		    trajectoire.lineTo(p.getX(), p.getY());
	    }	    	    
	}
	nonFinies.clear();
	caracActuelle.clear();
    }

    public void miseAJour() throws InterruptedException{
	majTrajectoires(etapeActuelle++);
    }

    public void majTrajectoires(int temps) throws InterruptedException{
	int i = 0;
	ArrayList<Molecule> finies = new ArrayList<Molecule>();
	for(Molecule m : nonFinies){
	    ListIterator<CaracteristiqueTemporelle> caracIterator = caracActuelle.get(m);
	    if(caracIterator.hasNext()){
		CaracteristiqueTemporelle carac = caracIterator.next();
		if (carac.temps() <= temps){
		    Path2D trajectoire = trajectoires.get(m);
		    Point2D p = creerPoint(carac);
		    if (caracIterator.previousIndex() == 0){
			trajectoire.moveTo(p.getX(), p.getY());
		    }
		    else{
			trajectoire.lineTo(p.getX(), p.getY());
		    }
		}
		else{
		    caracIterator.previous();
		}
		if(!caracIterator.hasNext()){
		    finies.add(m);
		}
	    }
	}
	nonFinies.removeAll(finies);

	for(Molecule m : finies)
	    caracActuelle.remove(m);
	repaint();
	Thread.sleep(delaiRafraichissement);

    }

    void ajouterSelectionnee(Molecule m){
	selectionnees.add(m);
    }

    void SupprimerSelectionnee(Molecule m){
	selectionnees.remove(m);
    }


    @Override
    public void paint(Graphics g){
	Graphics2D g2d = (Graphics2D)g;
	Set<Map.Entry<Molecule, Path2D>> collectionTrajectoires = trajectoires.entrySet();
	
	for(Map.Entry<Molecule, Path2D> moleculeEntry : collectionTrajectoires){
	    //Récupération des informations de dessin pour la molecule
	    Molecule molecule = moleculeEntry.getKey();
	    Path2D trajectoire = moleculeEntry.getValue();
	    Color c = molecule.getCouleur();
	    
	    //Dessin de la trajectoire de la molecule
	    g2d.setColor(c);	    
	    if(selectionnees.contains(molecule))
		g2d.setStroke(new BasicStroke(3));
	    else
		g2d.setStroke(new BasicStroke(1));
	    
	    g2d.draw(trajectoire);	    
	}
    }
}
