
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Fenetre extends JFrame{
	
	/* */
	JPanel panneauChoix = new JPanel();
	JPanel parametres = construitPanneauParam1();
	
	JButton passerParametre = new JButton("Valider");
	
	public Fenetre(){
		this.setTitle("Doré-Molécule");
		this.setSize(400,400); 
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);          

		panneauChoix = construitPanneauChoix();
		panneauChoix.setBorder(new EmptyBorder(5, 5, 5, 5));
		panneauChoix.setLayout(new BorderLayout(0, 0));
		this.getContentPane().add(panneauChoix);

		this.setVisible(true);
	}
	
	/* Construit le JPanel associe a la fenetre d'accueil (Choix du type d'analyse) */
	private JPanel construitPanneauChoix(){
		ButtonGroup groupe = new ButtonGroup();
		JPanel panneauChoix = new JPanel();
		JRadioButton choix1 = new JRadioButton("Chercheur");
		choix1.setToolTipText("Tableau d'instruments");
		choix1.setBounds(56, 57, 100, 23);
		groupe.add(choix1);
		panneauChoix.add(choix1);
		JRadioButton choix2 = new JRadioButton("Compositeur");
		choix2.setToolTipText("Approche axiale");
		choix2.setBounds(201, 57, 100, 23);
		groupe.add(choix2);
		panneauChoix.add(choix2);
		this.passerParametre.addActionListener(new EcouteurBouton());
		this.passerParametre.setBounds(123, 102, 100, 23);
		panneauChoix.add(passerParametre);
		return panneauChoix;

	}
	
	/* Construit le JPanel associe a la fenetre de parametrage pour le Chercheur */
	private JPanel construitPanneauParam1(){
		JPanel param = new JPanel();
		param.setLayout(new BorderLayout(0, 0));
		
		JButton btnValider = new JButton("Valider");
		//btnValider.setAction(validerChoix);
		btnValider.setBounds(132, 317, 91, 23);
		param.add(btnValider);
		
		/* Choix des fichiers pour l'analyse */
		JLabel lblFichiers = new JLabel("Fichiers");
		lblFichiers.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFichiers.setBounds(10, 11, 46, 14);
		param.add(lblFichiers);
		
		JLabel lblTrajectoires = new JLabel("Trajectoires");
		lblTrajectoires.setBounds(36, 33, 70, 14);
		param.add(lblTrajectoires);
		
		JFileChooser fichierTrajectoires = new JFileChooser("");
		
		JLabel lblMouvements = new JLabel("Mouvements");
		lblMouvements.setBounds(36, 55, 73, 14);
		param.add(lblMouvements);
		
		JFileChooser fichierMvt = new JFileChooser("");
		
		/* Configuration taille de l'image */
		JLabel lblTailleDeLimage = new JLabel("Taille de l'image");
		lblTailleDeLimage.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTailleDeLimage.setBounds(10, 90, 99, 14);
		param.add(lblTailleDeLimage);
		
		JLabel lblX = new JLabel("x");
		lblX.setBounds(132, 91, 46, 14);
		param.add(lblX);
		
		JTextField valeurX = new JTextField();
		lblX.setLabelFor(valeurX);
		valeurX.setBounds(144, 88, 34, 20);
		param.add(valeurX);
		valeurX.setColumns(10);
		
		JLabel lblY = new JLabel("y");
		lblY.setBounds(201, 91, 20, 14);
		param.add(lblY);
		
		JTextField valeurY = new JTextField();
		lblY.setLabelFor(valeurY);
		valeurY.setBounds(211, 88, 34, 20);
		param.add(valeurY);
		valeurY.setColumns(10);
		
		JLabel lblPixels = new JLabel("pixels");
		lblPixels.setBounds(255, 91, 46, 14);
		param.add(lblPixels);
		
		/* Configuration du tempo ? */
		JLabel lblTempo = new JLabel("Tempo");
		lblTempo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTempo.setBounds(10, 126, 46, 14);
		param.add(lblTempo);
		
		JTextField valeurTempo = new JTextField();
		valeurTempo.setBounds(66, 124, 46, 20);
		param.add(valeurTempo);
		valeurTempo.setColumns(10);
		
		JLabel lblS = new JLabel("s");
		lblS.setBounds(122, 127, 20, 14);
		param.add(lblS);
		
		/* Note de référence */
		JLabel lblNoteRfrence = new JLabel("Note r\u00E9f\u00E9rence");
		lblNoteRfrence.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNoteRfrence.setBounds(202, 126, 99, 14);
		param.add(lblNoteRfrence);
		
		String[] listeNotes = { "do", "ré", "mi", "fa", "sol", "la","si" };
		JComboBox notes = new JComboBox(listeNotes);
		notes.setBounds(300,126,50,20);
		param.add(notes);
		
		/* Configuration des seuils */
		JLabel lblSeuils = new JLabel("Seuils");
		lblSeuils.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSeuils.setBounds(10, 163, 46, 14);
		param.add(lblSeuils);
		
		/* Molecules immobiles */
		JLabel lblImmobiles = new JLabel("Immobiles");
		lblImmobiles.setBounds(36, 186, 62, 14);
		param.add(lblImmobiles);
		
		JLabel label = new JLabel("<");
		label.setBounds(132, 186, 20, 14);
		param.add(label);
		
		JTextField valeurI = new JTextField();
		valeurI.setBounds(152, 184, 34, 17);
		param.add(valeurI);
		valeurI.setColumns(10);
		
		/* Molecules confinees */
		JLabel lblConfines = new JLabel("Confin\u00E9es");
		lblConfines.setBounds(36, 205, 62, 14);
		param.add(lblConfines);
		
		JTextField valeurC1 = new JTextField();
		valeurC1.setBounds(122, 202, 34, 17);
		param.add(valeurC1);
		valeurC1.setColumns(10);
		
		JLabel label_1 = new JLabel("\u00E0");
		label_1.setBounds(166, 205, 20, 14);
		param.add(label_1);
		
		JTextField valeurC2 = new JTextField();
		valeurC2.setBounds(187, 202, 36, 17);
		param.add(valeurC2);
		valeurC2.setColumns(10);
		
		/* Molecules directionnelles */
		JLabel lblDirectionnelles = new JLabel("Directionnelles");
		lblDirectionnelles.setBounds(36, 225, 73, 14);
		param.add(lblDirectionnelles);
		
		JTextField valeurDir1 = new JTextField();
		valeurDir1.setColumns(10);
		valeurDir1.setBounds(122, 225, 34, 17);
		param.add(valeurDir1);
		
		JLabel label_2 = new JLabel("\u00E0");
		label_2.setBounds(166, 225, 20, 14);
		param.add(label_2);
		
		JTextField valeurDir2 = new JTextField();
		valeurDir2.setColumns(10);
		valeurDir2.setBounds(187, 225, 36, 17);
		param.add(valeurDir2);
		
		/* Molecules diffusives */
		JLabel lblDiffusives = new JLabel("Diffusives");
		lblDiffusives.setBounds(36, 246, 62, 14);
		param.add(lblDiffusives);
		
		JLabel label_3 = new JLabel(">");
		label_3.setBounds(132, 248, 20, 14);
		param.add(label_3);
		
		JTextField valeurDif = new JTextField();
		valeurDif.setColumns(10);
		valeurDif.setBounds(152, 246, 34, 17);
		param.add(valeurDif);

		/* Configuration du patron */
		JLabel lblPatron = new JLabel("Patron");
		lblPatron.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPatron.setBounds(20, 300, 46, 14);
		param.add(lblPatron);
		
		return param;

	}
	
	
	public void afficheParametre(){
	    this.setContentPane(this.parametres);
	    this.revalidate();
	}
	 
	public class EcouteurBouton implements ActionListener{
	    public void actionPerformed(ActionEvent clic) {
	        Fenetre.this.afficheParametre();
	    }

	}
	
	public static void main(String[] args)
	{
		Fenetre f= new Fenetre();
	}
}



 
 

 
