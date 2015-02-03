//package exemples;

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


class Lecture {
    
    Lecture(){
    }
    
    public void testLecture(String filepath){
	try
	    {
		File f = new File (filepath);
		FileReader fr = new FileReader (f);
		BufferedReader br = new BufferedReader (fr);
		Scanner scan = new Scanner(br);
		try
		    {
			String line = br.readLine();
 
			while (line != null)
			    {
				System.out.println (line);
				line = br.readLine();
			    }
 			br.close();
			fr.close();
		    }
		catch (IOException exception)
		    {
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		    }
	    }
	catch (FileNotFoundException exception)
	    {
		System.out.println ("Le fichier n'a pas été trouvé");
	    }
    }

    public void testLecture2(String filepath) throws IOException{
	try
	    {
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		String currentLine;
		while ((currentLine = reader.readLine()) != null) {
		    String[] tokens = currentLine.split("	 ");
  // Traiter les champ de la ligne stockés dans tokens
		    System.out.println("Lu : " + "Protéine : " + tokens[0] + " instant " + tokens[1] + " Position X : " +  tokens[2] + " Position Y : " + tokens[3]);
		}
	    }
	
	catch (FileNotFoundException exception)
	    {
		System.out.println ("Le fichier n'a pas été trouvé");
	    }
 }
    



    public void testLecture3(String filepath) throws IOException{
	try
	    {
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		String currentLine;
		while ((currentLine = reader.readLine()) != null) {
		    Scanner scan = new Scanner(currentLine);
		    scan.useLocale(Locale.US);
		    int numero = scan.nextInt();
		    int instant = scan.nextInt();
		    double posx = scan.nextDouble();
		    double posy = scan.nextDouble();
		    int info = scan.nextInt();
		    double info2 = scan.nextDouble();
		    System.out.println("Lu : " + "Protéine : " + numero + " instant " + instant  + " Position X : " +  posx + " Position Y : " + posy + " info " + info + " info2 : " + info2);
		}
	    }
	
	catch (FileNotFoundException exception)
	    {
		System.out.println ("Le fichier n'a pas été trouvé");
	    }
    }

 
    
     public void testLecture4(String filepath) throws IOException {
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
	     BufferedReader reader = new BufferedReader(new FileReader(filepath));
	     String currentLine;
	     int compteur = 1;
	     while ((currentLine = reader.readLine()) != null) {
		 Scanner scan = new Scanner(currentLine);
		 if (scan.findInLine(pattern) != null) {
		     MatchResult match = scan.match();
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

    public void testLecture5(String filepath) throws IOException {
	int posAlpha=0;
	int numeroMolecule = 0;
	
	/*
	 * expressions régulières qui décrivent le fichier
	 */
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
	//Pattern titre = Pattern.compile(); pour vérifier la première ligne
	
	Pattern frametime = Pattern.compile(debutDeLigne+
					    patternMot+
					    patternPonctuation+
					    patternEspace+
					    patternReel+
					    patternEspace+
					    "s"+
					    patternEspaceEtoile+
					    FinDeLigne);
	
	Pattern pixelsize = Pattern.compile(//"pixelsize:"+
					    debutDeLigne+
					    patternMot+
					    patternPonctuation+
					    patternEspace+
					    patternReel+
					    patternEspace+
					    patternMot+
					    patternEspaceEtoile+
					    FinDeLigne);
	
	Pattern minpointMSD = Pattern.compile(//"minpointMSD:"+
					      debutDeLigne+
					      patternMot+
					      patternPonctuation+
					      patternEspace+
					      patternEntier+
					      patternEspaceEtoile+
					      FinDeLigne);
	
	Pattern pourcentage = Pattern.compile(//"pourcentage_trajectory:"+
					      debutDeLigne+
					      patternMot+
					      patternPonctuation+
					      patternEspace+
					      patternEntier+
					      patternEspaceEtoile+
					      FinDeLigne);
	
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
	    //	    System.out.println(currentLine);
	    if (currentLine == null){
		//gérer l'erreur
		System.out.println("Erreur de lecture du fichier");
	    }
	    scan = new Scanner(currentLine);
	    if (currentLine != null) {
		System.out.printf("Ligne %d ignorée\n", compteur);
	    }
	    compteur++;

	    // Deuxieme ligne : frametime
	    currentLine = reader.readLine();
	    //	    System.out.println(currentLine);
	    if (currentLine == null){
		//gérer l'erreur
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
		//gérer l'erreur
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
		//gérer l'erreur
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
		//gérer l'erreur
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
		//scan = new Scanner(currentLine);
		Matcher matcher2 = caseT.matcher(currentLine);
		int occur = 0;
		while(matcher2.find()) {
		    //  System.out.println(matcher2.group());
		    if (matcher2.group().equals("alpha")) {
			posAlpha = occur;
			}
		    else {
			if (matcher2.group().equals("number")){
			    numeroMolecule = occur;
			}
		    }
		    occur ++;
		}
		//System.out.println(occur);;
	    }
	    compteur++;

	    // Lecture du tableau de nombres
	    while ((currentLine = reader.readLine()) != null) {
		scan = new Scanner(currentLine);
		if (scan.findInLine(pattern) != null) {
		    match = scan.match();
		    //System.out.printf("%s\n", match.group(0));
		    Matcher matcher3 = caseN.matcher(currentLine);
		    int occur2 = 0;
		    while(matcher3.find()) {
			//  System.out.println(matcher2.group());
			if (occur2 == posAlpha) {
			    System.out.printf("Ligne %d alpha = %f\n", compteur, Float.parseFloat(matcher3.group()));
			}
			else {
			    if (occur2 == numeroMolecule) {
				System.out.printf("Ligne %d molecule numéro %d\n", compteur, Integer.parseInt(matcher3.group()));
			    }
			}
			occur2++;
		    }
		    //System.out.println(occur2);
		}
		else {
		    System.out.println("Erreur de formation du fichier à la ligne " + compteur);
		}
		/*
		 * Cas d'erreur de formation du fichier à traiter
		 */
		compteur++;
	    }
	}
	catch (FileNotFoundException exception) {
	    System.out.println ("Le fichier n'a pas été trouvé");
	}
	 
    }
}

