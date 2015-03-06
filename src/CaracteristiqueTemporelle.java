

class CaracteristiqueTemporelle{
    public float x;
    public float y;
    public int temps;
    public float intensite;

    public CaracteristiqueTemporelle(){
    	
    }
    
    public CaracteristiqueTemporelle(float xt, float yt, int tempst, float intensitet){
    	x = xt;
    	y = yt;
    	temps = tempst;
    	intensite = intensitet;    	
    }
    
    protected float x(){
    	return x;
    }

    protected float y(){
    	return y;
    }
    
    protected int temps(){
    	return temps;
    }
    
    protected float intensite(){
    	return intensite;
    }

    // @Override servira peut être... dépend si on modifie une molecule lors de l'attribution de channel
    // public Object clone(){
    // 	Object o = null;
    // 	try {
    // 	    o = super.clone();
    // 	}
    // 	catch(CloneNotSupportedException cnse) {
    // 	}
    // 	return o;
    // }
}
