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
			initialiser("../soundbanks/TimGM6mb.sf2");
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

	static public void tremolo(Molecule mol,int timbre, int nbPas, int variation) throws InvalidMidiDataException{  //pour tester, en théorie on doit ne passer que la molécule                    
		int i;
		//int nbPas=10;  //A FAIRE: à definir suivant la vitesse initiale, eventuellement                                                                                                          
		//int nbPas = (fin - debut)/75;                                                                                                    
		int tInitial = mol.instantInitial() * Controleur.dureeNoire;
		int tFinal = mol.instantFinal() * Controleur.dureeNoire;
		int pas=(tInitial - tFinal)/nbPas;
		int channel=retournerChannel(timbre,mol.instantInitial(),mol.instantFinal());
		if((channel>=0)&&(channel<16)){
			//System.out.println("timbre: "+ timbre +"  channel " + channel +" pas " +pas);                                                                                                        
			for(i = 0; i<= nbPas; i++){
				//System.out.println("debut+i*pas " + debut+ i*pas + "\n");                                                                                                                        
				if(i%2 == 0){
					ajouterEvent(0, creerEvent(ShortMessage.NOTE_ON,channel,mol.noteAbs(),mol.getVolume(), tInitial + i*pas));
					//ajouterEvent(0, creerEvent(ShortMessage.NOTE_OFF,channel,note+4,0,debut+i*pas));//le +4 donne la tierce                                                                  
					ajouterEvent(0, creerEvent(ShortMessage.NOTE_OFF,channel,mol.noteAbs()+variation,0, tInitial +i*pas));
				}
				else{
					//ajouterEvent(0, creerEvent(ShortMessage.NOTE_ON,channel,note+4,volume,debut+i*pas));                                                                                         
					ajouterEvent(0, creerEvent(ShortMessage.NOTE_ON,channel,mol.noteAbs()+variation,mol.getVolume(), tInitial + i*pas));
					ajouterEvent(0, creerEvent(ShortMessage.NOTE_OFF,channel,mol.noteAbs(),0, tInitial + i*pas));
				}
			}
		}
	}


	static public void glissando(Molecule mol, int molette, Timbre timbre) throws InvalidMidiDataException{

		// nbPas  égal à 64 pour la molécule ayant la distanceParcourrue la plus grande parmi toutes les molécules
		//ainsi, i (angle de la molette) sera au max pour une molécule parcourrant la plus grande distance

		//int nbPas=64*distanceParcourrue / 100; //100 <==>  distanceMaximale provisoire;
		int tInitial = mol.instantInitial() * Controleur.dureeNoire;
		int tFinal = mol.instantFinal() * Controleur.dureeNoire;
		int nbPas=(int) (molette*mol.distance() / Glissando.getDistanceMax()); //100 <==>  distanceMaximale provisoire;
		int i;
		int pas= (tInitial - tFinal)/nbPas;
		int channel=retournerChannel(timbre.timbreMIDI(),mol.instantInitial(),mol.instantFinal());
		if((channel>=0)&&(channel<16)){
			ajouterEvent(0, creerEvent(ShortMessage.NOTE_ON,channel,mol.noteAbs(),mol.getVolume(),tInitial));
			if(mol.vitesseOrd() >=0){	  //glissando montant (suivant la vitesse ordonnée)
				for(i =0; i< nbPas; i++)  {
					ajouterEvent(0, creerEvent(ShortMessage.PITCH_BEND,channel,mol.noteAbs(),molette+i,tInitial + i*pas ));
				}
			}
			else{                //glissando descendant
				for(i = 0; i<=nbPas; i++){   
					ajouterEvent(0, creerEvent(ShortMessage.PITCH_BEND,channel,mol.noteAbs(),molette-i, tInitial + i*pas ));
				}
			}
		}
	}

	static public void noteTenue(int note, int volume, Timbre timbre, int ti, int tf) throws InvalidMidiDataException{
		int channel=retournerChannel(timbre.timbreMIDI(), ti, tf);
		System.out.println("channel" + channel);
		if((channel>=0)&&(channel<16)){
			ajouterEvent(0, creerEvent(ShortMessage.NOTE_ON,channel,note, volume,ti*Controleur.dureeNoire));
			ajouterEvent(0, creerEvent(ShortMessage.NOTE_OFF,channel,note, volume,tf*Controleur.dureeNoire));
		}
	}


	static private boolean isChannelLibre(int channel,int debut, int fin){
		MidiChannel[] m = synthetiseur.getChannels();
		int size = Controleur.intervalles().size();
		for (int i=0; i<size; i++){
			if ((Controleur.intervalles().get(i).instantInitial()==debut)&&(Controleur.intervalles().get(i).instantFinal()==fin)){
				int size2 = Controleur.intervalles.get(i).nombreMolecule();
				for (int j = 0; j < size2; size++) {
					if(m[channel].getProgram()==Controleur.intervalles().get(i).molecules().get(j).getTimbreAbs().timbreMIDI()){
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
