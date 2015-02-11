import Utilitaires.Midi;
import javax.sound.midi.*;




class Glissando implements Effet{

	
    public Glissando (){
		
    }
    
    
    public void remplirSequenceur( Molecule mol) throws InvalidMidiDataException{
    	Midi.glissando(1, 0, 1, 1, 3, 5, 5);
    }


}
