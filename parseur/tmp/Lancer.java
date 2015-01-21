package tmp;
import java.io.IOException;
class Lancer{
  public static void main(String... args) {
    boolean estMisAssertion = false;
    assert estMisAssertion = true;
    Parseur test = new Parseur();
    if (!estMisAssertion) {
      System.out.println("Execution impossible sans l'option -ea");
      return;
    }

    // comment lancer les tests ?

    //jauge.lancerTestsJaugeNaturel();
	test.lireFichier("./p.trc");
	
  }


  
}
