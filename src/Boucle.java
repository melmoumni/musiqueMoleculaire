import javax.sound.midi.*;



//pour les mouvements confinés (coté chercheur)
/*Les boucles sont constitués des notes de l’accord majeur associées à la tonalité choisie par l’utilisateur pouvant s’étaler sur plusieurs octaves. Le nombre de notes de la boucle dépend linéairement de la surface couverte par cette protéine. 
Le nombre de note sera compris entre 4 (surface minimale) et 7 (surface maximale).
Par exemple si la note principale est le Do, alors la protéine ayant la surface minimale par rapport aux autres sera composée des notes Do-Mi-Sol-Do, tandis que celle ayant la surface maximale sera composée des notes Do-Mi-Sol-Do-Mi-Sol-Do- sur 3 octaves.*/


//où sont définies les surfaces max et min? dans effet?

class Boucle implements Effet{

	private int nbNotes;
	private static float minMSD;   
	private static float maxMSD;
	private int interNote = Controleur.dureeNoire;

	static{
		minMSD = 0;
		maxMSD = (float) 0.05;		
	}
	
	public Boucle (float msd){
		// nbNotes entre 4 et 7.
		nbNotes =  (int) (3* ((msd - minMSD) / (maxMSD - minMSD)) + 4);
		System.out.println("nbNotes Constructeur : " + nbNotes + "msd : " + msd);

	}

	protected static void setMinMSD(float msd){
		minMSD = msd;
	}
	
	protected static void setMaxMSD(float msd){
		maxMSD = msd;
	}
	
	public void setNbNotes(int nb){
		nbNotes = nb;
	}

	public int nbNotes(){
		return nbNotes;
	}

	public int interNote(){
		return interNote;
	}

	public void setInterNote(int newInterNote){
		interNote = newInterNote;
	}


	public Boucle (){
		nbNotes = 6;
	}

	public void remplirSequenceur( Molecule mol) throws InvalidMidiDataException {
		int ti = mol.positions.get(0).temps;
		int tf = mol.positions.get(mol.positions.size() - 1).temps;
		int tmpNote = mol.noteAbs();
		int nextIntervalle;


		//System.out.printf("NbNote : %d \n", nbNotes);
		while (ti < tf){
			nextIntervalle = 4;
			//tmpNote = Controleur.noteRef;
			tmpNote = mol.noteAbs();
			for (int i = 0 ; i < nbNotes ; i++){
				if (ti < tf){

					System.out.printf("%d ", tmpNote);
					Midi.noteTenue(tmpNote, mol.getVolume(), mol.getTimbreAbs(), ti, ti + interNote);
					tmpNote += nextIntervalle;
					System.out.println(nextIntervalle);
				}
				System.out.printf("%d ", tmpNote);
				System.out.println(nextIntervalle);
				ti+=interNote/Controleur.dureeNoire;
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
