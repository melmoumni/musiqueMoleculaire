

class Timbre{

    static private int timbreMIDI;
    static private int min;
    static private int max;
    static private int octaveRef;

    
    public Timbre(int timbre){
    	timbreMIDI = timbre;
    	//setInfo(timbre);
    }
    
    static public int timbreMIDI(){
    	return timbreMIDI;
    }

    static public int min(){
    	return min;
    }
    
    static public int max(){
    	return max;
    }
    
    static public int octaveRef(){
    	return octaveRef;
    }
    
    // static private void setInfo(int timbre){  //affecter min, max et octave ref suivant le fichier liste d'instruments.txt
    // }
}
