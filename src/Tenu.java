import javax.sound.midi.*;

import Utilitaires.Midi;


class Tenu implements Effet{


	public void remplirSequenceur( Molecule mol) throws InvalidMidiDataException{
<<<<<<< HEAD
		System.out.println(Controleur.noteRef + " " +  mol.getTimbre() + " 64 " +  mol.positions.get(0).temps + " " + mol.positions.get(mol.positions.size() - 1).temps);
		Midi.noteTenue(60,  mol.getTimbre(), 100, mol.positions.get(0).temps, mol.positions.get(mol.positions.size() - 1).temps*1000);
=======
		System.out.println(Controleur.noteRef + " " + " 56 " + " 64 " +  mol.positions.get(0).temps + " " + mol.positions.get(mol.positions.size() - 1).temps);
		Midi.noteTenue(60, 56, 100, mol.positions.get(0).temps*1000, mol.positions.get(mol.positions.size() - 1).temps*1000);
>>>>>>> d1c76a0583d666777a5c366ca460a0a805bed3f0
	}
}
