import java.lang.Thread;
import java.util.List;

public class AffichageTrajectoiresThread {
    
    private AffichageTrajectoires affichage;

    public AffichageTrajectoiresThread(float largeur, float hauteur, int delaiRafraichissement, List<Molecule> molecules){
	affichage = new AffichageTrajectoires(largeur, hauteur, delaiRafraichissement, molecules);
    }

    public AffichageTrajectoires getAffichage(){
	return affichage;
    }

    public void run() throws InterruptedException{
	while(!affichage.aFini()){
	    affichage.miseAJour();
	}
    }
}
