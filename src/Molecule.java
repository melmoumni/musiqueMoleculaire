import java.util.LinkedList;

class Molecule{
/*
Cette classe contiens les différents attributs relatifs à une molécule
*/
    private int numero;
	protected LinkedList<CaracteristiqueTemporelle> positions;
    private int vitesse;
    private Timbre timbre;
    //private int noteref;
    private float alpha;
    private float msd;
    //private int seuil_intens;
    private Effet effet;
    private int tempo;
    private int pasmax;
    Molecule(){
	positions = new LinkedList<CaracteristiqueTemporelle>();
    }
}
