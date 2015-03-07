

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class ChangerFenetre extends AbstractAction{
	
	public ChangerFenetre(String texte){
		super(texte);
	};

	public void actionPerformed(ActionEvent e) {
        if(ControleurFenetres.recupereParametre())
        	ControleurFenetres.choixUtilisateur();
        else
        	ControleurFenetres.popupMessage();
    }
}
