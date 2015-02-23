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
    
    public Parseur parseur;
    public ArrayList<Molecule> molecules;
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
	alphaSeparation = new float[3];
	setAlphaTab((float) 0.25, (float) 0.9, (float) 1.1);
	isChercheur = ischercheur;
    }

    public void printMolecules(){
	for (Molecule mol : molecules){
	    mol.printMolecule();
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
