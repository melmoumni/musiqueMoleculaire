package Utilitaires;
import javax.sound.midi.*;




public class Midi{
    

    private Sequencer sequenceur;
    private Synthesizer synthetiseur;

    public Midi(){
	try{
	    sequenceur = MidiSystem.getSequencer();
	    synthetiseur = MidiSystem.getSynthesizer();
	}
	catch(MidiUnavailableException e){}
    }
    
    public void initialiser() throws MidiUnavailableException, InvalidMidiDataException{
	//lier le sequenceur ar defaut au synthetiseur par defaut
	Transmitter seqTrans = sequenceur.getTransmitter();
	Receiver synthRcvr = synthetiseur.getReceiver(); 
	seqTrans.setReceiver(synthRcvr);     
	//ouvrir sequenceur
	sequenceur.open();
	//ouvrir synthetiseur
	synthetiseur.open();
	/* charge une nouvelle sequence avec 16 tracks
	 * resolution = 960PPQ
	 * ticksParSeconde = resolution * (Tempo / 60.0);
	 * dureeTick = 1.0 / ticksParSeconde;
	 * dureeNoire = 960*dureeTick
	 */
	sequenceur.setSequence(new Sequence(Sequence.PPQ,960,16));
	//chargement des instruments dans le synthetiseur
	MidiChannel[] m = synthetiseur.getChannels();
	Soundbank soundbank = synthetiseur.getDefaultSoundbank();
        synthetiseur.loadAllInstruments(soundbank);
    }
    

    //liberer le sequeceur et le synthetiseur
    public void liberer(){
	sequenceur.close();
	synthetiseur.close();
    }
    
    //charger un instrument dans un channel 
    public void configurerChannel(int numChannel, int instrument){
	MidiChannel[] m = synthetiseur.getChannels();
	Soundbank soundbank = synthetiseur.getDefaultSoundbank();
	Instrument[] instr = soundbank.getInstruments();
	m[numChannel].programChange(instr[instrument].getPatch().getBank(), instr[instrument].getPatch().getProgram());
    }


    public void creerNote(int numChannel){
    }
}