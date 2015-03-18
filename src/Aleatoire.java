import java.util.ArrayList;
import java.lang.Math;
import javax.sound.midi.*;


//mouvements diffusifs (coté chercheur)

/* Les notes seront tirées aléatoirement entre la note principale et une note maximale. Cette note maximale sera déterminée avant l’émission de chaque note et dépendra de l’importance du pas effectué par la protéine.*/

class Aleatoire implements Effet{

	private int interNote;

	public Aleatoire(){
		interNote = Controleur.dureeNoire;
	}

	public int interNote(){
		return interNote;
	}

	public void setInterNote(int newInterNote){
		interNote = newInterNote;
	}

	public void remplirSequenceur( Molecule mol) throws InvalidMidiDataException
	{
		ArrayList<CaracteristiqueTemporelle> listPos = mol.positions();
		int ti = mol.positions.get(0).temps;
		int tf = mol.positions.get(mol.positions.size() - 1).temps;
		int note = Controleur.noteRef;


		int cpt = 1;
		int maxAlea = 0;
		Midi.noteTenue(note, mol.getVolume(),  mol.getTimbreAbs(), ti, ti+interNote);
		while (ti < tf){
			float pas = mol.distancePas(listPos.get(cpt-1).x, listPos.get(cpt-1).y, listPos.get(cpt).x, listPos.get(cpt).y);
			//MaxAlea entre 4 et 8 pour avoir une note aleatoire entre -8 et -4 et 4 et 8 suivant la note reference.
			maxAlea = (int) (Math.random()*(4*(pas/mol.pasMax()) + 4));
			if (Math.random() > 0.5){
				maxAlea = -maxAlea;
			}
			//System.out.printf("%d + %f \n",maxAlea, pas/mol.pasMax());
			//Midi.noteTenue(note + maxAlea, mol.getTimbre(), mol.getVolume(), ti, ti+INTER_NOTES);
			Midi.noteTenue(note + maxAlea, mol.getVolume(), mol.getTimbreAbs(), ti, ti+interNote);
			ti +=interNote;
			cpt++;
			if (listPos.size() <= cpt){
				cpt = 1;
			}
		}

	}

}
