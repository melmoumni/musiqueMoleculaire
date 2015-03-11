import javax.sound.midi.*;



//pour les mouvements confinés (coté chercheur)
/*Les boucles sont constitués des notes de l’accord majeur associées à la tonalité choisie par l’utilisateur pouvant s’étaler sur plusieurs octaves. Le nombre de notes de la boucle dépend linéairement de la surface couverte par cette protéine. 
Le nombre de note sera compris entre 2 (surface minimale) et 9 (surface maximale).
Par exemple si la note principale est le Do, alors la protéine ayant la surface minimale par rapport aux autres sera composée des notes Do-Mi, tandis que celle ayant la surface maximale sera composée des notes Do-Mi-Sol-Do-Mi-Sol-Do-Mi-Sol sur 3 octaves.*/


//où sont définies les surfaces max et min? dans effet?

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
		//System.out.printf("NbNote : %d \n", nbNotes);
    	while (ti < tf){
    		nextIntervalle = 4;
    		//tmpNote = Controleur.noteRef;
    		tmpNote = 60;
    		for (int i = 0 ; i < nbNotes ; i++){
    			if (ti < tf){

    				//System.out.printf("%d ", tmpNote);
    				Midi.noteTenue(tmpNote,  mol.getTimbre().timbreMIDI(), mol.getVolume(), ti, ti + INTER_NOTES);
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
    				System.err.println("Bug du prochain intervalle");
    				break;
    			}
    		}
    	}
    }
}
