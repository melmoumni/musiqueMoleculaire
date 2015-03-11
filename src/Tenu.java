import javax.sound.midi.*;


//mouvement immobile
class Tenu implements Effet{


	public void remplirSequenceur( Molecule mol) throws InvalidMidiDataException{

		//System.out.println(Controleur.noteRef + " " +  mol.getTimbre() + mol.getVolume() +  mol.positions.get(0).temps + " " + mol.positions.get(mol.positions.size() - 1).temps);
	    Midi.noteTenue(60,  mol.getTimbre().timbreMIDI(), mol.getVolume(), mol.positions.get(0).temps, mol.positions.get(mol.positions.size() - 1).temps*1000);

		//System.out.println(Controleur.noteRef + " " + " 56 " + " 64 " +  mol.positions.get(0).temps + " " + mol.positions.get(mol.positions.size() - 1).temps);
		//Midi.noteTenue(60, 56, 100, mol.positions.get(0).temps*1000, mol.positions.get(mol.positions.size() - 1).temps*1000);

	}
}
