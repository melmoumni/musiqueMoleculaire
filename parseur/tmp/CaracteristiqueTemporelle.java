package tmp;
import java.lang.Math;

class CaracteristiqueTemporelle{
    private float coordX;
    private float coordY;
    private float pas;
    private int intensite;
    

    CaracteristiqueTemporelle(float x, float y, float distance, int ValeurIntensite){
	coordX = x;
	coordY = y;
	pas = distance;
	intensite = ValeurIntensite;
    }

    float getCoordX(){
	return coordX;
    }

    float getCoordY(){
	return coordX;
    }

    /*
     * calcule le pas en fonction de la position précédente
     * de la molécule en cours d'ajout dans la liste.
     */
    float calculePas(float x, float y){
	return 5 // problème sur sqrt;
    }
}
