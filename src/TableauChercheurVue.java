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

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;


public class TableauChercheurVue extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btnStart;
	JButton btnAjouterUneLigne;
	JButton btnAjouterUneColonne;
	JButton btnSupprimerUneLigne;
	JButton btnSupprimerUneColonne;
	TableauChercheur TabC;
	final ArrayList<ArrayList<JSplitPane>> mat;
	final ArrayList<ArrayList<JComboBox<String>>> matCombo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		Controleur controleur = new Chercheur();
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
					TableauChercheurVue frame = new TableauChercheurVue(true, null);
					
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
	public TableauChercheurVue(boolean init, TableauChercheur tab) {
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
	    	TabC = new TableauChercheur(5,3);
	    }
	    else {
	    	TabC = tab;
		}
		
		ArrayList<String> listeTimbres = new ArrayList<String>();		
		for (int i = 0 ; i < 128 ; i++){
			listeTimbres.add(Integer.toString(Controleur.tableauTimbre[i].timbreMIDI()) + " - " + Controleur.tableauTimbre[i].nom());
		}
		
		int x = TabC.mat.size();
		int y = TabC.mat.get(0).size();
		
		for (Float i : TabC.abscisses){
			System.out.printf("%f ",i);
		}
		System.out.println("");
		for (ArrayList<Float> AL : TabC.ordonnees){
			for (Float i : AL){
				System.out.printf("%f ",i);
			}
			System.out.println("");
		}
		System.out.println("");
		

		
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
		
		
		
		
		JSplitPane splitPane = new JSplitPane();
		panel.add(splitPane);
		splitPane.setDividerSize(2);
	    splitPane.setBorder(BorderFactory.createEmptyBorder());		

	    /* Creation de la premiere ligne de splitPane */
		ArrayList<JSplitPane> tInit = new ArrayList<JSplitPane>();
		tInit.add(splitPane);


		
		
		/* Création de la matrice des splitPane locaux */
		mat = new ArrayList<ArrayList<JSplitPane>>();
		
		matCombo = new ArrayList<ArrayList<JComboBox<String>>>();
		
		final ArrayList<JSplitPane> premiereLigne = new ArrayList<JSplitPane>();
		
		/* Utilisation :
		 * 
		 * Pour une case (i,j) avec 0 <= i <= x-2 et 0 <= j <= y-1 :
		 * mat.get(j).get(i).getLeftComponent
		 * 
		 * Pour une case (i,j) avec i = x-1 et 0 <= j <= y-1 :
		 * mat.get(j).get(i-1).getRightComponent
		 * 
		 */

		
		/* Programme permettant la détection de changement de timbre */
		ActionListener actionListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
				changeMatTimbre(comboBox); 
				
				for (int i = 0 ; i < TabC.mat.size() ; i++){
					for (int j = 0 ; j < TabC.mat.get(0).size() ; j++){
						System.out.printf("%d ",TabC.mat.get(i).get(j).timbreMIDI());
					}
					System.out.println("");
				}
				System.out.println("");				
			}

			private void changeMatTimbre(JComboBox<String> comboBox) {
				for (int i = 0 ; i < matCombo.size() ; i++){
					for (int j = 0 ; j < matCombo.get(0).size() ; j++){
						if (comboBox.equals(matCombo.get(i).get(j))){
							TabC.mat.get(i).set(j, Controleur.tableauTimbre[comboBox.getSelectedIndex()]);
						}
					}
				}
			}
		};


		/* Programme permettant la détection de changement du divider de chaque splitPane */
		PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
		      public void propertyChange(PropertyChangeEvent changeEvent) {
		        JSplitPane sourceSplitPane = (JSplitPane) changeEvent.getSource();
		        String propertyName = changeEvent.getPropertyName();
		        if (propertyName.equals(JSplitPane.LAST_DIVIDER_LOCATION_PROPERTY)) {
		        	if ((sourceSplitPane.getOrientation() == JSplitPane.VERTICAL_SPLIT)  && ((int) changeEvent.getNewValue() != -1)){
		        		changeDividerVert(mat, sourceSplitPane);
		        	}
		        	else if ((sourceSplitPane.getOrientation() == JSplitPane.HORIZONTAL_SPLIT)  && ((int) changeEvent.getNewValue() != -1)){
		        		changeDividerHor(premiereLigne, sourceSplitPane);
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
				TabC.abscisses.set(indexI + 1,(float) current + TabC.abscisses.get(indexI));

			}

			private void changeDividerVert(ArrayList<ArrayList<JSplitPane>> mat, JSplitPane sourceSplitPane) {

				
				int indexI = 0;
				for (int i = 0 ; i < mat.size() ; i++){
					for (int j = 0 ; j < mat.get(0).size() ; j++){
						if (sourceSplitPane.equals(mat.get(i).get(j))){
							System.out.println(i + " " + j);
							indexI = i;
						}						
					}
				}

				for (int i = 0 ; i < TabC.ordonnees.get(indexI).size() - 2 ; i++){
					float f = (float) mat.get(indexI).get(i).getDividerLocation() +   TabC.ordonnees.get(indexI).get(i);
					System.out.println(i + " " + f);
					TabC.ordonnees.get(indexI).set(i + 1,f);	
				}

	    		for (Float i : TabC.abscisses){
	    			System.out.printf("%f ",i);
	    		}
	    		System.out.println("");
	    		for (ArrayList<Float> AL : TabC.ordonnees){
	    			for (Float i : AL){
	    				System.out.printf("%f ",i);
	    			}
	    			System.out.println("");
	    		}
	    		System.out.println("");

				
			}
		};
		
		

	    splitPane.addPropertyChangeListener(propertyChangeListener);
		
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox<String> comboBox_1 = new JComboBox(listeTimbres.toArray());
		splitPane.setLeftComponent(comboBox_1);
		mat.add(tInit);
		for (int j = 0 ; j < x-2 ; j++){
			ArrayList<JSplitPane> t = new ArrayList<JSplitPane>();
			JSplitPane splitPaneTmp = new JSplitPane();
		    splitPaneTmp.addPropertyChangeListener(propertyChangeListener);
		    splitPaneTmp.setDividerSize(2);
		    splitPaneTmp.setMinimumSize(new Dimension (TableauChercheur.MIN_CELL_WIDTH, TableauChercheur.MIN_CELL_HEIGHT));
		    splitPaneTmp.setBorder(BorderFactory.createEmptyBorder());
			t.add(splitPaneTmp);
			mat.get(j).get(0).setRightComponent(splitPaneTmp);
			mat.add(t);
		}
		/* Si une deuxieme ligne existe on anticipe */
		if (y>2){
			ArrayList<JSplitPane> t = new ArrayList<JSplitPane>();
			JSplitPane splitPaneTmp = new JSplitPane();
			splitPaneTmp.setOrientation(JSplitPane.VERTICAL_SPLIT);
		    splitPaneTmp.addPropertyChangeListener(propertyChangeListener);
		    splitPaneTmp.setMinimumSize(new Dimension (TableauChercheur.MIN_CELL_WIDTH, TableauChercheur.MIN_CELL_HEIGHT));
		    splitPaneTmp.setDividerSize(2);
			splitPaneTmp.setBorder(BorderFactory.createEmptyBorder());
			t.add(splitPaneTmp);
			mat.get(x-2).get(0).setRightComponent(splitPaneTmp);
			mat.add(t);
		}

		/* Ajout de chaque case (splitPane) pour toutes les lignes - pour toutes les colonnes*/ 
		for (int i = 1 ; i < y ; i++){
			if (i == 1){
				for (int j = 0 ; j < x-1 ; j++){
					JSplitPane splitPaneTmp = new JSplitPane();
					mat.get(j).add(splitPaneTmp);
					splitPaneTmp.setOrientation(JSplitPane.VERTICAL_SPLIT);
				    splitPaneTmp.addPropertyChangeListener(propertyChangeListener);
				    splitPaneTmp.setDividerSize(2);
				    splitPaneTmp.setMinimumSize(new Dimension (TableauChercheur.MIN_CELL_WIDTH, TableauChercheur.MIN_CELL_HEIGHT));
				    splitPaneTmp.setBorder(BorderFactory.createEmptyBorder());
					mat.get(j).get(i-1).setLeftComponent(splitPaneTmp);

				}
			}else{
				for (int j = 0 ; j < x-1 ; j++){
					JSplitPane splitPaneTmp = new JSplitPane();
					mat.get(j).add(splitPaneTmp);
					splitPaneTmp.setOrientation(JSplitPane.VERTICAL_SPLIT);
				    splitPaneTmp.addPropertyChangeListener(propertyChangeListener);
				    splitPaneTmp.setDividerSize(2);
				    splitPaneTmp.setMinimumSize(new Dimension (TableauChercheur.MIN_CELL_WIDTH, TableauChercheur.MIN_CELL_HEIGHT));
				    splitPaneTmp.setBorder(BorderFactory.createEmptyBorder());
					mat.get(j).get(i-1).setRightComponent(splitPaneTmp);

				}
			}
			/* La derniere case de la deuxieme ligne a deja ete anticipe, pour les autres on la cree manuellement*/
			if (i>1){
				JSplitPane splitPaneTmp = new JSplitPane();
				mat.get(x-1).add(splitPaneTmp);
				splitPaneTmp.setOrientation(JSplitPane.VERTICAL_SPLIT);
			    splitPaneTmp.addPropertyChangeListener(propertyChangeListener);
			    splitPaneTmp.setDividerSize(2);
			    splitPaneTmp.setMinimumSize(new Dimension (TableauChercheur.MIN_CELL_WIDTH, TableauChercheur.MIN_CELL_HEIGHT));
			    splitPaneTmp.setBorder(BorderFactory.createEmptyBorder());
				mat.get(x-1).get(i-2).setRightComponent(splitPaneTmp);
			}
		}

		/* On supprime la premiere ligne qui a juste servi a ordonner la construction, mais on la stocke dans un autre tableau */
		for (int i = 0 ; i < x-1 ; i++){
			premiereLigne.add(mat.get(i).get(0));
			mat.get(i).remove(0);
		}

		
		

		/* Definition des comboBox pour chaque Pane */
		for (int i = 0 ; i < x ; i++){
			ArrayList<JComboBox<String>> t = new ArrayList<JComboBox<String>>();
			for (int j = 0 ; j < y-1 ; j++){
				@SuppressWarnings({ "unchecked", "rawtypes" })
				JComboBox<String> comboBox = new JComboBox(listeTimbres.toArray());
				comboBox.setSelectedIndex(TabC.mat.get(i).get(j).timbreMIDI()-1);
				Dimension pageSize=new Dimension((int) (TabC.abscisses.get(i+1) - TabC.abscisses.get(i)),(int) (TabC.ordonnees.get(i).get(j+1) - TabC.ordonnees.get(i).get(j)));
				comboBox.setPreferredSize(pageSize);
				comboBox.addActionListener(actionListener);
				t.add(comboBox);
				mat.get(i).get(j).setLeftComponent(comboBox);
			}
			@SuppressWarnings({ "unchecked", "rawtypes" })
			JComboBox<String> comboBox = new JComboBox(listeTimbres.toArray());
			Dimension pageSize=new Dimension((int) (TabC.abscisses.get(i+1) - TabC.abscisses.get(i)),(int) (TabC.ordonnees.get(i).get(y) - TabC.ordonnees.get(i).get(y-1)));

			comboBox.setPreferredSize(pageSize);
			comboBox.setSelectedIndex(TabC.mat.get(i).get(y-1).timbreMIDI()-1);
			comboBox.addActionListener(actionListener);
			mat.get(i).get(y-2).setRightComponent(comboBox);
			t.add(comboBox);
			matCombo.add(t);
		}

	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if(source == btnStart){
			System.out.println("Ca commence");
		}
		else if(source == btnAjouterUneColonne){
			System.out.println("Vous avez ajouté une colonne.");
			try {
				dispose();
				TabC.ajouterColonne();
				TableauChercheurVue frame = new TableauChercheurVue(false, TabC);
				frame.setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		else if(source == btnAjouterUneLigne){
			System.out.println("Vous avez ajouté une ligne.");	
			try{
				dispose();
				TabC.ajouterLigne();
				TableauChercheurVue frame = new TableauChercheurVue(false, TabC);
				frame.setVisible(true);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		else if(source == btnSupprimerUneLigne){
			System.out.println("Vous avez supprimé une ligne.");	
			try{
				dispose();
				TabC.supprimerLigne();
				TableauChercheurVue frame = new TableauChercheurVue(false, TabC);
				frame.setVisible(true);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		else if(source == btnSupprimerUneColonne){
			System.out.println("Vous avez supprimé une colonne.");	
			try{
				dispose();
				TabC.supprimerColonne();
				TableauChercheurVue frame = new TableauChercheurVue(false, TabC);
				frame.setVisible(true);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}