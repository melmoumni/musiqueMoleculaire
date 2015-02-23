import java.util.ArrayList;


public class TableauChercheur extends Fenetre {
	public ArrayList<Float> absisses;
	public ArrayList<Float> ordonnees;
	public ArrayList<ArrayList<Timbre>> mat;
	
	public static int MAX_HEIGHT = 1000;
	public static int MAX_WIDTH = 1000;
	
	
	public TableauChercheur (){
		absisses = new ArrayList<Float>();
		ordonnees = new ArrayList<Float>();
		mat = new ArrayList<ArrayList<Timbre>>();
	}
	
	/* Initialisation du tableauChercheur pour une matrice X x Y avec des abscisses et ordonnées réparties
	 * de façon homogène et une matrice initialisée avec des timbres 0 (piano)
	 */
	public TableauChercheur (int x, int y){
		absisses = new ArrayList<Float>();
		ordonnees = new ArrayList<Float>();
		mat = new ArrayList<ArrayList<Timbre>>();
		
		for (int i = 0 ; i < x ; i++){
			absisses.add((float) (i * (MAX_WIDTH/x)));
		}
		
		for (int j = 0 ; j < j ; j++){
			ordonnees.add((float) (j * (MAX_HEIGHT/x)));
		}
		
		for (int i = 0 ; i < y ; i++){
			ArrayList<Timbre> t = new ArrayList<Timbre>(); 
			for (int j = 0 ; j < x ; j++){
				Timbre tim = new Timbre(1);
				tim = Controleur.tableauTimbre[0];
				t.add(tim);
			}
			mat.add(t);
		}
	}
	
	public void ajouterColonne(){
		/* On sépare la dernière colonne en 2*/
		absisses.add(MAX_WIDTH - absisses.get(absisses.size() - 1)/2 + absisses.get(absisses.size() - 1));
		ArrayList<Timbre> t = new ArrayList<Timbre>();
		ArrayList<Timbre> lastCol = mat.get(mat.get(0).size() - 1);
		for (Timbre tim : lastCol){
			Timbre newTimbre = tim;
			t.add(newTimbre);
		}
		mat.add(t);
	}

	public void ajouterLigne(){
		/* On sépare la dernière ligne en 2*/
		ordonnees.add(MAX_HEIGHT - ordonnees.get(ordonnees.size() - 1)/2 + ordonnees.get(ordonnees.size() - 1));
		for (ArrayList<Timbre> AT : mat){
			Timbre newTimbre = AT.get(AT.size() - 1);
			AT.add(newTimbre);
		}
	}
	
	public void supprimerColonne(){
		absisses.remove(absisses.size() - 1);
		mat.remove(mat.size() - 1);
	}
	
	public void supprimerLigne(){
		ordonnees.remove(ordonnees.size()-1);
		for (ArrayList<Timbre> AT : mat){
			AT.remove(AT.size() - 1);
		}
	}
	
	public void changerTailleColonne(int numColonne, float newTaille){
		float oldSize = absisses.get(numColonne) - absisses.get(numColonne - 1);
		absisses.set(numColonne, newTaille + absisses.get(numColonne - 1));
		MAX_WIDTH += newTaille - oldSize;
	}
	
	public void changerTailleLigne(int numLigne, float newTaille){
		float oldSize = ordonnees.get(numLigne) - ordonnees.get(numLigne - 1);
		ordonnees.set(numLigne, newTaille + ordonnees.get(numLigne - 1));
		MAX_WIDTH += newTaille - oldSize;	
	}
	
	
}
