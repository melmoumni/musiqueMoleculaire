import javax.sound.midi.*;

import Utilitaires.Midi;


class Tremolo implements Effet{
	public void remplirSequenceur( Molecule mol) throws InvalidMidiDataException{
		System.out.println( "60 " + " 0 " + " 64 " +  mol.positions.get(0).temps + " " + mol.positions.get(mol.positions.size() - 1).temps*100);
		Midi.tremolo(60, 0, 64, mol.positions.get(0).temps, mol.positions.get(mol.positions.size() - 1).temps*1000);
	}
}
