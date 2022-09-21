package vue.vueGraphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Class Dialogue
 * */
public class Dialogue extends AbstractDialogue{
    private JTextField widthTextField;
	private JTextField heightTextField;

	private Integer widthSelected;
	private Integer heightSelected;
	
	/**
	 * Constructeur avec 
	 * @param parent Frame principale
	 * */
	public Dialogue(JFrame parent) {
		super(parent, "Nouvelle partie");
	}

	@Override
	protected JComponent getMainUI() {
		JPanel mainContent = new JPanel();
		mainContent.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		JLabel widthLabel = new JLabel("Largeur : ");
		c.gridx = 0;
		c.gridy = 0;
		mainContent.add(widthLabel, c);

		widthTextField = new JTextField();
		widthTextField.setPreferredSize(new Dimension(200, 25));
		c.gridx = 1;
		c.gridy = 0;
		mainContent.add(widthTextField, c);

		JLabel heightLabel = new JLabel("Hauteur : ");
		c.gridx = 0;
		c.gridy = 1;
		mainContent.add(heightLabel, c);

		heightTextField = new JTextField();
		heightTextField.setPreferredSize(new Dimension(200, 25));
		c.gridx = 1;
		c.gridy = 1;
		mainContent.add(heightTextField, c);

		return mainContent;
	}

	@Override
	protected ActionListener getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			try {
				this.widthSelected = Integer.parseInt(widthTextField.getText());
			} catch(NumberFormatException ex) {
				this.widthSelected = null;
			}

			try {
				this.heightSelected = Integer.parseInt(heightTextField.getText());
			} catch(NumberFormatException ex) {
				this.heightSelected = null;
			}
			Dialogue.this.dispose();
		};
	}

	/**
	 * R�cup�re la largeur s�lectionn�e par l'utilisateur
	 * @return la largeur s�l�ction�e dans la boite de dialogue, null si la boite de dialogue n'a pas �t� valid�e
	 */
	public Integer getSelectedWidth() {
		return this.widthSelected;
	}

	/**
	 * R�cup�re la hauteur s�lectionn�e par l'utilisateur
	 * @return la hauteur s�l�ction�e dans la boite de dialogue, null si la boite de dialogue n'a pas �t� valid�e
	 */
	public Integer getSelectedHeight() {
		return this.heightSelected;
	}
}
