

import java.io.IOException;

import javax.swing.JFrame;

public class ControleurFenetres {
	static FenetreParametres params;
	static JFrame fenetrePrincipale ;
	static float alpha1,alpha2,alpha3;
	static String noteRef;
	static int duree;
	static int largeur, hauteur;
	static String fichierTrajectoire, fichierMvts;
	
	static Controleur controleur;
	static Parseur p = new Parseur();
	
	public ControleurFenetres(){
		params = new FenetreParametres();
		params.setVisible(true);
	}
	
	/* Choix de l'utilisateur : 
	 * 	Lance la fenetre correspondant a l'utilisateur
	 */
	public static void choixUtilisateur(){
		System.out.println(params.getUtilisateur());
		
		if(params.getUtilisateur().compareTo("chercheur")==0){
			params.dispose(); // fermeture de la fenetre parametre
			controleur = new Chercheur();
			parser();
			fenetrePrincipale = new TableauChercheurVue(true,null);
			fenetrePrincipale.setVisible(true);
		}
		else if(params.getUtilisateur().compareTo("compositeur")==0){
			controleur = new Compositeur(params.getIntensite());
			params.dispose();
			parser();
			fenetrePrincipale = new TableauCompositeurVue(true, null);
			fenetrePrincipale.setVisible(true);
		}
	}
	
	private static void parser(){
		try {
			p.lireFichierAnalyse(fichierMvts);
		}
		catch (IOException e) {}
		try {	
			p.lireFichierTrajectoire(fichierTrajectoire);
		}
		catch (IOException e) {}
		try {	
			p.lectureTimbre("./data/listeInstruments.txt");
		}
		catch (IOException e) {}
		controleur.initMolecules(p);
	}
	
	/* Recupere les differents parametres renseignees par l'utilisateur dans la fenetre de parametre */
	public static void recupereParametre(){
		alpha1 = params.getValeurAlpha1();
		alpha2 = params.getValeurAlpha2();
		alpha3 = params.getValeurAlpha3();
		noteRef = params.getNoteRef();
		duree = params.getDuree();
		largeur = params.getLargeur();
		hauteur = params.getHauteur();
		fichierTrajectoire = params.getFilenameT();
		fichierMvts = params.getFilenameM();
	}

	
	public static void main(String[] args) {
		ControleurFenetres vue = new ControleurFenetres();
	}
}
