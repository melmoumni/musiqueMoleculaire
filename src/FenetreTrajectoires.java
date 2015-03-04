import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import javax.swing.JApplet;
import javax.swing.JFrame;
/*import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;*/
import java.util.*;

public class FenetreTrajectoires extends JFrame {

	private ArrayList<Molecule> molecules;
	
	public void FenetreTrajectoires(ArrayList<Molecule> mols){
		setMols(mols);
		this.setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
	   this.getContentPane().setLayout(null);

	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, (int) dim.getWidth(), (int) dim.getHeight());
	    setLocationRelativeTo(null);
	    init();
		
	}
	
	
   public void init() {     
      setBackground(Color.black);
      setForeground(Color.black);
   }
   public void paint(Graphics g) {
			ArrayList<Molecule> molecules= this.getMols();
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
      
      for (Molecule mol : molecules){
	   		 //g2.setPaint(Color.gray);
	   		 //to get rainbow, pastel colors
				Random random = new Random();
				final float hue = random.nextFloat();
				final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
				final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
				Color color = Color.getHSBColor(hue, saturation, luminance);
				g2.setPaint(color);
				
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
     public static void main(String[] args){
      FenetreTrajectoires  ft = new FenetreTrajectoires();  
      //FenetreTrajectoires.setVisible(true);
   }

      
  
   	public ArrayList<Molecule> getMols()
	{
		return this.molecules;
	}
	
	public void setMols(ArrayList<Molecule> mols){
		molecules=mols;
	}
   
}
