import java.io.IOException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.lang.InterruptedException;


public class FenetreTestPlayer extends JFrame{
    
    /*
     * Classe gérant la fenetre principale et ses différents éléments
     */

    AffichageTrajectoires a;
    
    public FenetreTestPlayer(){
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setExtendedState(JFrame.MAXIMIZED_BOTH); 
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	setSize((int)d.getWidth(), (int)d.getHeight());
	setResizable(false);
	setBackground(Color.BLACK);
	repaint();
    }

    public void majTemps(int temps)throws InterruptedException{
	a.majTrajectoires(temps);
    }

    public void setAffichageTrajectoires(AffichageTrajectoires affichage){
	a = affichage;
	getContentPane().add(a);
    }
}
