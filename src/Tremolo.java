import javax.sound.midi.*;


//mouvement confiné (coté compositeur)
class Tremolo implements Effet{
	public void remplirSequenceur( Molecule mol) throws InvalidMidiDataException{

		//System.out.println( "60 " + mol.getTimbre() + mol.getVolume() +  mol.positions.get(0).temps + " " + mol.positions.get(mol.positions.size() - 1).temps*100);
		Midi.tremolo(60, mol.getTimbre(),mol.getVolume(), mol.positions.get(0).temps, mol.positions.get(mol.positions.size() - 1).temps*100);

		//System.out.println( "60 " + " 56 " + " 64 " +  mol.positions.get(0).temps + " " + mol.positions.get(mol.positions.size() - 1).temps*100);
		//Midi.tremolo(60, 56, 64, mol.positions.get(0).temps*1000, mol.positions.get(mol.positions.size() - 1).temps*1000);

	}
}
