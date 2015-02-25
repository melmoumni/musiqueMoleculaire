

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTable;

import java.awt.GridLayout;

import javax.swing.BoxLayout;

import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSplitPane;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.List;
import java.awt.Choice;
import java.awt.Scrollbar;
import java.io.IOException;
import java.util.ArrayList;


public class TableauChercheurVue extends JFrame {

	int x = 6;
	int y = 4;
	TableauChercheur TabC;

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
		
		final TableauChercheur t = new TableauChercheur(4,3);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableauChercheurVue frame = new TableauChercheurVue(t);
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
	public TableauChercheurVue(TableauChercheur tabChercheur) {
		this.setResizable(false);

		TabC = new TableauChercheur(x, y);
		
		ArrayList<Integer> listeTimbres = new ArrayList<Integer>();		
		for (int i = 0 ; i < 128 ; i++){
			listeTimbres.add(Controleur.tableauTimbre[i].timbreMIDI());
		}
		
		x = tabChercheur.mat.size();
		y = tabChercheur.mat.get(0).size();
		
		System.out.println(x + " " + y + " " + tabChercheur.absisses.size() + " " + tabChercheur.ordonnees.size());
		for (Float i : tabChercheur.absisses){
			System.out.printf("%f ",i);
		}
		System.out.println("");
		for (Float i : tabChercheur.ordonnees){
			System.out.printf("%f ",i);
		}
		System.out.println("");
		
		/* Utilisation :
		 * 
		 * Pour une case (i,j) avec 0 <= i <= x-2 et 0 <= j <= y-1 :
		 * mat.get(j).get(i).getLeftComponent
		 * 
		 * Pour une case (i,j) avec i = x-1 et 0 <= j <= y-1 :
		 * mat.get(j).get(i-1).getRightComponent
		 * 
		 */

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 500);

		JSplitPane splitPane = new JSplitPane();
		getContentPane().add(splitPane, BorderLayout.CENTER);

		/* Création de la matrice des splitPane locaux */
		ArrayList<ArrayList<JSplitPane>> mat = new ArrayList<ArrayList<JSplitPane>>();

		/* Creation de la premiere ligne de splitPane */
		ArrayList<JSplitPane> tInit = new ArrayList<JSplitPane>();
		tInit.add(splitPane);

		JComboBox comboBox_1 = new JComboBox(listeTimbres.toArray());
		splitPane.setLeftComponent(comboBox_1);
		mat.add(tInit);
		for (int j = 0 ; j < x-2 ; j++){
			ArrayList<JSplitPane> t = new ArrayList<JSplitPane>();
			JSplitPane splitPaneTmp = new JSplitPane();
			t.add(splitPaneTmp);
			mat.get(j).get(0).setRightComponent(splitPaneTmp);
			mat.add(t);
		}
		/* Si une deuxieme ligne existe on anticipe */
		if (y>2){
			ArrayList<JSplitPane> t = new ArrayList<JSplitPane>();
			JSplitPane splitPaneTmp = new JSplitPane();
			splitPaneTmp.setOrientation(JSplitPane.VERTICAL_SPLIT);
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
					mat.get(j).get(i-1).setLeftComponent(splitPaneTmp);

				}
			}else{
				for (int j = 0 ; j < x-1 ; j++){
					JSplitPane splitPaneTmp = new JSplitPane();
					mat.get(j).add(splitPaneTmp);
					splitPaneTmp.setOrientation(JSplitPane.VERTICAL_SPLIT);
					mat.get(j).get(i-1).setRightComponent(splitPaneTmp);

				}
			}
			/* La derniere case de la deuxieme ligne a deja ete anticipe, pour les autres on la cree manuellement*/
			if (i>1){
				JSplitPane splitPaneTmp = new JSplitPane();
				mat.get(x-1).add(splitPaneTmp);
				splitPaneTmp.setOrientation(JSplitPane.VERTICAL_SPLIT);
				mat.get(x-1).get(i-2).setRightComponent(splitPaneTmp);
			}
		}

		/* On supprime la premiere ligne qui a juste servi a ordonner la construction */
		for (int i = 0 ; i < x-1 ; i++){
			mat.get(i).remove(0);
		}

		/* On affiche la matrice des splitPane */
		//    	for (ArrayList<JSplitPane> a : mat){
		//    		for (JSplitPane j : a){
		//    			System.out.printf("1 ");
		//    		}
		//    		System.out.printf("\n");
		//    	}

		/* Definition des comboBox pour chaque Pane */
		for (int i = 0 ; i < x ; i++){
			for (int j = 0 ; j < y-1 ; j++){
				JComboBox comboBox = new JComboBox(listeTimbres.toArray());
				comboBox.setSelectedIndex(3);
				Dimension pageSize=new Dimension((int) (tabChercheur.absisses.get(j+1) - tabChercheur.absisses.get(j)),(int) (tabChercheur.ordonnees.get(i+1) - tabChercheur.ordonnees.get(i)));
				comboBox.setPreferredSize(pageSize);
				mat.get(i).get(j).setLeftComponent(comboBox);
			}
			JComboBox comboBox = new JComboBox(listeTimbres.toArray());
			mat.get(i).get(y-2).setRightComponent(comboBox);
		}

	}
}
