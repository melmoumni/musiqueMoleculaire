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


public class FenetreParametreGlissando extends JFrame {

	private JSpinner spinner;
	private JComboBox<String> comboBox_1;
	private JSlider sliderVol; 
	private JSlider sliderMol; 
	private JComboBox<String> comboTimbre;

	
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
					FenetreParametreGlissando frame = new FenetreParametreGlissando(null);
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
	public FenetreParametreGlissando(final Molecule mol) {
		this.setTitle("Fenetre parametre effet GLISSANDO");

		ArrayList<String> listeEffets = new ArrayList<String>();
		listeEffets.add("Tenu");
		listeEffets.add("Tremolo");
		listeEffets.add("Glissando");
		listeEffets.add("Boucle");
		listeEffets.add("Aleatoire");
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 503, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblParametreDeLa = new JLabel("Parametre de la molecule :");
		lblParametreDeLa.setBounds(12, 13, 202, 30);
		contentPane.add(lblParametreDeLa);
		
		JLabel lblapparitionTi = new JLabel("(Apparition : ti=" + mol.instantInitial() + " et tf="+ mol.instantFinal() + ")");
		lblapparitionTi.setBounds(263, 5, 177, 46);
		contentPane.add(lblapparitionTi);
	
		JLabel lblNoteDeReference = new JLabel("Note de reference :");
		lblNoteDeReference.setBounds(12, 61, 126, 30);
		contentPane.add(lblNoteDeReference);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, 0, 128, 1));
		spinner.setBounds(155, 65, 49, 22);
		spinner.setValue(mol.noteAbs());
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
		
		sliderMol = new JSlider();
		sliderMol.setPaintLabels(true);
		sliderMol.setPaintTicks(true);
		sliderMol.setSnapToTicks(true);
		sliderMol.setMajorTickSpacing(10);
		sliderMol.setMaximum(64);
		sliderMol.setBounds(150, 285, 212, 56);
		sliderMol.setValue(((Glissando) mol.getEffet()).molette());
		contentPane.add(sliderMol);
		
		JLabel lblMolette = new JLabel("Molette : ");
		lblMolette.setBounds(12, 297, 56, 16);
		contentPane.add(lblMolette);
		
		JLabel lblilVousFaudra = new JLabel("<html>(il vous faudra recharger la fenetre de parametre de la molecule)</html>");
		lblilVousFaudra.setBounds(263, 91, 207, 56);
		contentPane.add(lblilVousFaudra);
		
		JLabel lblTimbre = new JLabel("Timbre :");
		lblTimbre.setBounds(12, 237, 56, 16);
		contentPane.add(lblTimbre);
		
		ArrayList<String> listeTimbres = new ArrayList<String>();		
		for (int i = 0 ; i < 128 ; i++){
			listeTimbres.add(Integer.toString(Controleur.tableauTimbre[i].timbreMIDI()) + " - " + Controleur.tableauTimbre[i].nom());
		}

		
		comboTimbre = new JComboBox(listeTimbres.toArray());
		comboTimbre.setBounds(151, 234, 158, 22);
		comboTimbre.setSelectedIndex(mol.getTimbreAbs().timbreMIDI() - 1);
		contentPane.add(comboTimbre);
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mol.setNoteAbs((int) spinner.getValue());
				mol.setVolume(sliderVol.getValue());
				mol.setTimbreAbs(Controleur.tableauTimbre[comboTimbre.getSelectedIndex()]);
				((Glissando) mol.getEffet()).setMolette(sliderMol.getValue());
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
		btnValider.setBounds(189, 367, 87, 25);
		contentPane.add(btnValider);

		
	}
}
