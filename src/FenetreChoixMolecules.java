import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.ActionEvent;


public class FenetreChoixMolecules extends JFrame{

	private ArrayList<Molecule> ListeImmobile;
	private ArrayList<Molecule> ListeConfine;
	private ArrayList<Molecule> ListeDirectionnelle;
	private ArrayList<Molecule> ListeAleatoire;
	private ArrayList<JCheckBox> ListCheckBoxImmobile;
	private ArrayList<JCheckBox> ListCheckBoxConfine;
	private ArrayList<JCheckBox> ListCheckBoxDirectionnelle;
	private ArrayList<JCheckBox> ListCheckBoxAleatoire;
	
	JPanel panelBasGauche;
	
	ArrayList<Molecule> ListeDynamique;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			Controleur.initMolecules("./data/trajectoires.trc","./data/analyse.txt", "./data/listeInstruments.txt");
		}
		catch (IOException e) {
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreChoixMolecules frame = new FenetreChoixMolecules();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FenetreChoixMolecules() {
	   
		ListeImmobile = new ArrayList<Molecule>();
		ListeConfine = new ArrayList<Molecule>();
		ListeDirectionnelle = new ArrayList<Molecule>();
		ListeAleatoire = new ArrayList<Molecule>();
		ListCheckBoxImmobile = new ArrayList<JCheckBox>();
		ListCheckBoxConfine = new ArrayList<JCheckBox>();
		ListCheckBoxDirectionnelle = new ArrayList<JCheckBox>();
		ListCheckBoxAleatoire = new ArrayList<JCheckBox>();
		
		Controleur.trierMolecules(ListeImmobile, ListeConfine, ListeDirectionnelle, ListeAleatoire);

		this.setResizable(false);
	    setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    getContentPane().setLayout(null);
	    
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    dim.width -= 50;
		setBounds(0, 0, (int) dim.getWidth(), (int) dim.getHeight());
	    
	    JPanel panelHautGauche = new JPanel();
	    panelHautGauche.setBounds(0, 0, (int) (2*dim.getWidth()/3), (int) dim.getHeight() / 2);
	    FenetreTrajectoires TrajectoiresHaut = new FenetreTrajectoires(Controleur.molecules(), (int) (2*dim.getWidth()/3), (int) dim.getHeight()/2); 
	    panelHautGauche.add(new JScrollPane(TrajectoiresHaut));
	    panelHautGauche.setVisible(true);
	    getContentPane().add(panelHautGauche);
	    
	    panelBasGauche = new JPanel();
	    panelBasGauche.setBounds(0, (int) dim.getHeight() / 2, (int) (2*dim.getWidth()/3), (int) dim.getHeight() / 2);
	    ListeDynamique = new ArrayList<Molecule>(Controleur.molecules());
	    FenetreTrajectoires TrajectoiresBas = new FenetreTrajectoires(ListeDynamique, (int) (2*dim.getWidth()/3), (int) dim.getHeight()/2); 
	    panelBasGauche.add(new JScrollPane(TrajectoiresBas));
	    panelBasGauche.setVisible(true);
	    getContentPane().add(panelBasGauche);

	    
	    ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	          AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
	          boolean selected = abstractButton.getModel().isSelected();
	          ArrayList<Molecule> typeMolecule = ListeImmobile;
	          int indice = 0;
	          for (JCheckBox jc : ListCheckBoxImmobile){
	        	  if (jc.equals(abstractButton)){
	        		  typeMolecule = ListeImmobile;
	        		  indice = ListCheckBoxImmobile.indexOf(jc);
	        	  }
	          }
	          for (JCheckBox jc : ListCheckBoxConfine){
	        	  if (jc.equals(abstractButton)){
	        		  typeMolecule = ListeConfine;
	        		  indice = ListCheckBoxConfine.indexOf(jc);
	        	  }
	          }
	          for (JCheckBox jc : ListCheckBoxDirectionnelle){
	        	  if (jc.equals(abstractButton)){
	        		  typeMolecule = ListeDirectionnelle;
	        		  indice = ListCheckBoxDirectionnelle.indexOf(jc);
	        	  }
	          }
	          for (JCheckBox jc : ListCheckBoxAleatoire){
	        	  if (jc.equals(abstractButton)){
	        		  typeMolecule = ListeAleatoire;
	        		  indice = ListCheckBoxAleatoire.indexOf(jc);
	        	  }
	          }
	          if (selected){
	        	  abstractButton.setFont(new Font("default", Font.BOLD, 12));
	        	  ListeDynamique.add(typeMolecule.get(indice));
	          }
	          else {
	        	  abstractButton.setFont(new Font("default", Font.PLAIN, 12));
	        	  ListeDynamique.remove(typeMolecule.get(indice));
	          }
	          System.out.println(selected + " " + typeMolecule.get(indice).numero() + " " + ListeDynamique.size());
	          panelBasGauche.revalidate();
	          panelBasGauche.repaint();
	        }
	      };
	    
	    	    
	    JPanel panelDroit = new JPanel();
	    panelDroit.setBounds((int) (2*dim.getWidth()/3), 0,(int) (1*dim.getWidth()/3), (int) dim.getHeight());
	    getContentPane().add(panelDroit);
	    panelDroit.setLayout(null);
	    
		JLabel labelHautGauche = new JLabel("Immobile");
		Box boxHautGauche = Box.createVerticalBox();
		boxHautGauche.add(labelHautGauche);
		for (Molecule mol : ListeImmobile){
			JCheckBox j = new JCheckBox("Immobile "+mol.numero());
			j.setSelected(true);
			j.addActionListener(actionListener);
			boxHautGauche.add(j);
			ListCheckBoxImmobile.add(j);
		}

		JLabel labelHautDroit = new JLabel("Confine");
		Box boxHautDroit = Box.createVerticalBox();
		boxHautDroit.add(labelHautDroit);
		for (Molecule mol : ListeConfine){
			JCheckBox j = new JCheckBox("Confine "+mol.numero());
			j.setSelected(true);
			j.addActionListener(actionListener);
			boxHautDroit.add(j);
			ListCheckBoxConfine.add(j);
		}

		JLabel labelBasGauche = new JLabel("Directionnelle");
		Box boxBasGauche = Box.createVerticalBox();
		boxBasGauche.add(labelBasGauche);
		for (Molecule mol : ListeDirectionnelle){
			JCheckBox j = new JCheckBox("Directionnelle "+mol.numero());
			j.setSelected(true);
			j.addActionListener(actionListener);
			boxBasGauche.add(j);
			ListCheckBoxDirectionnelle.add(j);
		}
		
		JLabel labelBasDroit = new JLabel("Aleatoire");
		Box boxBasDroit = Box.createVerticalBox();
		boxBasDroit.add(labelBasDroit);
		for (Molecule mol : ListeAleatoire){
			JCheckBox j = new JCheckBox("Aleatoire "+mol.numero());
			j.setSelected(true);
			j.addActionListener(actionListener);
			boxBasDroit.add(j);
			ListCheckBoxAleatoire.add(j);
		}	    
	    
	    JScrollPane scrollPaneHautGauche = new JScrollPane(boxHautGauche);
	    scrollPaneHautGauche.setBounds(0, 15, panelDroit.getWidth()/2 -50 , panelDroit.getHeight()/4);
	    panelDroit.add(scrollPaneHautGauche);
	    
	    JScrollPane scrollPaneHautDroit = new JScrollPane(boxHautDroit);
	    scrollPaneHautDroit.setBounds(panelDroit.getWidth()/2, 15, panelDroit.getWidth()/2 -50 , panelDroit.getHeight()/4);
	    panelDroit.add(scrollPaneHautDroit);
	    
	    JScrollPane scrollPaneBasGauche = new JScrollPane(boxBasGauche);
	    scrollPaneBasGauche.setBounds(0, panelDroit.getHeight()/3, panelDroit.getWidth()/2 - 50, panelDroit.getHeight()/4);
	    panelDroit.add(scrollPaneBasGauche);
	    
	    JScrollPane scrollPaneBasDroit = new JScrollPane(boxBasDroit);
	    scrollPaneBasDroit.setBounds(panelDroit.getWidth()/2, panelDroit.getHeight()/3, panelDroit.getWidth()/2 -50 , panelDroit.getHeight()/4);
	    panelDroit.add(scrollPaneBasDroit);
	    
	    JButton buttonCocherImmobile = new JButton("Tout cocher");
	    buttonCocherImmobile.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		for (JCheckBox jc : ListCheckBoxImmobile){
	    			jc.setSelected(true);
	    		}
	    		for (Molecule mol : ListeImmobile){
	    			if (!(ListeDynamique.contains(mol))){
	    				ListeDynamique.add(mol);
	    			}
	    		}
	    		panelBasGauche.revalidate();
	    		panelBasGauche.repaint();
	    	}
	    });
	    buttonCocherImmobile.setBounds(0, 291, 110, 25);
	    panelDroit.add(buttonCocherImmobile);
	    
	    JButton buttonDecocherImmobile = new JButton("Tout decocher");
	    buttonDecocherImmobile.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		for (JCheckBox jc : ListCheckBoxImmobile){
	    			jc.setSelected(false);
	    		}
	    		for (Molecule mol : ListeImmobile){
	    			if (ListeDynamique.contains(mol)){
	    				ListeDynamique.remove(mol);
	    			}
	    		}
	    		panelBasGauche.revalidate();
	    		panelBasGauche.repaint();
	    	}
	    });
	    buttonDecocherImmobile.setBounds(146, 291, 124, 25);
	    panelDroit.add(buttonDecocherImmobile);
	    
	    JButton buttonCocherConfine = new JButton("Tout cocher");
	    buttonCocherConfine.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		for (JCheckBox jc : ListCheckBoxConfine){
	    			jc.setSelected(true);
	    		}
	    		for (Molecule mol : ListeConfine){
	    			if (!(ListeDynamique.contains(mol))){
	    				ListeDynamique.add(mol);
	    			}
	    		}
	    		panelBasGauche.revalidate();
	    		panelBasGauche.repaint();
	    	}
	    });
	    buttonCocherConfine.setBounds(320, 291, 110, 25);
	    panelDroit.add(buttonCocherConfine);
	    
	    JButton buttonDecocherConfine = new JButton("Tout decocher");
	    buttonDecocherConfine.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		for (JCheckBox jc : ListCheckBoxConfine){
	    			jc.setSelected(false);
	    		}
	    		for (Molecule mol : ListeConfine){
	    			if (ListeDynamique.contains(mol)){
	    				ListeDynamique.remove(mol);
	    			}
	    		}
	    		panelBasGauche.revalidate();
	    		panelBasGauche.repaint();
	    	}
	    });
	    buttonDecocherConfine.setBounds(466, 291, 124, 25);
	    panelDroit.add(buttonDecocherConfine);
	    
	    JButton buttonCocherDirectionnelle = new JButton("Tout cocher");
	    buttonCocherDirectionnelle.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		for (JCheckBox jc : ListCheckBoxDirectionnelle){
	    			jc.setSelected(true);
	    		}
	    		for (Molecule mol : ListeDirectionnelle){
	    			if (!(ListeDynamique.contains(mol))){
	    				ListeDynamique.add(mol);
	    			}
	    		}
	    		panelBasGauche.revalidate();
	    		panelBasGauche.repaint();
	    	}
	    });
	    buttonCocherDirectionnelle.setBounds(0, 643, 110, 25);
	    panelDroit.add(buttonCocherDirectionnelle);
	    
	    JButton buttonDecocherDirectionnelle = new JButton("Tout decocher");
	    buttonDecocherDirectionnelle.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		for (JCheckBox jc : ListCheckBoxDirectionnelle){
	    			jc.setSelected(false);
	    		}
	    		for (Molecule mol : ListeDirectionnelle){
	    			if (ListeDynamique.contains(mol)){
	    				ListeDynamique.remove(mol);
	    			}
	    		}
	    		panelBasGauche.revalidate();
	    		panelBasGauche.repaint();
	    	}
	    });
	    buttonDecocherDirectionnelle.setBounds(146, 643, 124, 25);
	    panelDroit.add(buttonDecocherDirectionnelle);

	    JButton buttonCocherAleatoire = new JButton("Tout cocher");
	    buttonCocherAleatoire.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		for (JCheckBox jc : ListCheckBoxAleatoire){
	    			jc.setSelected(true);
	    		}
	    		for (Molecule mol : ListeAleatoire){
	    			if (!(ListeDynamique.contains(mol))){
	    				ListeDynamique.add(mol);
	    			}
	    		}
	    		panelBasGauche.revalidate();
	    		panelBasGauche.repaint();
	    	}
	    });
	    buttonCocherAleatoire.setBounds(320, 643, 110, 25);
	    panelDroit.add(buttonCocherAleatoire);
	    
	    JButton buttonDecocherAleatoire = new JButton("Tout decocher");
	    buttonDecocherAleatoire.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		for (JCheckBox jc : ListCheckBoxAleatoire){
	    			jc.setSelected(false);
	    		}
	    		for (Molecule mol : ListeAleatoire){
	    			if (ListeDynamique.contains(mol)){
	    				ListeDynamique.remove(mol);
	    			}
	    		}
	    		panelBasGauche.revalidate();
	    		panelBasGauche.repaint();
	    	}
	    });
	    buttonDecocherAleatoire.setBounds(466, 643, 124, 25);
	    panelDroit.add(buttonDecocherAleatoire);
	    
	    JButton btnValider = new JButton("Valider");
	    btnValider.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    	}
	    });
	    btnValider.setBounds(177, 804, 244, 37);
	    panelDroit.add(btnValider);
	    
	    JButton btnSelectionAutomatique = new JButton("Selection automatique");
	    btnSelectionAutomatique.setBounds(167, 704, 263, 37);
	    panelDroit.add(btnSelectionAutomatique);
	   
	}

}