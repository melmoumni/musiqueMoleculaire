import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

//import Utilitaires.Midi;

abstract class Controleur{
	public static ArrayList<Molecule> molecules;
	public static ArrayList<Intervalle> intervalles;
	static public int noteRef;
	static public float intensiteMoy;
	//private Vue vue;
	public Vector<Fenetre> fenetres;
	public int periode;

	public static float[] alphaSeparation;
	public static boolean isChercheur;
	static public Timbre[] tableauTimbre;
	public static int dureeNoire;
	
	static {
		tableauTimbre = new Timbre[128];
		molecules = new ArrayList<Molecule>();
		alphaSeparation = new float[3];
		setAlphaTab((float) 0.25, (float) 0.9, (float) 1.1);
		intervalles = new ArrayList<Intervalle>();
	}


	public static void printTimbres(){
		for (Timbre timbre : tableauTimbre){
			timbre.printTimbre();
		}
	}


	public Controleur(boolean ischercheur){
		//vue = new Vue();
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
	
	/**
	 * selectionne les intervalles utilisés pour jouer entre deux instants donnés
	 * @param debut instant initial pour jouer la musique
	 * @param fin instant final pour jouer la musique
	 * @return liste des intervalles où des molécules jouent entre debut et fin
	 */
	public ArrayList<Intervalle> selectionIntervalle(int debut, int fin) {
		ArrayList<Intervalle> tmpList = new ArrayList<Intervalle>(intervalles);
		int index = 0;
		for (Intervalle intervalle : tmpList){
		if (intervalle.instantInitial() < debut) {
			tmpList.remove(index);
		}
		else if (intervalle.instantFinal() > fin) {
			tmpList.remove(index);
			}
		index++;
		}
		return tmpList;
	}
	
	/**
	 * Selectionne les molécules présentes dans un intervalle de temps donné
	 * @param debut instant initial pour jouer la musique
	 * @param fin instant final pour jouer la musique
	 * @return la liste des molécules qui peuvent jouer entre debut et fin
	 */
	public ArrayList<Molecule> selectionMoleculeIntervalle(int debut, int fin) {
		ArrayList<Molecule> tmpList = new ArrayList<Molecule>(molecules);
		for (Molecule mol : tmpList) {
			if ((mol.instantInitial() < debut) || (mol.instantFinal() < debut) ) {
				tmpList.remove(mol);
			}
			else if (mol.instantInitial() > fin) {
				tmpList.remove(mol);
			}
		}
		return tmpList;
	}

	public static void printTrajectoires(){
	    //FenetreTrajectoires f= new FenetreTrajectoires(this.molecules());
	    JFrame jf = new JFrame("test");
	    //AffichageTrajectoiresThread att = new AffichageTrajectoiresThread(ControleurFenetres.getLargeurVideo(), ControleurFenetres.getHauteurVideo(), 20, molecules);
	    AffichageTrajectoires att = new AffichageTrajectoires(ControleurFenetres.getLargeurVideo(), ControleurFenetres.getHauteurVideo(), 20, molecules);
	    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    jf.add(new JScrollPane(att));
	    //jf.add(new JScrollPane(att.getAffichage()));
	    //jf.add(new JScrollPane(new FenetreTrajectoires(molecules)));
	    jf.setBackground(Color.BLACK);
	    jf.setVisible(true);
	    //jf.revalidate();


	    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	    att.setSize((int)d.getWidth(), (int)d.getHeight());
	    
	    jf.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	    jf.setResizable(false);
	

	    try{		
		//att.run();
		while(!att.aFini()){
		    att.miseAJour();
		}
	    } catch(InterruptedException e){
		e.printStackTrace();
	    }
	}

	/**
	 * méthode qui initialise les attributs des molécules qui sont renseignés dans des fichiers
	 * @param cheminTraj chemin vers le fichier de trajectoire
	 * @param cheminAnalyse chemin vers le fichier contenant les alphas et les intensités
	 * @param cheminTimbre chemin vers le fichier contenant la liste des instruments et leurs carac
	 * @throws IOException si un des fichiers n'est pas trouvé ou correctement rempli
	 */
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

	public static void setAlphaTab (float x1, float x2, float x3){
		alphaSeparation[0] = x1;
		alphaSeparation[1] = x2;
		alphaSeparation[2] = x3;
	}

	public void setDureeNoire(int newduree){
		dureeNoire = newduree;
	}

	protected void setTempo (){

	}
/**
 * méthode qui alloue les notes aux molécules
 *  @compositeur 2 notes par molécule pour le compositeur
 *  en fonction du découpage renseigné dans le cahier des charges
 *  @chercheur une note par molécule pour le chercheur
 *  en fonction de la position initiale de la molécule
 */
	public static void allocationNotes() {
		if (isChercheur){
			for (Molecule mol : molecules) {
				mol.setNoteAbs(mol.getTimbreAbs().octaveRef() * 12 + noteRef);
			}
		}
		else{
			Dimension dim = Controleur.maxDimension();
			float intervalleAbs = (float) dim.width/(float) 128; 
			float intervalleOrd = (float) dim.height/(float) 128;
			for (Molecule mol : molecules){
				mol.setNoteAbs((int) (mol.positions.get(0).x/intervalleAbs));
				mol.setNoteOrd((int) (mol.positions.get(0).y/intervalleOrd));
			}
		}
	}
/**
 * méthode qui remplit la liste des intervalles de temps où les molécules jouent
 * Pour chaque intervalle de la liste InstantInitial < InstantFinal
 * et InstantFinal_i < InstantInitial_(i+1)
 * Chaque intervalle contient le nombre de molécules présentes et la liste de ces molécules
 */
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
			//System.out.println("Fin de liste");
			//intervalles.get(index).printIntervalle();
			//System.out.println("Intervalle choisi");
			//intervalles.get(i).printIntervalle();
			//mol.printMolecule();
			/**Cas 1-1 : la molécule à ajouter a un instant initial = et tf > */
			if ((intervalles.get(i).instantInitial() == mol.instantInitial()) &&
					(intervalles.get(i).instantFinal() < mol.instantFinal()) ) {
				k = i;
				//System.out.println("Cas 1-1");

				while ( (k <= index) && (intervalles.get(k).instantFinal() <= mol.instantFinal()) ) {
					/**modifs sur la liste jusqu'à l'instant où on trouve un intervalle adéquat*/
					if (i == k) {
						/**premier ajout dans la liste*/
						//System.out.println("a");
						intervalles.get(k).incrementeNombreMolecule();
						intervalles.get(k).ajouterTimbre(mol);
						//intervalles.get(k).printIntervalle();
						k++;
					}
					else {
						if (k < index){
							/** on met à jour les nombres dans les intervalles*/
							//System.out.println("b");
							intervalles.get(k).incrementeNombreMolecule();
							intervalles.get(k).ajouterTimbre(mol);
							tmp = intervalles.get(k).instantFinal();
							intervalles.get(k).setInstantFinal(tmp);
							//intervalles.get(k).printIntervalle();
						}
						k++;
					}
				}
				if (k == 0){
					//System.out.println("c");
					intervalles.get(k).incrementeNombreMolecule();
					intervalles.get(k).ajouterTimbre(mol);
					//intervalles.get(k).printIntervalle();
				}
				if (k >= index) {
					//System.out.println("e");
					//intervalles.get(k-1).printIntervalle();
					if (mol.instantFinal() != intervalles.get(k-1).instantFinal()) {
						if (k > index) {
							//System.out.println("sortie e1");
							intervalles.add(new Intervalle(intervalles.get(k-1).instantFinal(), mol.instantFinal(), 1));
							index++;
							intervalles.get(index).ajouterTimbre(mol);
							//intervalles.get(index).printIntervalle();
						}
						else {
							intervalles.add(new Intervalle(mol.instantFinal(), intervalles.get(k).instantFinal(), intervalles.get(k).nombreMolecule()));
							index++;
							intervalles.get(k).setInstantFinal(mol.instantFinal());
							intervalles.get(index).copierTimbres(intervalles.get(k).molecules());
							intervalles.get(k).ajouterTimbre(mol);
							intervalles.get(k).incrementeNombreMolecule();
						}
					}
					else {
						if (k != index) {
							//System.out.println("sortie e2");
							intervalles.get(index).incrementeNombreMolecule();
							intervalles.get(index).ajouterTimbre(mol);
							//intervalles.get(index).printIntervalle();
						}
					}
				}
				else {
					if(mol.instantFinal() < intervalles.get(k).instantFinal()){
						//System.out.println("sortie 2");
						//enRemplissage = intervalles.get(k);
						tmp = intervalles.get(k).instantFinal();
						intervalles.add(k+1,new Intervalle(mol.instantFinal(), tmp , intervalles.get(k).nombreMolecule()));
						intervalles.get(k+1).copierTimbres(intervalles.get(k).molecules());

						//intervalles.get(k+1).printIntervalle();
						index++;

						intervalles.get(k).setInstantFinal(mol.instantFinal());
						intervalles.get(k).incrementeNombreMolecule();
						intervalles.get(k).ajouterTimbre(mol);
						//intervalles.get(k).printIntervalle();
					}
					else if(mol.instantFinal() == intervalles.get(k).instantFinal()){
						//System.out.println("sortie 3");
						intervalles.get(k).incrementeNombreMolecule();
						intervalles.get(k).ajouterTimbre(mol);
						//intervalles.get(k).printIntervalle();
					}
					else {
						//System.out.println("sortie 4");
						intervalles.add(k,new Intervalle(intervalles.get(k).instantFinal(), mol.instantFinal(), 1));
						intervalles.get(k).ajouterTimbre(mol);
						//intervalles.get(k).printIntervalle();
						index++;
					}
				}
			}
			/** Cas 1-2 ti et tf == */
			else if ((intervalles.get(i).instantInitial() == mol.instantInitial()) &&
					(intervalles.get(i).instantFinal() == mol.instantFinal()) ) {
				//System.out.println("Cas 1-2");
				intervalles.get(i).incrementeNombreMolecule();
				intervalles.get(i).ajouterTimbre(mol);
				//intervalles.get(i).printIntervalle();
			}


			/** Cas 2 : intervalle de la liste englobe la molecule*/
			else if (intervalles.get(i).instantFinal() > mol.instantFinal() ) {
				//System.out.println("Cas 2");
				enRemplissage = intervalles.get(i);
				intervalles.add(i+1,new Intervalle(mol.instantInitial(), mol.instantFinal(), enRemplissage.nombreMolecule() + 1));
				intervalles.get(i+1).copierTimbres(intervalles.get(i).molecules());
				intervalles.get(i+1).ajouterTimbre(mol);
				index++;
				//intervalles.get(i+1).printIntervalle();
				intervalles.add(i+2, new Intervalle(mol.instantFinal(), enRemplissage.instantFinal(), enRemplissage.nombreMolecule()));
				intervalles.get(i+2).copierTimbres(intervalles.get(i).molecules());
				//intervalles.get(i+2).printIntervalle();
				intervalles.get(i).setInstantFinal(mol.instantInitial());
				index++;
			}
			/** Cas 3 : intervalle de la liste couvre le debut de l'intervalle de la molecule */
			else if (intervalles.get(i).instantFinal() < mol.instantFinal() && intervalles.get(i).instantFinal() > mol.instantInitial()) {
				//System.out.println("Cas 3");
				//System.out.println("titi" + i + index);
				enRemplissage = intervalles.get(i);
				k = i;
				if ( (k< index) && (mol.instantInitial() == intervalles.get(k+1).instantInitial())) {
					//System.out.println("a");
					k++;
				}
				else {
					//System.out.println("b");
					intervalles.add(k+1, new Intervalle(mol.instantInitial(), enRemplissage.instantFinal(), enRemplissage.nombreMolecule()));
					intervalles.get(k+1).copierTimbres(intervalles.get(k).molecules());
					index++;
					intervalles.get(k).setInstantFinal(mol.instantInitial());
					//intervalles.get(k).printIntervalle();
					//intervalles.get(k+1).printIntervalle();
					k++;
				}
				while ( (k <= index) && (intervalles.get(k).instantFinal() < mol.instantFinal()) ) {
					/** modifs sur la liste jusqu'au bon intervalle */
					//System.out.println("toto" + k + index);
					intervalles.get(k).incrementeNombreMolecule();
					intervalles.get(k).ajouterTimbre(mol);
					//intervalles.get(k).printIntervalle();
					k++;
				}
				if ((k > index) && intervalles.get(k-1).instantFinal() == mol.instantFinal()) {
					//System.out.println("S1");
					intervalles.get(k-1).incrementeNombreMolecule();
					intervalles.get(k-1).ajouterTimbre(mol);
					//intervalles.get(k-1).printIntervalle();
				}
				else if (k > index){
					//System.out.println("S2");
					intervalles.add(new Intervalle(intervalles.get(k-1).instantFinal(), mol.instantFinal(),1 ));
					index++;
					intervalles.get(index).ajouterTimbre(mol);
					//intervalles.get(index).printIntervalle();
				}
				else if ((k <= index) && (intervalles.get(k).instantFinal() == mol.instantFinal())) {
					//System.out.println("S3");
					intervalles.get(k).incrementeNombreMolecule();
					intervalles.get(k).ajouterTimbre(mol);
					//intervalles.get(k).printIntervalle();
				}
				else {
					//System.out.println("e");
					if ((k <= index) && (intervalles.get(k).instantInitial() > mol.instantFinal())) {
						//System.out.println("sortie4");
						i = k;
						intervalles.add(k,new Intervalle (intervalles.get(i-1).instantFinal(), mol.instantFinal(), intervalles.get(i).nombreMolecule()+1));
						index++;
						intervalles.get(i).ajouterTimbre(mol);
						//intervalles.get(i).printIntervalle();
					}
					else {
						//System.out.println("sortie5");
						intervalles.add(k+1, new Intervalle(mol.instantFinal(), intervalles.get(k).instantFinal(), intervalles.get(k).nombreMolecule()));
						intervalles.get(k+1).copierTimbres(intervalles.get(k).molecules());


						index++;
						intervalles.get(k).setInstantFinal(mol.instantFinal());
						intervalles.get(k).incrementeNombreMolecule();
						intervalles.get(k).ajouterTimbre(mol);
						//intervalles.get(k).printIntervalle();
						//intervalles.get(k+1).printIntervalle();
					}
				}
			}
			/** Cas 4 : ensemble disjoints */
			else {
				//System.out.println("Cas 4");
				intervalles.add(new Intervalle(mol.instantInitial(), mol.instantFinal(), 1) );
				index++;
				intervalles.get(index).ajouterTimbre(mol);
				//intervalles.get(index).printIntervalle();
			}
		}
	}

	/**
	 * méthode qui remplit les attributs des molécules qui doivent être calculés
	 */
	protected static void analyseMolecules(){
		float intensiteMoyIntermediaire = 0;
		for (Molecule mol : molecules()){
			mol.analyseMolecule(alphaSeparation, isChercheur);
			mol.analyseDistance();
			mol.analyseIntensite();
			if (mol.moyenneIntensite() > intensiteMoyIntermediaire) {
				intensiteMoyIntermediaire = mol.moyenneIntensite();
			}
		}
		intensiteMoy = intensiteMoyIntermediaire;
		analyseDistanceMaxDirectionnelle();
		analyseMSD();
		for (Molecule mol : molecules()){
			mol.setVolume((int) ((mol.moyenneIntensite() * 125) / intensiteMoy));
			}
		Collections.sort(molecules);
	}

	/**
	 * remplit les champs minMSD et maxMSD de la classe Boucle
	 */
	private static void analyseMSD() {
		float minMSD = Float.MAX_VALUE;
		float maxMSD = 0;
		for (Molecule mol : molecules){
			if (mol.getEffetAbs().getClass().getName().equals("Confine")){
				if (minMSD > mol.msd()){
					minMSD = mol.msd();
				}
				if (maxMSD < mol.msd()){
					maxMSD = mol.msd();
				}
			}
		}
		Boucle.setMinMSD(minMSD);
		Boucle.setMaxMSD(maxMSD);
	}


	/**
	 * Remplit le champs distanceMax de l'effet Glissando
	 */
	private static void analyseDistanceMaxDirectionnelle() {
		float maxDistance = 0;
		if (isChercheur){
			for (Molecule mol : molecules){
				if (mol.getEffetAbs().getClass().getName().equals("Directionnelle")){
					if (mol.distance() > maxDistance){
						maxDistance = mol.distance();
					}
				}
			}
		}else{
			for (Molecule mol : molecules){
				if (mol.getEffetAbs().getClass().getName().equals("Directionnelle") || mol.getEffetAbs().getClass().getName().equals("Diffusif")){
					if (mol.distance() > maxDistance){
						maxDistance = mol.distance();
					}
				}
			}
		}
		Glissando.setDistanceMax(maxDistance);
	}


	static void remplirSequence(ArrayList<Molecule> list){
		try{
			Midi.initialiser("../soundbanks/TimGM6mb.sf2");
			Midi.configurerChannel(0, 56);
			for (Molecule mol : list){
				mol.remplirSequenceMolecule();
			}
		}catch(MidiUnavailableException e){}
		catch(InvalidMidiDataException e){}
	}

/**
 * méthode qui trie les molécules selon leur paramètre alpha
 * et remplit une liste pour chaque caractère
 * @param ListeImmobile
 * @param ListeConfine
 * @param ListeDirectionnelle
 * @param ListeAleatoire
 */
	public static void trierMolecules(	ArrayList<Molecule> ListeImmobile,
			ArrayList<Molecule> ListeConfine,
			ArrayList<Molecule> ListeDirectionnelle,
			ArrayList<Molecule> ListeAleatoire) {

		float alpha;
		for (Molecule mol : molecules){
			alpha = mol.getAlpha();
			if (alpha < Controleur.alphaSeparation[0]){
				ListeImmobile.add(mol);
			}else if (alpha > Controleur.alphaSeparation[0] && alpha < Controleur.alphaSeparation[1]){
				ListeConfine.add(mol);
			}else if (alpha > Controleur.alphaSeparation[1] && alpha < Controleur.alphaSeparation[2]){
				ListeAleatoire.add(mol);
			}else {
				ListeDirectionnelle.add(mol);
			}
		}
	}

	public static ArrayList<Intervalle> intervalles(){
		return intervalles;
	}

	public static void setIntervalles(ArrayList<Intervalle> list){
		intervalles = list;
	}

	static public Timbre[] tableauTimbre() {
		return tableauTimbre;
	}

	public static Dimension maxDimension(){
		float maxAbs = 0;
		float maxOrd = 0;
		for (Molecule mol : molecules()){
			for (CaracteristiqueTemporelle ct : mol.positions()){
				if (ct.x() > maxAbs){
					maxAbs = ct.x();
				}
				if (ct.y() > maxOrd){
					maxOrd = ct.y();
				}
			}
		}
		return new Dimension((int) maxAbs, (int) maxOrd);
	}

}
