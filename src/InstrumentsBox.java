import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class InstrumentsBox extends JPanel implements ActionListener, ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JComboBox<String> typeInstruments = new JComboBox<String>(new String[]{"Instruments a vent","Instruments a corde", "Percussions", "Sons de Synthese" });
	private JComboBox<String> boxInstruments = new JComboBox<String>();

	@SuppressWarnings("rawtypes")
	private ComboBoxModel[] models = new ComboBoxModel[4];

	int indexInstrument;

	@SuppressWarnings("unchecked")
	public InstrumentsBox() {
		try {
			Parseur.lectureTimbre("./data/listeInstruments.txt", Controleur.tableauTimbre);
		}
		catch (IOException e){}

		ArrayList<String> listeTimbres = new ArrayList<String>();		
		for (int i = 0 ; i < 128 ; i++){
			listeTimbres.add(Integer.toString(Controleur.tableauTimbre[i].timbreMIDI()) + " - " + Controleur.tableauTimbre[i].nom());
		}
		
		/* instruments à vent */
		ArrayList<String> listeTimbresVent = new ArrayList<String>();		
		for (int i = 57 ; i < 80 ; i++){
			listeTimbresVent.add(listeTimbres.get(i));
		}
		listeTimbresVent.add(listeTimbres.get(109));
		listeTimbresVent.add(listeTimbres.get(111));
		
		Object[] instrumentsVent = listeTimbresVent.toArray();

		/* instruments à corde */
		ArrayList<String> listeTimbresCorde = new ArrayList<String>();		
		for (int i = 0 ; i < 8 ; i++){
			listeTimbresCorde.add(listeTimbres.get(i));
		}
		for (int i = 24 ; i < 56 ; i++){
			listeTimbresCorde.add(listeTimbres.get(i));
		}
		for (int i = 104 ; i < 108 ; i++){
			listeTimbresCorde.add(listeTimbres.get(i));
		}
		listeTimbresCorde.add(listeTimbres.get(110));
		Object[] instrumentsCorde = listeTimbresCorde.toArray();

		/*percussions*/
		ArrayList<String> listeTimbresPercussions= new ArrayList<String>();
		for (int i = 8 ; i < 16 ; i++){
			listeTimbresPercussions.add(listeTimbres.get(i));
		}
		listeTimbresPercussions.add(listeTimbres.get(108));
		for (int i = 112 ; i < 120 ; i++){
			listeTimbresPercussions.add(listeTimbres.get(i));
		}
		Object[] percussions = listeTimbresPercussions.toArray();
		
		/*Sons de syntheses */
		ArrayList<String> listeTimbresSyntheses= new ArrayList<String>();
		for (int i = 80 ; i < 104 ; i++){
			listeTimbresSyntheses.add(listeTimbres.get(i));
		}
		for (int i = 120 ; i < 128 ; i++){
			listeTimbresSyntheses.add(listeTimbres.get(i));
		}
		Object[] syntheses = listeTimbresSyntheses.toArray();

		models[0]= new DefaultComboBoxModel<>(instrumentsVent);
		models[1]= new DefaultComboBoxModel<>(instrumentsCorde);
		models[2]= new DefaultComboBoxModel<>(percussions);
		models[3]= new DefaultComboBoxModel<>(syntheses);

		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		boxInstruments.setModel(models[0]);
		boxInstruments.addItemListener(this);
		this.add(typeInstruments);
		this.add(boxInstruments);	
		typeInstruments.addActionListener(this);
		
		indexInstrument = calculIndexInstrument();
		

	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		int i = typeInstruments.getSelectedIndex();
		boxInstruments.setModel(models[i]);
	}

	public int getIndexInstrument(){		
		return indexInstrument;
	}
	
	public int calculIndexInstrument(){
		String name = boxInstruments.getSelectedItem().toString();
		String nb[] = name.split("-");
		String instrument = nb[0].trim();
		return Integer.parseInt(instrument);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		indexInstrument = calculIndexInstrument();
		System.out.println(indexInstrument);
	}
	
}
