

class Timbre{

    final private int timbreMIDI;
    final private int min;
    final private int max;
    final private int octaveRef;
    
    public Timbre(int timbre, int mint, int maxt, int octave){
	timbreMIDI = timbre;
	min = mint;
	max = maxt;
	octaveRef = octave;
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
