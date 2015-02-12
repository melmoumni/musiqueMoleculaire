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

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import Utilitaires.Midi;


abstract class Controleur{

	public ArrayList<Molecule> molecules;
	public int duree;
	static public int noteRef; 
	private Vue vue;
	public Vector<Fenetre> fenetres;
	public int periode;
	public float[] alphaSeparation;
	public boolean isChercheur;

	public Controleur(boolean ischercheur){
		vue = new Vue();
		fenetres = new Vector<Fenetre>();
		molecules = new ArrayList<Molecule>();
		alphaSeparation = new float[3];
		setAlphaTab((float) 0.25, (float) 0.9, (float) 1.1);
		isChercheur = ischercheur;
	}

	public void printMolecules(){
		for (Molecule mol : molecules){
			mol.printMolecule();
		}
	}

	void lireFichierTrajectoire(String nomDuFichier) throws IOException {
		int index = 0;
		int nombreMolecules = this.molecules.size();
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
			int proteineEnRemplissage = (this.molecules.get(index)).numero();
			int compteur = 1; // pour connaitre la ligne en cours de traitement
			ArrayList listeEnRemplissage = new ArrayList<CaracteristiqueTemporelle>();
			while (((currentLine = reader.readLine()) != null)&&(index<nombreMolecules)) {
				Scanner scan = new Scanner(currentLine);
				if (scan.findInLine(pattern) != null) {
					MatchResult match = scan.match();
					int numeroProteine = Integer.parseInt(match.group(1));

					if ((numeroProteine != proteineEnRemplissage) && (numeroProteine > proteineEnRemplissage)){
						//on renseigne les champs de la molécule en cours : on a fini de la traiter
						(this.molecules.get(index )).setPositions(listeEnRemplissage);
						//on passe à la molécule suivante
						if (index < nombreMolecules) { //problème pour le dernier élément de la liste dépassement
							index++;
						}
						// on met à jour le numéro de proteine en cours
						if (index < nombreMolecules) { ////problème pour le dernier élément de la liste dépassement
							proteineEnRemplissage = this.molecules.get(index).numero();    
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
			if (index < nombreMolecules && this.molecules.get(index).positions==null){
				(this.molecules.get(index)).setPositions(listeEnRemplissage);
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
					this.molecules.add(new Molecule(numeroMolecule, alpha, valeurMSD));
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


	public ArrayList<Molecule> molecules(){
		return molecules;
	}

	public int periode(){
		return periode;
	}

	public void setMolecules(ArrayList<Molecule> newList){
    	molecules = newList;
    }
    
    public void setAlphaTab (float x1, float x2, float x3){
    	alphaSeparation[0] = x1;
    	alphaSeparation[1] = x2;
    	alphaSeparation[2] = x3;
    }
    
    public void setDuree(int newduree){
    	duree = newduree;
    }

    public void setNote(int newnote){
    	noteRef = newnote;
    }
    
    public void setPeriode (int p){
    	periode = p;
    }
    
    
    protected void setTempo (){
    	
    }
    
    protected void analyseMolecules(){
    	for (Molecule mol : molecules){
    		mol.analyseMolecule(alphaSeparation, isChercheur);
    	}
    }
    
    void remplirSequence(){
    	try{
    		Midi.initialiser();
    		Midi.configurerChannel(0, 56);
    		for (Molecule mol : molecules){
    			mol.remplirSequenceMolecule();
    		}
    	}catch(MidiUnavailableException e){}
    	catch(InvalidMidiDataException e){}
    }
    
}
