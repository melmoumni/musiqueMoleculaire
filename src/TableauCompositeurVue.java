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

import Utilitaires.Midi;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;




public class TableauCompositeurVue extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btnStart;
	TableauCompositeur TabC;
	JSplitPane splitPane00, splitPane01, splitPane02,splitPane10 ,splitPane11, splitPane12, splitPane20, splitPane21, splitPane22;
	InstrumentsBox comboOrdonnees1, comboOrdonnees2, comboOrdonnees3, comboAbcisses1, comboAbcisses2, comboAbcisses3 ;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		Controleur controleur = new Compositeur(1);
		try {
		    controleur.initMolecules("./data/trajectoires.trc","./data/analyse.txt", "./data/listeInstruments.txt");
		}
		catch (IOException e) {
		}
		
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		        	for (Float f : TabC.abscisses){
		    			System.out.printf("%f ", f);
		    		}
		    		System.out.println();
		    		for (Float f : TabC.ordonnees){
		    			System.out.printf("%f ", f);
		    		}
		    		System.out.println();
		        }
		      }

			private void changeDividerHor(JSplitPane sourceSplitPane) {
				TabC.abscisses[1] = TabC.abscisses[0] + splitPane00.getDividerLocation();
				TabC.abscisses[2] = TabC.abscisses[1] + splitPane01.getDividerLocation();
				comboAbcisses1.setBounds((int) TabC.abscisses[0] ,
						comboAbcisses1.getY(), 
						min(TableauCompositeur.MAX_WIDTH/3 - 50, (int) (TabC.abscisses[1] - TabC.abscisses[0])), 
						25);		
				comboAbcisses2.setBounds((int) TabC.abscisses[1] ,
						comboAbcisses2.getY(),
						min(TableauCompositeur.MAX_WIDTH/3 - 50,(int) (TabC.abscisses[2] - TabC.abscisses[1])),
						25);
				comboAbcisses3.setBounds((int) TabC.abscisses[2] ,
						comboAbcisses3.getY(), 
						min(TableauCompositeur.MAX_WIDTH/3 - 50, (int) (TabC.abscisses[3] - TabC.abscisses[2])), 
						25);		
			}

			private int min(int width, int i) {
				if (width < i){
					return width;
				}
				return i;
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
						TabC.ordonnees[1] = current + TabC.ordonnees[0];
						splitPane10.setEnabled(false);
						splitPane11.setEnabled(false);
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
				TabC.ordonnees[2] = TabC.ordonnees[1] + splitPane20.getDividerLocation(); // Au cas ou les cases du bas bouge de maniere indirecte.

				comboOrdonnees2.setBounds(0, (int) TabC.ordonnees[1], comboOrdonnees2.getWidth(), 25);
				comboOrdonnees1.setBounds(0, (int) TabC.ordonnees[2], comboOrdonnees1.getWidth(), 25);

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
	

		comboOrdonnees1 = new InstrumentsBox();
		comboOrdonnees1.setName("comboOrdonnees3");
		comboOrdonnees1.setBounds(0, 0, TableauCompositeur.MAX_WIDTH/3  - 50, 50);
		panel.add(comboOrdonnees1);
		
		comboOrdonnees2 = new InstrumentsBox();
		comboOrdonnees2.setName("comboOrdonnees2");
		comboOrdonnees2.setBounds(0, (int) TabC.ordonnees[1], TableauCompositeur.MAX_WIDTH/3 - 50, 50);
		panel.add(comboOrdonnees2);
		
		comboOrdonnees3 = new InstrumentsBox();
		comboOrdonnees3.setName("comboOrdonnees3");
		comboOrdonnees3.setBounds(0, (int) TabC.ordonnees[2], TableauCompositeur.MAX_WIDTH/3 - 50, 50);
		panel.add(comboOrdonnees3);
		
		comboAbcisses1 = new InstrumentsBox();
		comboAbcisses1.setName("comboAbcisses1");
		comboAbcisses1.setBounds((int) TabC.abscisses[0], (int) TabC.ordonnees[3] + 20, TableauCompositeur.MAX_WIDTH/3 - 50, 50);
		panel.add(comboAbcisses1);
		
		comboAbcisses2 = new InstrumentsBox();
		comboAbcisses2.setName("comboAbcisses2");
		comboAbcisses2.setBounds((int) TabC.abscisses[1], (int) TabC.ordonnees[3] + 20, TableauCompositeur.MAX_WIDTH/3 - 50, 50);
		panel.add(comboAbcisses2);
		
		comboAbcisses3 = new InstrumentsBox();
		comboAbcisses3.setName("comboAbcisses3");
		comboAbcisses3.setBounds((int) TabC.abscisses[2], (int) TabC.ordonnees[3] + 20, TableauCompositeur.MAX_WIDTH/3 - 50 , 50);
		panel.add(comboAbcisses3);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == btnStart){

			TabC.timbreOrd[0] = Controleur.tableauTimbre[comboOrdonnees1.getIndexInstrument()-1];
			TabC.timbreOrd[1] = Controleur.tableauTimbre[comboOrdonnees2.getIndexInstrument()-1];
			TabC.timbreOrd[2] = Controleur.tableauTimbre[comboOrdonnees3.getIndexInstrument()-1];

			TabC.timbreAbs[0] = Controleur.tableauTimbre[comboAbcisses1.getIndexInstrument()-1];
			TabC.timbreAbs[1] = Controleur.tableauTimbre[comboAbcisses2.getIndexInstrument()-1];
			TabC.timbreAbs[2] = Controleur.tableauTimbre[comboAbcisses3.getIndexInstrument()-1];
		
	    	for (Timbre t : TabC.timbreAbs){
				System.out.printf("%d ", t.timbreMIDI());
			}
			System.out.println();
			for (Timbre t : TabC.timbreOrd){
				System.out.printf("%d ", t.timbreMIDI());
			}
			System.out.println("Ca commence");
			Controleur.printTrajectoires();
			Controleur.remplirSequence();
			Midi.jouerSequence();
		}
	}
}
