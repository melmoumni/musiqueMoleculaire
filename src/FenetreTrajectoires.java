import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import javax.swing.JApplet;
import javax.swing.JFrame;
/*import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;*/
import java.util.*;

public class FenetreTrajectoires extends JApplet {
   public void init() {
      setBackground(Color.white);
      setForeground(Color.white);
   }
   public void paint(Graphics g, ArrayList<Molecule> molecules) {

      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
      
      for (Molecule mol : molecules){
	   		 g2.setPaint(Color.gray);
	   		 ListIterator <CaracteristiqueTemporelle> it = mol.positions().listIterator();
	   		 CaracteristiqueTemporelle tmp = new CaracteristiqueTemporelle();
	   		 if(it.hasNext())	{
			 		 tmp=it.next();
			 		 CaracteristiqueTemporelle tmp2 = new CaracteristiqueTemporelle();
						while (it.hasNext()) {
								tmp2=it.next();
								g2.draw(new Line2D.Double(tmp.x(), tmp.y(), tmp2.x(), tmp2.y()));
								tmp=tmp2;
							
		   				}
			 		 
			 		 }
	   		 }
	   		 
			}
      
      
  
   public static void main(String s[]) {
      JFrame f = new JFrame("Line");
      f.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            System.exit(0);
         }
      });
      JApplet applet = new FenetreTrajectoires();
      f.getContentPane().add("Center", applet);
      applet.init();
      f.pack();
      f.setSize(new Dimension(300, 300));
      f.setVisible(true);
   }
   
}
