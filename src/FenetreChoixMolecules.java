import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;



public class FenetreChoixMolecules extends JFrame implements ActionListener{

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

	private JButton btnSave;
	
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
			Controleur.analyseMolecules();
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
				System.out.println(moleculePropriete.getEffet().getClass().getName());
				switch (moleculePropriete.getEffet().getClass().getName()){
				case "Tenu":
					FenetreParametreTenu frameTenu = new FenetreParametreTenu(moleculePropriete);
					frameTenu.setVisible(true);
					break;
				case "Glissando":
					FenetreParametreGlissando frameGlissando = new FenetreParametreGlissando(moleculePropriete);
					frameGlissando.setVisible(true);
					break;
				case "Tremolo":
					FenetreParametreTremolo frameTremolo = new FenetreParametreTremolo(moleculePropriete);
					frameTremolo.setVisible(true);
					break;
				case "Boucle":
					FenetreParametreBoucle frameBoucle = new FenetreParametreBoucle(moleculePropriete);
					frameBoucle.setVisible(true);
					break;
				case "Aleatoire":
					FenetreParametreAleatoire frameAleatoire= new FenetreParametreAleatoire(moleculePropriete);
					frameAleatoire.setVisible(true);
					break;
				default:
					break;

				}
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
		
		Dimension maxDim = Controleur.maxDimension();
		
		JPanel panelHautGauche = new JPanel();
		panelHautGauche.setBounds(0, 0, (int) (dim.getWidth()/2), (int) dim.getHeight() / 2);
		panelHautGauche.setLayout(new BoxLayout(panelHautGauche, BoxLayout.X_AXIS));
		FenetreTrajectoires TrajectoiresHaut = new FenetreTrajectoires(Controleur.molecules(),(int) (maxDim.getWidth() + maxDim.getWidth()/10), (int) (maxDim.getHeight() + maxDim.getHeight()/10), true); 
		panelHautGauche.add(new JScrollPane(TrajectoiresHaut));
		panelHautGauche.setVisible(true);
		getContentPane().add(panelHautGauche);

		panelBasGauche = new JPanel();
		panelBasGauche.setBounds(0, (int) dim.getHeight() / 2, (int) (dim.getWidth()/2), (int) dim.getHeight() / 2 - 30);
		ListeDynamique = new ArrayList<Molecule>(Controleur.molecules());
		panelBasGauche.setLayout(new BorderLayout(0, 0));
		FenetreTrajectoires TrajectoiresBas = new FenetreTrajectoires(ListeDynamique, (int) (maxDim.getWidth() + maxDim.getWidth()/10), (int) (maxDim.getHeight() + maxDim.getHeight()/10), true); 
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
		panelDroit.setBounds((int) (dim.getWidth()/2), 10,(int) (dim.getWidth()/2), (int) dim.getHeight());
		getContentPane().add(panelDroit);
		panelDroit.setLayout(null);
		Box boxHautGauche = Box.createVerticalBox();
		for (Molecule mol : ListeImmobile){
			JCheckBox j = new MyJCheckBox("Immobile "+mol.numero()+"      ("+mol.instantInitial()+" - "+ mol.instantFinal()+")", mol);
			j.setSelected(true);
			j.addActionListener(actionListener);
			boxHautGauche.add(j);
			ListCheckBoxImmobile.add(j);
		}
		Box boxHautDroit = Box.createVerticalBox();
		for (Molecule mol : ListeConfine){
			JCheckBox j = new MyJCheckBox("Confine "+mol.numero()+"      ("+mol.instantInitial()+" - "+ mol.instantFinal()+")", mol);
			j.setSelected(true);
			j.addActionListener(actionListener);
			boxHautDroit.add(j);
			ListCheckBoxConfine.add(j);
		}
		Box boxBasGauche = Box.createVerticalBox();
		for (Molecule mol : ListeDirectionnelle){
			JCheckBox j = new MyJCheckBox("Directionnelle "+mol.numero()+"      ("+mol.instantInitial()+" - "+ mol.instantFinal()+")", mol);
			j.setSelected(true);
			j.addActionListener(actionListener);
			boxBasGauche.add(j);
			ListCheckBoxDirectionnelle.add(j);
		}
		Box boxBasDroit = Box.createVerticalBox();
		for (Molecule mol : ListeAleatoire){
			JCheckBox j = new MyJCheckBox("Aleatoire "+mol.numero()+"      ("+mol.instantInitial()+" - "+ mol.instantFinal()+")", mol);
			j.setSelected(true);
			j.addActionListener(actionListener);
			boxBasDroit.add(j);
			ListCheckBoxAleatoire.add(j);
		}	    

		JLabel labelHautGauche = new JLabel("Immobile");
		labelHautGauche.setBounds(panelDroit.getWidth()/4-50, 0, 53, 16);
		panelDroit.add(labelHautGauche);

		JLabel labelHautDroit = new JLabel("Confine");
		labelHautDroit.setBounds(3*panelDroit.getWidth()/4 - 50 , 0, 43, 16);
		panelDroit.add(labelHautDroit);

		JLabel labelBasGauche = new JLabel("Directionnelle");
		labelBasGauche.setBounds(panelDroit.getWidth()/4-53, panelDroit.getHeight()/3-15, 99, 16);
		panelDroit.add(labelBasGauche);

		JLabel labelBasDroit = new JLabel("Aleatoire");
		labelBasDroit.setBounds(3*panelDroit.getWidth()/4 - 50, panelDroit.getHeight()/3-15, 51, 16);
		panelDroit.add(labelBasDroit);


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
		buttonCocherImmobile.setBounds(0, panelDroit.getHeight()/4 + 25, 110, 25);
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
		buttonDecocherImmobile.setBounds(panelDroit.getWidth()/2 -50 - 124, panelDroit.getHeight()/4 + 25, 124, 25);
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
		buttonCocherConfine.setBounds(panelDroit.getWidth()/2, panelDroit.getHeight()/4 + 25, 110, 25);
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
		buttonDecocherConfine.setBounds(panelDroit.getWidth() - 50 -124, panelDroit.getHeight()/4 + 25, 124, 25);
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
		buttonCocherDirectionnelle.setBounds(0, panelDroit.getHeight()/3 + panelDroit.getHeight()/4 + 10, 110, 25);
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
		buttonDecocherDirectionnelle.setBounds(panelDroit.getWidth()/2 -50 - 124, panelDroit.getHeight()/3 + panelDroit.getHeight()/4 + 10, 124, 25);
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
		buttonCocherAleatoire.setBounds(panelDroit.getWidth()/2, panelDroit.getHeight()/3 + panelDroit.getHeight()/4 + 10, 110, 25);
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
		buttonDecocherAleatoire.setBounds(panelDroit.getWidth() - 50 -124, panelDroit.getHeight()/3 + panelDroit.getHeight()/4 + 10, 124, 25);
		panelDroit.add(buttonDecocherAleatoire);

		JPanel panel = new JPanel();
		panel.setBounds(0, panelDroit.getHeight()/3 + panelDroit.getHeight()/4 + 20, 628, 337);
		panelDroit.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{25, 0, 0, 25, 0};
		gbl_panel.rowHeights = new int[]{25, 0, 10, 0, 10, 0, 0, 25, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblNombreDeMolcules = new JLabel("<html>Nombre de Molécules par catégorie :</html>");
		GridBagConstraints gbc_lblNombreDeMolcules = new GridBagConstraints();
		gbc_lblNombreDeMolcules.anchor = GridBagConstraints.WEST;
		gbc_lblNombreDeMolcules.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreDeMolcules.gridx = 1;
		gbc_lblNombreDeMolcules.gridy = 1;
		panel.add(lblNombreDeMolcules, gbc_lblNombreDeMolcules);

		spinnerNbMol = new JSpinner();
		GridBagConstraints gbc_spinnerNbMol = new GridBagConstraints();
		gbc_spinnerNbMol.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerNbMol.gridx = 2;
		gbc_spinnerNbMol.gridy = 1;
		panel.add(spinnerNbMol, gbc_spinnerNbMol);
		spinnerNbMol.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(0), new Integer(1)));
		int maxNbMol = max(ListeAleatoire.size(),ListeConfine.size(),ListeDirectionnelle.size(),ListeImmobile.size());
		if (maxNbMol >1){
			spinnerNbMol.setValue(new Integer(2));
		}


		JButton btnSelectionAutomatique = new JButton("Selection automatique");
		GridBagConstraints gbc_btnSelectionAutomatique = new GridBagConstraints();
		gbc_btnSelectionAutomatique.insets = new Insets(0, 0, 5, 0);
		gbc_btnSelectionAutomatique.gridx = 4;
		gbc_btnSelectionAutomatique.gridy = 1;
		panel.add(btnSelectionAutomatique, gbc_btnSelectionAutomatique);
		btnSelectionAutomatique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectRandomMolecule((int) spinnerNbMol.getValue());
			}
		});

		JLabel lblContraintes = new JLabel("Contraintes :");
		GridBagConstraints gbc_lblContraintes = new GridBagConstraints();
		gbc_lblContraintes.insets = new Insets(0, 0, 5, 5);
		gbc_lblContraintes.gridx = 1;
		gbc_lblContraintes.gridy = 3;
		panel.add(lblContraintes, gbc_lblContraintes);
		lblContraintes.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel lblNombreDePoints = new JLabel("Nombre de points minimum :");
		GridBagConstraints gbc_lblNombreDePoints = new GridBagConstraints();
		gbc_lblNombreDePoints.anchor = GridBagConstraints.WEST;
		gbc_lblNombreDePoints.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreDePoints.gridx = 1;
		gbc_lblNombreDePoints.gridy = 5;
		panel.add(lblNombreDePoints, gbc_lblNombreDePoints);

		spinnerPoints = new JSpinner();
		GridBagConstraints gbc_spinnerPoints = new GridBagConstraints();
		gbc_spinnerPoints.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerPoints.gridx = 2;
		gbc_spinnerPoints.gridy = 5;
		panel.add(spinnerPoints, gbc_spinnerPoints);
		spinnerPoints.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));

		JButton btnEnregistrerLesContraintes = new JButton("Enregistrer les contraintes");
		GridBagConstraints gbc_btnEnregistrerLesContraintes = new GridBagConstraints();
		gbc_btnEnregistrerLesContraintes.gridheight = 2;
		gbc_btnEnregistrerLesContraintes.insets = new Insets(0, 0, 5, 0);
		gbc_btnEnregistrerLesContraintes.gridx = 4;
		gbc_btnEnregistrerLesContraintes.gridy = 5;
		panel.add(btnEnregistrerLesContraintes, gbc_btnEnregistrerLesContraintes);
		btnEnregistrerLesContraintes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectMoleculesContraintes((int) spinnerPoints.getValue(), (int) spinnerIntensite.getValue());
			}
		});

		JLabel lblIntensite = new JLabel("Intensite minimum :");
		GridBagConstraints gbc_lblIntensite = new GridBagConstraints();
		gbc_lblIntensite.anchor = GridBagConstraints.WEST;
		gbc_lblIntensite.insets = new Insets(0, 0, 5, 5);
		gbc_lblIntensite.gridx = 1;
		gbc_lblIntensite.gridy = 6;
		panel.add(lblIntensite, gbc_lblIntensite);

		spinnerIntensite = new JSpinner();
		GridBagConstraints gbc_spinnerIntensite = new GridBagConstraints();
		gbc_spinnerIntensite.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerIntensite.gridx = 2;
		gbc_spinnerIntensite.gridy = 6;
		panel.add(spinnerIntensite, gbc_spinnerIntensite);
		spinnerIntensite.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));

		JButton btnValider = new JButton("Valider le choix des proteines");
		GridBagConstraints gbc_btnValider = new GridBagConstraints();
		gbc_btnValider.gridwidth = 3;
		gbc_btnValider.insets = new Insets(0, 0, 5, 5);
		gbc_btnValider.gridx = 1;
		gbc_btnValider.gridy = 8;
		panel.add(btnValider, gbc_btnValider);
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Midi.liberer();
				//Controleur.printTrajectoires();
				Controleur.remplirSequence(ListeDynamique);
				Midi.jouerSequence();
			}
		});
		
		btnSave = new JButton("Enregistrer le son");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.gridwidth = 3;
		gbc_btnSave.insets = new Insets(0, 0, 0, 5);
		gbc_btnSave.gridx = 1;
		gbc_btnSave.gridy = 9;
		panel.add(btnSave, gbc_btnSave);
		btnSave.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSave){
			JFileChooser chooser = new JFileChooser();
	    	FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt", "trc");
	    	chooser.setFileFilter(filter);
	    	int returnVal = chooser.showSaveDialog(this);
	    	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    		try {
					Midi.saveMidi(chooser.getSelectedFile().getPath());
				} catch (IOException e1) {
					e1.printStackTrace();
				} 
	    	}
		}
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