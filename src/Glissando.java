import Utilitaires.Midi;
import javax.sound.midi.*;



// chercheur : mouvement directionnel;  compositeur: mouvements diffusifs et directionnels
class Glissando implements Effet{

	
    public Glissando (){
		
    }
    
    
    public void remplirSequenceur( Molecule mol) throws InvalidMidiDataException{
    	System.out.println("ON EST LA");

    	System.out.println("60  "+ mol.getTimbre()+ mol.getVolume() + mol.positions.get(0).temps + " " + mol.positions.get(mol.positions.size() - 1).temps*1000 + " 70 " + (int) mol.vitesseOrd());
    	Midi.glissando(60,  mol.getTimbre(), mol.getVolume(), mol.positions.get(0).temps, mol.positions.get(mol.positions.size() - 1).temps *1000, 20, (int) mol.vitesseOrd());

   // 	System.out.println("60  56  100 " + mol.positions.get(0).temps + " " + mol.positions.get(mol.positions.size() - 1).temps*1000 + " 70 " + (int) mol.vitesseOrd());
///    	Midi.glissando(60, 56, 100, mol.positions.get(0).temps, mol.positions.get(mol.positions.size() - 1).temps *1000,100, (int) mol.vitesseOrd());

    }


}
