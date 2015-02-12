import javax.sound.midi.*;

import Utilitaires.Midi;


class Tenu implements Effet{


	public void remplirSequenceur( Molecule mol) throws InvalidMidiDataException{
		System.out.println(Controleur.noteRef + " " + " 0 " + " 64 " +  mol.positions.get(0).temps + " " + mol.positions.get(mol.positions.size() - 1).temps);
		Midi.noteTenue(60, 0, 100, mol.positions.get(0).temps, mol.positions.get(mol.positions.size() - 1).temps*1000);
	}
}
