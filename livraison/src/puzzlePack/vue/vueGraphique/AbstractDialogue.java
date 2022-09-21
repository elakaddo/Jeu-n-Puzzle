package vue.vueGraphique;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Class AbstractDialogue
 * */
public abstract class AbstractDialogue extends JDialog {
	
	/**
	 * Constructeur avec 
	 * @param parent Frame principale
	 * @param title le titre
	 * */
	public AbstractDialogue(JFrame parent, String title) {
		super(parent, title, true);
		initDialogUI(parent, title);
	}

	/**
	 * Création d'une boite de dialogue
	 * @param parent la JFrame parente à  la boite de dialogue
	 * @param title le titre de la boîte de dialogue
	 */
	private void initDialogUI(JFrame parent, String title) {

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(getMainUI(), BorderLayout.CENTER);
		panel.add(getControlButtons(), BorderLayout.PAGE_END);

		this.setContentPane(panel);

		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Création du contenu centrale de la boite de dialogue
	 * @return JComponent
	 */
	protected abstract JComponent getMainUI();

	/**
	 * Action émise lors d'un clic sur le bouton "Valider"
	 * @return l'action émise
	 */
	protected abstract ActionListener getValidButtonEventHandler();

	/**
	 * Création du layout contenant les boutons "Annuler" et "Valider"
	 * @return le layout créé
	 */
	private JComponent getControlButtons() {
		//Création du bouton "Annuler"
		JButton boutonAnnuler = new JButton("Annuler");
		boutonAnnuler.addActionListener(new ActionListener() {
			//Suppréssion de la boîte de dialogue lorsque l'on clique sur "Annuler"
			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractDialogue.this.dispose();
			}
		});

		//Création du bouton "Valider"
		JButton boutonValider = new JButton("Valider");
		boutonValider.addActionListener(getValidButtonEventHandler());

		//Création du layout des boutons  "Annuler" et "Valider"
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.LINE_AXIS));

		controlPanel.add(Box.createHorizontalGlue());
		controlPanel.add(boutonAnnuler);
		controlPanel.add(boutonValider);

		return controlPanel;
	}

}