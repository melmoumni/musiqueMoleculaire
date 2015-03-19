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

	private JSpinner spinnerNoteAbs;
	private JComboBox<String> comboBox_1;
	private JSlider sliderVol; 
	private JSlider sliderMol; 
	private JComboBox<String> comboTimbreAbs;
	private JComboBox<String> comboTimbreOrd;

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JSpinner spinnerNoteOrd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreParametreGlissando frame = new FenetreParametreGlissando(null,1);
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
	public FenetreParametreGlissando(final Molecule mol, final int numero) {
		this.setTitle("Fenetre parametre effet GLISSANDO");

		
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
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mol.setVolume(sliderVol.getValue());
				if (numero==1){
					mol.setNoteAbs((int) spinnerNoteAbs.getValue());
					mol.setTimbreAbs(Controleur.tableauTimbre[comboTimbreAbs.getSelectedIndex()]);
				}else{
					mol.setNoteOrd((int) spinnerNoteOrd.getValue());
					mol.setTimbreOrd(Controleur.tableauTimbre[comboTimbreOrd.getSelectedIndex()]);					
				}
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
