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
	int index = -1;
	Triplet enRemplissage = new Triplet(molecules.get(0).instantInitial(), molecules.get(0).instantFinal(), 0);
	Triplet tmp = new Triplet(molecules.get(0).instantInitial(), molecules.get(0).instantFinal(), 1);
	for (Molecule mol : molecules){
	    // cas 1 : intervalle prochaine molecule C intervalle en cours
	    if (((enRemplissage.instantInitial()) < (mol.instantInitial())) && ((enRemplissage.instantFinal()) > (mol.instantFinal()))) {
		enRemplissage.setInstantFinal(mol.instantInitial());
		intervalles.add(enRemplissage);
		index++;
		enRemplissage.printTriplet();
		enRemplissage = new Triplet(mol.instantInitial(), mol.instantFinal(), tmp.nombreMolecule() + 1);
		intervalles.add(enRemplissage);
		index++;
		tmp = new Triplet(mol.instantFinal(), tmp.instantFinal(), enRemplissage.nombreMolecule());
		enRemplissage = tmp;
	    }
	    else {
		// cas 2 : temps final prochaine molecule > intervalle  en cours
		if ((enRemplissage.instantInitial() < mol.instantInitial()) && (enRemplissage.instantFinal() < mol.instantFinal())) {
		    enRemplissage.setInstantFinal(mol.instantInitial());
		    intervalles.add(enRemplissage);
		    tmp = new Triplet(mol.instantInitial(), enRemplissage.instantFinal(), enRemplissage.nombreMolecule() + 1);
		    intervalles.add(tmp);
		    index++;
		    tmp = new Triplet(enRemplissage.instantFinal(), mol.instantFinal(), enRemplissage.nombreMolecule());
		    enRemplissage = tmp;
		}
		else {
		    // cas où les temps initiaux sont égaux 
		    if (enRemplissage.instantInitial() == mol.instantInitial()) {
			if (enRemplissage.instantFinal() > mol.instantFinal()){
			    if ((intervalles.size() > 0) && (intervalles.get(index).instantFinal() > mol.instantFinal())){
				intervalles.get(index).incrementeNombreMolecule();
				intervalles.get(index).setInstantFinal(mol.instantFinal());
				tmp.setInstantInitial(intervalles.get(index).instantFinal());
				tmp.setInstantFinal(enRemplissage.instantFinal());
			    }
			    else {
				enRemplissage.setInstantFinal(mol.instantFinal());
				enRemplissage.incrementeNombreMolecule();
				intervalles.add(enRemplissage);
				index++;
				enRemplissage = //new Triplet(tmp.instantInitial(), tmp.instantFinal(), tmp.nombreMolecule());
				    tmp;
				enRemplissage.printTriplet();
			    }
			    System.out.println("passage");
			}
			else {
			    if (enRemplissage.instantFinal() == mol.instantFinal()) {
				enRemplissage.incrementeNombreMolecule();
			    }
			    else {
				// intervalles.add(enRemplissage);
				// index++;
				// enRemplissage = new Triplet(tmp.instantFinal(), mol.instantFinal(), 1);
				// tmp = enRemplissage;
			    }
			}// manque le cas d'intervalle disjoints à traiter
		    }
		}
	    }
	}
	intervalles.add(enRemplissage);
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
