import java.util.ArrayList;
/**
 * Classe qui sert à savoir combien de molécules sont présentes 
 * dans un intervalle de temps donné.
 **/
class Intervalle {
    private int instantInitial;
    private int instantFinal;
    private int nombreMolecule;
    private ArrayList<Integer> timbres;
    
    public Intervalle(int instanti, int instantf, int nbMolecule) {
	instantInitial = instanti;
	instantFinal = instantf;
	nombreMolecule = nbMolecule;
	timbres = new ArrayList<Integer>();
    }

    public void printIntervalle(){
	System.out.println("==================================");
	System.out.println("Instant Initial : " + instantInitial);
	System.out.println("Instant Final : " + instantFinal);
	System.out.println("Max : " + nombreMolecule);
	for (Integer nb : timbres){
	    System.out.printf("%d ",nb);
	}
	System.out.println("==================================");

    }
    
    public int instantInitial(){
	return instantInitial;
    }

    public void setInstantInitial(int instantt){
	instantInitial = instantt;
    }

    public int instantFinal(){
	return instantFinal;
    }

    public void setInstantFinal(int instantt){
	instantFinal = instantt;
    }
	
    public int nombreMolecule(){
	return nombreMolecule;
    }

    public void setNombreMolecule(int nbMolecule){
	nombreMolecule = nbMolecule;
    }
    
    public void incrementeNombreMolecule(){
	nombreMolecule += 1;
    }
    
    public void ajouterTimbre(int numero){
	int i = 0;
	int index = timbres.size();
	while ((i < index) && ( (int) timbres.get(i) < numero)) {
	    i++;
	}
	if (i >= index) {
	    timbres.add(new Integer(numero));
	}
	else {
	    if (timbres.get(i) == numero) {
		return;
	    }
	    else {
		timbres.add(i, new Integer(numero));
	    }
	}
    }

    public void copierTimbres(ArrayList<Integer> liste){
	timbres = new ArrayList<Integer>(liste);
    }
    
    public ArrayList<Integer> timbres() {
	return timbres;
    }
}
