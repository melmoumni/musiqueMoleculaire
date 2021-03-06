import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ControleurFenetres {
	static FenetreParametres params;
	static JFrame fenetrePrincipale ;
	static float alpha1,alpha2,alpha3;
	static String noteRef;
	static int tempo;
	static int largeur, hauteur;
	static String fichierTrajectoire, fichierMvts;
	static String utilisateur;
	static int seuilIntensite;
	
	static String fichierBanqueMidi;
	static String fichierParametres;
	
	static Controleur controleur;
	
	public ControleurFenetres(){
		params = new FenetreParametres();
		params.setVisible(true);
	}
	static public int getHauteurVideo(){
		return hauteur;
	}
	static public int getLargeurVideo(){
		return largeur;
	}
	/* Choix de l'utilisateur : 
	 * 	Lance la fenetre correspondant a l'utilisateur
	 */
	public static void choixUtilisateur(){
		System.out.println(params.getUtilisateur());
		
		if(utilisateur.compareTo("chercheur")==0){
			controleur = new Chercheur();
			afficherParam(); 
			params.dispose(); // fermeture de la fenetre parametre
			initialiserMolecules();
			fenetrePrincipale = new TableauChercheurVue(true,null);
			fenetrePrincipale.setVisible(true);
		}
		else if(utilisateur.compareTo("compositeur")==0){
			controleur = new Compositeur(seuilIntensite);
			//afficherParam(); 
			params.dispose(); // fermeture de la fenetre parametre
			initialiserMolecules();
			fenetrePrincipale = new TableauCompositeurVue(true, null);
			fenetrePrincipale.setVisible(true);
		}
	}
	
	private static void afficherParam() {
		System.out.println(alpha1);
		System.out.println(alpha2);
		System.out.println(alpha3);
		System.out.println(noteRef);
		System.out.println(tempo);
		System.out.println(largeur);
		System.out.println(hauteur);
		System.out.println(fichierTrajectoire);
		System.out.println(fichierMvts);
		
	}

	private static void initialiserMolecules(){
		try {
			Controleur.initMolecules(fichierTrajectoire, fichierMvts, "./data/listeInstruments.txt");
		}
		catch (IOException e) {
		}
		if (params.isAutoSize()){
			Dimension dim = Controleur.maxDimension();
			System.out.println(dim);
			largeur = (int) dim.getWidth() +10 ;
			hauteur = (int) dim.getHeight() +10;			
		}
		float alphas[] = {alpha1, alpha2, alpha3};
		Controleur.dureeNoire = 60000/tempo;
		Controleur.alphaSeparation = alphas;
		Controleur.analyseMolecules();
	}
	
	/* Recupere les differents parametres renseignees par l'utilisateur dans la fenetre de parametre 
	 * Retourne vrai si les parametres ont tous ete rempli
	 * 			faux sinon
	 */
	public static boolean recupereParametre(){
		alpha1 = params.getValeurAlpha1();
		alpha2 = params.getValeurAlpha2();
		alpha3 = params.getValeurAlpha3();
		noteRef = params.getNoteRef();
		tempo = params.getTempo();
		largeur = params.getLargeur();
		hauteur = params.getHauteur();
		fichierTrajectoire = params.getFilenameT();
		fichierMvts = params.getFilenameM();
		utilisateur = params.getUtilisateur();
		seuilIntensite = params.getIntensite();
		fichierBanqueMidi = params.getBanqueMidi();
		fichierParametres = params.getParametres();
		
		if((tempo>0) && (((largeur>0) && (hauteur>0)) || (params.isAutoSize())) && !(fichierTrajectoire.isEmpty()) && !(fichierMvts.isEmpty())
				&& !(utilisateur.isEmpty()))
			return true;
		else
			return false;
	}
	
	public static void popupMessage()
	{
		JOptionPane.showMessageDialog(params,
    		    "Les parametres suivants doivent etre renseignees :\n"
    		    + " - Chemins des fichiers de trajectoires et d'analyse\n"
    		    + " - Taille de l'image (Largeur et Hauteur) ou ajustement automatique\n"
    		    + " - Type d'utilisateur : Chercheur ou Compositeur  \n");
	}

	
	public static void main(String[] args) {
		new ControleurFenetres();
	}

}
