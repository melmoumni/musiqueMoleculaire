

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
			controleur = new Chercheur();
			afficherParam(); 
			params.dispose(); // fermeture de la fenetre parametre
			parser();
			fenetrePrincipale = new TableauChercheurVue(true,null);
			fenetrePrincipale.setVisible(true);
		}
		else if(params.getUtilisateur().compareTo("compositeur")==0){
			controleur = new Compositeur(params.getIntensite());
			afficherParam(); 
			params.dispose(); // fermeture de la fenetre parametre
			parser();
			fenetrePrincipale = new TableauCompositeurVue(true, null);
			fenetrePrincipale.setVisible(true);
		}
	}
	
	private static void afficherParam() {
		System.out.println(alpha1);
		System.out.println(alpha2);
		System.out.println(alpha3);
		System.out.println(noteRef);
		System.out.println(duree);
		System.out.println(largeur);
		System.out.println(hauteur);
		System.out.println(fichierTrajectoire);
		System.out.println(fichierMvts);
		
	}

	private static void parser(){
		try {
			Controleur.initMolecules(fichierTrajectoire, fichierMvts, "./data/listeInstruments.txt");
		}
		catch (IOException e) {
		}
		Controleur.analyseMolecules();
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
