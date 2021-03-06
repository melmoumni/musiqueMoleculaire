

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FenetreParametres extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField filenameT; // nom du fichier Trajectoires
	private JTextField filenameM; // nom du fichier Mouvements
	private JTextField banqueMidi; // nom du fichier pour la banque Mifdi
	private JTextField parametres; // nom du fichier des anciens parametres sauvegardes
	
	/* Declaration des boutons, necessaires pour lancer des actions */
	JButton btnChoisirTrajectoires;
	JButton btnChoisirMvt;
	JButton btnValider;
	JButton btnChoisirParametres;
	JButton btnChoisirBanqueMidi;
	
	JSpinner valeurH; // hauteur de l'image
	JSpinner valeurL; // largeur de l'image
	
	JCheckBox chckbxAjusterLaTaille;
	
	JComboBox<String> notes; // liste des notes de references possibles
	JSpinner valeurTempo; // duree souhaitee pour l'enregistrement
	
	JSpinner valeurAlpha1; // valeur du 1er seuil alpha : entre immobiles et confinees
	JSpinner valeurAlpha2; // valeur du 2eme seuil alpha : entre confinees et directionnelles 
	JSpinner valeurAlpha3; // valeur du 3eme seuil alpha : entre directionnelles et diffusives
	
	JSlider valeurI; // valeur du seuil intensité, uniquement pour le compositeur
	
	ButtonGroup groupe;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new FenetreParametres();
	}

	/**
	 * Creation de la fenetre et ajout des differents composants
	 */
	public FenetreParametres() {
		this.setTitle("Parametres de l'application");
		this.setBounds(100, 100, 555, 672);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Donnees d'entrees", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel label = new JLabel("Fichiers");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel label_5 = new JLabel("Trajectoires");
		
		filenameT = new JTextField();
		filenameT.setColumns(10);
		
		filenameM = new JTextField();
		filenameM.setColumns(10);
		
		JLabel label_6 = new JLabel("Analyse");
		
		btnChoisirTrajectoires = new JButton("Choisir un fichier");
		btnChoisirTrajectoires.addActionListener(this);
		btnChoisirMvt = new JButton("Choisir un fichier");
		btnChoisirMvt.addActionListener(this);
		
		JLabel label_7 = new JLabel("Taille de l'image");
		label_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblHauteur = new JLabel("Hauteur");	
		JLabel lblLargeur = new JLabel("Largeur");	
		valeurH = new JSpinner(new SpinnerNumberModel(new Integer(0),new Integer(0),new Integer(5000),new Integer(1)));
		valeurL = new JSpinner(new SpinnerNumberModel(new Integer(0),new Integer(0),new Integer(5000),new Integer(1)));
		
		JPanel panel = new JPanel();
		
		JLabel lblNoteDeRfrnce = new JLabel("Note de reference");
		String[] listeNotes = { "do", "re", "mi", "fa", "sol", "la","si" };
		notes = new JComboBox<String>(listeNotes);
		
		JLabel lblSeuils = new JLabel("Seuils alpha");
		lblSeuils.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblImmobiles = new JLabel("Immobiles");
		JLabel lblConfines = new JLabel("Confinees");
		JLabel lblDirectionnelles = new JLabel("Directionnelles");	
		JLabel lblDiffusives = new JLabel("Diffusives");
		JLabel label_2 = new JLabel("<");
		
		valeurAlpha1 = new JSpinner();
		valeurAlpha1.setModel(new SpinnerNumberModel(new Float(0.25), new Float(0), new Float(2), new Float(0.05)));
		
		valeurAlpha2 = new JSpinner();
		valeurAlpha2.setModel(new SpinnerNumberModel(new Float(0.90), new Float(0), new Float(2), new Float(0.05)));
		
		valeurAlpha3 = new JSpinner();
		valeurAlpha3.setModel(new SpinnerNumberModel(new Float(1.10), new Float(0), new Float(2), new Float(0.05)));
		
		ChangeListener listener = new ChangeListener() {
		      public void stateChanged(ChangeEvent e) {
		    	  Float alpha1 = getValeurAlpha1();
		    	  Float alpha2 = getValeurAlpha2();
		    	  Float alpha3 = getValeurAlpha3();
		    	  if(e.getSource()==valeurAlpha1){  
		    		  if(alpha1>alpha2){
		    			  valeurAlpha2.setValue(alpha1 + 0.1f);
		    		  }
		    	  }
		    	  else if(e.getSource()==valeurAlpha2){	  
		    		  if(alpha2<=alpha1){
		    			  valeurAlpha1.setValue(alpha2 - 0.1f);
		    		  }
		    		  if(alpha2>=alpha3){
		    			  valeurAlpha3.setValue(alpha2 + 0.1f);
		    		  }
		    	  }
		    	  else if(e.getSource()==valeurAlpha2){	  
		    		  if(alpha3<=alpha2){
		    			  valeurAlpha2.setValue(alpha3 - 0.1f);
		    		  }
		    	  }
		      }
		    };
		
		valeurAlpha1.addChangeListener(listener);
		valeurAlpha2.addChangeListener(listener);
		valeurAlpha3.addChangeListener(listener);
		
		JLabel label_4 = new JLabel("<");	
		JLabel label_3 = new JLabel("<");
		
		JLabel lblDure = new JLabel("Tempo souhaitee");
		valeurTempo = new JSpinner(new SpinnerNumberModel(new Integer(60),new Integer(10),new Integer(300),new Integer(1)));
		
		JLabel lblSeuilIntensite = new JLabel("Seuil intensite");
		valeurI = new JSlider(JSlider.HORIZONTAL,0,100,80);
		valeurI.setMajorTickSpacing(10);
		valeurI.setPaintTicks(true);
		valeurI.setPaintLabels(true);
		
		groupe = new ButtonGroup();
		JRadioButton rdbtnChercheur = new JRadioButton("Chercheur");
		rdbtnChercheur.setActionCommand("chercheur");
		rdbtnChercheur.setToolTipText("Tableau des instruments");
		groupe.add(rdbtnChercheur);
		
		JRadioButton rdbtnCompositeur = new JRadioButton("Compositeur");
		rdbtnCompositeur.setActionCommand("compositeur");
		rdbtnCompositeur.setToolTipText("Analyse axiale");
		groupe.add(rdbtnCompositeur);
		
		btnValider = new JButton(new ChangerFenetre("Valider"));
		
		
		/* Positionnement des differents composants 
		 *  Layout GroupLayout
		 *  Realise a l'aide du plugin WindowsBuilder sur Eclipse
		 */
		
		JLabel lblChargerDesParamtres = new JLabel("Charger des parametres");
		
		JLabel lblEn = new JLabel("en %");
		
		parametres = new JTextField();
		parametres.setColumns(10);
		
		btnChoisirParametres = new JButton("Choisir un fichier");
		btnChoisirParametres.addActionListener(this);
		
		JLabel lblBanqueMidi = new JLabel("Banque Midi");
		
		banqueMidi= new JTextField();
		banqueMidi.setColumns(10);
		
		btnChoisirBanqueMidi= new JButton("Choisir un fichier");
		btnChoisirBanqueMidi.addActionListener(this);
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblSeuils))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(34)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(valeurAlpha1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(lblImmobiles)
											.addGap(18)
											.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblConfines)))
							.addGap(29)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(valeurAlpha2, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblDiffusives)))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(1)
									.addComponent(valeurAlpha3, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(18)
									.addComponent(label_3)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblDirectionnelles))))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNoteDeRfrnce)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(notes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(48)
							.addComponent(lblDure)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(valeurTempo, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblSeuilIntensite)
							.addGap(18)
							.addComponent(valeurI, GroupLayout.PREFERRED_SIZE, 313, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblEn))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(113)
							.addComponent(rdbtnChercheur)
							.addGap(71)
							.addComponent(rdbtnCompositeur))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblBanqueMidi)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(banqueMidi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnChoisirBanqueMidi))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblChargerDesParamtres)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(parametres, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnChoisirParametres))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(206)
							.addComponent(btnValider)))
					.addContainerGap(23, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNoteDeRfrnce)
						.addComponent(notes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDure)
						.addComponent(valeurTempo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(16)
					.addComponent(lblSeuils)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblImmobiles)
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblConfines)
						.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDiffusives)
						.addComponent(label_3)
						.addComponent(lblDirectionnelles))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valeurAlpha2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(valeurAlpha1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(valeurAlpha3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(25)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(valeurI, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSeuilIntensite)
						.addComponent(lblEn))
					.addGap(48)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBanqueMidi)
						.addComponent(banqueMidi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnChoisirBanqueMidi))
					.addGap(31)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblChargerDesParamtres)
						.addComponent(parametres, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnChoisirParametres))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnCompositeur)
						.addComponent(rdbtnChercheur))
					.addGap(13)
					.addComponent(btnValider)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(this.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 465, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(78, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel lblPixels = new JLabel("pixels");
		
		JLabel label_1 = new JLabel("pixels");
		
		chckbxAjusterLaTaille = new JCheckBox("<html>Ajuster la taille par rapport au fichier</html>");
		chckbxAjusterLaTaille.setSelected(true);
		
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(label)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(24)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(label_6)
									.addGap(18)
									.addComponent(filenameM))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label_5)
									.addGap(18)
									.addComponent(filenameT)))
							.addGap(18)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(btnChoisirMvt)
								.addComponent(btnChoisirTrajectoires)))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(label_7)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(27)
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_1.createSequentialGroup()
											.addComponent(lblLargeur)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(valeurL, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(label_1))
										.addGroup(gl_panel_1.createSequentialGroup()
											.addComponent(lblHauteur)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(valeurH, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(lblPixels)))))
							.addPreferredGap(ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
							.addComponent(chckbxAjusterLaTaille, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addContainerGap(34, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(filenameT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_5))
						.addComponent(btnChoisirTrajectoires))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_6)
						.addComponent(filenameM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnChoisirMvt))
					.addGap(18)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(label_7)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblHauteur)
								.addComponent(valeurH, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPixels))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblLargeur)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
									.addComponent(valeurL, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(label_1))))
						.addComponent(chckbxAjusterLaTaille, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		this.getContentPane().setLayout(groupLayout);
	}

	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnChoisirTrajectoires) {
	    	JFileChooser chooser = new JFileChooser();
	    	FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt", "trc");

	    	chooser.setFileFilter(filter);
	    	int returnVal = chooser.showOpenDialog(this);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            filenameT.setText(chooser.getSelectedFile().getPath()); 
	        }
	   }
	    else if (e.getSource() == btnChoisirMvt) {
	    	JFileChooser chooser = new JFileChooser();
	    	FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt","trc");
	    	chooser.setFileFilter(filter);
	    	int returnVal = chooser.showOpenDialog(this);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            filenameM.setText(chooser.getSelectedFile().getPath());
	        }
	    }
	    else if (e.getSource() == btnChoisirBanqueMidi) {
	    	JFileChooser chooser = new JFileChooser();
	    	int returnVal = chooser.showOpenDialog(this);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            banqueMidi.setText(chooser.getSelectedFile().getPath());
	        }
	    }
	    else if (e.getSource() == btnChoisirParametres) {
	    	JFileChooser chooser = new JFileChooser();
	    	FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
	    	chooser.setFileFilter(filter);
	    	int returnVal = chooser.showOpenDialog(this);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            parametres.setText(chooser.getSelectedFile().getPath());
	        }
	    }
	}
	
	/* Accesseurs pour les differents champs de formulaire */
	public String getFilenameT(){
		return filenameT.getText();
	}
	
	public String getFilenameM(){
		return filenameM.getText();
	}
	
	public String getBanqueMidi(){
		String banque="";
		try{
			banque = banqueMidi.getText();
		}
		catch(NullPointerException e){}
		return banque;
	}
	
	public String getParametres(){
		String Fparametres="";
		try{
			Fparametres = parametres.getText();
		}
		catch(NullPointerException e){}
		return Fparametres;

	}
	
	public int getHauteur(){
		return (Integer) valeurH.getValue();
	}
	
	public int getLargeur(){
		return (Integer) valeurL.getValue();
	}
	
	public float getValeurAlpha1(){
		return (Float) valeurAlpha1.getValue();
	}
	
	public float getValeurAlpha2(){
		return (Float) valeurAlpha2.getValue();
	}
	
	public float getValeurAlpha3(){
		return (Float) valeurAlpha3.getValue();
	}
	
	public int getTempo(){
		return (Integer) valeurTempo.getValue();
	}
	
	public int getIntensite(){
		return valeurI.getValue();
	}
	
	public String getNoteRef(){
		return notes.getSelectedItem().toString();
	}
	
	public String getUtilisateur(){
		String utilisateur="";
		try{
			utilisateur = groupe.getSelection().getActionCommand();
		}
		catch(NullPointerException e){}
		return utilisateur;
	}
	
	public boolean isAutoSize(){
		return chckbxAjusterLaTaille.isSelected();
	}
}
