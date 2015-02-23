

class Timbre{

    final private int timbreMIDI;
    final private int min;
    final private int max;
    final private int octaveRef;
    
    public Timbre(int timbre){//ne sert que pour les tests actuels
	timbreMIDI = timbre;
	min = 0;
	max = 0;
	octaveRef = 0;
    }

    public Timbre(int timbre, int mint, int maxt, int octave){
	timbreMIDI = timbre;
	min = mint;
	max = maxt;
	octaveRef = octave;
    }
    
    public void printTimbre(){
	System.out.println("==================================");
	System.out.println("Timbre Midi numéro : " + timbreMIDI);
	System.out.println("Min : " + min);
	System.out.println("Max : " + max);
	//System.out.println("Octave : " + octaveRef);
	System.out.println("==================================");

    }

    public int timbreMIDI(){
    	return timbreMIDI;
    }

    public int min(){
    	return min;
    }
    
    public int max(){
    	return max;
    }
    
    public int octaveRef(){
    	return octaveRef;
    }
    
    // static private void setInfo(int timbre){  //affecter min, max et octave ref suivant le fichier liste d'instruments.txt
    // }
}
