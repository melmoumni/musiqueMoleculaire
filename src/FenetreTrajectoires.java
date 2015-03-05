import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Vector;
import java.io.File;
import java.io.Reader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.Collections;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import Utilitaires.Midi;

public class FenetreTrajectoires extends javax.swing.JPanel {
private ArrayList<Molecule> molecules;
private Dimension preferredSize;   
private int largeurInitiale, hauteurInitiale;


public static void main(String[] args) { 
	final Controleur controleur = new Chercheur();
	Parseur p = new Parseur();
	try {
		p.lireFichierAnalyse("./data/analyse.txt");
	}
	catch (IOException e) {
	}
	try {	
		System.out.println("Lecture du 2e fichier");
		p.lireFichierTrajectoire("./data/trajectoires.trc");
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
			    JFrame jf = new JFrame("test");
			    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    jf.setSize(400, 400);
			    jf.add(new JScrollPane(new FenetreTrajectoires(controleur.molecules(),1000,1000)));
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
public FenetreTrajectoires(ArrayList<Molecule> mols, int largeurVideo, int hauteurVideo) {
	preferredSize= new Dimension(largeurVideo,hauteurVideo); 
	largeurInitiale=largeurVideo;
	hauteurInitiale=hauteurVideo;
	setMols(mols);
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



public void paint(Graphics g) {
    super.paint(g);
    ArrayList<Molecule> molecules= this.getMols();
	  Graphics2D g2 = (Graphics2D) g;
	  for (Molecule mol : molecules){
	  if(mol.positions().size()>50){
			g2.setColor(mol.getCouleur());
		   
		   
			ListIterator <CaracteristiqueTemporelle> it = mol.positions().listIterator();
	   		CaracteristiqueTemporelle tmp = new CaracteristiqueTemporelle();
	   		 if(it.hasNext())	{
		 		 tmp=it.next();
		 		 CaracteristiqueTemporelle tmp2 = new CaracteristiqueTemporelle();
					while (it.hasNext()) {
						 	float w = (float)getWidth()/(float)getLargeurInit();
						    float h = (float)getHeight()/(float)getHauteurInit();
							tmp2=it.next();
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
