import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;


public class FenetreParametreAleatoire extends JFrame {

	JSpinner spinner;
	JComboBox<String> comboBox_1;
	JSlider sliderVol; 

	
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
					FenetreParametreAleatoire frame = new FenetreParametreAleatoire(null);
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FenetreParametreAleatoire(final Molecule mol) {
		this.setTitle("Fenetre parametre effet ALEATOIRE");

		ArrayList<String> listeEffets = new ArrayList<String>();
		listeEffets.add("Tenu");
		listeEffets.add("Tremolo");
		listeEffets.add("Glissando");
		listeEffets.add("Boucle");
		listeEffets.add("Aleatoire");
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 498, 317);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblParametreDeLa = new JLabel("Parametre de la molecule :");
		lblParametreDeLa.setBounds(151, 13, 202, 30);
		contentPane.add(lblParametreDeLa);
		
		JLabel lblNoteDeReference = new JLabel("Note de reference :");
		lblNoteDeReference.setBounds(12, 61, 126, 30);
		contentPane.add(lblNoteDeReference);
		
		
		JLabel lblilVousFaudra = new JLabel("<html>(il vous faudra recharger la fenetre de parametre de la molecule)</html>");
		lblilVousFaudra.setBounds(263, 91, 207, 56);
		contentPane.add(lblilVousFaudra);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, 0, 128, 1));
		spinner.setBounds(155, 65, 49, 22);
		spinner.setValue(mol.note());
		contentPane.add(spinner);
				
		JLabel lblEffet = new JLabel("Effet :");
		lblEffet.setBounds(12, 105, 56, 16);
		contentPane.add(lblEffet);
		
		comboBox_1 = new JComboBox(listeEffets.toArray());
		comboBox_1.setBounds(150, 102, 94, 22);
		comboBox_1.setSelectedItem(mol.getEffet().getClass().getName());
		contentPane.add(comboBox_1);
		
		JLabel lblVolume = new JLabel("Volume :");
		lblVolume.setBounds(12, 162, 56, 16);
		contentPane.add(lblVolume);
		
		sliderVol = new JSlider();
		sliderVol.setPaintLabels(true);
		sliderVol.setMajorTickSpacing(25);
		sliderVol.setSnapToTicks(true);
		sliderVol.setMinorTickSpacing(1);
		sliderVol.setMaximum(128);
		sliderVol.setBounds(140, 155, 207, 46);
		sliderVol.setValue(mol.getVolume());
		contentPane.add(sliderVol);
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mol.setNote((int) spinner.getValue());
				mol.setVolume(sliderVol.getValue());
				if (!(mol.getEffet().getClass().getName().equals(comboBox_1.getSelectedItem()))){
					switch((String) comboBox_1.getSelectedItem()){
					case "Tenu":
						mol.setEffet(new Tenu());
						break;
					case "Boucle":
						mol.setEffet(new Boucle());
						break;
					case "Glissando":
						mol.setEffet(new Glissando());
						break;
					case "Tremolo":
						mol.setEffet(new Tremolo());
						break;
					default:
						break;
					}
				}				
				dispose();

			}
		});
		btnValider.setBounds(194, 227, 87, 25);
		contentPane.add(btnValider);

		
	}
}
