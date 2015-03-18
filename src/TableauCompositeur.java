import java.awt.Toolkit;


public class TableauCompositeur {
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
	
	
	public void allocationTimbres(){
		int i=0;
		float copieAbscisses[]=new float[4];
		for(int k=0; k<4; k++)
			copieAbscisses[k]=abscisses[k]-abscisses[0];   //abscisses dcals  cause de jpanel...donc correction
		float maxTabAbsc=copieAbscisses[copieAbscisses.length - 1];
		float maxTabOrd = ordonnees[ordonnees.length-1];
		float scaleAbsc= maxTabAbsc/ControleurFenetres.getLargeurVideo();
		float scaleOrd=maxTabOrd/ControleurFenetres.getHauteurVideo();
		System.out.println("maxtababs et maxtabord : " + maxTabAbsc +" "+maxTabOrd);
		
		System.out.println("absc: " + copieAbscisses[0] + " "+copieAbscisses[1] + " "+copieAbscisses[2]+ " " +copieAbscisses[3]);
		System.out.println("timbre:" + timbreAbs[0].timbreMIDI() + " "+timbreAbs[1].timbreMIDI() + " "+timbreAbs[2].timbreMIDI());
		System.out.println("ord: " + ordonnees[0] + " "+ordonnees[1] + " "+ordonnees[2]+" " +ordonnees[3]);
		System.out.println("timbre: " + timbreOrd[0].timbreMIDI() + " "+timbreOrd[1].timbreMIDI() + " "+timbreOrd[2].timbreMIDI());
		
		for(Molecule mol: Controleur.molecules()){
			i++;System.out.println("mol "+i +" x et x converti : "+  mol.positions().get(0).x() + " "+ mol.positions().get(0).x() *scaleAbsc + " y et y converti : "+mol.positions().get(0).y()+" "+  mol.positions().get(0).y() *scaleOrd );
			int x=0; int y = 0;
			float  tmp,tmp2;


		 	              // first iterate through the "outer list"
			for(int j=0; j<copieAbscisses.length-1; j++){   // then iterate through all the "inner lists"
            	tmp=copieAbscisses[j];
            	tmp2=copieAbscisses[j+1];;
                 if(mol.positions().get(0).x()* scaleAbsc>=tmp && mol.positions().get(0).x() * scaleAbsc <=tmp2){
                	 x=j;
                	 break;
                 }
            }
	 	 		
	         for(int j=0; j<ordonnees.length-1; j++){   // then iterate through all the "inner lists"
	            	tmp=ordonnees[ordonnees.length-1]-ordonnees[j]; //car les ordonnees sont de bas en haut sur la video, mais de haut en bas sur le tableau compositeur
	            	tmp2=ordonnees[ordonnees.length-1]-ordonnees[j+1];
	            	System.out.println("tmp1 et tmp2 " + tmp + " " + tmp2);
	                 if(mol.positions().get(0).y()* scaleOrd<=tmp && mol.positions().get(0).y()* scaleOrd >=tmp2){
	                	 y=j;
	                	 break;
	                 }
	            }
	         
	        System.out.println("timbre x "+timbreAbs[x].timbreMIDI() + " timbre y "+timbreOrd[y].timbreMIDI());
			mol.setTimbreAbs(timbreAbs[x]);   //pour le moment on affecte qu'un seul timbre
			mol.setTimbreOrd(timbreOrd[y]);
			
		}
		Controleur.allocationNotes();
	}
    
}
