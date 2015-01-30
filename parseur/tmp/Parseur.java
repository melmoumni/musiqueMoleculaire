package tmp;

import java.io.File;
import java.io.Reader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Locale;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

class Parseur {
    /*
     * remplit la liste de molécule
     */
    // void lireFichier(String nomDuFichier) {
    // 	try
    // 	    {
    // 		File f = new File (nomDuFichier);
    // 		FileReader fr = new FileReader (f);
    // 		BufferedReader br = new BufferedReader (fr);
    // 		String line;
    // 		try {
    // 		    Molecule cellule = new Molecule(1);
    // 		    while ((line = br.readLine()) != null) {
    // 			Scanner scan = new Scanner(line);
    // 			scan.useLocale(Locale.US); // nécessaire pour lire les floats avec la classe scanner
    // 			int tmp = scan.nextInt();
    // 			System.out.println(tmp); 
    // 			if ( cellule.numero == tmp ) {
    // 			    System.out.println("molecule "); 
    // 			    int instant = scan.nextInt();
    // 			    float posX = scan.nextFloat();
    // 			    float posY = scan.nextFloat();
    // 			    int info = scan.nextInt();
    // 			    double info2 = scan.nextDouble();
    // 			    if (instant == 1) {
    // 				CaracteristiqueTemporelle caracteristique = new CaracteristiqueTemporelle(posX, posY, 0, 0);
    // 				cellule.positions.addLast(caracteristique);
    // 				System.out.println("molecule " + cellule.numero + " instant " + instant + " coord (" + posX + ", " + posY + ") info " + info);
    // 			    }
    // 			    else {
    // 			    //faire marcher le truc du pas et sqrt
    // 				CaracteristiqueTemporelle caracteristique = new CaracteristiqueTemporelle(posX, posY, 0, 0);
    // 				cellule.positions.addLast(caracteristique);
    // 				System.out.println("molecule " + cellule.numero + " instant " + instant + " coord (" + posX + ", " + posY + ") info " + info);
    // 			    }
    // 			}
    // 			else {
    // 			    //stock.addLast(cellule);
    // 			    cellule = new Molecule(tmp);
    // 			    int instant = scan.nextInt();
    // 			    float posX = scan.nextFloat();
    // 			    float posY = scan.nextFloat();
    // 			    int info = scan.nextInt();
    // 			    double info2 = scan.nextDouble();
    // 			    if (instant == 0) {
    // 				CaracteristiqueTemporelle caracteristique = new CaracteristiqueTemporelle(posX, posY, 0, 0);
    // 				cellule.positions.addLast(caracteristique);
    // 				System.out.println("molecule " + cellule.numero + " instant " + instant + " coord (" + posX + ", " + posY + ") info " + info);
    // 			    }
    // 			    else {
    // 				CaracteristiqueTemporelle caracteristique = new CaracteristiqueTemporelle(posX, posY, 0, 0);
    // 				cellule.positions.addLast(caracteristique);
    // 				System.out.println("molecule " + cellule.numero + " instant " + instant + " coord (" + posX + ", " + posY + ") info " + info);
    // 			    }
    // 			}
			
    // 		    }
    // 		    // stock.addLast(cellule);
    // 		}
    // 		catch (IOException exception) {
    // 		    System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
    // 		}
    // 		try {
    // 		    br.close();
    // 		    fr.close();
    // 		}
    // 		catch (IOException exception) {
    // 		    System.out.println ("Erreur de fermeture du fichier : " + exception.getMessage());
    // 		}
    // 	    }
    // 	catch (FileNotFoundException exception) {
    // 	    System.out.println ("Le fichier n'a pas été trouvé");
    // 	}
    // }	
    
    void lireFichierPosition(String nomDuFichier){
	Controleur controleur = new Controleur();
	String patternEntier = "(\\d+)";
	String patternEspace = "(\\s+)";
	String patternReel = "(\\d+(\\.\\d+)?)";
	String patternEntierRelatif = "(-\\d)";
	String patternEspaceEtoile = "(\\s*)";
	String FinDeLigne ="$";
	Pattern pattern = Pattern.compile(patternEntier+
					  patternEspace+
					  patternEntier+
					  patternEspace+
					  patternReel+
					  patternEspace+
					  patternReel+
					  patternEspace+
					  patternEntierRelatif+
					  patternEspace+
					  patternReel+
					  patternEspaceEtoile+
					  FinDeLigne);
	try {
	    BufferedReader reader = new BufferedReader(new FileReader(nomDuFichier));
	    String currentLine;
	    int compteur = 1; // pour connaitre la ligne en cours de traitement
	    int proteineEnRemplissage = -1;
	    LinkedList listeEnRemplissage = new LinkedList<CaracteristiqueTemporelle>();
	    while ((currentLine = reader.readLine()) != null) {
		Scanner scan = new Scanner(currentLine);
		if (scan.findInLine(pattern) != null) {
		    MatchResult match = scan.match();
		    int numeroProteine = Integer.parseInt(match.group(1));
		    
		    if ( (numeroProteine != proteineEnRemplissage) && (proteineEnRemplissage != -1)){
			//ici on crée le nouvelle molecule
			controleur.molecules.addLast(new Molecule(proteineEnRemplissage, listeEnRemplissage));
			// on met à jour le numéro de proteine en cours
			proteineEnRemplissage = numeroProteine;
			//et la nouvelle liste de position intermediaire
			listeEnRemplissage = new LinkedList<CaracteristiqueTemporelle>();
		    }
		    int instant = Integer.parseInt(match.group(3));
		    CaracteristiqueTemporelle caracteristique = new CaracteristiqueTemporelle(/*position en x*/Float.parseFloat(match.group(5)),
											      /*position en y*/Float.parseFloat(match.group(8)),
											      instant,
											      /*intensite*/Float.parseFloat(match.group(13)));
		    listeEnRemplissage.addLast(caracteristique);
		    System.out.printf("Proteine %d, Instant %d, coordX : %f, coordY : %f, info1 : %s, info2 : %f%n", Integer.parseInt(match.group(1)), Integer.parseInt(match.group(3)), Float.parseFloat(match.group(5)), Float.parseFloat(match.group(8)), match.group(11), Float.parseFloat(match.group(13)));
		}
		else {
		    System.out.println("Erreur de formation du fichier à la ligne " + compteur);
		}
		
		compteur++;
	    }
	}
	catch (FileNotFoundException exception) {
	    System.out.println ("Le fichier n'a pas été trouvé");
	}
    }
}
