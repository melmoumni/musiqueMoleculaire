
public class Compositeur extends Controleur {
	public int seuilIntensite;

    public Compositeur(int seuilIntensitet) {
	super();
	seuilIntensite = seuilIntensitet;
    }
	
	
	public void setSeuil(int newseuil){
		seuilIntensite = newseuil;
	}
}
