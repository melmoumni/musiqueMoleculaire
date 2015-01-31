

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
    
    protected float x (){
    	return x;
    }

    protected float y (){
    	return y;
    }
    
    protected float temps (){
    	return temps;
    }
    
    protected float intensite (){
    	return intensite;
    }
    
}
