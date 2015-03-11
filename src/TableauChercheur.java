import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.ListIterator;


public class TableauChercheur{
	public ArrayList<Float> abscisses;
	public ArrayList<ArrayList<Float>> ordonnees;
	public ArrayList<ArrayList<Timbre>> mat;
	public final static int MIN_CELL_WIDTH = 150;
	public final static int MIN_CELL_HEIGHT = 50;
	
	public static int MAX_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static int MAX_WIDTH = (int) (4*Toolkit.getDefaultToolkit().getScreenSize().getWidth()/5);
	
	
	public TableauChercheur (){
		abscisses = new ArrayList<Float>();
		ordonnees = new ArrayList<ArrayList<Float>>();
		mat = new ArrayList<ArrayList<Timbre>>();
	}
	
	/* Initialisation du tableauChercheur pour une matrice X x Y avec des abscisses et ordonnes rparties
	 * de fon homogne et une matrice initialise avec des timbres 0 (piano)
	 */
	public TableauChercheur (int x, int y){
		abscisses = new ArrayList<Float>();
		ordonnees = new ArrayList<ArrayList<Float>>();
		mat = new ArrayList<ArrayList<Timbre>>();
		
		for (int i = 0 ; i < y ; i++){
			abscisses.add((float) (i * (MAX_WIDTH/y) - 25*i));
		}
		abscisses.add((float) MAX_WIDTH - 25*y);
		
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
		int indice = abscisses.size()-1;
		int gain = 0;
		boolean full = false;
		/* On sépare la dernière colonne en 2*/
		if (abscisses.get(indice) /(abscisses.size()) >= MIN_CELL_WIDTH){
			abscisses.add(indice, (abscisses.get(indice) - abscisses.get(indice - 1))/2 + abscisses.get(indice - 1));
			
			/* Ou on décale si c'est trop petit*/
			if (((abscisses.get(indice+1) - abscisses.get(indice)) < MIN_CELL_WIDTH)){
				while (gain < MIN_CELL_WIDTH && !full ){
					if (abscisses.get(indice + 1) - abscisses.get(indice) > MIN_CELL_WIDTH ){
						gain += abscisses.get(indice + 1) - abscisses.get(indice) - MIN_CELL_WIDTH;
					}
					
					if (indice <= 0 && abscisses.get(1) - abscisses.get(0) < MIN_CELL_WIDTH*2){
						full = true;
					}
					
					if (gain < MIN_CELL_WIDTH && indice > 0){
						abscisses.set(indice, abscisses.get(indice+1)- MIN_CELL_WIDTH);
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
		
		/* On separe la dernere ligne en 2*/
		if (ordonnees.get(0).get(indice) /(ordonnees.get(0).size()) >= MIN_CELL_HEIGHT){

			for (int i = 0 ; i < abscisses.size() -1; i++){
				indice = ordonnees.get(i).size()-1;
				gain = 0;
				full = false;
				
				ordonnees.get(i).add(ordonnees.get(i).size() - 1,(ordonnees.get(i).get(ordonnees.get(i).size()-1) - ordonnees.get(i).get(ordonnees.get(i).size()-2))/2 + ordonnees.get(i).get(ordonnees.get(i).size() - 2));

				/* Ou on dale si c'est trop petit*/
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
		if (abscisses.size() >3){
			abscisses.remove(abscisses.size() - 2);
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
	
	public void allocationTimbres(){
		int i=0;

		//final Controleur controleur = new Chercheur();
		int timbre;
		float maxTabAbsc=abscisses.get(abscisses.size()-1);
		float maxTabOrd = ordonnees.get(0).get(ordonnees.get(0).size()-1);
		float scaleAbsc= maxTabAbsc/ControleurFenetres.getLargeurVideo();
		float scaleOrd=maxTabOrd/ControleurFenetres.getHauteurVideo();
		System.out.println("maxtababs et maxtabord : " + maxTabAbsc +" "+maxTabOrd);
		for(Molecule mol: Controleur.molecules()){
			i++;System.out.println("mol "+i +" x et x converti : "+  mol.positions().get(0).x() + " "+ mol.positions().get(0).x() *scaleAbsc + " y et y converti : "+mol.positions().get(0).y()+" "+  mol.positions().get(0).y() *scaleOrd );
			int x=0; int y = 0;
			float  tmp,tmp2;
			int index=-1;
			ListIterator<Float> it = abscisses.listIterator();
			
			if(it.hasNext())	{
		 		tmp=it.next();
		 		while (it.hasNext()) {
		 			index++;
		 			tmp2=it.next();System.out.println("index "+ index+" tmp : "+tmp+" tmp2 "+ tmp2);
		 			if(mol.positions().get(0).x() *scaleAbsc >= tmp && mol.positions().get(0).x()*scaleAbsc <= tmp2 ){
		 				x=index;
		 				System.out.println("trouv  x: "+x);
		 				break;
		 			}
		 			tmp=tmp2;
		 		}
			}

	 		/*it = ordonnees.get(x).listIterator();
			if(it.hasNext())	{

		 		tmp=it.next();
		 		while (it.hasNext()) {
		 			index++;
		 			tmp2=it.next();
		 			if(mol.positions().get(0).y()*scaleOrd > tmp && mol.positions().get(0).y()*scaleOrd < tmp2 ){
		 				y=index;

		 				break;
		 			}
		 			tmp=tmp2;
		 		}
			}*/
		 	              // first iterate through the "outer list"
	 	 		
	         for(int j=0; j<ordonnees.get(0).size()-1; j++){   // then iterate through all the "inner lists"
	            	tmp=ordonnees.get(x).get(j);
	            	tmp2=ordonnees.get(x).get(j+1);
	            	System.out.println("j "+j + " tmp "+tmp+ " tmp2 "+tmp2);
	                 if(mol.positions().get(0).y()* scaleOrd>=tmp && mol.positions().get(0).y() * scaleOrd <=tmp2){
	                	 y=j;
	                	 System.out.println("trouve  y: "+y);
	                	 break;
	                 }
	            }
         // first iterate through the "outer list"
	
	           /* for(int j=0; j<ordonnees.get(x).size()-1; j++){   // then iterate through all the "inner lists"
	            	tmp=ordonnees.get(x).get(j);
	            	tmp2=ordonnees.get(x).get(j+1);
	                 if(ordonnees.get(x).get(j)* scaleAbsc>=tmp && ordonnees.get(x).get(j) * scaleAbsc <=tmp2){
	                	 y=j;
	                	 break;
	                 }
	            }*/
	        
	       
	        System.out.println("mat size x: " + mat.size()+" mat size y: " + mat.get(0).size());
			System.out.println("x "+ x + " y "+y+" timbre " + mat.get(x).get(y).timbreMIDI());
			mol.setTimbre(mat.get(x).get(y).timbreMIDI());
			
			
		}
		Controleur.allocationNotes();
	}
}
