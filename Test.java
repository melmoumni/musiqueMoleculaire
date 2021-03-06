import javax.sound.midi.*;
import java.io.IOException;

/* Pour tester faites javac Test.java -d build -cp build
 * puis cd build
 * puis java Test
 */


public class Test{

    public static void main(String[] args) throws IOException{
	int i = 0;
	try{
	    //Midi m = new Midi();
	    Midi.initialiser("../soundbanks/TimGM6mb.sf2");
	    //Midi.configurerChannel(0, 56);
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

	    //	    Midi.tremolo(60,  56, 60, 3000, 4500);

	    //Midi.glissando(60, 0,100, 0, 4000, 100, 3000);
	    //Midi.glissando(62, 0,100, 4000, 8000, 100, 3000);
	    //	    Midi.noteTenue(61,0,100, 5000,5201);
	    //Midi.glissando(60,56,100,10000, 20000, 100, 30);
	    //m.glissando(60, 56,100, 1001, 2000, 100, -15);

	    

	    // 	    Midi.noteTenue(64,0,100,0,500);
	    // Midi.noteTenue(63,1,100,500,1000);
	    // Midi.noteTenue(64,2,100,1000,1500);
	    // Midi.noteTenue(63,23,100,1500,2000);
	    // Midi.noteTenue(64,5,100,2000,2500);
	    // Midi.noteTenue(59,6,100,2500,3000);
	    // Midi.noteTenue(62,0,100,3000,3500);
	    // Midi.noteTenue(60,15,100,3500,4000);
	    // Midi.noteTenue(57,0,100,4000,5000);

	    // Midi.noteTenue(48,0,100,5500,6000);
	    // Midi.noteTenue(52,0,100,6000,6500);
	    // Midi.noteTenue(57,0,100,6500,7000);
	    // Midi.noteTenue(59,0,100,7000,8000);

	    // Midi.noteTenue(52,0,100,8500,9000);
	    // Midi.noteTenue(56,0,100,9000,9500);
	    // Midi.noteTenue(59,0,100,9500,10000);
	    // Midi.noteTenue(60,0,100,10000,11500);


	    //Test avec des molécules qui se recouvrent pas totalement
	     for (i=0;i<100;i++){
	     	//Midi.noteTenue(60,i*1,100,300+i*100,300+i*800);
	     	System.out.println("La c'est le"+ i);
	     }

	     //Test avec des molécules qui s'enchaine sans possibilités de remplacement
	     // for (i=0;i<100;i++){
	     // 	Midi.noteTenue(60,i*1,100,300+i*100,300+i*800);
	     // 	System.out.println("La c'est le"+ i);
	     // }
	    
	     Midi.jouerSequence();
	     
	     Midi.saveMidi("./test1234.midi");
	}
	catch(MidiUnavailableException e){}
	catch(InvalidMidiDataException e){}

    }


}
