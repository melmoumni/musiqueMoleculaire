

class Timbre{

    private int timbreMIDI;
    private int min;
    private int max;
    private int octaveRef;

    
    public Timbre(int timbre){
    	timbreMIDI = timbre;
    	setInfo(timbre);
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
    
    private void setInfo(int timbre){  //affecter min, max et octave ref suivant le fichier liste d'instruments.txt
    
    
    
    }
}
