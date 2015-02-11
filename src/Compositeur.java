
public class Compositeur extends Controleur {
	public int seuilIntensite;

    public Compositeur(int seuilIntensitet) {
	super(false);
	seuilIntensite = seuilIntensitet;
    }
	
	
	public void setSeuil(int newseuil){
		seuilIntensite = newseuil;
	}
}
