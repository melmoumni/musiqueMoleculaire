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
	JButton btnAjouterUneLigne;
	JButton btnAjouterUneColonne;
	JButton btnSupprimerUneLigne;
	JButton btnSupprimerUneColonne;
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
		
		ArrayList<String> listeTimbres = new ArrayList<String>();		
		for (int i = 0 ; i < 128 ; i++){
			listeTimbres.add(Integer.toString(Controleur.tableauTimbre[i].timbreMIDI()) + " - " + Controleur.tableauTimbre[i].nom());
		}
		
		
		
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
	    
	    btnAjouterUneLigne = new JButton("Ajouter Une Ligne");
	    btnAjouterUneLigne.setName("btnAjouterUneLigne");
	    btnAjouterUneLigne.addActionListener(this);
	    panel_1.add(btnAjouterUneLigne, "2, 2");
	    
	    btnSupprimerUneLigne = new JButton("Supprimer Une Ligne");
	    btnSupprimerUneLigne.setName("btnSupprimerUneLigne");
	    btnSupprimerUneLigne.addActionListener(this);
	    panel_1.add(btnSupprimerUneLigne, "2, 4");
	    
	    btnAjouterUneColonne = new JButton("Ajouter une Colonne");
	    btnAjouterUneColonne.setName("btnAjouterUneColonne");
	    btnAjouterUneColonne.addActionListener(this);
	    panel_1.add(btnAjouterUneColonne, "2, 10");

	    btnSupprimerUneColonne = new JButton("Supprimer une Colonne");
	    btnSupprimerUneColonne.setName("btnSupprimerUneColonne");
	    btnSupprimerUneColonne.addActionListener(this);
	    panel_1.add(btnSupprimerUneColonne, "2, 12");
	    
	    btnStart = new JButton("Valider le tableau");
	    btnStart.setName("btnStart");
	    btnStart.addActionListener(this);
	    panel_1.add(btnStart, "2, 26");
		
		
		
		/* Programme permettant la détection de changement de timbre */
		ActionListener actionListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox comboBox = (JComboBox) e.getSource();
				
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
		        		//changeDividerVert(mat, sourceSplitPane);
		        	}
		        	else if ((sourceSplitPane.getOrientation() == JSplitPane.HORIZONTAL_SPLIT)  && ((int) changeEvent.getNewValue() != -1)){
		        	//	changeDividerHor(premiereLigne, sourceSplitPane);
		        	}
		        }
		      }

			private void changeDividerHor(ArrayList<JSplitPane> premiereLigne, JSplitPane sourceSplitPane) {

				int current = sourceSplitPane.getDividerLocation();
				int indexI = 0;
				for (int i = 0 ; i < premiereLigne.size() ; i++){
					if (sourceSplitPane.equals(premiereLigne.get(i))){
						indexI = i;
					}
				}
				//TabC.absisses.set(indexI + 1,(float) current + TabC.absisses.get(indexI));

			}

			private void changeDividerVert(ArrayList<ArrayList<JSplitPane>> mat, JSplitPane sourceSplitPane) {

				
				int indexI = 0;
				int indexJ = 0;
				for (int i = 0 ; i < mat.size() ; i++){
					for (int j = 0 ; j < mat.get(0).size() ; j++){
						if (sourceSplitPane.equals(mat.get(i).get(j))){
							System.out.println(i + " " + j);
							indexI = i;
							indexJ = j;
						}						
					}
				}

//				for (int i = 0 ; i < TabC.ordonnees.get(indexI).size() - 2 ; i++){
//					float f = (float) mat.get(indexI).get(i).getDividerLocation() +   TabC.ordonnees.get(indexI).get(i);
//					System.out.println(i + " " + f);
//					TabC.ordonnees.get(indexI).set(i + 1,f);	
//				}

				
			}
		};
		panel.setLayout(null);
		
		
		
		JSplitPane splitPane00 = new JSplitPane();
		splitPane00.setBounds(603, 0, 355, 79);
		panel.add(splitPane00);
		splitPane00.setDividerSize(2);
		splitPane00.setBorder(BorderFactory.createEmptyBorder());		
		splitPane00.addPropertyChangeListener(propertyChangeListener);
		
		JSplitPane splitPane01 = new JSplitPane();
		splitPane00.setRightComponent(splitPane01);
		splitPane01.setDividerSize(2);
		splitPane01.setBorder(BorderFactory.createEmptyBorder());		
		splitPane01.addPropertyChangeListener(propertyChangeListener);
		
//		JSplitPane splitPane02 = new JSplitPane();
//		panel.add(splitPane02);
//		splitPane02.setDividerSize(2);
//	    splitPane02.setBorder(BorderFactory.createEmptyBorder());		
//	    splitPane02.addPropertyChangeListener(propertyChangeListener);
	    		
		JSplitPane splitPane10 = new JSplitPane();
		splitPane10.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane00.setLeftComponent(splitPane10);
		splitPane10.setDividerSize(2);
		splitPane10.setBorder(BorderFactory.createEmptyBorder());		
		splitPane10.addPropertyChangeListener(propertyChangeListener);
		
		JSplitPane splitPane11 = new JSplitPane();
		splitPane11.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane01.setRightComponent(splitPane11);
		splitPane11.setDividerSize(2);
		splitPane11.setBorder(BorderFactory.createEmptyBorder());		
		splitPane11.addPropertyChangeListener(propertyChangeListener);
		
		JSplitPane splitPane12 = new JSplitPane();
		splitPane12.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane01.setLeftComponent(splitPane12);
		splitPane12.setDividerSize(2);
		splitPane12.setBorder(BorderFactory.createEmptyBorder());		
		splitPane12.addPropertyChangeListener(propertyChangeListener);
		
		JSplitPane splitPane20 = new JSplitPane();
		splitPane20.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane10.setRightComponent(splitPane20);
		splitPane20.setDividerSize(2);
		splitPane20.setBorder(BorderFactory.createEmptyBorder());		
		splitPane20.addPropertyChangeListener(propertyChangeListener);
		
		JSplitPane splitPane21 = new JSplitPane();
		splitPane21.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane11.setRightComponent(splitPane21);
		splitPane21.setDividerSize(2);
		splitPane21.setBorder(BorderFactory.createEmptyBorder());		
		splitPane21.addPropertyChangeListener(propertyChangeListener);
		
		JSplitPane splitPane22 = new JSplitPane();
		splitPane22.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane12.setRightComponent(splitPane22);
		splitPane22.setDividerSize(2);
		splitPane22.setBorder(BorderFactory.createEmptyBorder());
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(603, 92, 117, 22);
		panel.add(comboBox_2);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(724, 92, 117, 22);
		panel.add(comboBox_3);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setBounds(839, 92, 119, 22);
		panel.add(comboBox_4);
		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setBounds(508, 0, 31, 22);
		panel.add(comboBox_5);
		
		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setBounds(508, 33, 31, 22);
		panel.add(comboBox_6);
		
		JComboBox comboBox_7 = new JComboBox();
		comboBox_7.setBounds(508, 68, 31, 22);
		panel.add(comboBox_7);
	    splitPane22.addPropertyChangeListener(propertyChangeListener);
		JComboBox comboBox_1 = new JComboBox(listeTimbres.toArray());
		
		

		/* Definition des comboBox pour chaque Pane */
		JComboBox comboBox = new JComboBox(listeTimbres.toArray());
		//comboBox.setSelectedIndex(TabC.mat.get(i).get(j).timbreMIDI()-1);
		//Dimension pageSize=new Dimension((int) (TabC.absisses.get(i+1) - TabC.absisses.get(i)),(int) (TabC.ordonnees.get(i).get(j+1) - TabC.ordonnees.get(i).get(j)));
		//comboBox.setPreferredSize(pageSize);
		comboBox.addActionListener(actionListener);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if(source == btnStart){
			System.out.println("Ca commence");
		}
		else if(source == btnAjouterUneColonne){
			System.out.println("Vous avez ajouté une colonne.");
		}
		else if(source == btnAjouterUneLigne){
			System.out.println("Vous avez ajouté une ligne.");	
		}
		else if(source == btnSupprimerUneLigne){
			System.out.println("Vous avez supprimé une ligne.");	
		}
		else if(source == btnSupprimerUneColonne){
			System.out.println("Vous avez supprimé une colonne.");	
		}
	}
}