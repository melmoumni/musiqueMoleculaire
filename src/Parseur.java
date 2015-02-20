import java.util.ArrayList;
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

class Parseur {
    public ArrayList<Molecule> molecules;

    public Parseur(){
	molecules = new ArrayList<Molecule>();
    }
    
    void lireFichierTrajectoire(String nomDuFichier) throws IOException {
	int index = 0;
	int nombreMolecules = molecules.size();
	String patternEntier = "(\\d+)";
	String patternEspace = "(\\s+)";
	String patternReel = "(\\d*(\\.\\d+)?)";
	String patternEntierRelatif = "(-\\d)";
	String patternEspaceEtoile = "(\\s*)";
	String FinDeLigne ="$";
	Pattern pattern = Pattern.compile(patternEntier+patternEspace+patternEntier+patternEspace+patternReel+patternEspace+patternReel+patternEspace+patternEntierRelatif+patternEspace+patternReel+patternEspaceEtoile+FinDeLigne);
	try {
	    BufferedReader reader = new BufferedReader(new FileReader(nomDuFichier));
	    String currentLine;
	    int proteineEnRemplissage = (molecules.get(index)).numero();
	    int compteur = 1; // pour connaitre la ligne en cours de traitement
	    ArrayList listeEnRemplissage = new ArrayList<CaracteristiqueTemporelle>();
	    while (((currentLine = reader.readLine()) != null)&&(index<nombreMolecules)) {
		Scanner scan = new Scanner(currentLine);
		if (scan.findInLine(pattern) != null) {
		    MatchResult match = scan.match();
		    int numeroProteine = Integer.parseInt(match.group(1));

		    if ((numeroProteine != proteineEnRemplissage) && (numeroProteine > proteineEnRemplissage)){
			//on renseigne les champs de la molécule en cours : on a fini de la traiter
			(molecules.get(index)).setPositions(listeEnRemplissage); // on remplit la liste de caracs temporelles ie les positions
			(molecules.get(index)).setInstantInitial(molecules.get(index).positions.get(0).temps());//on remplit l'instant initial de la molecule avec la liste des caracs temporelles
			(molecules.get(index)).setInstantFinal(molecules.get(index).positions.get(listeEnRemplissage.size() - 1).temps());// idem mais pour l'instant final
			//on passe à la molécule suivante
			if (index < nombreMolecules) { //problème pour le dernier élément de la liste dépassement
			    index++;
			}
			// on met à jour le numéro de proteine en cours
			if (index < nombreMolecules) { ////problème pour le dernier élément de la liste dépassement
			    proteineEnRemplissage = molecules.get(index).numero();    
			}
			//et la nouvelle liste de position intermediaire
			listeEnRemplissage = new ArrayList<CaracteristiqueTemporelle>();
		    }
		    else {
			if (proteineEnRemplissage == numeroProteine) {
			    int instant = Integer.parseInt(match.group(3));
			    CaracteristiqueTemporelle caracteristique = new CaracteristiqueTemporelle(/*position en x*/Float.parseFloat(match.group(5)),
												      /*position en y*/Float.parseFloat(match.group(8)),
												      instant,
												      /*intensite*/Float.parseFloat(match.group(13)));
			    listeEnRemplissage.add((CaracteristiqueTemporelle)caracteristique);
			}
		    }
		}
		else {
		    System.out.println("Erreur de formation du fichier à la ligne " + compteur);
		}
		scan.close();
		compteur++;
	    }
	    if (index < nombreMolecules && molecules.get(index).positions==null){
		(molecules.get(index)).setPositions(listeEnRemplissage); // on remplit la liste de caracs temporelles ie les positions
		(molecules.get(index)).setInstantInitial(molecules.get(index).positions.get(0).temps()); //on remplit l'instant initial de la molecule avec la liste des caracs temporelles
		(molecules.get(index)).setInstantFinal(molecules.get(index).positions.get(listeEnRemplissage.size() - 1).temps()); // idem mais pour l'instant final
	    }
	    reader.close();
	}
	catch (FileNotFoundException exception) {
	    System.out.println ("Le fichier n'a pas été trouvé");
	}
    }

    /*
     * remplit la liste de molecule avec le fichier d'analyse (ie alpha et msd)
     */
    void lireFichierAnalyse(String nomDuFichier) throws IOException {
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
	String patternReel = "(-*\\d+(\\.\\d+)?(e(-)[0-9]+)?)";
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
	    BufferedReader reader = new BufferedReader(new FileReader(nomDuFichier));
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
		//verification eventuelle de la premiere ligne
	    }
	    compteur++;
	    scan.close();

	    // Deuxieme ligne : frametime
	    currentLine = reader.readLine();
	    if (currentLine == null){
		System.out.println("Erreur de lecture du fichier");
	    }
	    scan = new Scanner(currentLine);
	    if (scan.findInLine(frametime) != null) {
		match = scan.match();
	    }
	    compteur++;
	    scan.close();
	    // Troisieme ligne pixelsize
	    currentLine = reader.readLine();
	    if (currentLine == null){
		System.out.println("Erreur de lecture du fichier");
	    }
	    scan = new Scanner(currentLine);
	    if (scan.findInLine(pixelsize) != null) {
		match = scan.match();
	    }
	    compteur++;
	    scan.close();

	    //4e ligne minPointMSD
	    currentLine = reader.readLine();
	    if (currentLine == null){
		System.out.println("Erreur de lecture du fichier");
	    }
	    scan = new Scanner(currentLine);
	    if (scan.findInLine(minpointMSD) != null) {
		match = scan.match();
	    }
	    compteur++;
	    scan.close();

	    //5e ligne seuil
	    currentLine = reader.readLine();
	    if (currentLine == null){
		System.out.println("Erreur de lecture du fichier");
	    }
	    scan = new Scanner(currentLine);
	    if (scan.findInLine(pourcentage) != null) {
		match = scan.match();
	    }
	    compteur++;
	    scan.close();

	    //6e ligne cases du tableau
	    currentLine = reader.readLine();
	    if (currentLine == null){
		System.out.println("Erreur de lecture du fichier");
	    }
	    scan = new Scanner(currentLine);
	    if (scan.findInLine(debutTableau) != null) {
		match = scan.match();
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
	    scan.close();

	    // Lecture du tableau de nombres
	    while ((currentLine = reader.readLine()) != null) {
		scan = new Scanner(currentLine);
		if (scan.findInLine(pattern) != null) {
		    match = scan.match();
		    Matcher matcher3 = caseN.matcher(currentLine);
		    int occur2 = 0;
		    while(matcher3.find()) {
			if (occur2 == posAlpha) {
			    alpha = Float.parseFloat(matcher3.group());
			}
			else {
			    if (occur2 == caseMolecule) {
				numeroMolecule = Integer.parseInt(matcher3.group());
			    }
			    else {
				if (occur2 ==  positionMSD) {
				    valeurMSD = Float.parseFloat(matcher3.group());
				}
			    }
			}
			occur2++;
		    }
		    molecules.add(new Molecule(numeroMolecule, alpha, valeurMSD));
		}
		else {
		    System.out.println("Erreur de formation du fichier à la ligne " + compteur);
		}
		compteur++;
		scan.close();
	    }
	    reader.close();
	}
	catch (FileNotFoundException exception) {
	    System.out.println ("Le fichier n'a pas été trouvé");
	}
    }


    public void lectureTimbre(String filepath) throws IOException {
	String debutDeLigne = "^";
	String patternEntier = "(\\d+)";
	String patternEspace = "\\s+";
	String patternEspaceEtoile = "\\s*";
	String patternNomInstrument = "([A-Za-z0-9\\(\\)\\-\\+]+)";
	String FinDeLigne ="$";
	Pattern pattern = Pattern.compile(debutDeLigne+
					  patternEntier+
					  patternEspace+
					  patternNomInstrument+
					  patternEspace+
					  patternEntier+
					  patternEspace+
					  patternEntier+
					  patternEspace+
					  patternEntier+
					  patternEspaceEtoile+
					  FinDeLigne);
	try {
	    BufferedReader reader = new BufferedReader(new FileReader(filepath));
	    String currentLine;
	    int compteur = 1;
	    while ((currentLine = reader.readLine()) != null) {
		Scanner scan = new Scanner(currentLine);
		if (scan.findInLine(pattern) != null) {
		    MatchResult match = scan.match();
		    System.out.printf("Instrument %d,  %s,  min : %d, max : %d%n", Integer.parseInt(match.group(1)), match.group(2), Integer.parseInt(match.group(3)), Integer.parseInt(match.group(4)));
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
