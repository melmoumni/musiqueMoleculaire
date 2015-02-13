import javax.sound.midi.*;

import Utilitaires.Midi;


class Tremolo implements Effet{
	public void remplirSequenceur( Molecule mol) throws InvalidMidiDataException{
<<<<<<< HEAD
		System.out.println( "60 " + mol.getTimbre() + " 64 " +  mol.positions.get(0).temps + " " + mol.positions.get(mol.positions.size() - 1).temps*100);
		Midi.tremolo(60, 0, mol.getTimbre() , mol.positions.get(0).temps, mol.positions.get(mol.positions.size() - 1).temps*100);
=======
		System.out.println( "60 " + " 56 " + " 64 " +  mol.positions.get(0).temps + " " + mol.positions.get(mol.positions.size() - 1).temps*100);
		Midi.tremolo(60, 56, 64, mol.positions.get(0).temps*1000, mol.positions.get(mol.positions.size() - 1).temps*1000);
>>>>>>> d1c76a0583d666777a5c366ca460a0a805bed3f0
	}
}
