import javax.sound.midi.*;

//pour les mouvements confinés (coté chercheur)
/*Les boucles sont constitués des notes de l’accord majeur associées à la tonalité choisie par l’utilisateur pouvant s’étaler sur plusieurs octaves. Le nombre de notes de la boucle dépend linéairement de la surface couverte par cette protéine. 
Le nombre de note sera compris entre 2 (surface minimale) et 9 (surface maximale).
Par exemple si la note principale est le Do, alors la protéine ayant la surface minimale par rapport aux autres sera composée des notes Do-Mi, tandis que celle ayant la surface maximale sera composée des notes Do-Mi-Sol-Do-Mi-Sol-Do-Mi-Sol sur 3 octaves.*/


//où sont définies les surfaces max et min? dans effet?

class Boucle implements Effet{

    private int nbnotes;

    public Boucle (int nbNotes){
    	nbnotes = nbNotes;
    }

    public Boucle (){

    }
    
    public void remplirSequenceur( Molecule mol) throws InvalidMidiDataException {}
}
