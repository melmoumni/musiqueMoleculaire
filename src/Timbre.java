

class Timbre{

    private int timbreMIDI;
    private int min;
    private int max;
    private int octaveRef;

    
    public Timbre(int timbre, int mint, int maxt, int octaveReft){
    	timbreMIDI = timbre;
    	min = mint;
    	max = maxt;
    	octaveRef = octaveReft;
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
    
}
