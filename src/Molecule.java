import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;


class Molecule{
/*
Cette classe contiens les différents attributs relatifs à une molécule
*/
    private int numero;
    protected ArrayList<CaracteristiqueTemporelle> positions;
    private float vitesseAbs;
    private float vitesseOrd;
    private Timbre timbre;
    private float alpha;
    private float msd;
    private Effet effet;
    private int tempo; // ou pas !!!
    private int pasMax; // juste pour les boucles
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
    	//System.out.println("Effet" + effet.getClass().getName());
    	//System.out.println("Tempo : "+tempo);
    	System.out.println("PasMax : " + pasMax);
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
    
    public int vitesse(){
    	return (int) Math.sqrt(vitesseAbs()*vitesseAbs() + vitesseOrd() * vitesseOrd());
    }
    
    public void setEffet (Effet effett){
    	effet = effett;
    }
    
    public void setTempo (int tempot){
    	tempo = tempot;
    }
    
    
    public void analyseMolecule(float[] alphaSeparation, boolean isChercheur){
    	analyseVitesse();
    	analyseAlpha(alphaSeparation, isChercheur);
    	
    }	
    private void analyseAlpha(float[] alphaSeparation, boolean isChercheur) {
    	if (alpha < alphaSeparation[0]){
    		
    	}else if (alpha > alphaSeparation[0] && alpha > alphaSeparation[1]){
    		
    	}else if (alpha > alphaSeparation[1] && alpha > alphaSeparation[2]){
    		
    	}else {
    		
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

    
}
