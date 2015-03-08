import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
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
	    
	    JPanel panelGauche = new JPanel();
	    panelGauche.setBounds(0, 0, (int) (2*dim.getWidth()/3), (int) dim.getHeight());
	    getContentPane().add(panelGauche);
	    
	    JPanel panelDroit = new JPanel();
	    panelDroit.setBounds((int) (2*dim.getWidth()/3), 0,(int) (1*dim.getWidth()/3), (int) dim.getHeight());
	    getContentPane().add(panelDroit);
	    panelDroit.setLayout(null);
	    
		JLabel labelHautGauche = new JLabel("Immobile");
		Box boxHautGauche = Box.createVerticalBox();
		boxHautGauche.add(labelHautGauche);
		for (Molecule mol : ListeImmobile){
			JCheckBox j = new JCheckBox("Immobile "+mol.numero());
			boxHautGauche.add(j);
			ListCheckBoxImmobile.add(j);
		}

		JLabel labelHautDroit = new JLabel("Confine");
		Box boxHautDroit = Box.createVerticalBox();
		boxHautDroit.add(labelHautDroit);
		for (Molecule mol : ListeConfine){
			JCheckBox j = new JCheckBox("Confine "+mol.numero());
			boxHautDroit.add(j);
			ListCheckBoxConfine.add(j);
		}

		JLabel labelBasGauche = new JLabel("Directionnelle");
		Box boxBasGauche = Box.createVerticalBox();
		boxBasGauche.add(labelBasGauche);
		for (Molecule mol : ListeDirectionnelle){
			JCheckBox j = new JCheckBox("Directionnelle "+mol.numero());
			boxBasGauche.add(j);
			ListCheckBoxDirectionnelle.add(j);
		}
		
		JLabel labelBasDroit = new JLabel("Aleatoire");
		Box boxBasDroit = Box.createVerticalBox();
		boxBasDroit.add(labelBasDroit);
		for (Molecule mol : ListeAleatoire){
			JCheckBox j = new JCheckBox("Aleatoire "+mol.numero());
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
	    	}
	    });
	    buttonDecocherAleatoire.setBounds(466, 643, 124, 25);
	    panelDroit.add(buttonDecocherAleatoire);
	    
	    

	 
		
	}
}