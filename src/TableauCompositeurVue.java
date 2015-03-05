import java.awt.BorderLayout;
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
		
		ArrayList<String> listeTimbresCordes = new ArrayList<String>();		
		for (int i = 0 ; i < 128 ; i++){
			listeTimbresCordes.add(Integer.toString(Controleur.tableauTimbre[i].timbreMIDI()) + " - " + Controleur.tableauTimbre[i].nom());
		}
		ArrayList<String> listeTimbresVents = new ArrayList<String>();		
		for (int i = 0 ; i < 128 ; i++){
			listeTimbresVents.add(Integer.toString(Controleur.tableauTimbre[i].timbreMIDI()) + " - " + Controleur.tableauTimbre[i].nom());
		}
		
		
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
					TabC.ordonnees[1] = current + TabC.ordonnees[0];
					break;
				case "splitPane11":
					TabC.ordonnees[1] = current + TabC.ordonnees[0];
					break;
				case "splitPane12":
					TabC.ordonnees[1] = current + TabC.ordonnees[0];
					break;
				case "splitPane20":
					TabC.ordonnees[2] = current + TabC.ordonnees[1];
					break;
				case "splitPane21":
					TabC.ordonnees[2] = current + TabC.ordonnees[1];
					break;
				case "splitPane22":
					TabC.ordonnees[2] = current + TabC.ordonnees[1];
					break;
				default:
					break;
				}
			
			}
		};
		panel.setLayout(null);
		
		
		
		JSplitPane splitPane00 = new JSplitPane();
		splitPane00.setName("splitPane00");
		splitPane00.setBounds((int) TabC.abscisses[0], (int) TabC.ordonnees[0],(int) (TabC.abscisses[3] - TabC.abscisses[0]), (int) (TabC.ordonnees[3] - TabC.ordonnees[0]));
		splitPane00.setDividerLocation((int) (TabC.abscisses[1] - TabC.abscisses[0]));
		panel.add(splitPane00);
		splitPane00.setDividerSize(2);
		splitPane00.setBorder(BorderFactory.createEmptyBorder());		
		splitPane00.addPropertyChangeListener(propertyChangeListener);
		
		JSplitPane splitPane01 = new JSplitPane();
		splitPane01.setName("splitPane01");
		splitPane01.setBounds((int) TabC.abscisses[1], (int) TabC.ordonnees[0],(int) (TabC.abscisses[3] - TabC.abscisses[1]), (int) (TabC.ordonnees[3] - TabC.ordonnees[0]));
		splitPane01.setDividerLocation((int) (TabC.abscisses[2] - TabC.abscisses[1]));
		splitPane00.setRightComponent(splitPane01);
		splitPane01.setDividerSize(2);
		splitPane01.setBorder(BorderFactory.createEmptyBorder());		
		splitPane01.addPropertyChangeListener(propertyChangeListener);
		
	    		
		JSplitPane splitPane10 = new JSplitPane();
		splitPane10.setName("splitPane10");
		splitPane10.setBounds((int) TabC.abscisses[0], (int) TabC.ordonnees[0],(int) (TabC.abscisses[1] - TabC.abscisses[0]), (int) (TabC.ordonnees[3] - TabC.ordonnees[0]));
		splitPane10.setDividerLocation((int) (TabC.ordonnees[1] - TabC.ordonnees[0]));
		splitPane10.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane00.setLeftComponent(splitPane10);
		splitPane10.setDividerSize(2);
		splitPane10.setBorder(BorderFactory.createEmptyBorder());		
		splitPane10.addPropertyChangeListener(propertyChangeListener);
		
		JSplitPane splitPane11 = new JSplitPane();
		splitPane11.setName("splitPane11");
		splitPane11.setBounds((int) TabC.abscisses[1], (int) TabC.ordonnees[0],(int) (TabC.abscisses[2] - TabC.abscisses[1]), (int) (TabC.ordonnees[3] - TabC.ordonnees[0]));
		splitPane11.setDividerLocation((int) (TabC.ordonnees[1] - TabC.ordonnees[0]));
		splitPane11.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane01.setRightComponent(splitPane11);
		splitPane11.setDividerSize(2);
		splitPane11.setBorder(BorderFactory.createEmptyBorder());		
		splitPane11.addPropertyChangeListener(propertyChangeListener);
		
		JSplitPane splitPane12 = new JSplitPane();
		splitPane12.setName("splitPane12");
		splitPane12.setBounds((int) TabC.abscisses[2], (int) TabC.ordonnees[0],(int) (TabC.abscisses[3] - TabC.abscisses[2]), (int) (TabC.ordonnees[3] - TabC.ordonnees[0]));
		splitPane12.setDividerLocation((int) (TabC.ordonnees[1] - TabC.ordonnees[0]));
		splitPane12.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane01.setLeftComponent(splitPane12);
		splitPane12.setDividerSize(2);
		splitPane12.setBorder(BorderFactory.createEmptyBorder());		
		splitPane12.addPropertyChangeListener(propertyChangeListener);
		
		JSplitPane splitPane20 = new JSplitPane();
		splitPane20.setName("splitPane20");
		splitPane20.setBounds((int) TabC.abscisses[0], (int) TabC.ordonnees[1],(int) (TabC.abscisses[1] - TabC.abscisses[0]), (int) (TabC.ordonnees[3] - TabC.ordonnees[1]));
		splitPane20.setDividerLocation((int) (TabC.ordonnees[2] - TabC.ordonnees[1]));
		splitPane20.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane10.setRightComponent(splitPane20);
		splitPane20.setDividerSize(2);
		splitPane20.setBorder(BorderFactory.createEmptyBorder());		
		splitPane20.addPropertyChangeListener(propertyChangeListener);		
		
		JSplitPane splitPane21 = new JSplitPane();
		splitPane21.setName("splitPane21");
		splitPane21.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane21.setBounds((int) TabC.abscisses[1], (int) TabC.ordonnees[1],(int) (TabC.abscisses[2] - TabC.abscisses[1]), (int) (TabC.ordonnees[3] - TabC.ordonnees[1]));
		splitPane21.setDividerLocation((int) (TabC.ordonnees[2] - TabC.ordonnees[1]));
		splitPane11.setRightComponent(splitPane21);
		splitPane21.setDividerSize(2);
		splitPane21.setBorder(BorderFactory.createEmptyBorder());		
		splitPane21.addPropertyChangeListener(propertyChangeListener);

		JSplitPane splitPane22 = new JSplitPane();
		splitPane22.setName("splitPane22");
		splitPane22.setBounds((int) TabC.abscisses[2], (int) TabC.ordonnees[1],(int) (TabC.abscisses[2] - TabC.abscisses[1]), (int) (TabC.ordonnees[3] - TabC.ordonnees[1]));
		splitPane22.setDividerLocation((int) (TabC.ordonnees[2] - TabC.ordonnees[1]));
		splitPane22.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane12.setRightComponent(splitPane22);
		splitPane22.setDividerSize(2);
		splitPane22.setBorder(BorderFactory.createEmptyBorder());
	    splitPane22.addPropertyChangeListener(propertyChangeListener);

		
		JPanel HautGauche = new JPanel();
		splitPane10.setLeftComponent(HautGauche);
		
		JPanel HautCentre = new JPanel();
		splitPane11.setLeftComponent(HautCentre);
		
		JPanel HautDroit = new JPanel();
		splitPane12.setLeftComponent(HautDroit);
		
		JPanel CentreGauche = new JPanel();
		splitPane20.setLeftComponent(CentreGauche);
				
		JPanel CentreCentre = new JPanel();
		splitPane21.setLeftComponent(CentreCentre);
		
		JPanel CentreDroit = new JPanel();
		splitPane22.setLeftComponent(CentreDroit);
		
		JPanel BasGauche = new JPanel();
		splitPane20.setRightComponent(BasGauche);
		
		JPanel BasCentre = new JPanel();
		splitPane21.setRightComponent(BasCentre);
		
		JPanel BasDroit = new JPanel();
		splitPane22.setRightComponent(BasDroit);
		

		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox<String> comboVent3 = new JComboBox(listeTimbresVents.toArray());
		comboVent3.setName("comboVent3");
		comboVent3.setBounds(0, 0, TableauCompositeur.MAX_WIDTH/3  - 50, 25);
		comboVent3.addActionListener(actionListener);
		panel.add(comboVent3);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox<String> comboVent2 = new JComboBox(listeTimbresVents.toArray());
		comboVent2.setName("comboVent2");
		comboVent2.setBounds(0, (int) TabC.ordonnees[1], TableauCompositeur.MAX_WIDTH/3 - 50, 25);
		comboVent2.addActionListener(actionListener);
		panel.add(comboVent2);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox<String> comboVent1 = new JComboBox(listeTimbresVents.toArray());
		comboVent1.setName("comboVent1");
		comboVent1.setBounds(0, (int) TabC.ordonnees[2], TableauCompositeur.MAX_WIDTH/3 - 50, 25);
		comboVent1.addActionListener(actionListener);
		panel.add(comboVent1);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox<String> comboCorde1 = new JComboBox(listeTimbresVents.toArray());
		comboCorde1.setName("comboCorde1");
		comboCorde1.setBounds((int) TabC.abscisses[0], (int) TabC.ordonnees[3] + 20, TableauCompositeur.MAX_WIDTH/3 - 50, 25);
		comboCorde1.addActionListener(actionListener);
		panel.add(comboCorde1);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox<String> comboCorde2 = new JComboBox(listeTimbresVents.toArray());
		comboCorde2.setName("comboCorde2");
		comboCorde2.setBounds((int) TabC.abscisses[1], (int) TabC.ordonnees[3] + 20, TableauCompositeur.MAX_WIDTH/3 - 50, 25);
		comboCorde2.addActionListener(actionListener);
		panel.add(comboCorde2);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox<String> comboCorde3 = new JComboBox(listeTimbresVents.toArray());
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