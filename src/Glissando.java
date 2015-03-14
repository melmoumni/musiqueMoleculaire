
import javax.sound.midi.*;



// chercheur : mouvement directionnel;  compositeur: mouvements diffusifs et directionnels
class Glissando implements Effet{
    private int molette;
    static public int distanceMax;
	
    static{
	distanceMax = 100;
    }
    
    public Glissando(int molettet){
	molette = molettet;
    }
    
    public Glissando(){
	molette = 64;
    }

    public int molette(){
	return molette;
    }
    
    static public void setDistance(int distance){
    	distanceMax = distance;
    }
    
    public void setMolette(int mol){
    	molette = mol;
    }
    
    
    public void remplirSequenceur(Molecule mol) throws InvalidMidiDataException{

    	//System.out.println("60  "+ mol.getTimbre()+ mol.getVolume() + mol.positions.get(0).temps + " " + mol.positions.get(mol.positions.size() - 1).temps*1000 + " 70 " + (int) mol.vitesseOrd());
    	//Midi.glissando(mol.note,  mol.getTimbre().timbreMIDI(), mol.getVolume(), mol.positions.get(0).temps, mol.positions.get(mol.positions.size() - 1).temps *1000, 20, (int) mol.vitesseOrd());
	Midi.glissando(mol, molette, mol.getTimbre());
	if (!Controleur.isChercheur) {
	    Midi.glissando(mol, molette, mol.getTimbre2());
	}
   // 	System.out.println("60  56  100 " + mol.positions.get(0).temps + " " + mol.positions.get(mol.positions.size() - 1).temps*1000 + " 70 " + (int) mol.vitesseOrd());
///    	Midi.glissando(60, 56, 100, mol.positions.get(0).temps, mol.positions.get(mol.positions.size() - 1).temps *1000,100, (int) mol.vitesseOrd());

    }


}
