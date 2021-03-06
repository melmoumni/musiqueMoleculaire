import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;


public class FenetreParametreBoucle extends JFrame {

	private JSpinner spinnerNoteAbs;
	private JSpinner spinnerNoteOrd;
	private JComboBox<String> comboBox_1;
	private JSlider sliderVol; 
	private JSlider sliderInt;
	private JSlider sliderAmp;
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
					FenetreParametreBoucle frame = new FenetreParametreBoucle(null, 1);
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
	public FenetreParametreBoucle(final Molecule mol, final int numero) {
		this.setTitle("Fenetre parametre effet BOUCLE");

		
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
		setBounds(100, 100, 520, 499);
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
		
		JLabel lblIntervalleEntre = new JLabel("<html>Intervalle entre 2 notes :<br> (1 => 1 noire)</html>");
		lblIntervalleEntre.setBounds(12, 269, 107, 66);
		contentPane.add(lblIntervalleEntre);

		class JFloatSlider extends JSlider
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private final int SCALE = 12;
			public JFloatSlider(int position,float min, float max, float init, float tick)
			{
				this.setPaintLabels(true);
				setSnapToTicks(true);
				//this.setPaintTicks(true);

				setMinimum((int)(min*SCALE));
				setMaximum((int)(max*SCALE));
				this.setValue((int)(init*SCALE));
				this.setOrientation(position);
				Hashtable ht = new Hashtable();
				for (float i = min; i <= max; i+=tick)
				{
					if (i != (float) 3 && i != (float) 0){
						JLabel l = new JLabel(""+(int) i);
						ht.put(new Integer((int)Math.rint(i*SCALE)), l);
					}
				}
				this.setLabelTable(ht);
				this.setMinorTickSpacing((int)(tick * SCALE / 2));
				this.setMajorTickSpacing((int) (tick * SCALE / 2));
				this.setPaintTicks(true);
			}
			public float getFloatValue() { return (float)getValue()/(float)SCALE; }
		}

		
		sliderInt = new JFloatSlider(0,(float) 0, (float) 4, (float) ((Boucle) mol.getEffetAbs()).interNote()/Controleur.dureeNoire, (float) 1);
		sliderInt.setBounds(151, 273, 200, 50);
		contentPane.add(sliderInt);
		
		JLabel lblAmplitudeDeLa = new JLabel("Amplitude de la boucle :");
		lblAmplitudeDeLa.setBounds(12, 361, 146, 30);
		contentPane.add(lblAmplitudeDeLa);
		
		sliderAmp = new JSlider();
		sliderAmp.setValue(3);
		sliderAmp.setPaintTicks(true);
		sliderAmp.setPaintLabels(true);
		sliderAmp.setMinorTickSpacing(1);
		sliderAmp.setMinimum(2);
		sliderAmp.setMaximum(9);
		sliderAmp.setMajorTickSpacing(1);
		sliderAmp.setBounds(176, 349, 229, 52);
		sliderAmp.setValue(((Boucle) mol.getEffetAbs()).nbNotes());
		System.out.println("nbNotes value : " + sliderAmp.getValue());
		contentPane.add(sliderAmp);
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mol.setVolume(sliderVol.getValue());
				if (numero==1){
					mol.setNoteAbs((int) spinnerNoteAbs.getValue());
					mol.setTimbreAbs(Controleur.tableauTimbre[comboTimbreAbs.getSelectedIndex()]);
				}
				else{
					mol.setNoteOrd((int) spinnerNoteOrd.getValue());
					mol.setTimbreOrd(Controleur.tableauTimbre[comboTimbreOrd.getSelectedIndex()]);					
				}
				((Boucle) mol.getEffetAbs()).setNbNotes(sliderAmp.getValue());
				if ((int) (((JFloatSlider) sliderInt).getFloatValue()) != 0){
					((Boucle) mol.getEffetAbs()).setInterNote((int) (((JFloatSlider) sliderInt).getFloatValue()*Controleur.dureeNoire));
				}
				if (!(mol.getEffetAbs().getClass().getName().equals(comboBox_1.getSelectedItem()))){
					switch((String) comboBox_1.getSelectedItem()){
					case "Tenu":
						mol.setEffetAbs(new Tenu());
						break;
					case "Glissando":
						mol.setEffetAbs(new Glissando());
						break;
					case "Aleatoire":
						mol.setEffetAbs(new Aleatoire());
						break;
					case "Tremolo":
						mol.setEffetAbs(new Tremolo());
						break;
					default:
						break;
					}
				}				
				dispose();

			}
		});
		btnValider.setBounds(200, 414, 87, 25);
		contentPane.add(btnValider);

		
	}
}
