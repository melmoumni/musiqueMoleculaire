package Utilitaires;
import javax.sound.midi.*;


import static java.lang.System.out;

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

    
    public void ajouterEvent(int numTrack, MidiEvent e){
	Track[] t = sequenceur.getSequence().getTracks();
	t[numTrack].add(e);
    }

    public MidiEvent creerEvent(int command, int channel, int data1, int data2, long tick) throws InvalidMidiDataException{
	ShortMessage msg = new ShortMessage();
	msg.setMessage(command, channel, data1, data2);
	return new MidiEvent(msg, tick);
    }

    public void jouerSequence(){
	sequenceur.start();
    }
    
    public void tremolo(int note, int timbre, int volume, int debut, int fin) throws InvalidMidiDataException{  //pour tester, en théorie on doit ne passer que la molécule
    	int i;
    	int nbPas=10;  //A FAIRE: à definir suivant la vitesse initiale, eventuellement
    	int pas=(fin-debut)/nbPas;  
    	int channel=retournerChannel(timbre);
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
    
   public void glissando(int note, int timbre,int volume,int debut, int fin, int distanceParcourrue, int vitesseOrd) throws InvalidMidiDataException{
   
   // nbPas  égal à 64 pour la molécule ayant la distanceParcourrue la plus grande parmi toutes les molécules
   //ainsi, i (angle de la molette) sera au max pour une molécule parcourrant la plus grande distance
	
   		int nbPas=64*distanceParcourrue / 100; //100 <==>  distanceMaximale provisoire;
		int i;
   		int pas= (fin-debut)/nbPas;
      int channel=retournerChannel(timbre);
      ajouterEvent(0, creerEvent(ShortMessage.NOTE_ON,channel,note,volume,0));
      
      if(vitesseOrd >=0)	  //glissando montant (suivant la vitesse ordonnée)
      	for(i =0; i< nbPas; i++)  
				ajouterEvent(0, creerEvent(ShortMessage.PITCH_BEND,channel,note,64+i,debut+i*pas ));      
      
      else                //glissando descendant
      	for(i = 0; i<=nbPas; i++)   
				ajouterEvent(0, creerEvent(ShortMessage.PITCH_BEND,channel,note,64-i,debut+i*pas ));
      
	      
    }
    
     public void noteTenue(int note, int timbre,int volume,int debut, int fin) throws InvalidMidiDataException{
		 int channel=retournerChannel(timbre);
		 ajouterEvent(0, creerEvent(ShortMessage.NOTE_ON,channel,note,volume,debut));
		 ajouterEvent(0, creerEvent(ShortMessage.NOTE_OFF,channel,note,volume,fin));
     
     }
     
    
    private int retournerChannel(int timbre){
    	MidiChannel[] m = synthetiseur.getChannels();
    	for(int i=0; i<16; i++){
    		if(m[i].getProgram()==timbre)
    			return i;
    	}
    	return -1;// s'il ne trouve pas il faudrait lever une exception
    }
 
}
