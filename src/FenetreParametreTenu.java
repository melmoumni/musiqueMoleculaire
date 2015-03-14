import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;


public class FenetreParametreTenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreParametreTenu frame = new FenetreParametreTenu(null);
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
	public FenetreParametreTenu(Molecule mol) {
		this.setTitle("Fenetre parametre effet TENU");
		
		ArrayList<String> listeEffets = new ArrayList<String>();
		listeEffets.add("Tenu");
		listeEffets.add("Tremolo");
		listeEffets.add("Glissando");
		listeEffets.add("Boucle");
		listeEffets.add("Aleatoire");
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 488, 271);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblParametreDeLa = new JLabel("Parametre de la molecule :");
		lblParametreDeLa.setBounds(145, 13, 202, 30);
		contentPane.add(lblParametreDeLa);
		
		JLabel lblNoteDeReference = new JLabel("Note de reference :");
		lblNoteDeReference.setBounds(12, 61, 126, 30);
		contentPane.add(lblNoteDeReference);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, 0, 128, 1));
		spinner.setBounds(155, 65, 49, 22);
		spinner.setValue(mol.note());
		contentPane.add(spinner);
		
		JLabel lblilVousFaudra = new JLabel("<html>(il vous faudra recharger la fenetre de parametre de la molecule)</html>");
		lblilVousFaudra.setBounds(263, 91, 207, 56);
		contentPane.add(lblilVousFaudra);
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnValider.setBounds(185, 186, 87, 25);
		contentPane.add(btnValider);
		
		JLabel lblEffet = new JLabel("Effet :");
		lblEffet.setBounds(12, 105, 56, 16);
		contentPane.add(lblEffet);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox<String> comboBox_1 = new JComboBox(listeEffets.toArray());
		comboBox_1.setBounds(150, 102, 94, 22);
		comboBox_1.setSelectedItem(mol.getEffet().getClass().getName());
		contentPane.add(comboBox_1);
		
	}
}
