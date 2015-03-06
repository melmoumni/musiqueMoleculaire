import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class InstrumentsBox extends JPanel implements ActionListener, ItemListener {

		Controleur controleur;
		private final JComboBox<String> typeInstruments = new JComboBox<String>(new String[]{"Instruments a vent","Instruments a corde", "Percussions" });
		private JComboBox<String> boxInstruments = new JComboBox<String>();

		private ComboBoxModel[] models = new ComboBoxModel[3];
		
		int indexInstrument;

	    public InstrumentsBox() {
	    	controleur = new Chercheur();
	    	
		try {
		    Parseur.lectureTimbre("./data/listeInstruments", controleur.tableauTimbre);
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
			Object[] instrumentsVent = listeTimbresVent.toArray();
			models[0]= new DefaultComboBoxModel<>(instrumentsVent);
			
			/* instruments à corde */
			ArrayList<String> listeTimbresCorde = new ArrayList<String>();		
			for (int i = 0 ; i < 8 ; i++){
				listeTimbresCorde.add(listeTimbres.get(i));
			}
			for (int i = 24 ; i < 63 ; i++){
				listeTimbresCorde.add(listeTimbres.get(i));
			}
			Object[] instrumentsCorde = listeTimbresCorde.toArray();
			
			/*percussions*/
			ArrayList<String> listeTimbresPercussions= new ArrayList<String>();
			for (int i = 8 ; i < 16 ; i++){
				listeTimbresPercussions.add(listeTimbres.get(i));
			}
			for (int i = 112 ; i < 120 ; i++){
				listeTimbresPercussions.add(listeTimbres.get(i));
			}
			Object[] percussions = listeTimbresPercussions.toArray();

			models[0]= new DefaultComboBoxModel<>(instrumentsVent);
			models[1]= new DefaultComboBoxModel<>(instrumentsCorde);
			models[2]= new DefaultComboBoxModel<>(percussions);
			
			boxInstruments.setModel(models[0]);
			boxInstruments.addItemListener(this);
	        this.add(boxInstruments);
	        this.add(typeInstruments);
	        boxInstruments.setVisible(false);
	        typeInstruments.addActionListener(this);
			
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	int i = typeInstruments.getSelectedIndex();
	        boxInstruments.setModel(models[i]);
	        boxInstruments.setVisible(true);
	    }
	    
	    public int getIndexInstrument(){
	    	String name = boxInstruments.getSelectedItem().toString();
	    	String nb = name.substring(0,3);
	    	nb = nb.trim();
	    	return Integer.parseInt(nb);
	    }

		@Override
		public void itemStateChanged(ItemEvent e) {
			indexInstrument = getIndexInstrument();
			System.out.println(indexInstrument);
		}
}
