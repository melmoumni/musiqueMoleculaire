

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Color;

public class FenetreParametres extends JFrame implements ActionListener{

	private JTextField filenameT; // nom du fichier Trajectoires
	private JTextField filenameM; // nom du fichier Mouvements
	
	/* Declaration des boutons, necessaires pour lancer des actions */
	JButton btnChoisirTrajectoires;
	JButton btnChoisirMvt;
	JButton btnValider;
	
	JSpinner valeurH; // hauteur de l'image
	JSpinner valeurL; // largeur de l'image
	
	JComboBox<String> notes; // liste des notes de references possibles
	
	JSpinner valeurDuree; // duree souhaitee pour l'enregistrement
	
	JSpinner valeurAlpha1; // valeur du 1er seuil alpha : entre immobiles et confinees
	JSpinner valeurAlpha2; // valeur du 2eme seuil alpha : entre confinees et directionnelles 
	JSpinner valeurAlpha3; // valeur du 3eme seuil alpha : entre directionnelles et diffusives
	
	ButtonGroup groupe;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		FenetreParametres param = new FenetreParametres();
	}

	/**
	 * Creation de la fenetre et ajout des differents composants
	 */
	public FenetreParametres() {
		this.setTitle("Parametres de l'application");
		this.setBounds(100, 100, 525, 500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Donnés d'entrées", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel label = new JLabel("Fichiers");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel label_5 = new JLabel("Trajectoires");
		
		filenameT = new JTextField();
		filenameT.setColumns(10);
		
		filenameM = new JTextField();
		filenameM.setColumns(10);
		
		JLabel label_6 = new JLabel("Mouvements");
		
		btnChoisirTrajectoires = new JButton("Choisir un fichier");
		btnChoisirTrajectoires.addActionListener(this);
		btnChoisirMvt = new JButton("Choisir un fichier");
		btnChoisirMvt.addActionListener(this);
		
		JLabel label_7 = new JLabel("Taille de l'image");
		label_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblHauteur = new JLabel("Hauteur");	
		JLabel lblLargeur = new JLabel("Largeur");	
		valeurH = new JSpinner();
		valeurL = new JSpinner();
		
		JPanel panel = new JPanel();
		
		JLabel lblNoteDeRfrnce = new JLabel("Note de reference");
		String[] listeNotes = { "do", "ré", "mi", "fa", "sol", "la","si" };
		notes = new JComboBox<String>(listeNotes);
		
		JLabel lblSeuils = new JLabel("Seuils alpha");
		lblSeuils.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblImmobiles = new JLabel("Immobiles");
		JLabel lblConfines = new JLabel("Confinees");
		JLabel lblDirectionnelles = new JLabel("Directionnelles");	
		JLabel lblDiffusives = new JLabel("Diffusives");
		JLabel label_2 = new JLabel("<");
		
		valeurAlpha1 = new JSpinner();
		valeurAlpha1.setModel(new SpinnerNumberModel(new Float(0.25), new Float(0), new Float(2), new Float(0.1)));
		
		valeurAlpha2 = new JSpinner();
		valeurAlpha2.setModel(new SpinnerNumberModel(new Float(0.90), new Float(0), new Float(2), new Float(0.1)));
		
		valeurAlpha3 = new JSpinner();
		valeurAlpha3.setModel(new SpinnerNumberModel(new Float(1.10), new Float(0), new Float(2), new Float(0.1)));
		
		JLabel label_4 = new JLabel("<");	
		JLabel label_3 = new JLabel("<");
		
		JLabel lblDure = new JLabel("Duréé souhaitée");
		valeurDuree = new JSpinner();
		JLabel lblS = new JLabel("s");
		
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
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblSeuils))
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(34)
											.addComponent(lblImmobiles)
											.addGap(18)
											.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblConfines)))
									.addGap(29))
								.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(rdbtnChercheur)
										.addComponent(valeurAlpha1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
									.addGap(49)))
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
								.addComponent(valeurAlpha2, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblDirectionnelles)
									.addGap(8)
									.addComponent(label_3)
									.addGap(18)
									.addComponent(lblDiffusives))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(77)
									.addComponent(valeurAlpha3, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(rdbtnCompositeur))))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNoteDeRfrnce)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(notes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(48)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnValider)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblDure)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(valeurDuree, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblS, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(69, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNoteDeRfrnce)
						.addComponent(notes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDure)
						.addComponent(valeurDuree, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblS))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(115)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnCompositeur)
								.addComponent(rdbtnChercheur)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(16)
							.addComponent(lblSeuils)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblImmobiles)
								.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblConfines)
								.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDirectionnelles)
								.addComponent(label_3)
								.addComponent(lblDiffusives))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(valeurAlpha2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(valeurAlpha3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(valeurAlpha1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(18)
					.addComponent(btnValider)
					.addContainerGap(30, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(this.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 494, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 465, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(21, Short.MAX_VALUE))
		);
		
		JLabel lblPixels = new JLabel("pixels");
		
		JLabel label_1 = new JLabel("pixels");
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
					.addContainerGap(85, Short.MAX_VALUE))
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
							.addComponent(label_1)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
	}
	
	/* Accesseurs pour les differents champs de formulaire */
	public String getFilenameT(){
		return filenameT.getText();
	}
	
	public String getFilenameM(){
		return filenameM.getText();
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
	
	public int getDuree(){
		return (Integer) valeurDuree.getValue();
	}
	
	public String getNoteRef(){
		return notes.getSelectedItem().toString();
	}
	
	public String getUtilisateur(){
		return groupe.getSelection().getActionCommand();
	}
}
