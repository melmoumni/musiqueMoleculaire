

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ChangerFenetre extends AbstractAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
