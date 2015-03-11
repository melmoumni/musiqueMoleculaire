import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import java.awt.event.ActionEvent;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;



public class FenetreChoixMolecules extends JFrame{

	private Molecule moleculePropriete;
	
	private ArrayList<Molecule> ListeImmobile;
	private ArrayList<Molecule> ListeConfine;
	private ArrayList<Molecule> ListeDirectionnelle;
	private ArrayList<Molecule> ListeAleatoire;
	private ArrayList<JCheckBox> ListCheckBoxImmobile;
	private ArrayList<JCheckBox> ListCheckBoxConfine;
	private ArrayList<JCheckBox> ListCheckBoxDirectionnelle;
	private ArrayList<JCheckBox> ListCheckBoxAleatoire;
	
	private JPanel panelBasGauche;
	
	private JSpinner spinnerNbMol;
	private JSpinner spinnerIntensite;
	private JSpinner spinnerPoints;
	
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
		final JPopupMenu menu = new JPopupMenu("Popup");

		class MyJCheckBox extends JCheckBox {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public MyJCheckBox(String text, Molecule mol) {
				super(text);
				addMouseListener(new PopupTriggerListener());
				moleculePropriete = mol;
			}
			
			class PopupTriggerListener extends MouseAdapter {
				public void mousePressed(MouseEvent ev) {
					if (ev.isPopupTrigger()) {
						menu.show(ev.getComponent(), ev.getX(), ev.getY());
						MyJCheckBox ch = (MyJCheckBox) ev.getSource();
						if (ListCheckBoxImmobile.contains(ch)){
							moleculePropriete = ListeImmobile.get(ListCheckBoxImmobile.indexOf(ch));
						}else if (ListCheckBoxConfine.contains(ch)){
							moleculePropriete = ListeConfine.get(ListCheckBoxConfine.indexOf(ch));
						}else if (ListCheckBoxDirectionnelle.contains(ch)){
							moleculePropriete = ListeDirectionnelle.get(ListCheckBoxDirectionnelle.indexOf(ch));
						}else if (ListCheckBoxAleatoire.contains(ch)){
							moleculePropriete = ListeAleatoire.get(ListCheckBoxAleatoire.indexOf(ch));
						}
					}
				}
				public void mouseReleased(MouseEvent ev) {
					if (ev.isPopupTrigger()) {
						menu.show(ev.getComponent(), ev.getX(), ev.getY());
						MyJCheckBox ch = (MyJCheckBox) ev.getSource();
						if (ListCheckBoxImmobile.contains(ch)){
							moleculePropriete = ListeImmobile.get(ListCheckBoxImmobile.indexOf(ch));
						}else if (ListCheckBoxConfine.contains(ch)){
							moleculePropriete = ListeConfine.get(ListCheckBoxConfine.indexOf(ch));
						}else if (ListCheckBoxDirectionnelle.contains(ch)){
							moleculePropriete = ListeDirectionnelle.get(ListCheckBoxDirectionnelle.indexOf(ch));
						}else if (ListCheckBoxAleatoire.contains(ch)){
							moleculePropriete = ListeAleatoire.get(ListCheckBoxAleatoire.indexOf(ch));
						}
					}
				}
				public void mouseClicked(MouseEvent ev) {
				}
			}
		}

		JMenuItem item = new JMenuItem("Propriete");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("FenetreProprieteMolecule" + moleculePropriete.numero());
			}
		});
		menu.add(item);
		
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
			JCheckBox j = new MyJCheckBox("Immobile "+mol.numero(), mol);
			j.setSelected(true);
			j.addActionListener(actionListener);
			boxHautGauche.add(j);
			ListCheckBoxImmobile.add(j);
		}

		JLabel labelHautDroit = new JLabel("Confine");
		Box boxHautDroit = Box.createVerticalBox();
		boxHautDroit.add(labelHautDroit);
		for (Molecule mol : ListeConfine){
			JCheckBox j = new MyJCheckBox("Confine "+mol.numero(), mol);
			j.setSelected(true);
			j.addActionListener(actionListener);
			boxHautDroit.add(j);
			ListCheckBoxConfine.add(j);
		}

		JLabel labelBasGauche = new JLabel("Directionnelle");
		Box boxBasGauche = Box.createVerticalBox();
		boxBasGauche.add(labelBasGauche);
		for (Molecule mol : ListeDirectionnelle){
			JCheckBox j = new MyJCheckBox("Directionnelle "+mol.numero(), mol);
			j.setSelected(true);
			j.addActionListener(actionListener);
			boxBasGauche.add(j);
			ListCheckBoxDirectionnelle.add(j);
		}
		
		JLabel labelBasDroit = new JLabel("Aleatoire");
		Box boxBasDroit = Box.createVerticalBox();
		boxBasDroit.add(labelBasDroit);
		for (Molecule mol : ListeAleatoire){
			JCheckBox j = new MyJCheckBox("Aleatoire "+mol.numero(), mol);
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
	    			jc.setFont(new Font("default", Font.BOLD, 12));
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
	    			jc.setFont(new Font("default", Font.PLAIN, 12));
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
	    			jc.setFont(new Font("default", Font.BOLD, 12));
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
	    			jc.setFont(new Font("default", Font.PLAIN, 12));
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
	    			jc.setFont(new Font("default", Font.BOLD, 12));
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
	    			jc.setFont(new Font("default", Font.PLAIN, 12));
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
	    			jc.setFont(new Font("default", Font.BOLD, 12));
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
	    			jc.setFont(new Font("default", Font.PLAIN, 12));
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
	    
	    JButton btnValider = new JButton("Valider le choix des proteines");
	    btnValider.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    	}
	    });
	    btnValider.setBounds(159, 912, 317, 67);
	    panelDroit.add(btnValider);
	    
	    JButton btnSelectionAutomatique = new JButton("Selection automatique");
	    btnSelectionAutomatique.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		selectRandomMolecule((int) spinnerNbMol.getValue());
	    	}
	    });
	    btnSelectionAutomatique.setBounds(255, 711, 211, 25);
	    panelDroit.add(btnSelectionAutomatique);
	    
	    spinnerNbMol = new JSpinner();
	    int maxNbMol = max(ListeAleatoire.size(),ListeConfine.size(),ListeDirectionnelle.size(),ListeImmobile.size());
	    System.out.println(maxNbMol);
	    spinnerNbMol.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(maxNbMol), new Integer(1)));
	    if (maxNbMol >1){
	    	spinnerNbMol.setValue(new Integer(2));
	    }
	    spinnerNbMol.setBounds(192, 711, 51, 25);
	    panelDroit.add(spinnerNbMol);
	    
	    JLabel lblNombreDeMolcules = new JLabel("Nombre de Molécules");
	    lblNombreDeMolcules.setBounds(53, 693, 139, 37);
	    panelDroit.add(lblNombreDeMolcules);
	    
	    JLabel lblParCatgorie = new JLabel("par catégorie :");
	    lblParCatgorie.setBounds(81, 714, 124, 27);
	    panelDroit.add(lblParCatgorie);
	    
	    JLabel lblNombreDePoints = new JLabel("Nombre de points minimum :");
	    lblNombreDePoints.setBounds(12, 789, 175, 37);
	    panelDroit.add(lblNombreDePoints);
	    
	    JLabel lblContraintes = new JLabel("Contraintes :");
	    lblContraintes.setFont(new Font("Tahoma", Font.BOLD, 14));
	    lblContraintes.setBounds(12, 754, 124, 25);
	    panelDroit.add(lblContraintes);
	    
	    JLabel lblIntensite = new JLabel("Intensite minimum :");
	    lblIntensite.setBounds(12, 819, 124, 37);
	    panelDroit.add(lblIntensite);
	    
	    spinnerPoints = new JSpinner();
	    spinnerPoints.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
	    spinnerPoints.setBounds(186, 796, 51, 25);
	    panelDroit.add(spinnerPoints);
	    
	    spinnerIntensite = new JSpinner();
	    spinnerIntensite.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
	    spinnerIntensite.setBounds(186, 826, 51, 25);
	    panelDroit.add(spinnerIntensite);

	    JButton btnEnregistrerLesContraintes = new JButton("Enregistrer les contraintes");
	    btnEnregistrerLesContraintes.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		selectMoleculesContraintes((int) spinnerPoints.getValue(), (int) spinnerIntensite.getValue());
	    	}
	    });
	    btnEnregistrerLesContraintes.setBounds(255, 796, 211, 60);
	    panelDroit.add(btnEnregistrerLesContraintes);
	    
	}

	protected void selectMoleculesContraintes(int nbPoints, int minIntensite) {
		ArrayList<Molecule> copieListeDynamique = new ArrayList<Molecule>(ListeDynamique);
		for (Molecule mol: copieListeDynamique){
			if (((mol.positions().size() < nbPoints) || (mol.moyenneIntensite() < minIntensite)) && ListeDynamique.contains(mol)){
				ListeDynamique.remove(mol);
				if (ListeImmobile.contains(mol)){
					ListCheckBoxImmobile.get(ListeImmobile.indexOf(mol)).doClick();
				}else if (ListeConfine.contains(mol)){
					ListCheckBoxConfine.get(ListeConfine.indexOf(mol)).doClick();					
				}else if (ListeDirectionnelle.contains(mol)){
					ListCheckBoxDirectionnelle.get(ListeDirectionnelle.indexOf(mol)).doClick();
				}else if (ListeAleatoire.contains(mol)){
					ListCheckBoxAleatoire.get(ListeAleatoire.indexOf(mol)).doClick();
				}
			}
		}
		panelBasGauche.revalidate();
		panelBasGauche.repaint();
	}

	private int max(int i, int j, int k, int l) {
		if(i>=j && i>=k && i>=l){
			return i;
		}
		else if(j>=i && j>=k && j>=l){
			return j;
		}
		else if (k>=i && k>=j && k>=l){
			return k;
		}
		return l;
	}

	protected void selectRandomMolecule(int nbMol) {
		Random randomizer = new Random();
		JCheckBox random;
		ListeDynamique.clear();
		toutDecocher();
		ArrayList<JCheckBox> randomList = new ArrayList<JCheckBox>();
		for (int i = 0 ; i < nbMol ; i++){
			random = ListCheckBoxImmobile.get(randomizer.nextInt(ListCheckBoxImmobile.size()));
			if (!(randomList.contains(random))){
				random.doClick();
			}
			random = ListCheckBoxConfine.get(randomizer.nextInt(ListCheckBoxConfine.size()));
			if (!(randomList.contains(random))){
				random.doClick();
			}
			random = ListCheckBoxDirectionnelle.get(randomizer.nextInt(ListCheckBoxDirectionnelle.size()));
			if (!(randomList.contains(random))){
				random.doClick();
			}
			random = ListCheckBoxAleatoire.get(randomizer.nextInt(ListCheckBoxAleatoire.size()));
			if (!(ListeDynamique.contains(random))){
				random.doClick();
			}
		}
		panelBasGauche.revalidate();
		panelBasGauche.repaint();
	}

	private void toutDecocher() {
		for (JCheckBox jc : ListCheckBoxImmobile){
			jc.setSelected(false);
			jc.setFont(new Font("default", Font.PLAIN, 12));
		}
		for (JCheckBox jc : ListCheckBoxConfine){
			jc.setSelected(false);
			jc.setFont(new Font("default", Font.PLAIN, 12));
		}
		for (JCheckBox jc : ListCheckBoxDirectionnelle){
			jc.setSelected(false);
			jc.setFont(new Font("default", Font.PLAIN, 12));
		}
		for (JCheckBox jc : ListCheckBoxAleatoire){
			jc.setSelected(false);
			jc.setFont(new Font("default", Font.PLAIN, 12));
		}
	}
}