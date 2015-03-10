import java.util.ArrayList;
import java.lang.Math;
import javax.sound.midi.*;


//mouvements diffusifs (coté chercheur)

/* Les notes seront tirées aléatoirement entre la note principale et une note maximale. Cette note maximale sera déterminée avant l’émission de chaque note et dépendra de l’importance du pas effectué par la protéine.*/

class Aleatoire implements Effet{

    public static int INTER_NOTES = 1000;

	
	public void remplirSequenceur( Molecule mol) throws InvalidMidiDataException
	{
		ArrayList<CaracteristiqueTemporelle> listPos = mol.positions();
		int ti = mol.positions.get(0).temps*1000;
    	int tf = mol.positions.get(mol.positions.size() - 1).temps*1000;
    	//int note = Controleur.noteRef;
    	
    	int note = 60;
    	
    	int cpt = 1;
    	int maxAlea = 0;
		//Midi.noteTenue(note,  mol.getTimbre(), mol.getVolume(), ti, ti+INTER_NOTES);
    	Midi.noteTenue(note,  56, 100, ti, ti+INTER_NOTES);
    	while (ti < tf){
    		float pas = mol.distancePas(listPos.get(cpt-1).x, listPos.get(cpt-1).y, listPos.get(cpt).x, listPos.get(cpt).y);
    		//MaxAlea entre 4 et 8 pour avoir une note aleatoire entre -8 et -4 et 4 et 8 suivant la note reference.
    		maxAlea = (int) (Math.random()*(4*(pas/mol.pasMax()) + 4));
    		if (Math.random() > 0.5){
    			maxAlea = -maxAlea;
    		}
    		//System.out.printf("%d + %f \n",maxAlea, pas/mol.pasMax());
    		//Midi.noteTenue(note + maxAlea, mol.getTimbre(), mol.getVolume(), ti, ti+INTER_NOTES);
    		Midi.noteTenue(note + maxAlea, 56, 100, ti, ti+INTER_NOTES);
    		ti +=INTER_NOTES;
    		cpt++;
    	}
	
	}

}
