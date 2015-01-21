package tmp;

import java.io.File;
import java.io.Reader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Scanner;
import java.util.Locale;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.io.IOException;


class Parseur {
    /*
     * remplit la liste de molécule
     */
    void lireFichier(String nomDuFichier) {
	try
	    {
		File f = new File (nomDuFichier);
		FileReader fr = new FileReader (f);
		BufferedReader br = new BufferedReader (fr);
		String line;
		try {
		    Molecule cellule = new Molecule(1);
		    while ((line = br.readLine()) != null) {
			Scanner scan = new Scanner(line);
			scan.useLocale(Locale.US); // nécessaire pour lire les floats avec la classe scanner
			int tmp = scan.nextInt();
			System.out.println(tmp); 
			if ( cellule.numero == tmp ) {
			    System.out.println("molecule "); 
			    int instant = scan.nextInt();
			    float posX = scan.nextFloat();
			    float posY = scan.nextFloat();
			    int info = scan.nextInt();
			    double info2 = scan.nextDouble();
			    if (instant == 1) {
				CaracteristiqueTemporelle caracteristique = new CaracteristiqueTemporelle(posX, posY, 0, 0);
				cellule.positions.addLast(caracteristique);
				System.out.println("molecule " + cellule.numero + " instant " + instant + " coord (" + posX + ", " + posY + ") info " + info);
			    }
			    else {
				CaracteristiqueTemporelle caracteristique = new CaracteristiqueTemporelle(posX, posY, 0, 0);
				cellule.positions.addLast(caracteristique);
				System.out.println("molecule " + cellule.numero + " instant " + instant + " coord (" + posX + ", " + posY + ") info " + info);
			    }
			}
			else {
			    //stock.addLast(cellule);
			    cellule = new Molecule(tmp);
			    int instant = scan.nextInt();
			    float posX = scan.nextFloat();
			    float posY = scan.nextFloat();
			    int info = scan.nextInt();
			    double info2 = scan.nextDouble();
			    if (instant == 0) {
				CaracteristiqueTemporelle caracteristique = new CaracteristiqueTemporelle(posX, posY, 0, 0);
				cellule.positions.addLast(caracteristique);
				System.out.println("molecule " + cellule.numero + " instant " + instant + " coord (" + posX + ", " + posY + ") info " + info);
			    }
			    else {
				CaracteristiqueTemporelle caracteristique = new CaracteristiqueTemporelle(posX, posY, 0, 0);
				cellule.positions.addLast(caracteristique);
				System.out.println("molecule " + cellule.numero + " instant " + instant + " coord (" + posX + ", " + posY + ") info " + info);
			    }
			}
			
		    }
		    // stock.addLast(cellule);
		}
		catch (IOException exception) {
		    System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
		try {
		    br.close();
		    fr.close();
		}
		catch (IOException exception) {
		    System.out.println ("Erreur de fermeture du fichier : " + exception.getMessage());
		}
	    }
	catch (FileNotFoundException exception) {
	    System.out.println ("Le fichier n'a pas été trouvé");
	}
    }	
    
    
}
