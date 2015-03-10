import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;


class Molecule implements Comparable<Molecule>, Cloneable{
	/*
      Cette classe contiens les différents attributs relatifs à une molécule
	 */
	private int numero;
	protected ArrayList<CaracteristiqueTemporelle> positions;
	private float vitesseAbs;
	private float vitesseOrd;
	private Timbre timbre = new Timbre(56); // changer ensuite
	private Timbre timbre2= new Timbre(56);  //deuxieme timbre pour la partie compositeur
	private int volume = 100;           //mettre dans constructeur plus tard et faire varier
	private int instantInitial;
	private int instantFinal;
	private float distance;
	private float alpha;
	private float msd;
	private Color couleur;
	private Effet effet;
	private int tempo; // ou pas !!!
	private float pasMax; // juste pour les aleatoires
	Molecule(){
		positions = new ArrayList<CaracteristiqueTemporelle>();
	}

	public void printMolecule(){
		System.out.println("==================================");
		System.out.println("Molecule numero "+numero);
		System.out.println("Positions :");
		for (CaracteristiqueTemporelle CT : positions){
			System.out.printf("%f %f, ",CT.x, CT.y);
		}
		System.out.println("vitesseAbs : "+ vitesseAbs+" , vitesseOrd : "+ vitesseOrd);
		//System.out.println("Timbre : "+ timbre.timbreMIDI() + " (min : " + timbre.min() + " max : "+timbre.max() + " OctRef : "+ timbre.octaveRef());
		System.out.println("alpha : " + alpha);
		System.out.println("MSD : " + msd);
		System.out.println("Effet" + effet.getClass().getName());
		//System.out.println("Tempo : "+tempo);
		System.out.println("PasMax : " + pasMax);
		System.out.println("Instant initial : " + instantInitial);
		System.out.println("Instant final : " + instantFinal);
		System.out.println("Distance parcourue : " + distance);
		System.out.println("==================================");

	}

	public Molecule(int numerot, ArrayList<CaracteristiqueTemporelle> positionst){
		numero = numerot;
		positions = positionst;
	}

	public Molecule(int numerot, float alphat, float msdt) {
		numero = numerot;
		alpha = alphat;
		msd = msdt;
	}

	public void setMolecule(float alphat, float msdt){
		alpha = alphat;
		msd = msdt;
	}

	public int numero(){
		return numero;
	}

	public float msd(){
		return msd;
	}

	public float pasMax(){
		return pasMax;
	}

	public void setPositions(ArrayList<CaracteristiqueTemporelle> positionst) {
		positions = positionst;
	}

	public ArrayList<CaracteristiqueTemporelle> positions(){
		return positions;
	}

	public float vitesseAbs(){
		return vitesseAbs;
	}

	public float vitesseOrd(){
		return vitesseOrd;
	}

	public Color getCouleur(){
		return couleur;
	}

	public void setCouleur(Color c){
		couleur=c;
	}

	public void setInstantInitial(int instant){
		instantInitial = instant;
	}

	public void setInstantFinal(int instant){
		instantFinal = instant;
	}

	public int instantInitial(){
		return instantInitial;
	}

	public int instantFinal(){
		return instantFinal;
	}

	public int vitesse(){
		return (int) Math.sqrt(vitesseAbs()*vitesseAbs() + vitesseOrd() * vitesseOrd());
	}

	public float distancePas(float abs1, float ord1, float abs2, float ord2) {
		return (float) Math.sqrt(Math.abs (Math.pow( (abs2-abs1), 2.0) + Math.pow( (ord2-ord1), 2.0))) ; 
	}

	public void setEffet (Effet effett){
		effet = effett;
	}

	public void setTempo (int tempot){
		tempo = tempot;
	}

	public void setTimbre(int timbret){
		timbre=new Timbre(timbret);
	}

	public int getTimbre(){
		return timbre.timbreMIDI();
	}
	
	public void setTimbre2(int timbret){
		timbre2=new Timbre(timbret);
	}

	public int getTimbre2(){
		return timbre2.timbreMIDI();
	}

	public void setVolume(int volumet){
		volume=volumet;
	}

	public int getVolume(){
		return volume;
	}

	public void analyseMolecule(float[] alphaSeparation, boolean isChercheur){
		analyseVitesse();
		analyseAlpha(alphaSeparation, isChercheur);

	}	
	private void analyseAlpha(float[] alphaSeparation, boolean isChercheur) {
		if (alpha < alphaSeparation[0]){
			effet = new Tenu();
		}else if (alpha > alphaSeparation[0] && alpha < alphaSeparation[1] && isChercheur){
			effet = new Boucle();
		}
		else if (alpha > alphaSeparation[0] && alpha < alphaSeparation[1] && !isChercheur){
			effet = new Tremolo();
		}else if (alpha > alphaSeparation[1] && alpha < alphaSeparation[2] && isChercheur){
			effet = new Aleatoire();
		}else if (alpha > alphaSeparation[1] && alpha < alphaSeparation[2] && !isChercheur){
			effet = new Glissando();
		}else {
			effet = new Glissando(); 
		}
		//creation de l'effet
	}

	public void analyseVitesse(){
		ArrayList<Float> vitesseAbsisses = new ArrayList<Float>(); 
		ArrayList<Float> vitesseOrdonnees = new ArrayList<Float>();
		CaracteristiqueTemporelle tmp = new CaracteristiqueTemporelle();
		float abs0 = positions.get(0).x;
		float ord0 = positions.get(0).y;
		Iterator<CaracteristiqueTemporelle> it = positions.iterator();
		while(it.hasNext())
		{
			tmp = it.next();
			vitesseAbsisses.add(tmp.x() - abs0);
			abs0 = tmp.x();
			vitesseOrdonnees.add(tmp.y() - ord0);
			ord0 = tmp.y();
		}
		float sumAbs = 0;
		for (Float abs : vitesseAbsisses){
			sumAbs += abs;
		}
		vitesseAbs = sumAbs / vitesseAbsisses.size();
		float sumOrd = 0;
		for (Float ord : vitesseOrdonnees){
			sumOrd += ord;
		}
		vitesseOrd = sumOrd / vitesseOrdonnees.size();
	}

	public void analyseDistance(){
		//ArrayList<Float> vitesseAbsisses = new ArrayList<Float>(); 
		//ArrayList<Float> vitesseOrdonnees = new ArrayList<Float>();
		//CaracteristiqueTemporelle tmp = new CaracteristiqueTemporelle();
		int index = 0;
		distance = 0;
		float absTmp = positions.get(0).x;
		float ordTmp = positions.get(0).y;
		index++;
		while(index < positions.size()) {
			float pas = distancePas(absTmp, ordTmp, positions.get(index).x, positions.get(index).y);
			distance += pas;
			if (pasMax < pas) {
				pasMax = pas;
			}
			//System.out.println(distance);
			absTmp = positions.get(index).x;
			ordTmp = positions.get(index).y;
			index++;
		}
	}

	public void remplirSequenceMolecule(){
		try{
			effet.remplirSequenceur(this);
		}catch (Exception e){

		}
	}

	/**
	 * fonction pour trier les molécules avec instant et initial dans l'ordre croissant
	 * et à instant initial égal les instants finaux dans l'ordre croissant
	 */
	@Override
	public int compareTo(Molecule mol){
		if ((instantInitial < mol.instantInitial) && (instantFinal < mol.instantFinal)) {
			return -1;
		}
		else {
			if ((instantFinal == mol.instantFinal) && (instantInitial < mol.instantInitial)) {
				return 0;
			}
			else {
				if ((instantInitial == mol.instantInitial) && (instantFinal < mol.instantFinal)){
					return -1;
				}
				else {
					return 1;
				}
			}
		}
	}

	/**
	 * Sorte de constructeur par recopie
	 */
	@Override
	public Molecule clone(){
		Molecule mol = null;
		try {
			mol = (Molecule) super.clone();
		}
		catch(CloneNotSupportedException cnse) {
		}
		// for (CaracteristiqueTemporelle carac : positions) {
		//     mol.positions.add((CaracteristiqueTemporelle) carac.clone());
		// }
		return mol;
	}

	public float getAlpha() {
		return alpha;
	}

}

