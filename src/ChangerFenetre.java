

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ChangerFenetre extends AbstractAction{
	
	public ChangerFenetre(String texte){
		super(texte);
	};

	public void actionPerformed(ActionEvent e) {
        ControleurFenetres.recupereParametre();
        ControleurFenetres.choixUtilisateur();
    }
}
