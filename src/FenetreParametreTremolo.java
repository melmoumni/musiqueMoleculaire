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

	private JSpinner spinnerNoteAbs;
	private JSpinner spinnerNoteOrd;
	private JComboBox<String> comboBox_1;
	private JSlider sliderVol; 
	private JSlider sliderFreq;
	private JSlider sliderAmpl; 
	private JComboBox<String> comboTimbreAbs;
	private JComboBox<String> comboTimbreOrd;

	
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
					FenetreParametreTremolo frame = new FenetreParametreTremolo(null, 1);
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
	public FenetreParametreTremolo(final Molecule mol, final int numero) {
		this.setTitle("Fenetre parametre effet TREMOLO");

		
		ArrayList<String> listeEffets = new ArrayList<String>();
		listeEffets.add("Tenu");
		listeEffets.add("Tremolo");
		listeEffets.add("Glissando");
		listeEffets.add("Boucle");
		listeEffets.add("Aleatoire");
		
		ArrayList<String> listeTimbres = new ArrayList<String>();		
		for (int i = 0 ; i < 128 ; i++){
			listeTimbres.add(Integer.toString(Controleur.tableauTimbre[i].timbreMIDI()) + " - " + Controleur.tableauTimbre[i].nom());
		}
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 482);
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

		
		JLabel lblNoteDeReference = new JLabel("Note :");
		lblNoteDeReference.setBounds(12, 61, 49, 30);
		contentPane.add(lblNoteDeReference);
		
		if (numero ==1){
			spinnerNoteAbs = new JSpinner();
			spinnerNoteAbs.setModel(new SpinnerNumberModel(0, 0, 128, 1));
			spinnerNoteAbs.setBounds(90, 65, 49, 22);
			spinnerNoteAbs.setValue(mol.noteAbs());
			contentPane.add(spinnerNoteAbs);
		}else{
			spinnerNoteOrd = new JSpinner();
			spinnerNoteOrd.setModel(new SpinnerNumberModel(0, 0, 128, 1));
			spinnerNoteOrd.setBounds(90, 64, 49, 22);
			spinnerNoteOrd.setValue(mol.noteOrd());
			contentPane.add(spinnerNoteOrd);
		}		
		
		JLabel lblilVousFaudra = new JLabel("<html>(il vous faudra recharger la fenetre de parametre de la molecule)</html>");
		lblilVousFaudra.setBounds(230, 91, 207, 56);
		contentPane.add(lblilVousFaudra);
				
		JLabel lblEffet = new JLabel("Effet :");
		lblEffet.setBounds(12, 110, 56, 16);
		contentPane.add(lblEffet);
		
		comboBox_1 = new JComboBox(listeEffets.toArray());
		comboBox_1.setBounds(90, 110, 94, 22);
		comboBox_1.setSelectedItem(mol.getEffetAbs().getClass().getName());
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
		sliderVol.setBounds(90, 155, 207, 46);
		sliderVol.setValue(mol.getVolume());
		contentPane.add(sliderVol);
		
		
		JLabel lblTimbre = new JLabel("Timbre :");
		lblTimbre.setBounds(12, 237, 56, 16);
		contentPane.add(lblTimbre);

		
		if (numero == 1){
			comboTimbreAbs = new JComboBox(listeTimbres.toArray());
			comboTimbreAbs.setBounds(90, 234, 158, 22);
			comboTimbreAbs.setSelectedIndex(mol.getTimbreAbs().timbreMIDI() - 1);
			contentPane.add(comboTimbreAbs);
		}
		else{
			comboTimbreOrd = new JComboBox(listeTimbres.toArray());
			comboTimbreOrd.setSelectedIndex(mol.getTimbreOrd().timbreMIDI() - 1);
			comboTimbreOrd.setBounds(90, 234, 158, 22);
			contentPane.add(comboTimbreOrd);
		}
		
		JLabel lblAmplitudeDuTremolo = new JLabel("Amplitude du tremolo :");
		lblAmplitudeDuTremolo.setBounds(12, 290, 144, 22);
		contentPane.add(lblAmplitudeDuTremolo);
		
		JLabel lblFrequenceDuTremolo = new JLabel("Frequence du tremolo :");
		lblFrequenceDuTremolo.setBounds(12, 348, 144, 22);
		contentPane.add(lblFrequenceDuTremolo);
		
		sliderAmpl = new JSlider();
		sliderAmpl.setSnapToTicks(true);
		sliderAmpl.setValue(1);
		sliderAmpl.setPaintTicks(true);
		sliderAmpl.setPaintLabels(true);
		sliderAmpl.setMinorTickSpacing(1);
		sliderAmpl.setMajorTickSpacing(2);
		sliderAmpl.setMinimum(1);
		sliderAmpl.setMaximum(10);
		sliderAmpl.setBounds(185, 277, 235, 52);
		sliderAmpl.setValue(((Tremolo) mol.getEffetAbs()).variationNote());
		contentPane.add(sliderAmpl);
		
		sliderFreq = new JSlider();
		sliderFreq.setPaintLabels(true);
		sliderFreq.setSnapToTicks(true);
		sliderFreq.setMinorTickSpacing(1);
		sliderFreq.setMinimum(1);
		sliderFreq.setMajorTickSpacing(49);
		sliderFreq.setBounds(185, 342, 235, 52);
		sliderFreq.setValue(((Tremolo) mol.getEffetAbs()).nombrePas());
		contentPane.add(sliderFreq);
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mol.setVolume(sliderVol.getValue());
				if (numero == 1){
					mol.setNoteAbs((int) spinnerNoteAbs.getValue());
					mol.setTimbreAbs(Controleur.tableauTimbre[comboTimbreAbs.getSelectedIndex()]);
				}else{
					mol.setNoteOrd((int) spinnerNoteOrd.getValue());
					mol.setTimbreOrd(Controleur.tableauTimbre[comboTimbreOrd.getSelectedIndex()]);					
				}
				((Tremolo) mol.getEffetAbs()).setNombrePas(sliderFreq.getValue());
				((Tremolo) mol.getEffetAbs()).setVariationNote(sliderAmpl.getValue());
				if (!(mol.getEffetAbs().getClass().getName().equals(comboBox_1.getSelectedItem()))){
					switch((String) comboBox_1.getSelectedItem()){
					case "Tenu":
						mol.setEffetAbs(new Tenu());
						break;
					case "Boucle":
						mol.setEffetAbs(new Boucle());
						break;
					case "Aleatoire":
						mol.setEffetAbs(new Aleatoire());
						break;
					case "Glissando":
						mol.setEffetAbs(new Glissando());
						break;
					default:
						break;
					}
				}				
				dispose();

			}
		});
		btnValider.setBounds(227, 397, 87, 25);
		contentPane.add(btnValider);

		
	}
}
