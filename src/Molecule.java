import java.util.LinkedList;

class Molecule{
/*
Cette classe contiens les différents attributs relatifs à une molécule
*/
    private int numero;
	protected LinkedList<CaracteristiqueTemporelle> positions;
    private int vitesse;
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
    
    public int vitesse(){
    	return vitesse;
    }
    
    public void setEffet (Effet effett){
    	effet = effett;
    }
    
    public void setTempo (int tempot){
    	tempo = tempot;
    }
    
    
    public void analyseMolecules(){
    	
    	
    }
    
}
