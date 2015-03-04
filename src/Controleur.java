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
import java.util.Collections;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import Utilitaires.Midi;


abstract class Controleur{

	public Parseur parseur;
	public ArrayList<Molecule> molecules;
	public ArrayList<Triplet> intervalles;
	public int duree;
	static public int noteRef; 
	private Vue vue;
	public Vector<Fenetre> fenetres;
	public int periode;
	public float[] alphaSeparation;
	public boolean isChercheur;
	static public Timbre[] tableauTimbre;

	public Controleur(boolean ischercheur){
		vue = new Vue();
		parseur = new Parseur();
		fenetres = new Vector<Fenetre>();
		molecules = new ArrayList<Molecule>();
		intervalles = new ArrayList<Triplet>();
		alphaSeparation = new float[3];
		setAlphaTab((float) 0.25, (float) 0.9, (float) 1.1);
		isChercheur = ischercheur;
	}

	public void printMolecules(){
		for (Molecule mol : molecules){
			mol.printMolecule();
		}
	}

	public void printIntervalles(){
		for (Triplet triplet : intervalles){
			triplet.printTriplet();
		}
	}

	public void printTimbres(){
		for (Timbre timbre : tableauTimbre){
			timbre.printTimbre();
		}
	}

	public void printTrajectoires(){
		FenetreTrajectoires f=  new FenetreTrajectoires(this.molecules());
	}

	public void initMolecules(Parseur parseur){
		molecules = parseur.molecules;
		tableauTimbre = parseur.tableauTimbre;
	}
	public void setMolecules(Parseur parseur){
		molecules = parseur.molecules;
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
			mol.analyseDistance();
		}
		Collections.sort(molecules);
	}

	void remplirIntervalles(){
		int index = 0;
		int i = 0;
		Triplet enRemplissage = new Triplet(molecules.get(0).instantInitial(), molecules.get(0).instantFinal(), 0);
		//Triplet tmp = new Triplet(molecules.get(0).instantInitial(), molecules.get(0).instantFinal(), 1);
		intervalles.add(enRemplissage);
		for (Molecule mol : molecules){
			i = 0; // on se remet au debut de liste pour la parcourir
			while ( (i < index) && (intervalles.get(i).instantInitial() < mol.instantInitial()) ) { //on cherche le triplet ayant un ti < mol.ti 
				i++;
			}

			// Cas 3-2 : instant initial = et tf >
			if ((intervalles.get(i).instantInitial() == mol.instantInitial()) &&
					(intervalles.get(i).instantFinal() < mol.instantFinal()) ) {

				//quasi-identique au cas 2
				enRemplissage = intervalles.get(i);
				int k = i;
				while ( (k <= index) && (intervalles.get(k).instantFinal() < mol.instantFinal()) ) {
					//modifs sur la liste jusqu'à  l'instant où mol.tf < intervalles.i.ti
					if (i == k) {
						intervalles.get(k).incrementeNombreMolecule();
						k++;
					}
					else {
						intervalles.get(k).incrementeNombreMolecule();
						enRemplissage = intervalles.get(k);
						intervalles.get(k).setInstantInitial(intervalles.get(k-1).instantFinal());
						intervalles.get(k).setInstantFinal(enRemplissage.instantFinal());
						k++;
					}
				}
				if (k == 0){
					intervalles.get(k).incrementeNombreMolecule();
					System.out.println("toto");
				}
				if (k > index) {
					intervalles.add(new Triplet(intervalles.get(k-1).instantFinal(), mol.instantFinal(), 1));
					index++;
				}
				else {

					intervalles.add(k,new Triplet(intervalles.get(k).instantFinal(), mol.instantFinal(), intervalles.get(k).nombreMolecule()) );
					index++;
				}
			}
			// Cas 3 ti et tf ==
			else if ((intervalles.get(i).instantInitial() == mol.instantInitial()) &&
					(intervalles.get(i).instantFinal() == mol.instantFinal()) ) {
				intervalles.get(index).incrementeNombreMolecule();
			}


			// Cas 1 : dernier triplet de la liste englobe la molecule
			else if (intervalles.get(i).instantFinal() > mol.instantFinal() ) {
				enRemplissage = intervalles.get(i);
				enRemplissage.incrementeNombreMolecule();
				intervalles.get(i).setInstantFinal(mol.instantInitial());
				intervalles.add(i+1,new Triplet(mol.instantInitial(), mol.instantFinal(), enRemplissage.nombreMolecule()));
				index++;
				enRemplissage.setInstantInitial(mol.instantFinal());
				intervalles.add(i+2, enRemplissage);
				index++;
				i+=2;
			}
			// Cas 2 : dernier triplet de la liste couvre le debut du triplet de la molecule
			else if (intervalles.get(i).instantFinal() < mol.instantFinal() ) {
				enRemplissage = intervalles.get(i);
				int k = i;

				while ( (k <= index) && (intervalles.get(k).instantFinal() < mol.instantFinal()) ) {
					//modifs sur la liste jusqu'à  l'instant où mol.tf < intervalles.i.ti
					if (i == k) {
						enRemplissage.setInstantInitial(mol.instantInitial());
						intervalles.get(k).setInstantFinal(mol.instantInitial());
						intervalles.add(k+1, enRemplissage);
						k+=2;
					}
					else {
						intervalles.get(k).incrementeNombreMolecule();
						enRemplissage = intervalles.get(k);
						intervalles.get(k).setInstantInitial(intervalles.get(k-1).instantFinal());
						intervalles.get(k).setInstantFinal(enRemplissage.instantFinal());
						k++;
					}
				}
				if (k >= index) {
					intervalles.add(new Triplet(intervalles.get(k).instantFinal(), mol.instantFinal(), 1));
					index++;
				}
				else {
					intervalles.add(k,new Triplet(intervalles.get(k).instantFinal(), mol.instantFinal(), intervalles.get(k).nombreMolecule()) );
					index++;
				}
			}

			// Cas 4 : ensemble disjoints
			else {
				enRemplissage.setInstantInitial(mol.instantInitial()); 
				enRemplissage.setInstantFinal(mol.instantFinal());
				enRemplissage.setNombreMolecule(1);
				intervalles.add(enRemplissage);
				index++;
			}
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

