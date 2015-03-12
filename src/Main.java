import java.io.IOException;


public class Main {


	public static void main (String[] args){

		if (args.length == 1 && args[0].equals("test")){
			new ControleurFenetres();
			
		}
		else{

			//0 - Creer le controleur initial.
			//Controleur controleur = new Chercheur();
			//Parseur p = new Parseur();
			//1 - Lancer la vue
			//2 - Lancer la fenetre de parametrage (choix chercheur/compositeur, noteRef, alpha, seuil ...)
			//3 - Creer le controleur correspondant, le tableau correspondant
			//4 - Parser le fichier analyse.trc
			// 4-1 Remplir la liste des molecules avec le alpha et le numero de la proteine

			// 4-2 Creer l'effet correspondant pour la molecule.
			//5 - Parser le fichier trajectoires.trc
			// 5-1 Completer les informations des proteines avec la liste des positions.

			try {
				Controleur.initMolecules("./data/trajectoires.trc","./data/analyse.txt", "./data/listeInstruments.txt");
			}
			catch (IOException e) {
			}
			//controleur.initMolecules(p);
			Controleur.analyseMolecules();
			Controleur.printTrajectoires();
			Controleur.printMolecules();
			Controleur.printTimbres();


			//5b - pour le compositeur : Choisir les molecules ou lui demander de choisir. 
			//6 - Afficher le tableau des timbres
			//7 - Renseigner le timbre correspondant pour chaque molecule.
			//8 - Offrir la possibilite a l'utilisateur de jouer le son.

			//9 - Jouer le son de toutes les molecules et Reconstituer la video des positions de chaque molecules
			Controleur.remplirSequence();
			Midi.jouerSequence();
			//System.out.println("Liberation");
			//Midi.liberer();
			//10 - Relancer a l'etape 0, 2, 6 ou 8.
		}
	}
}
