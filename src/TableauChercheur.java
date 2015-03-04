import java.awt.Toolkit;
import java.util.ArrayList;


public class TableauChercheur extends Fenetre {
	public ArrayList<Float> absisses;
	public ArrayList<ArrayList<Float>> ordonnees;
	public ArrayList<ArrayList<Timbre>> mat;
	public final static int MIN_CELL_WIDTH = 150;
	public final static int MIN_CELL_HEIGHT = 50;
	
	public static int MAX_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static int MAX_WIDTH = (int) (4*Toolkit.getDefaultToolkit().getScreenSize().getWidth()/5);
	
	
	public TableauChercheur (){
		absisses = new ArrayList<Float>();
		ordonnees = new ArrayList<ArrayList<Float>>();
		mat = new ArrayList<ArrayList<Timbre>>();
	}
	
	/* Initialisation du tableauChercheur pour une matrice X x Y avec des abscisses et ordonnées réparties
	 * de façon homogène et une matrice initialisée avec des timbres 0 (piano)
	 */
	public TableauChercheur (int x, int y){
		absisses = new ArrayList<Float>();
		ordonnees = new ArrayList<ArrayList<Float>>();
		mat = new ArrayList<ArrayList<Timbre>>();
		
		for (int i = 0 ; i < y ; i++){
			absisses.add((float) (i * (MAX_WIDTH/y) - 25*i));
		}
		absisses.add((float) MAX_WIDTH - 25*y);
		
		for (int i = 0 ; i < y ; i++){
			ArrayList<Float> t = new ArrayList<Float>();
			for (int j = 0 ; j < x ; j++){
				t.add((float) (j * (MAX_HEIGHT/x) - 25*j));
			}
			t.add((float) MAX_HEIGHT - 25 *x);
			ordonnees.add(t);
		}
		
		for (int i = 0 ; i < y ; i++){
			ArrayList<Timbre> t = new ArrayList<Timbre>(); 
			for (int j = 0 ; j < x ; j++){
				Timbre tim = new Timbre(0);
				tim = Controleur.tableauTimbre[0];
				t.add(tim);
			}
			mat.add(t);
		}
	}
	
	public void ajouterColonne(){
		int indice = absisses.size()-1;
		int gain = 0;
		boolean full = false;
		/* On sépare la dernière colonne en 2*/
		if (absisses.get(indice) /(absisses.size()) >= MIN_CELL_WIDTH){
			absisses.add(indice, (absisses.get(indice) - absisses.get(indice - 1))/2 + absisses.get(indice - 1));
			
			/* Ou on décale si c'est trop petit*/
			if (((absisses.get(indice+1) - absisses.get(indice)) < MIN_CELL_WIDTH)){
				while (gain < MIN_CELL_WIDTH && !full ){
					if (absisses.get(indice + 1) - absisses.get(indice) > MIN_CELL_WIDTH ){
						gain += absisses.get(indice + 1) - absisses.get(indice) - MIN_CELL_WIDTH;
					}
					
					if (indice <= 0 && absisses.get(1) - absisses.get(0) < MIN_CELL_WIDTH*2){
						full = true;
					}
					
					if (gain < MIN_CELL_WIDTH && indice > 0){
						absisses.set(indice, absisses.get(indice+1)- MIN_CELL_WIDTH);
					}
					indice--;
					
				}
			}
			
			ArrayList<Float> t1 = new ArrayList<Float>();
			for (Float f : ordonnees.get(ordonnees.size()-1)){
				t1.add(f);
			}
			ordonnees.add(t1);
			ArrayList<Timbre> t2 = new ArrayList<Timbre>();
			for (Timbre tim : mat.get(mat.size()- 1)){
				Timbre newTimbre = tim;
				t2.add(newTimbre);
			}
			mat.add(t2);
				
		}
	}
	
	public void ajouterLigne(){
		int indice = ordonnees.get(0).size()-1;
		int gain = 0;
		boolean full = false;
		
		/* On sépare la dernière ligne en 2*/
		if (ordonnees.get(0).get(indice) /(ordonnees.get(0).size()) >= MIN_CELL_HEIGHT){

			for (int i = 0 ; i < absisses.size() -1; i++){
				indice = ordonnees.get(i).size()-1;
				gain = 0;
				full = false;
				
				ordonnees.get(i).add(ordonnees.get(i).size() - 1,(ordonnees.get(i).get(ordonnees.get(i).size()-1) - ordonnees.get(i).get(ordonnees.get(i).size()-2))/2 + ordonnees.get(i).get(ordonnees.get(i).size() - 2));

				/* Ou on décale si c'est trop petit*/
				if (((ordonnees.get(i).get(indice+1) - ordonnees.get(i).get(indice)) < MIN_CELL_HEIGHT)){
					while (gain < MIN_CELL_HEIGHT && !full ){
						if (ordonnees.get(i).get(indice + 1) - ordonnees.get(i).get(indice) > MIN_CELL_HEIGHT ){
							gain += ordonnees.get(i).get(indice + 1) - ordonnees.get(i).get(indice) - MIN_CELL_HEIGHT;
						}

						if (indice <= 0 && ordonnees.get(i).get(1) - ordonnees.get(i).get(0) < MIN_CELL_HEIGHT*2){
							full = true;
						}

						if (gain < MIN_CELL_HEIGHT && indice > 0){
							ordonnees.get(i).set(indice, ordonnees.get(i).get(indice+1)- MIN_CELL_HEIGHT);
						}
						indice--;

					}
				}
			}
		
			
			
			for (ArrayList<Timbre> AT : mat){
				Timbre newTimbre = AT.get(AT.size() - 1);
				AT.add(newTimbre);
			}
		}
	}
	
	public void supprimerColonne(){
		if (absisses.size() >3){
			absisses.remove(absisses.size() - 2);
			ordonnees.remove(ordonnees.size() - 1);
			mat.remove(mat.size() - 1);
		}
	}
	
	public void supprimerLigne(){
		if (ordonnees.get(0).size() > 4){
			for (ArrayList<Float> af : ordonnees){
				af.remove(af.size()-2);
			}
			for (ArrayList<Timbre> AT : mat){
				AT.remove(AT.size() - 2);
			}
		}
	}
	
//	public void changerTailleColonne(int numColonne, float newTaille){
//
//		if(numColonne > 0 && numColonne < absisses.size()-1){
//			absisses.set(numColonne, newTaille + absisses.get(numColonne - 1));
//		}
//		else if (numColonne == absisses.size() - 1){
//			float oldSize = absisses.get(numColonne) - absisses.get(numColonne-1);
//			absisses.set(numColonne-1, (oldSize - newTaille) + absisses.get(numColonne - 1));
//		}
//	}
//	
//	public void changerTailleLigne(int numLigne, float newTaille){
//		if(numLigne > 0 && numLigne < ordonnees.size()-1){
//			ordonnees.set(numLigne, newTaille + ordonnees.get(numLigne - 1));
//		}
//		else if (numLigne == ordonnees.size() - 1){
//			float oldSize = ordonnees.get(numLigne) - ordonnees.get(numLigne-1);
//			ordonnees.set(numLigne-1, (oldSize - newTaille) + ordonnees.get(numLigne - 1));
//		}
//	}
	
	
}
