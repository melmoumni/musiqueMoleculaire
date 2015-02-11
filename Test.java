import Utilitaires.Midi;
import javax.sound.midi.*;


/* Pour tester faites javac Test.java -d build -cp build
 * puis cd build
 * puis java Test
 */


public class Test{

    public static void main(String[] args){
	int i = 0;
	try{
	    Midi m = new Midi();
	    m.initialiser();
	    m.configurerChannel(0, 56);
	    //tremolo entre la note 60 et 63
	    /*
	           for(i = 0; i<=30; i++){
		   if(i%2 == 0){
		       m.ajouterEvent(0, m.creerEvent(ShortMessage.NOTE_ON,0,60,60,i*100));
		           m.ajouterEvent(0, m.creerEvent(ShortMessage.NOTE_OFF,0,64,0,i*100));
			   }
			   else{
			       m.ajouterEvent(0, m.creerEvent(ShortMessage.NOTE_ON,0,64,60,i*100));
			           m.ajouterEvent(0, m.creerEvent(ShortMessage.NOTE_OFF,0,60,0,i*100));
				   }
				   }*/
	    //vibrato sur la note 63
	    // m.ajouterEvent(0, m.creerEvent(ShortMessage.NOTE_ON,0,63,100,0));
	    // for(i = 0; i<=3000; i++){
	    // if(i%2 == 0)
	    //     m.ajouterEvent(0, m.creerEvent(ShortMessage.POLY_PRESSURE,0,63,100,i));
	    // else
	    //     m.ajouterEvent(0, m.creerEvent(ShortMessage.POLY_PRESSURE,0,63,80,i));
	    // }
	    // //glissande sur la note 63
	    /* m.ajouterEvent(0, m.creerEvent(ShortMessage.NOTE_ON,0,63,100,0));
	            for(i = 64; i>=0; i--)
		    m.ajouterEvent(0, m.creerEvent(ShortMessage.PITCH_BEND,0,63,i,(64-i+1)*100));
		         for(i = 0; i<=127; i++)
			 m.ajouterEvent(0, m.creerEvent(ShortMessage.PITCH_BEND,0,63,i,(i+1)*100 + 6500));*/

	    //m.tremolo(60,  56, 60, 3000, 4500);

	    //m.glissando(60, 56,100, 0, 1000, 100, 15);
	    //m.glissando(60, 56,100, 1001, 2000, 100, -15);

	    m.noteTenue(64,0,100,0,500);
	    m.noteTenue(63,0,100,500,1000);
	    m.noteTenue(64,0,100,1000,1500);
	    m.noteTenue(63,0,100,1500,2000);
	    m.noteTenue(64,0,100,2000,2500);
	    m.noteTenue(59,0,100,2500,3000);
	    m.noteTenue(62,0,100,3000,3500);
	    m.noteTenue(60,0,100,3500,4000);
	    m.noteTenue(57,0,100,4000,5000);

	    m.noteTenue(48,0,100,5500,6000);
	    m.noteTenue(52,0,100,6000,6500);
	    m.noteTenue(57,0,100,6500,7000);
	    m.noteTenue(59,0,100,7000,8000);

	    m.noteTenue(52,0,100,8500,9000);
	    m.noteTenue(56,0,100,9000,9500);
	    m.noteTenue(59,0,100,9500,10000);
	    m.noteTenue(60,0,100,10000,11500);
	    
	    m.jouerSequence();
	}
	catch(MidiUnavailableException e){}
	catch(InvalidMidiDataException e){}

    }


}
