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
					   FinDeLigne);
	 try {
	     BufferedReader reader = new BufferedReader(new FileReader(filepath));
	     String currentLine;
	     while ((currentLine = reader.readLine()) != null) {
		 Scanner scan = new Scanner(currentLine);
		 if (scan.findInLine(pattern) != null) {
		     MatchResult match = scan.match();
		     System.out.printf("Proteine %d, Instant %d, coordX : %f, coordY : %f, info1 : %s, info2 : %f%n", Integer.parseInt(match.group(1)), Integer.parseInt(match.group(3)), Float.parseFloat(match.group(5)), Float.parseFloat(match.group(8)), match.group(11), Float.parseFloat(match.group(13)));
		 }
		 /*
		  * Cas d'erreur de formation du fichier à traiter
		  */
	     }
	 }
	 catch (FileNotFoundException exception) {
	     System.out.println ("Le fichier n'a pas été trouvé");
	 }
	 
     }
}

