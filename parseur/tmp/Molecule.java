package tmp;
import java.util.LinkedList;


class Molecule{
    protected int numero;
    protected LinkedList<CaracteristiqueTemporelle> positions;


    Molecule(){
	positions = new LinkedList<CaracteristiqueTemporelle>();
    }
    Molecule(int i){
	positions = new LinkedList<CaracteristiqueTemporelle>();
	numero = i;
    }


}
