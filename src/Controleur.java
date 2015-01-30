import java.util.ArrayList;
import java.util.Vector;

abstract class Controleur{

    public ArrayList<Molecule> molecules;
    public int duree;
    public int noteRef; 
    private Vue vue;
    public Vector<Fenetre> fenetres;
 
    public Controleur(){
    	vue = new Vue();
    	fenetres = new Vector<Fenetre>();
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
    
    
}
