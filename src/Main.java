import java.io.IOException;
public class Main {

	
    public static void main (String[] args){
		
	//0 - Creer le controleur initial.
	Compositeur controleur = new Compositeur(7);
	//1 - Lancer la vue
	//2 - Lancer la fenetre de parametrage (choix chercheur/compositeur, noteRef, alpha, seuil ...)
	//3 - Creer le controleur correspondant, le tableau correspondant
	//4 - Parser le fichier analyse.trc
	// 4-1 Remplir la liste des molecules avec le alpha et le numero de la proteine
	try {
	    controleur.lireFichierAnalyse("./data/fichiersTests/analyseTestImmobile.txt");
	}
	catch (IOException e) {
	}
	// 4-2 Creer l'effet correspondant pour la molecule.
	//5 - Parser le fichier trajectoires.trc
	// 5-1 Completer les informations des proteines avec la liste des positions.
	try {	
	    controleur.lireFichierTrajectoire("./data/fichiersTests/trajectoiresTestImmobile.trc");
	    System.out.println("Lecture du 2e fichier");
	
	}
	catch (IOException e) {
	}
	controleur.analyseMolecules();
	controleur.printMolecules();
	//5b - pour le compositeur : Choisir les molecules ou lui demander de choisir. 
	//6 - Afficher le tableau des timbres
	//7 - Renseigner le timbre correspondant pour chaque molecule.
	//8 - Offrir la possibilite a l'utilisateur de jouer le son.
	//9 - Jouer le son de toutes les molecules et Reconstituer la video des positions de chaque molecules
	//10 - Relancer a l'etape 0, 2, 6 ou 8.
		
    }
}
