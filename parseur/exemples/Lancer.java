//package exemples;

import java.io.IOException;

class Lancer{
  public static void main(String... args) {
    boolean estMisAssertion = false;
    assert estMisAssertion = true;
    Lecture test = new Lecture();
    if (!estMisAssertion) {
      System.out.println("Execution impossible sans l'option -ea");
      return;
    }

    // comment lancer les tests ?

    //jauge.lancerTestsJaugeNaturel();
    try{
	test.testLecture4("./p.trc");
	test.testLecture5("./test.txt");
    }
    catch (IOException e)
	{
	    System.out.println("Problème de formation du fichier");
	}
  }


  
}
