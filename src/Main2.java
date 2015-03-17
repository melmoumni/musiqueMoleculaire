import javax.swing.JFrame;
import java.lang.InterruptedException;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;

public class Main2{
    public static void main(String[] args) throws InterruptedException{
	FenetreTestPlayer fp = new FenetreTestPlayer();
	ArrayList<Molecule> molecules = new ArrayList<Molecule>();
	
	Parseur p = new Parseur();
	try {
	    p.lireFichierAnalyse("./data/analyse.txt", molecules);
	}
	catch (IOException e) {
	}
	try {	
	    System.out.println("Lecture du 2e fichier");
	    p.lireFichierTrajectoire("./data/trajectoires.trc", molecules);
	}
	catch (IOException e) {
	}

	/* AVEC THREAD */
	/*AffichageTrajectoiresThread a = new AffichageTrajectoiresThread(200, 85, 1, molecules);

	fp.setVisible(true);

	fp.setAffichageTrajectoires(a.getAffichage());
	fp.setBackground(Color.BLACK);

	fp.revalidate();
	a.run();
	a.getAffichage().dessinerTrajectoiresCompletes();*/

	/* SANS THREAD */
	AffichageTrajectoires a = new AffichageTrajectoires(200, 85, 1, molecules);
	
	fp.setVisible(true);

	fp.setAffichageTrajectoires(a);
	fp.revalidate();
	
	while(!a.aFini()){
	    a.miseAJour();
	}
    }
}
