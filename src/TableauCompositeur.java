import java.awt.Toolkit;


public class TableauCompositeur extends Fenetre {
	public Timbre[] timbreAbs;
	public Timbre[] timbreOrd;
	public float[] abscisses;
	public float[] ordonnees;
	
	public static int MAX_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-200;
	public static int MAX_WIDTH = (int) (3*Toolkit.getDefaultToolkit().getScreenSize().getWidth()/5);

	
	TableauCompositeur (){
		abscisses = new float[4];
		ordonnees = new float[4];
		timbreAbs = new Timbre[3];
		timbreOrd = new Timbre[3];
		for (int i = 0 ; i <= 3 ; i++){
			abscisses[i] = (MAX_WIDTH/3) * (i+1);
			ordonnees[i] = (MAX_HEIGHT/3) * i;
		}
		timbreAbs[0] = Controleur.tableauTimbre[43]; // Contrebasse
		timbreAbs[1] = Controleur.tableauTimbre[42]; // Violoncelle
		timbreAbs[2] = Controleur.tableauTimbre[40]; // Violon
		timbreOrd[0] = Controleur.tableauTimbre[72]; // Clarinet
		timbreOrd[1] = Controleur.tableauTimbre[74]; // Flute
		timbreOrd[2] = Controleur.tableauTimbre[73]; // Piccolo
	}
	
	TableauCompositeur(Timbre[] Abs, Timbre[] Ord){
		abscisses = new float[4];
		ordonnees = new float[4];
		timbreAbs = new Timbre[3];
		timbreOrd = new Timbre[3];
		for (int i = 0 ; i < 3 ; i++){
			abscisses[i] = (MAX_WIDTH/3) * i;
			ordonnees[i] = (MAX_HEIGHT/3) * i;
			timbreAbs[i] = Abs[i];
			timbreOrd[i] = Ord[i];
		}
		abscisses[3] = MAX_WIDTH;
		ordonnees[3] = MAX_HEIGHT;
	}

	TableauCompositeur(Timbre[] Abs, Timbre[] Ord, int[] Abscisses, int[] Ordonnees){
		abscisses = new float[4];
		ordonnees = new float[4];
		timbreAbs = new Timbre[3];
		timbreOrd = new Timbre[3];
		for (int i = 0 ; i < 3 ; i++){
			abscisses[i] = Abscisses[i];
			ordonnees[i] = Ordonnees[i];
			timbreAbs[i] = Abs[i];
			timbreOrd[i] = Ord[i];
		}
		abscisses[3] = Abscisses[3];
		ordonnees[3] = Ordonnees[3];
	}
	
	public void setAbs (Timbre t1, Timbre t2, Timbre t3){
		timbreAbs[0] = t1;
		timbreAbs[1] = t2;
		timbreAbs[2] = t3;
	}

	public void setOrd (Timbre t1, Timbre t2, Timbre t3){
		timbreOrd[0] = t1;
		timbreOrd[1] = t2;
		timbreOrd[2] = t3;
	}
}
