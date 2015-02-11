import javax.sound.midi.*;


interface Effet{

	public void remplirSequenceur(Molecule mol) throws InvalidMidiDataException;

}
