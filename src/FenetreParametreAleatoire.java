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



public class FenetreParametreAleatoire extends JFrame {

	private JSpinner spinnerNoteAbs;
	private JSpinner spinnerNoteOrd;
	private JComboBox<String> comboBox_1;
	private JSlider sliderVol; 
	private JSlider sliderInt;
	private JComboBox<String> comboTimbreAbs;


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
		
		ArrayList<String> listeTimbres = new ArrayList<String>();		
		for (int i = 0 ; i < 128 ; i++){
			listeTimbres.add(Integer.toString(Controleur.tableauTimbre[i].timbreMIDI()) + " - " + Controleur.tableauTimbre[i].nom());
		}
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 498, 422);
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
		
		spinnerNoteAbs = new JSpinner();
		spinnerNoteAbs.setModel(new SpinnerNumberModel(0, 0, 128, 1));
		spinnerNoteAbs.setBounds(90, 65, 49, 22);
		spinnerNoteAbs.setValue(mol.noteAbs());
		contentPane.add(spinnerNoteAbs);
		
		if (!Controleur.isChercheur){
			spinnerNoteOrd = new JSpinner();
			spinnerNoteOrd.setModel(new SpinnerNumberModel(0, 0, 128, 1));
			spinnerNoteOrd.setBounds(222, 64, 49, 22);
			spinnerNoteOrd.setValue(mol.noteOrd());
			contentPane.add(spinnerNoteOrd);

			JLabel lblabs = new JLabel("(Abscisse)");
			lblabs.setBounds(145, 68, 56, 16);
			contentPane.add(lblabs);

			JLabel labelOrd = new JLabel("(Ordonnee)");
			labelOrd.setBounds(277, 68, 56, 16);
			contentPane.add(labelOrd);

			JComboBox comboTimbreOrd = new JComboBox(listeTimbres.toArray());
			comboTimbreOrd.setSelectedIndex(mol.getTimbreOrd().timbreMIDI() - 1);
			comboTimbreOrd.setBounds(282, 234, 158, 22);
			contentPane.add(comboTimbreOrd);
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

		
		comboTimbreAbs = new JComboBox(listeTimbres.toArray());
		comboTimbreAbs.setBounds(90, 234, 158, 22);
		comboTimbreAbs.setSelectedIndex(mol.getTimbreAbs().timbreMIDI() - 1);
		contentPane.add(comboTimbreAbs);

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

		sliderInt = new JFloatSlider(0,(float) 0, (float) 4, (float) ((Aleatoire) mol.getEffet()).interNote()/Controleur.dureeNoire, (float) 1);
		sliderInt.setBounds(151, 273, 200, 50);
		contentPane.add(sliderInt);

		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(((JFloatSlider) sliderInt).getFloatValue());
				mol.setNoteAbs((int) spinnerNoteAbs.getValue());
				mol.setVolume(sliderVol.getValue());
				mol.setTimbreAbs(Controleur.tableauTimbre[comboTimbreAbs.getSelectedIndex()]);
				((Aleatoire) mol.getEffet()).setInterNote((int) (((JFloatSlider) sliderInt).getFloatValue()*Controleur.dureeNoire));
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
		btnValider.setBounds(191, 337, 87, 25);
		contentPane.add(btnValider);


	}
}
