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
     * remplit la liste de molecule avec le fichier trajectoire(ie position, instants,...)
     */
    void lireFichierTrajectoire(String nomDuFichier) {
	Controleur controleur = new Controleur();
	int index = 0;
	
	String patternEntier = "(\\d+)";
	String patternEspace = "(\\s+)";
	String patternReel = "(\\d+(\\.\\d+)?)";
	String patternEntierRelatif = "(-\\d)";
	String patternEspaceEtoile = "(\\s*)";
	String FinDeLigne ="$";
	Pattern pattern = Pattern.compile(patternEntier+patternEspace+patternEntier+patternEspace+patternReel+patternEspace+patternReel+patternEspace+patternEntierRelatif+patternEspace+patternReel+patternEspaceEtoile+FinDeLigne);
	try {
	    BufferedReader reader = new BufferedReader(new FileReader(nomDuFichier));
	    String currentLine;
	    int proteineEnRemplissage = (controleur.molecules.get(index)).numero;
	    int compteur = 1; // pour connaitre la ligne en cours de traitement
	    LinkedList listeEnRemplissage = new LinkedList<CaracteristiqueTemporelle>();
	    while ((currentLine = reader.readLine()) != null) {
		Scanner scan = new Scanner(currentLine);
		if (scan.findInLine(pattern) != null) {
		    MatchResult match = scan.match();
		    int numeroProteine = Integer.parseInt(match.group(1));
		    
		    if (numeroProteine != proteineEnRemplissage) {
			//on renseigne les champs de la molécule en cours : on a fini de la traiter
			(controleur.molecules.get(index)).setPositions(listeEnRemplissage);
			//on passe à la molécule suivante
			index++;
			// on met à jour le numéro de proteine en cours
			proteineEnRemplissage = controleur.molecules.get(index).numero;
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
    
    /*
     * remplit la liste de molecule avec le fichier d'analyse (ie alpha et msd)
     */
    void lireFichierAnalyse(String nomDuFichier) {
	int posAlpha=0;
	int caseMolecule = 0;
	int positionMSD = 0;
	float alpha = 0;
	float valeurMSD = 0;
	int numeroMolecule = 0;
	
	//expressions régulières qui décrivent les éléments du fichier
	String debutDeLigne = "^";
	String patternEntier = "(\\d+)";
	String patternMot = "(\\w+)";
	String patternPonctuation ="\\W";
	String patternEspace = "\\s+";
	String patternReel = "(-*\\d+(\\.\\d+)?(e(-)[0-9]*)?)";
	String patternEspaceEtoile = "\\s*";
	String patternLigneTableau = "([a-zA-Z0-9\\-_/]+(\\((.*?)\\))*\\s*)*";
	String patternCaseTableau = "([a-zA-Z0-9\\-_/]+(\\((.*?)\\))*)";
	String patternLigneNumerique = "("+patternReel+"\\s*)*";
	String FinDeLigne ="$";
	
	//pattern qui décrivent une ligne ou  une "case" du fichier
	//Pattern titre = Pattern.compile(); pour vérifier la première ligne
	Pattern frametime = Pattern.compile(debutDeLigne+patternMot+patternPonctuation+patternEspace+patternReel+patternEspace+"s"+patternEspaceEtoile+FinDeLigne);
	Pattern pixelsize = Pattern.compile(debutDeLigne+patternMot+patternPonctuation+patternEspace+patternReel+patternEspace+patternMot+patternEspaceEtoile+FinDeLigne);
	Pattern minpointMSD = Pattern.compile(debutDeLigne+patternMot+patternPonctuation+patternEspace+patternEntier+patternEspaceEtoile+FinDeLigne);
	Pattern pourcentage = Pattern.compile(debutDeLigne+patternMot+patternPonctuation+patternEspace+patternEntier+patternEspaceEtoile+FinDeLigne);
	Pattern debutTableau = Pattern.compile(debutDeLigne+patternLigneTableau+FinDeLigne);
	Pattern caseT = Pattern.compile(patternCaseTableau);
	Pattern caseN = Pattern.compile(patternReel);
	Pattern pattern = Pattern.compile(debutDeLigne+patternLigneNumerique+FinDeLigne);
			        
	try {
	    BufferedReader reader = new BufferedReader(new FileReader(filepath));
	    String currentLine;
	    MatchResult match;
	    Scanner scan;
	    int compteur = 1;
	    
	    // Premiere ligne
	    currentLine = reader.readLine();
	    if (currentLine == null){
		System.out.println("Erreur de lecture du fichier");
	    }
	    scan = new Scanner(currentLine);
	    if (currentLine != null) {
		System.out.printf("Ligne %d ignorée\n", compteur);
	    }
	    compteur++;

	    // Deuxieme ligne : frametime
	    currentLine = reader.readLine();
	    if (currentLine == null){
		System.out.println("Erreur de lecture du fichier");
	    }
	    scan = new Scanner(currentLine);
	    if (scan.findInLine(frametime) != null) {
		match = scan.match();
		System.out.printf("Ligne %d : %s %s\n", compteur, match.group(1), match.group(2));
	    }
	    compteur++;

	    // Troisieme ligne pixelsize
	    currentLine = reader.readLine();
	    if (currentLine == null){
		System.out.println("Erreur de lecture du fichier");
	    }
	    scan = new Scanner(currentLine);
	    if (scan.findInLine(pixelsize) != null) {
		match = scan.match();
		System.out.printf("Ligne %d : %s %s\n", compteur, match.group(1), match.group(2));
	    }
	    compteur++;
	    
	    //4e ligne minPointMSD
	    currentLine = reader.readLine();
	    if (currentLine == null){
		System.out.println("Erreur de lecture du fichier");
	    }
	    scan = new Scanner(currentLine);
	    if (scan.findInLine(minpointMSD) != null) {
		match = scan.match();
		System.out.printf("Ligne %d : %s %s\n", compteur, match.group(1), match.group(2));
	    }
	    compteur++;

	    //5e ligne seuil
	    currentLine = reader.readLine();
	    //System.out.println(currentLine);
	    if (currentLine == null){
		System.out.println("Erreur de lecture du fichier");
	    }
	    scan = new Scanner(currentLine);
	    if (scan.findInLine(pourcentage) != null) {
		match = scan.match();
		System.out.printf("Ligne %d : %s %s\n", compteur, match.group(1), match.group(2));
	    }
	    compteur++;

	    //6e ligne cases du tableau
	    currentLine = reader.readLine();
	    //System.out.println(currentLine);
	    if (currentLine == null){
		//gérer l'erreur
		System.out.println("Erreur de lecture du fichier");
	    }
	    scan = new Scanner(currentLine);
	    if (scan.findInLine(debutTableau) != null) {
		match = scan.match();
		System.out.printf("Ligne %d : %s\n", compteur, match.group(0));
		Matcher matcher2 = caseT.matcher(currentLine);
		int occur = 0;
		while(matcher2.find()) {
		    if (matcher2.group().equals("alpha")) {
			posAlpha = occur;
		    }
		    else {
			if (matcher2.group().equals("number")) {
			    caseMolecule = occur;
			}
			else {
			    if (matcher2.group().equals("intersect_MSD(um^2)")) {
				positionMSD = occur;
			    }
			}
		    }
		    occur ++;
		}
	    }
	    compteur++;

	    // Lecture du tableau de nombres
	    while ((currentLine = reader.readLine()) != null) {
		scan = new Scanner(currentLine);
		if (scan.findInLine(pattern) != null) {
		    match = scan.match();
		    Matcher matcher3 = caseN.matcher(currentLine);
		    int occur2 = 0;
		    while(matcher3.find()) {
			if (occur2 == posAlpha) {
			    System.out.printf("Ligne %d alpha = %f\n", compteur, Float.parseFloat(matcher3.group()));
			    alpha = Float.parseFloat(matcher3.group());
			}
			else {
			    if (occur2 == caseMolecule) {
				System.out.printf("Ligne %d molecule numéro %d\n", compteur, Integer.parseInt(matcher3.group()));
				numeroMolecule = Integer.parseInt(matcher3.group());
			    }
			    else {
				if (occur2 ==  positionMSD) {
				    System.out.printf("Ligne %d MSD %d\n", compteur, Float.parseFloat(matcher3.group()));
				    valeurMSD = Float.parseFloat(matcher3.group());
				}
			    }
			occur2++;
		    }
		    controleur.molecules.addLast(new Molecule(numeroMolecule, posAlpha, valeurMSD));
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
