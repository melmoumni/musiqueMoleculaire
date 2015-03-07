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
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import Utilitaires.Midi;

abstract class Controleur{
	public static ArrayList<Molecule> molecules;
	public static ArrayList<Intervalle> intervalles;
	public int duree;
	static public int noteRef;
	private Vue vue;
	public Vector<Fenetre> fenetres;
	public int periode;

	public static float[] alphaSeparation;
	public static boolean isChercheur;
	static public Timbre[] tableauTimbre;

	static {
		tableauTimbre = new Timbre[128];
		molecules = new ArrayList<Molecule>();
		alphaSeparation = new float[3];
		intervalles = new ArrayList<Intervalle>();
	}


	public static void printTimbres(){
		for (Timbre timbre : tableauTimbre){
			timbre.printTimbre();
		}
	}


	public Controleur(boolean ischercheur){
		vue = new Vue();
		//parseur = new Parseur();
		fenetres = new Vector<Fenetre>();
		setAlphaTab((float) 0.25, (float) 0.9, (float) 1.1);
		isChercheur = ischercheur;
	}

	public static void printMolecules(){
		for (Molecule mol : molecules()){
			mol.printMolecule();
		}
	}

	public void setNote(int newnote){
		noteRef = newnote;
	}

	public void setPeriode (int p){
		periode = p;
	}

	public static void printIntervalles(){
		for (Intervalle intervalle : intervalles){
			intervalle.printIntervalle();
		}
	}

	public static void printTrajectoires(){
		//FenetreTrajectoires f= new FenetreTrajectoires(this.molecules());
		JFrame jf = new JFrame("test");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(ControleurFenetres.getLargeurVideo(), ControleurFenetres.getHauteurVideo());
		jf.add(new JScrollPane(new FenetreTrajectoires(molecules())));
		jf.setVisible(true);
	}

	public static void initMolecules(String cheminTraj, String cheminAnalyse, String cheminTimbre) throws IOException{
		Parseur.lireFichierAnalyse(cheminAnalyse, molecules);
		Parseur.lireFichierTrajectoire(cheminTraj, molecules());
		Parseur.lectureTimbre(cheminTimbre,tableauTimbre);
	}

	public static ArrayList<Molecule> molecules(){
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

	protected void setTempo (){

	}

	static void remplirIntervalles(){
		int index = 0;
		int i = 0;
		int k = 0;
		int tmp = 0;
		Intervalle enRemplissage = new Intervalle(molecules.get(0).instantInitial(), molecules.get(0).instantFinal(), 0);
		intervalles.add(enRemplissage);


		for (Molecule mol : molecules){
			i = 0; /** on se remet au debut de liste pour la parcourir et trouver où placer la molécule*/
			while ( (i < index) && (intervalles.get(i).instantInitial() < mol.instantInitial())  && (intervalles.get(i).instantFinal() <= mol.instantInitial()) ) { /**on cherche l'intervalle ayant un temps initial (ti) < mol.ti && temps final (tf) <= mol.tf */
				i++;
			}
			System.out.println("Fin de liste");
			intervalles.get(index).printIntervalle();
			System.out.println("Intervalle choisi");
			intervalles.get(i).printIntervalle();
			mol.printMolecule();
			/**Cas 1-1 : la molécule à ajouter a un instant initial = et tf > */
			if ((intervalles.get(i).instantInitial() == mol.instantInitial()) &&
					(intervalles.get(i).instantFinal() < mol.instantFinal()) ) {
				k = i;
				System.out.println("Cas 1-1");

				while ( (k <= index) && (intervalles.get(k).instantFinal() <= mol.instantFinal()) ) {
					/**modifs sur la liste jusqu'à l'instant où on trouve un intervalle adéquat*/
					if (i == k) {
						/**premier ajout dans la liste*/
						System.out.println("a");
						intervalles.get(k).incrementeNombreMolecule();
						intervalles.get(k).ajouterTimbre(mol);
						intervalles.get(k).printIntervalle();
						k++;
					}
					else {
						if (k < index){
							/** on met à jour les nombres dans les intervalles*/
							System.out.println("b");
							intervalles.get(k).incrementeNombreMolecule();
							intervalles.get(k).ajouterTimbre(mol);
							tmp = intervalles.get(k).instantFinal();
							intervalles.get(k).setInstantFinal(tmp);
							intervalles.get(k).printIntervalle();
						}
						k++;
					}
				}
				if (k == 0){
					System.out.println("c");
					intervalles.get(k).incrementeNombreMolecule();
					intervalles.get(k).ajouterTimbre(mol);
					intervalles.get(k).printIntervalle();
				}
				if (k >= index) {
					System.out.println("e");
					intervalles.get(k-1).printIntervalle();
					if (mol.instantFinal() != intervalles.get(k-1).instantFinal()) {
						System.out.println("sortie e1");
						intervalles.add(new Intervalle(intervalles.get(k-1).instantFinal(), mol.instantFinal(), 1));
						index++;
						intervalles.get(index).ajouterTimbre(mol);
						intervalles.get(index).printIntervalle();
					}
					else {
						if (k != index) {
							System.out.println("sortie e2");
							intervalles.get(index).incrementeNombreMolecule();
							intervalles.get(index).ajouterTimbre(mol);
							intervalles.get(index).printIntervalle();
						}
					}
				}
				else {
					if(mol.instantFinal() < intervalles.get(k).instantFinal()){
						System.out.println("sortie 2");
						enRemplissage = intervalles.get(k);
						tmp = intervalles.get(k).instantFinal();
						intervalles.add(k+1,new Intervalle(mol.instantFinal(), tmp , intervalles.get(k).nombreMolecule()));
						intervalles.get(k+1).copierTimbres(intervalles.get(k).molecules());

						intervalles.get(k+1).printIntervalle();
						index++;

						intervalles.get(k).setInstantFinal(mol.instantFinal());
						intervalles.get(k).incrementeNombreMolecule();
						intervalles.get(k).ajouterTimbre(mol);
						intervalles.get(k).printIntervalle();
					}
					else if(mol.instantFinal() == intervalles.get(k).instantFinal()){
						System.out.println("sortie 3");
						intervalles.get(k).incrementeNombreMolecule();
						intervalles.get(k).ajouterTimbre(mol);
						intervalles.get(k).printIntervalle();
					}
					else {
						System.out.println("sortie 4");
						intervalles.add(k+1,new Intervalle(intervalles.get(k).instantFinal(), mol.instantFinal(), 1));
						intervalles.get(k+1).ajouterTimbre(mol);
						intervalles.get(k).printIntervalle();
						index++;
					}
				}
			}
			/** Cas 1-2 ti et tf == */
			else if ((intervalles.get(i).instantInitial() == mol.instantInitial()) &&
					(intervalles.get(i).instantFinal() == mol.instantFinal()) ) {
				System.out.println("Cas 1-2");
				intervalles.get(i).incrementeNombreMolecule();
				intervalles.get(i).ajouterTimbre(mol);
				intervalles.get(i).printIntervalle();
			}


			/** Cas 2 : intervalle de la liste englobe la molecule*/
			else if (intervalles.get(i).instantFinal() > mol.instantFinal() ) {
				System.out.println("Cas 2");
				enRemplissage = intervalles.get(i);
				intervalles.add(i+1,new Intervalle(mol.instantInitial(), mol.instantFinal(), enRemplissage.nombreMolecule() + 1));
				intervalles.get(i+1).copierTimbres(intervalles.get(i).molecules());
				intervalles.get(i+1).ajouterTimbre(mol);
				index++;
				intervalles.get(i+1).printIntervalle();
				intervalles.add(i+2, new Intervalle(mol.instantFinal(), enRemplissage.instantFinal(), enRemplissage.nombreMolecule()));
				intervalles.get(i+2).copierTimbres(intervalles.get(i).molecules());
				intervalles.get(i+2).printIntervalle();
				intervalles.get(i).setInstantFinal(mol.instantInitial());
				index++;
			}
			/** Cas 3 : intervalle de la liste couvre le debut de l'intervalle de la molecule */
			else if (intervalles.get(i).instantFinal() < mol.instantFinal() && intervalles.get(i).instantFinal() > mol.instantInitial()) {
				System.out.println("Cas 3");
				//System.out.println("titi" + i + index);
				enRemplissage = intervalles.get(i);
				k = i;
				if ( (k< index) && (mol.instantInitial() == intervalles.get(k+1).instantInitial())) {
					System.out.println("a");
					k++;
				}
				else {
					System.out.println("b");
					intervalles.add(k+1, new Intervalle(mol.instantInitial(), enRemplissage.instantFinal(), enRemplissage.nombreMolecule()));
					intervalles.get(k+1).copierTimbres(intervalles.get(k).molecules());
					index++;
					intervalles.get(k).setInstantFinal(mol.instantInitial());
					intervalles.get(k).printIntervalle();
					intervalles.get(k+1).printIntervalle();
					k++;
				}
				while ( (k <= index) && (intervalles.get(k).instantFinal() < mol.instantFinal()) ) {
					/** modifs sur la liste jusqu'au bon intervalle */
					System.out.println("toto" + k + index);
					intervalles.get(k).incrementeNombreMolecule();
					intervalles.get(k).ajouterTimbre(mol);
					intervalles.get(k).printIntervalle();
					k++;
				}
				if ((k > index) && intervalles.get(k-1).instantFinal() == mol.instantFinal()) {
					System.out.println("S1");
					intervalles.get(k-1).incrementeNombreMolecule();
					intervalles.get(k-1).ajouterTimbre(mol);
					intervalles.get(k-1).printIntervalle();
				}
				else if (k > index){
					System.out.println("S2");
					intervalles.add(new Intervalle(intervalles.get(k-1).instantFinal(), mol.instantFinal(),1 ));
					index++;
					intervalles.get(index).ajouterTimbre(mol);
					intervalles.get(index).printIntervalle();
				}
				else if ((k <= index) && (intervalles.get(k).instantFinal() == mol.instantFinal())) {
					System.out.println("S3");
					intervalles.get(k).incrementeNombreMolecule();
					intervalles.get(k).ajouterTimbre(mol);
					intervalles.get(k).printIntervalle();
				}
				else {
					System.out.println("e");
					if ((k <= index) && (intervalles.get(k).instantInitial() > mol.instantFinal())) {
						System.out.println("sortie4");
						i = k;
						intervalles.add(k,new Intervalle (intervalles.get(i-1).instantFinal(), mol.instantFinal(), intervalles.get(i).nombreMolecule()+1));
						index++;
						intervalles.get(i).ajouterTimbre(mol);
						intervalles.get(i).printIntervalle();
					}
					else {
						System.out.println("sortie5");
						intervalles.add(k+1, new Intervalle(mol.instantFinal(), intervalles.get(k).instantFinal(), intervalles.get(k).nombreMolecule()));
						intervalles.get(k+1).copierTimbres(intervalles.get(k).molecules());


						index++;
						intervalles.get(k).setInstantFinal(mol.instantFinal());
						intervalles.get(k).incrementeNombreMolecule();
						intervalles.get(k).ajouterTimbre(mol);
						intervalles.get(k).printIntervalle();
						intervalles.get(k+1).printIntervalle();
					}
				}
			}
			/** Cas 4 : ensemble disjoints */
			else {
				System.out.println("Cas 4");
				intervalles.add(new Intervalle(mol.instantInitial(), mol.instantFinal(), 1) );
				index++;
				intervalles.get(index).ajouterTimbre(mol);
				intervalles.get(index).printIntervalle();
			}
		}
	}

		protected static void analyseMolecules(){
			for (Molecule mol : molecules()){
				mol.analyseMolecule(alphaSeparation, isChercheur);
				mol.analyseDistance();
			}
			Collections.sort(molecules);
		}

		static void remplirSequence(){
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
