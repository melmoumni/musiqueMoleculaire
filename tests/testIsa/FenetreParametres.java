package testIsa;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;

public class FenetreParametres {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreParametres window = new FenetreParametres();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FenetreParametres() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Paramètres de l'application");
		frame.setBounds(100, 100, 630, 450);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblFichiers = new JLabel("Fichiers");
		
		JLabel lblTrajectoires = new JLabel("Trajectoires");
		
		JLabel lblMouvements = new JLabel("Mouvements");
		
		JLabel lblTailleDeLimage = new JLabel("Taille de l'image");
		
		JLabel lblX = new JLabel("hauteur");
		
		JLabel lblPixels = new JLabel("pixels");
		
		JSpinner valeurH = new JSpinner();
		
		JSpinner valeurL = new JSpinner();
		
		JLabel lblLargeur = new JLabel("largeur");
		
		JLabel label_1 = new JLabel("pixels");
		
		JLabel lblNoteDeRfrnce = new JLabel("Note de référénce");
		
		String[] listeNotes = { "do", "ré", "mi", "fa", "sol", "la","si" };
		JComboBox notes = new JComboBox(listeNotes);
		
		JLabel lblSeuils = new JLabel("Seuils");
		
		JLabel lblImmobiles = new JLabel("Immobiles");
		
		JLabel lblConfines = new JLabel("Confinées");
		
		JLabel lblDirectionnelles = new JLabel("Directionnelles");
		
		JLabel lblDiffusives = new JLabel("Diffusives");
		
		JLabel label_2 = new JLabel("<");
		
		JSpinner valeurAlpha1 = new JSpinner();
		valeurAlpha1.setModel(new SpinnerNumberModel(new Float(0.1), new Float(0), new Float(2), new Float(0.1)));
		
		JSpinner valeurAlpha2 = new JSpinner();
		valeurAlpha2.setModel(new SpinnerNumberModel(new Float(0.1), new Float(0), new Float(2), new Float(0.1)));
		
		JSpinner valeurAlpha3 = new JSpinner();
		valeurAlpha3.setModel(new SpinnerNumberModel(new Float(0.5), new Float(0), new Float(2), new Float(0.1)));
		
		JLabel label_4 = new JLabel("<");
		
		JLabel label_3 = new JLabel("<");
		
		JButton btnValider = new JButton("Valider");
		
		JLabel lblDure = new JLabel("Durée");
		
		JSpinner valeurDuree = new JSpinner();
		
		JLabel lblS = new JLabel("s");
		
		JRadioButton rdbtnChercheur = new JRadioButton("Chercheur");
		rdbtnChercheur.setToolTipText("Tableau des instruments");
		
		JRadioButton rdbtnCompositeur = new JRadioButton("Compositeur");
		rdbtnCompositeur.setToolTipText("Analyse axiale");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(12)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(lblNoteDeRfrnce)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(notes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(lblSeuils)
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(12)
											.addComponent(lblImmobiles)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel.createSequentialGroup()
													.addComponent(valeurAlpha1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
													.addGap(75)
													.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
														.addComponent(btnValider)
														.addComponent(valeurAlpha2, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)))
												.addGroup(gl_panel.createSequentialGroup()
													.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(lblConfines)
													.addGap(18)
													.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
												.addComponent(rdbtnChercheur))))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblDirectionnelles)
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(2)
											.addComponent(rdbtnCompositeur))))
								.addGroup(gl_panel.createSequentialGroup()
									.addContainerGap()
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblLargeur)
										.addGroup(gl_panel.createSequentialGroup()
											.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
												.addComponent(lblTailleDeLimage)
												.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
													.addComponent(lblMouvements)
													.addComponent(lblTrajectoires)))
											.addGap(18)
											.addComponent(lblX)))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(valeurH, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblPixels))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(valeurL, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(label_1)))))
							.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(7)
									.addComponent(valeurAlpha3, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblDure, Alignment.TRAILING)
										.addComponent(label_3, Alignment.TRAILING))
									.addGap(18)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblDiffusives)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(valeurDuree, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblS)))))
							.addGap(28))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblFichiers)))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(18)
					.addComponent(lblFichiers)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblTrajectoires)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblMouvements)
					.addGap(25)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTailleDeLimage)
						.addComponent(lblX)
						.addComponent(valeurH, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPixels)
						.addComponent(lblDure)
						.addComponent(valeurDuree, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblS))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLargeur)
						.addComponent(valeurL, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1))
					.addGap(29)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(notes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNoteDeRfrnce))
					.addGap(18)
					.addComponent(lblSeuils)
					.addGap(8)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblImmobiles)
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblConfines)
						.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDirectionnelles)
						.addComponent(lblDiffusives)
						.addComponent(label_3))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(valeurAlpha2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(valeurAlpha3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(valeurAlpha1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnCompositeur)
						.addComponent(rdbtnChercheur))
					.addGap(9)
					.addComponent(btnValider))
		);
		panel.setLayout(gl_panel);
		
	}
}
