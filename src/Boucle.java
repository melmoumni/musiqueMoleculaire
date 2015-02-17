import javax.sound.midi.*;

import Utilitaires.Midi;



class Boucle implements Effet{

    private int nbnotes;
    private float minMSD;
    private float maxMSD;
    public static int INTER_NOTES = 1000;
    
    public Boucle (int nbNotes){
    	nbnotes = nbNotes;
    }

    public Boucle (){

    }
    
    public void remplirSequenceur( Molecule mol) throws InvalidMidiDataException {
    	int ti = mol.positions.get(0).temps*1000;
    	int tf = mol.positions.get(mol.positions.size() - 1).temps*1000;
    	//int tmpNote = Controleur.noteRef;
    	int tmpNote = 60;
    	int nextIntervalle;
    	
    	// Temporairement : msdMIN et msdMAX
    	minMSD = 0;
    	maxMSD = (float) 0.05;
    	// Temporaire
    	
    	// nbNotes entre 4 et 7.
    	int nbNotes =  (int) (3* ((mol.msd() - minMSD) / (maxMSD - minMSD)) + 4);
		System.out.printf("NbNote : %d \n", nbNotes);
    	while (ti < tf){
    		nextIntervalle = 4;
    		//tmpNote = Controleur.noteRef;
    		tmpNote = 60;
    		for (int i = 0 ; i < nbNotes ; i++){
    			if (ti < tf){

    				System.out.printf("%d ", tmpNote);
    				Midi.noteTenue(tmpNote, 56, 100, ti, ti + INTER_NOTES);
    				tmpNote += nextIntervalle;
    			}
    			ti+=INTER_NOTES;
    			switch (nextIntervalle){
    			case 0:
    				nextIntervalle = 4;
    				break;
    			case 4:
    				nextIntervalle = 3;
    				break;
    			case 3:
    				nextIntervalle = 5;
    				break;
    			case 5:
    				nextIntervalle = 4;    				
    				break;
    			default:
    				System.err.println("Bug du prochain interalle");
    				break;
    			}
    		}
    	}
    }
}
