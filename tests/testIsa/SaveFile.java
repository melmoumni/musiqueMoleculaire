package testIsa;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveFile extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnSauvegarder;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SaveFile frame = new SaveFile();
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
	public SaveFile() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		 btnSauvegarder = new JButton("Sauvegarder");
		 btnSauvegarder.addActionListener(this);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(51)
					.addComponent(btnSauvegarder)
					.addContainerGap(284, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(217, Short.MAX_VALUE)
					.addComponent(btnSauvegarder)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSauvegarder){
			JFileChooser chooser = new JFileChooser();
	    	FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt", "trc");
	    	chooser.setFileFilter(filter);
	    	int returnVal = chooser.showSaveDialog(this);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            Midi.saveMidi(chooser.getSelectedFile().getPath()); 
	        }
		}
		
	}
}
