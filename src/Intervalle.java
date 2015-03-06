import java.util.ArrayList;
/**
 * Classe qui sert à savoir combien de molécules sont présentes 
 * dans un intervalle de temps donné.
 **/
class Intervalle {
    private int instantInitial;
    private int instantFinal;
    private int nombreMolecule;
    private ArrayList<Molecule> molecules;
    
    public Intervalle(int instanti, int instantf, int nbMolecule) {
	instantInitial = instanti;
	instantFinal = instantf;
	nombreMolecule = nbMolecule;
	molecules = new ArrayList<Molecule>();
    }
    
    public void printIntervalle(){
	System.out.println("==================================");
	System.out.println("Instant Initial : " + instantInitial);
	System.out.println("Instant Final : " + instantFinal);
	System.out.println("Max : " + nombreMolecule);
	for (Molecule mol : molecules){
	    System.out.printf("%d ",mol.numero());
	}
	System.out.printf("%n");
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
    
    public void ajouterTimbre(Molecule mol){
	int i = 0;
	int index = molecules.size();
	while ((i < index) && ( molecules.get(i).numero() < mol.numero())) {
	    i++;
	}
	if (i >= index) {
	    molecules.add(mol.clone());
	}
	else {
	    if (molecules.get(i).numero() == mol.numero()) {
		return;
	    }
	    else {
		molecules.add(i, mol.clone());
	    }
	}
    }

    public void copierTimbres(ArrayList<Molecule> liste){
	molecules = new ArrayList<Molecule>(liste);
    }
    
    public ArrayList<Molecule> molecules() {
	return molecules;
    }
}
