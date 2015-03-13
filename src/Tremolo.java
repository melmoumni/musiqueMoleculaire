import javax.sound.midi.*;


//mouvement confiné (coté compositeur)                                                           
class Tremolo implements Effet{

    private int nombrePas;
    private int variationNote;

    public Tremolo (int nbPas, int variation){
        nombrePas = nbPas;
        variationNote =variation;
    }

    public Tremolo(){
        nombrePas = 10;
        variationNote = 4;
    }


    public void remplirSequenceur( Molecule mol) throws InvalidMidiDataException{

        Midi.tremolo(mol,mol.getTimbre().timbreMIDI(), nombrePas, variationNote);

        }

    public void setNombrePas(int nbPas){
    nombrePas = nbPas;
    }

    public void    setVariationNote(int variation){
        variationNote =    variation;
    }

    public int nombrePas(){
        return nombrePas;
    }


    public int variationNote(){
    return variationNote;
    }
}
