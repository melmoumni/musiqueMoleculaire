//package Utilitaires;
import java.io.File;
import java.io.IOException;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

public class Midi{


    static private Sequencer sequenceur;
    static private Synthesizer synthetiseur;

    public Midi(){
	try{
	    sequenceur = MidiSystem.getSequencer();
	    synthetiseur = MidiSystem.getSynthesizer();
	}
	catch(MidiUnavailableException e){}
    }

    static{
	try{
	    sequenceur = MidiSystem.getSequencer();
	    synthetiseur = MidiSystem.getSynthesizer();
	}
	catch(MidiUnavailableException e){}
    }
	
    static public void initialiser(String chemin) throws MidiUnavailableException, InvalidMidiDataException{
	//lier le sequenceur par defaut au synthetiseur par defaut
	Transmitter seqTrans = sequenceur.getTransmitter();
	Receiver synthRcvr = synthetiseur.getReceiver(); 
	seqTrans.setReceiver(synthRcvr);     
	//ouvrir sequenceur
	sequenceur.open();
	//ouvrir synthetiseur
	synthetiseur.open();
	//Charger la banque de sons
	try{
	    File src = new File(chemin);
	    Soundbank soundbank = MidiSystem.getSoundbank(src);
	    if(synthetiseur.isSoundbankSupported(soundbank))
		synthetiseur.loadAllInstruments(soundbank);
	    else
		System.out.println("Soundbank not supported by default synthesizer");	    
	}
	catch(MidiUnavailableException e){}
	catch(InvalidMidiDataException e){}
	catch(IOException e){}
	/* charge une nouvelle sequence avec 16 tracks
	 * resolution = 1000PPQ
	 * ticksParSeconde = resolution * (Tempo / 60.0);
	 * dureeTick = 1.0 / ticksParSeconde;
	 * dureeNoire = 1000*dureeTick
	 */
	sequenceur.setSequence(new Sequence(Sequence.PPQ,1000,16));
	//chargement des instruments dans le synthetiseur
    }

    static public void jouerTempo(int tempo, int duration){
	try{
	    int i = 0;
	    initialiser();
	    long NoirToTick = 120000/tempo;
	    System.out.println("noitotick :"+NoirToTick);
	    for(i = 0; i<tempo*duration; i++){
		ajouterEvent(10, creerEvent(ShortMessage.NOTE_ON, 9, 35, 120, i*NoirToTick));
		ajouterEvent(10, creerEvent(ShortMessage.NOTE_OFF, 9, 35, 0, (i+1)*NoirToTick));
	    }
	    System.out.println("last :"+i*NoirToTick);
	    jouerSequence();
	}
	catch(MidiUnavailableException e){}
	catch(InvalidMidiDataException e){}
	
    }
    //liberer le sequenceur et le synthetiseur
    static public void liberer(){
	sequenceur.close();
	synthetiseur.close();
    }

    //charger un instrument dans un channel 
    static public void configurerChannel(int numChannel, int instrument){
	MidiChannel[] m = synthetiseur.getChannels();
	Soundbank soundbank = synthetiseur.getDefaultSoundbank();
	Instrument[] instr = soundbank.getInstruments();
	m[numChannel].programChange(instr[instrument].getPatch().getBank(), instr[instrument].getPatch().getProgram());
    }


    static public void ajouterEvent(int numTrack, MidiEvent e){
	Track[] t = sequenceur.getSequence().getTracks();
	t[numTrack].add(e);
    }

    static public MidiEvent creerEvent(int command, int channel, int data1, int data2, long tick) throws InvalidMidiDataException{
	ShortMessage msg = new ShortMessage();
	msg.setMessage(command, channel, data1, data2);
	return new MidiEvent(msg, tick);
    }

    static public void jouerSequence(){
	sequenceur.start();
    }

    static public void tremolo(int note, int timbre, int volume, int debut, int fin) throws InvalidMidiDataException{  //pour tester, en théorie on doit ne passer que la molécule
	int i;
	//int nbPas=10;  //A FAIRE: à definir suivant la vitesse initiale, eventuellement
	int nbPas = (fin - debut)/75;
	int pas=(fin-debut)/nbPas;  
	int channel=retournerChannel(timbre,debut,fin);
	if((channel>0)&&(channel<15)){
	    //System.out.println("timbre: "+ timbre +"  channel " + channel +" pas " +pas);
	    for(i = 0; i<= nbPas; i++){
		//System.out.println("debut+i*pas " + debut+ i*pas + "\n");
		if(i%2 == 0){
		    ajouterEvent(0, creerEvent(ShortMessage.NOTE_ON,channel,note,volume,debut+i*pas));
		    ajouterEvent(0, creerEvent(ShortMessage.NOTE_OFF,channel,note+4,0,debut+i*pas));    //le +4 donne la tierce
		}
		else{
		    ajouterEvent(0, creerEvent(ShortMessage.NOTE_ON,channel,note+4,volume,debut+i*pas));
		    ajouterEvent(0, creerEvent(ShortMessage.NOTE_OFF,channel,note,0,debut+i*pas));
		}
	    }
	}
		
    }
    
    static public void glissando(int note, int timbre,int volume,int debut, int fin, int distanceParcourrue, int vitesseOrd) throws InvalidMidiDataException{

	// nbPas  égal à 64 pour la molécule ayant la distanceParcourrue la plus grande parmi toutes les molécules
	//ainsi, i (angle de la molette) sera au max pour une molécule parcourrant la plus grande distance

	int nbPas=64*distanceParcourrue / 100; //100 <==>  distanceMaximale provisoire;
	int i;
	int pas= (fin-debut)/nbPas;
	int channel=retournerChannel(timbre,debut,fin);
	if((channel>0)&&(channel<15)){
	    ajouterEvent(0, creerEvent(ShortMessage.NOTE_ON,channel,note,volume,debut));
	    if(vitesseOrd >=0){	  //glissando montant (suivant la vitesse ordonnée)
		for(i =0; i< nbPas; i++)  {
		    ajouterEvent(0, creerEvent(ShortMessage.PITCH_BEND,channel,note,64+i,debut+i*pas ));
		}
	    }
	    else{                //glissando descendant
		for(i = 0; i<=nbPas; i++){   
		    ajouterEvent(0, creerEvent(ShortMessage.PITCH_BEND,channel,note,64-i,debut+i*pas ));
		}
	    }
	}
    }
    
    static public void noteTenue(int note, int timbre,int volume,int debut, int fin) throws InvalidMidiDataException{
	int channel=retournerChannel(timbre,debut,fin);
	System.out.println("channel" + channel);
	if((channel>0)&&(channel<15)){
	    ajouterEvent(0, creerEvent(ShortMessage.NOTE_ON,channel,note,volume,debut));
	    ajouterEvent(0, creerEvent(ShortMessage.NOTE_OFF,channel,note,volume,fin));
	}
    }
    
    
    static private boolean isChannelLibre(int channel,int debut, int fin){
	MidiChannel[] m = synthetiseur.getChannels();
    	int size = Controleur.intervalles().size();
	for (int i=0; i<size; i++){
	    if ((Controleur.intervalles().get(i).instantInitial()==debut)&&(Controleur.intervalles().get(i).instantFinal()==fin)){
		int size2 = Controleur.intervalles.get(i).nombreMolecule();
		for (int j = 0; j < size2; size++) {
		    if(m[channel].getProgram()==Controleur.intervalles().get(i).molecules().get(j).getTimbre().timbreMIDI()){
			return true;
		    }
		    
		}
	    }
	}
	return false;
    }
    
    
    
    static private int retournerChannel(int timbre, int debut, int fin)throws InvalidMidiDataException{
	
	MidiChannel[] m = synthetiseur.getChannels();
	for(int i=0; i<16; i++){
	    if(m[i].getProgram()==timbre){
		return i;
	    }
	    else if ((i!=0)&&(m[i].getProgram()==0)){
		configurerChannel(i,timbre);
		return i;
	    }
	}
	for(int i=1; i<16; i++){
	    if(isChannelLibre(i, debut, fin)){
		ajouterEvent(0, creerEvent(ShortMessage.PROGRAM_CHANGE,i,timbre,0, debut));
		return i;
	    }
	}
	
	return -1;// s'il ne trouve pas il faudrait lever une exception
    }
    
    static public void saveMidi(String pathname)throws IOException{
	Sequence seq = sequenceur.getSequence();
	File file = new File(pathname);
	int fileType = MidiSystem.getMidiFileTypes(seq)[0];
	if (MidiSystem.isFileTypeSupported(fileType,seq)){
	    MidiSystem.write(seq,fileType,file);
	}
    }
    
}
