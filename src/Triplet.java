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

    public int getInstantInitial(){
	return instantInitial;
    }

    public void setInstantInitial(int instantt){
	instantInitial = instantt;
    }

    public int getInstantFinal(){
	return instantFinal;
    }

    public void setInstantFinal(int instantt){
	instantFinal = instantt;
    }
	
    public int getNombreMolecule(){
	return nombreMolecule;
    }

    public void setNombreMolecule(int nbMolecule){
	nombreMolecule = nbMolecule;
    }
    
    public void incrementeNombreMolecule(){
	nombreMolecule += 1;
}
