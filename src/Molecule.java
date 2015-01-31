import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;


class Molecule{
/*
Cette classe contiens les différents attributs relatifs à une molécule
*/
    private int numero;
	protected LinkedList<CaracteristiqueTemporelle> positions;
    private float vitesseAbs;
    private float vitesseOrd;
    private Timbre timbre;
    private float alpha;
    private float msd;
    private Effet effet;
    private int tempo;
    private int pasMax;
    Molecule(){
	positions = new LinkedList<CaracteristiqueTemporelle>();
    }
    
    public Molecule(int numerot, LinkedList<CaracteristiqueTemporelle> positionst){
    	numero = numerot;
    	positions = positionst;
    	analyseMolecules();
    }

    public void setMolecule(int alphat, int msdt){
    	alpha = alphat;
    	msd = msdt;
    }
    
    public int numero(){
    	return numero;
    }
    
    public LinkedList<CaracteristiqueTemporelle> positions(){
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
    
    
    public void analyseMolecules(){
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
