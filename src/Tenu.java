import javax.sound.midi.*;


//mouvement immobile
class Tenu implements Effet{
	private int note;

	public Tenu(){
		note = 60;
	}

	public Tenu(int notet){
		note = notet;
	}

	public void setNote(int notet){
		note = notet;
	}

	public int note(){
		return note;
	}

	public void remplirSequenceur( Molecule mol) throws InvalidMidiDataException{

		//System.out.println(Controleur.noteRef + " " +  mol.getTimbre() + mol.getVolume() +  mol.positions.get(0).temps + " " + mol.positions.get(mol.positions.size() - 1).temps);
		//Midi.noteTenue(60,  mol.getTimbre().timbreMIDI(), mol.getVolume(), mol.positions.get(0).temps, mol.positions.get(mol.positions.size() - 1).temps);
		Midi.noteTenue(mol.noteAbs(), mol.getVolume(), mol.getTimbreAbs(), mol.positions.get(0).temps, mol.positions.get(mol.positions.size() - 1).temps);
		if (!Controleur.isChercheur) {
			Midi.noteTenue(mol.noteOrd(), mol.getVolume(), mol.getTimbreOrd(), mol.positions.get(0).temps, mol.positions.get(mol.positions.size() - 1).temps);
		}
		//System.out.println(Controleur.noteRef + " " + " 56 " + " 64 " +  mol.positions.get(0).temps + " " + mol.positions.get(mol.positions.size() - 1).temps);
		//Midi.noteTenue(60, 56, 100, mol.positions.get(0).temps, mol.positions.get(mol.positions.size() - 1).temps);

	}
}
