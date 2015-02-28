/**
 * Classe qui sert à savoir combien de molécules sont présentes 
 * dans un intervalle de temps donné.
 **/
class Triplet {
    private int instantInitial;
    private int instantFinal;
    private int nombreMolecule;
    
    public Triplet(int instanti, int instantf, int nbMolecule) {
	instantInitial = instanti;
	instantFinal = instantf;
	nombreMolecule = nbMolecule;
    }

    public void printTriplet(){
	System.out.println("==================================");
	System.out.println("Instant Initial : " + instantInitial);
	System.out.println("Instant Final : " + instantFinal);
	System.out.println("Max : " + nombreMolecule);
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
}
