import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.Border;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


public class TableauCompositeurVue extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btnStart;
	TableauCompositeur TabC;
	JSplitPane splitPane00, splitPane01, splitPane02,splitPane10 ,splitPane11, splitPane12, splitPane20, splitPane21, splitPane22;
	
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		Controleur controleur = new Compositeur(1);
		Parseur p = new Parseur();
		try {
			p.lireFichierAnalyse("./data/fichiersTests/analyseTest.txt");
		}
		catch (IOException e) {
		}
		try {	
			System.out.println("Lecture du 2e fichier");
			p.lireFichierTrajectoire("./data/fichiersTests/trajectoiresTest.trc");
		}
		catch (IOException e) {
		}
		try {	
			System.out.println("Lecture du fichier de timbres");
			p.lectureTimbre("./data/listeInstruments.txt");
		}
		catch (IOException e) {
		}
		controleur.initMolecules(p);
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableauCompositeurVue frame = new TableauCompositeurVue(true, null);
					
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
	public TableauCompositeurVue(boolean init, TableauCompositeur tab) {
		this.setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	    this.getContentPane().setLayout(null);

	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, (int) dim.getWidth(), (int) dim.getHeight());
	    setLocationRelativeTo(null);
	    
	    JPanel panel = new JPanel();
	    panel.setBounds(0, 0,(int) (4*dim.getWidth()/5), (int) dim.getHeight());
	    this.getContentPane().add(panel);
	 
	    
	    JPanel panel_1 = new JPanel();
	    panel_1.setBounds((int) (4*dim.getWidth()/5), 0, (int) dim.getWidth()/5, (int) dim.getWidth());
	    this.getContentPane().add(panel_1);
	    
	    
	    if (init){
	    	TabC = new TableauCompositeur();
	    }
	    else {
	    	TabC = tab;
		}
		
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
		
		/* instruments à corde */
		ArrayList<String> listeTimbresCorde = new ArrayList<String>();		
		for (int i = 0 ; i < 8 ; i++){
			listeTimbresCorde.add(listeTimbres.get(i));
		}
		for (int i = 24 ; i < 63 ; i++){
			listeTimbresCorde.add(listeTimbres.get(i));
		}
		Object[] instrumentsCorde = listeTimbresCorde.toArray();
		
		
		for (Float f : TabC.abscisses){
			System.out.printf("%f ", f);
		}
		System.out.println();
		for (Float f : TabC.ordonnees){
			System.out.printf("%f ", f);
		}
		System.out.println();
		
	    panel_1.setLayout(new FormLayout(new ColumnSpec[] {
	    		FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
	    		ColumnSpec.decode("161px"),},
	    	new RowSpec[] {
	    		FormFactory.LINE_GAP_ROWSPEC,
	    		RowSpec.decode("25px"),
	    		FormFactory.RELATED_GAP_ROWSPEC,
	    		FormFactory.DEFAULT_ROWSPEC,
	    		FormFactory.RELATED_GAP_ROWSPEC,
	    		FormFactory.DEFAULT_ROWSPEC,
	    		FormFactory.RELATED_GAP_ROWSPEC,
	    		FormFactory.DEFAULT_ROWSPEC,
	    		FormFactory.RELATED_GAP_ROWSPEC,
	    		FormFactory.DEFAULT_ROWSPEC,
	    		FormFactory.RELATED_GAP_ROWSPEC,
	    		FormFactory.DEFAULT_ROWSPEC,
	    		FormFactory.RELATED_GAP_ROWSPEC,
	    		FormFactory.DEFAULT_ROWSPEC,
	    		FormFactory.RELATED_GAP_ROWSPEC,
	    		FormFactory.DEFAULT_ROWSPEC,
	    		FormFactory.RELATED_GAP_ROWSPEC,
	    		FormFactory.DEFAULT_ROWSPEC,
	    		FormFactory.RELATED_GAP_ROWSPEC,
	    		FormFactory.DEFAULT_ROWSPEC,
	    		FormFactory.RELATED_GAP_ROWSPEC,
	    		FormFactory.DEFAULT_ROWSPEC,
	    		FormFactory.RELATED_GAP_ROWSPEC,
	    		FormFactory.DEFAULT_ROWSPEC,
	    		FormFactory.RELATED_GAP_ROWSPEC,
	    		FormFactory.DEFAULT_ROWSPEC,}));
	    
	    
	    btnStart = new JButton("Valider le tableau");
	    btnStart.setName("btnStart");
	    btnStart.addActionListener(this);
	    panel_1.add(btnStart, "2, 26");
		
		
		
		/* Programme permettant la détection de changement de timbre */
		ActionListener actionListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				JComboBox<String> comboBox = (JComboBox) e.getSource();
				switch (comboBox.getName()){
				case "comboVent1":
					TabC.timbreOrd[0] = Controleur.tableauTimbre[(int) comboBox.getSelectedItem()];
					break;
				case "comboVent2":
					TabC.timbreOrd[1] = Controleur.tableauTimbre[(int) comboBox.getSelectedItem()];
					break;
				case "comboVent3":
					TabC.timbreOrd[2] = Controleur.tableauTimbre[(int) comboBox.getSelectedItem()];
					break;
				case "comboCorde1":
					TabC.timbreAbs[0] = Controleur.tableauTimbre[(int) comboBox.getSelectedItem()];
					break;
				case "comboCorde2":
					TabC.timbreAbs[1] = Controleur.tableauTimbre[(int) comboBox.getSelectedItem()];
					break;
				case "comboCorde3":
					TabC.timbreAbs[2] = Controleur.tableauTimbre[(int) comboBox.getSelectedItem()];
					break;
				default:
					break;
				}
				
			}

		};

		/* Programme permettant la détection de changement du divider de chaque splitPane */
		PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
		      public void propertyChange(PropertyChangeEvent changeEvent) {
		        JSplitPane sourceSplitPane = (JSplitPane) changeEvent.getSource();
		        String propertyName = changeEvent.getPropertyName();
		        if (propertyName.equals(JSplitPane.LAST_DIVIDER_LOCATION_PROPERTY)) {
//		        	System.out.println(changeEvent);
		        	if ((sourceSplitPane.getOrientation() == JSplitPane.VERTICAL_SPLIT)  && ((int) changeEvent.getNewValue() != -1)){
		        		changeDividerVert(sourceSplitPane);
		        	}
		        	else if ((sourceSplitPane.getOrientation() == JSplitPane.HORIZONTAL_SPLIT)  && ((int) changeEvent.getNewValue() != -1)){
		        		changeDividerHor(sourceSplitPane);
		        	}
		        }
		      }

			private void changeDividerHor(JSplitPane sourceSplitPane) {
				int current = sourceSplitPane.getDividerLocation();
				if (sourceSplitPane.getName().equals("splitPane00")){
					TabC.abscisses[1] = current + TabC.abscisses[0];
				}else if (sourceSplitPane.getName().equals("splitPane01")){
					TabC.abscisses[2] = current + TabC.abscisses[1];
				}
			}

			private void changeDividerVert(JSplitPane sourceSplitPane) {
				int current = sourceSplitPane.getDividerLocation();
				switch (sourceSplitPane.getName()){
				case "splitPane10":
					if(splitPane10.isEnabled()){ // on verfie que le composant est activé
						TabC.ordonnees[1] = current + TabC.ordonnees[0];
						splitPane11.setEnabled(false); // on desactive les autres divideurs pour eviter les deplacements en boucle
						splitPane12.setEnabled(false);
						splitPane11.setDividerLocation(current); // deplacement des deux autres divideurs en meme temps
						splitPane12.setDividerLocation(current);
						splitPane11.setEnabled(true); // on reactive les deux autres divideurs pour pouvoir les selectionner et les deplacer
						splitPane12.setEnabled(true);
					}
					break;
				case "splitPane11":
					if(splitPane11.isEnabled()){
						TabC.ordonnees[1] = current + TabC.ordonnees[0];
						splitPane10.setEnabled(false);
						splitPane12.setEnabled(false);
						splitPane10.setDividerLocation(current);
						splitPane12.setDividerLocation(current);
						splitPane10.setEnabled(true);
						splitPane12.setEnabled(true);
					}
					break;
				case "splitPane12":
					if(splitPane12.isEnabled()){
						splitPane10.setEnabled(false);
						splitPane11.setEnabled(false);
						TabC.ordonnees[1] = current + TabC.ordonnees[0];
						splitPane10.setDividerLocation(current);
						splitPane11.setDividerLocation(current);
						splitPane10.setEnabled(true);
						splitPane11.setEnabled(true);
					}
					break;
				case "splitPane20":
					TabC.ordonnees[2] = current + TabC.ordonnees[1];
					if(splitPane20.isEnabled()){
						splitPane21.setEnabled(false);
						splitPane22.setEnabled(false);
						splitPane21.setDividerLocation(current);
						splitPane22.setDividerLocation(current);
						splitPane21.setEnabled(true);
						splitPane22.setEnabled(true);
					}
					break;
				case "splitPane21":
					if(splitPane21.isEnabled()){
						TabC.ordonnees[2] = current + TabC.ordonnees[1];
						splitPane20.setEnabled(false);
						splitPane22.setEnabled(false);
						splitPane20.setDividerLocation(current);
						splitPane22.setDividerLocation(current);
						splitPane20.setEnabled(true);
						splitPane22.setEnabled(true);
					}
					break;
				case "splitPane22":
					if(splitPane22.isEnabled()){
						TabC.ordonnees[2] = current + TabC.ordonnees[1];
						splitPane20.setEnabled(false);
						splitPane21.setEnabled(false);
						splitPane20.setDividerLocation(current);
						splitPane21.setDividerLocation(current);
						splitPane20.setEnabled(true);
						splitPane21.setEnabled(true);
					}
					break;
				default:
					break;
				}
			
			}
		};
		panel.setLayout(null);
		
		
		splitPane00 = new JSplitPane();
		splitPane00.setName("splitPane00");
		splitPane00.setBounds((int) TabC.abscisses[0], (int) TabC.ordonnees[0],(int) (TabC.abscisses[3] - TabC.abscisses[0]), (int) (TabC.ordonnees[3] - TabC.ordonnees[0]));
		splitPane00.setDividerLocation((int) (TabC.abscisses[1] - TabC.abscisses[0]));
		panel.add(splitPane00);
		splitPane00.setDividerSize(2);
		splitPane00.setBorder(BorderFactory.createEmptyBorder());		
		splitPane00.addPropertyChangeListener(propertyChangeListener);
		
		
		splitPane01 = new JSplitPane();
		splitPane01.setName("splitPane01");
		splitPane01.setBounds((int) TabC.abscisses[1], (int) TabC.ordonnees[0],(int) (TabC.abscisses[3] - TabC.abscisses[1]), (int) (TabC.ordonnees[3] - TabC.ordonnees[0]));
		splitPane01.setDividerLocation((int) (TabC.abscisses[2] - TabC.abscisses[1]));
		splitPane00.setRightComponent(splitPane01);
		splitPane01.setDividerSize(2);
		splitPane01.setBorder(BorderFactory.createEmptyBorder());		
		splitPane01.addPropertyChangeListener(propertyChangeListener);
		
	    		
		splitPane10 = new JSplitPane();
		splitPane10.setName("splitPane10");
		splitPane10.setBounds((int) TabC.abscisses[0], (int) TabC.ordonnees[0],(int) (TabC.abscisses[1] - TabC.abscisses[0]), (int) (TabC.ordonnees[3] - TabC.ordonnees[0]));
		splitPane10.setDividerLocation((int) (TabC.ordonnees[1] - TabC.ordonnees[0]));
		splitPane10.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane00.setLeftComponent(splitPane10);
		splitPane10.setDividerSize(2);
		splitPane10.setBorder(BorderFactory.createEmptyBorder());		
		splitPane10.addPropertyChangeListener(propertyChangeListener);
		
		splitPane11 = new JSplitPane();
		splitPane11.setName("splitPane11");
		splitPane11.setBounds((int) TabC.abscisses[1], (int) TabC.ordonnees[0],(int) (TabC.abscisses[2] - TabC.abscisses[1]), (int) (TabC.ordonnees[3] - TabC.ordonnees[0]));
		splitPane11.setDividerLocation((int) (TabC.ordonnees[1] - TabC.ordonnees[0]));
		splitPane11.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane01.setRightComponent(splitPane11);
		splitPane11.setDividerSize(2);
		splitPane11.setBorder(BorderFactory.createEmptyBorder());		
		splitPane11.addPropertyChangeListener(propertyChangeListener);
		
		splitPane12 = new JSplitPane();
		splitPane12.setName("splitPane12");
		splitPane12.setBounds((int) TabC.abscisses[2], (int) TabC.ordonnees[0],(int) (TabC.abscisses[3] - TabC.abscisses[2]), (int) (TabC.ordonnees[3] - TabC.ordonnees[0]));
		splitPane12.setDividerLocation((int) (TabC.ordonnees[1] - TabC.ordonnees[0]));
		splitPane12.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane01.setLeftComponent(splitPane12);
		splitPane12.setDividerSize(2);
		splitPane12.setBorder(BorderFactory.createEmptyBorder());		
		splitPane12.addPropertyChangeListener(propertyChangeListener);
		
		splitPane20 = new JSplitPane();
		splitPane20.setName("splitPane20");
		splitPane20.setBounds((int) TabC.abscisses[0], (int) TabC.ordonnees[1],(int) (TabC.abscisses[1] - TabC.abscisses[0]), (int) (TabC.ordonnees[3] - TabC.ordonnees[1]));
		splitPane20.setDividerLocation((int) (TabC.ordonnees[2] - TabC.ordonnees[1]));
		splitPane20.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane10.setRightComponent(splitPane20);
		splitPane20.setDividerSize(2);
		splitPane20.setBorder(BorderFactory.createEmptyBorder());		
		splitPane20.addPropertyChangeListener(propertyChangeListener);		
		
		splitPane21 = new JSplitPane();
		splitPane21.setName("splitPane21");
		splitPane21.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane21.setBounds((int) TabC.abscisses[1], (int) TabC.ordonnees[1],(int) (TabC.abscisses[2] - TabC.abscisses[1]), (int) (TabC.ordonnees[3] - TabC.ordonnees[1]));
		splitPane21.setDividerLocation((int) (TabC.ordonnees[2] - TabC.ordonnees[1]));
		splitPane11.setRightComponent(splitPane21);
		splitPane21.setDividerSize(2);
		splitPane21.setBorder(BorderFactory.createEmptyBorder());		
		splitPane21.addPropertyChangeListener(propertyChangeListener);

		splitPane22 = new JSplitPane();
		splitPane22.setName("splitPane22");
		splitPane22.setBounds((int) TabC.abscisses[2], (int) TabC.ordonnees[1],(int) (TabC.abscisses[2] - TabC.abscisses[1]), (int) (TabC.ordonnees[3] - TabC.ordonnees[1]));
		splitPane22.setDividerLocation((int) (TabC.ordonnees[2] - TabC.ordonnees[1]));
		splitPane22.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane12.setRightComponent(splitPane22);
		splitPane22.setDividerSize(2);
		splitPane22.setBorder(BorderFactory.createEmptyBorder());
	    splitPane22.addPropertyChangeListener(propertyChangeListener);

		
		JPanel HautGauche = new JPanel();
		HautGauche.setBackground(Color.pink);
		splitPane10.setLeftComponent(HautGauche);
		
		JPanel HautDroit = new JPanel();
		HautDroit.setBackground(Color.pink);
		splitPane11.setLeftComponent(HautDroit);
		
		JPanel HautCentre = new JPanel();
		HautCentre.setBackground(Color.pink);
		splitPane12.setLeftComponent(HautCentre);
		
		JPanel CentreGauche = new JPanel();
		CentreGauche.setBackground(new Color(178,102,255));
		splitPane20.setLeftComponent(CentreGauche);
				
		JPanel CentreDroit = new JPanel();
		CentreDroit.setBackground(Color.pink);
		splitPane21.setLeftComponent(CentreDroit);
		
		JPanel CentreCentre = new JPanel();
		CentreCentre.setBackground(new Color(178,102,255));
		splitPane22.setLeftComponent(CentreCentre);
		
		JPanel BasGauche = new JPanel();
		BasGauche.setBackground(new Color(102,178,255));
		splitPane20.setRightComponent(BasGauche);
		
		JPanel BasDroit = new JPanel();
		BasDroit.setBackground(Color.pink);
		splitPane21.setRightComponent(BasDroit);
		
		JPanel BasCentre = new JPanel();
		splitPane22.setRightComponent(BasCentre);
		BasCentre.setBackground(new Color(178,102,255));
		

		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox<String> comboVent3 = new JComboBox(instrumentsVent);
		comboVent3.setName("comboVent3");
		comboVent3.setBounds(0, 0, TableauCompositeur.MAX_WIDTH/3  - 50, 25);
		comboVent3.addActionListener(actionListener);
		panel.add(comboVent3);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })

		JComboBox<String> comboVent2 = new JComboBox(instrumentsVent);
		comboVent2.setName("comboVent2");
		comboVent2.setBounds(0, (int) TabC.ordonnees[1], TableauCompositeur.MAX_WIDTH/3 - 50, 25);
		comboVent2.addActionListener(actionListener);
		panel.add(comboVent2);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox<String> comboVent1 = new JComboBox(instrumentsVent);
		comboVent1.setName("comboVent1");
		comboVent1.setBounds(0, (int) TabC.ordonnees[2], TableauCompositeur.MAX_WIDTH/3 - 50, 25);
		comboVent1.addActionListener(actionListener);
		panel.add(comboVent1);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox<String> comboCorde1 = new JComboBox(instrumentsCorde);
		comboCorde1.setName("comboCorde1");
		comboCorde1.setBounds((int) TabC.abscisses[0], (int) TabC.ordonnees[3] + 20, TableauCompositeur.MAX_WIDTH/3 - 50, 25);
		comboCorde1.addActionListener(actionListener);
		panel.add(comboCorde1);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox<String> comboCorde2 = new JComboBox(instrumentsCorde);
		comboCorde2.setName("comboCorde2");
		comboCorde2.setBounds((int) TabC.abscisses[1], (int) TabC.ordonnees[3] + 20, TableauCompositeur.MAX_WIDTH/3 - 50, 25);
		comboCorde2.addActionListener(actionListener);
		panel.add(comboCorde2);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox<String> comboCorde3 = new JComboBox(instrumentsCorde);
		comboCorde3.setName("comboCorde3");
		comboCorde3.setBounds((int) TabC.abscisses[2], (int) TabC.ordonnees[3] + 20, TableauCompositeur.MAX_WIDTH/3 - 50 , 25);
		comboCorde3.addActionListener(actionListener);
		panel.add(comboCorde3);
		
		
		

		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if(source == btnStart){
			System.out.println("Ca commence");
		}
	}
}