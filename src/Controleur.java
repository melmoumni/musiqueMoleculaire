import java.util.ArrayList;
import java.util.Vector;

abstract class Controleur{

    public ArrayList<Molecule> molecules;
    public int duree;
    public int noteRef; 
    private Vue vue;
    public Vector<Fenetre> fenetres;
    public int periode;
 
    public Controleur(){
    	vue = new Vue();
    	fenetres = new Vector<Fenetre>();
    }
    
    public void printMolecules(){
    	for (Molecule mol : molecules){
    		mol.printMolecule();
    	}
    }
    
    public ArrayList<Molecule> molecules(){
    	return molecules;
    }
    
    public int periode(){
    	return periode;
    }
    
    public void setMolecules(ArrayList<Molecule> newList){
    	molecules = newList;
    }
    
    public void setDuree(int newduree){
    	duree = newduree;
    }

    public void setNote(int newnote){
    	noteRef = newnote;
    }
    
    public void setPeriode (int p){
    	periode = p;
    }
    
    
    protected void setTempo (){
    	
    }
    
}
