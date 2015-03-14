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


public class FenetreParametreTremolo extends JFrame {

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
					FenetreParametreTremolo frame = new FenetreParametreTremolo(null);
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
	public FenetreParametreTremolo(final Molecule mol) {
		this.setTitle("Fenetre parametre effet TREMOLO");

		ArrayList<String> listeEffets = new ArrayList<String>();
		listeEffets.add("Tenu");
		listeEffets.add("Tremolo");
		listeEffets.add("Glissando");
		listeEffets.add("Boucle");
		listeEffets.add("Aleatoire");
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 541, 427);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblParametreDeLa = new JLabel("Parametre de la molecule :");
		lblParametreDeLa.setBounds(173, 13, 202, 30);
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
		
		JLabel lblAmplitudeDuTremolo = new JLabel("Amplitude du tremolo :");
		lblAmplitudeDuTremolo.setBounds(12, 214, 144, 22);
		contentPane.add(lblAmplitudeDuTremolo);
		
		JLabel lblFrequenceDuTremolo = new JLabel("Frequence du tremolo :");
		lblFrequenceDuTremolo.setBounds(12, 265, 144, 22);
		contentPane.add(lblFrequenceDuTremolo);
		
		JSlider slider = new JSlider();
		slider.setSnapToTicks(true);
		slider.setValue(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMinorTickSpacing(1);
		slider.setMajorTickSpacing(2);
		slider.setMinimum(1);
		slider.setMaximum(10);
		slider.setBounds(185, 210, 235, 52);
		contentPane.add(slider);
		
		JSlider slider_1 = new JSlider();
		slider_1.setPaintLabels(true);
		slider_1.setSnapToTicks(true);
		slider_1.setMinorTickSpacing(1);
		slider_1.setMinimum(1);
		slider_1.setMajorTickSpacing(49);
		slider_1.setBounds(185, 265, 235, 52);
		contentPane.add(slider_1);
		
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
					case "Aleatoire":
						mol.setEffet(new Aleatoire());
						break;
					case "Glissando":
						mol.setEffet(new Glissando());
						break;
					default:
						break;
					}
				}				
				dispose();

			}
		});
		btnValider.setBounds(227, 342, 87, 25);
		contentPane.add(btnValider);

		
	}
}
