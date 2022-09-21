package vue.vueGraphique;
import model.*;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.TaquinListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.awt.Image;

/**
 * Classe FentrePrincipale Le jeu en mode graphique
 * */
public class FentrePrincipale extends JFrame implements TaquinListener{
    
		/** taquin le modèle*/
        public  Taquin taquin;
        
        private static final String NUMBER_GRID = "NUMBER";
	    private static final String IMAGE_GRID = "IMAGE";

	    private static final int DEFAULT_NBCOL = 3;
	    private static final int DEFAULT_NBLI = 3;
	    
	    /** imageGride*/
	    private ImageGrid imageGrid;
	    /** numberGride*/
	    private NumberGrid numberGrid;
	    
	    /**
	     * Constructeur 
	     * */
        public FentrePrincipale()
        {
			
            createMenuBar();
            createMainUI();
			
        }
        /**
    	 *Constructeur avec
    	 * @param title titre de la Frame
    	 * */
        public FentrePrincipale(String title)
        {
                this.setSize(550,600);
                setTitle(title);
                //pack();
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                setResizable(false);
                createMenuBar();
                createMainUI();
                setVisible(true);
        }
        private void createMenuBar() 
	{
		JMenuBar menuBar = new JMenuBar();

		JMenu menuFichier = new JMenu("Jeu");
		JMenu menuAffichage = new JMenu("Mode");
		JMenuItem itemNewGame = new JMenuItem("Nouvelle partie");
		menuFichier.add(itemNewGame);
		

		//Evénement de l'item "nouvelle partie"
		itemNewGame.addActionListener((ActionEvent e) -> {
			Dialogue dialog = new Dialogue(this);
			Integer taquinGridWidth = dialog.getSelectedWidth();
			Integer taquinGridHeight = dialog.getSelectedHeight();

			//On vérifie les tailles rentrées
			if(taquinGridWidth != null && taquinGridHeight != null) {
				if(taquinGridWidth >=2 && taquinGridWidth <22 && taquinGridHeight >=2 && taquinGridHeight <22) {
					newGame(taquinGridWidth, taquinGridHeight);
				} else{
					JOptionPane.showMessageDialog(this, "verifier La largeur et la hauteur de la grille ", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		//Création de la sous-categorie "Mode image" de "Mode"
		JCheckBoxMenuItem itemGridType = new JCheckBoxMenuItem("Mode image");
		itemGridType.setState(true);
		menuAffichage.add(itemGridType);
		//Evénement de l'item "mode image"
		itemGridType.addItemListener((ItemEvent e) -> {
			if(itemGridType.getState())
				showTaquinGrid(IMAGE_GRID);
			else
				showTaquinGrid(NUMBER_GRID);
		});
		JMenuItem itemChangerImage = new JMenuItem("Charger une image");
		menuAffichage.add(itemChangerImage);
		itemChangerImage.addActionListener((ActionEvent e) -> {

			FileNameExtensionFilter imagesFilter = new FileNameExtensionFilter("Fichiers image", "png", "bmp", "jpg", "jpeg");

			//Choix d'une image dans les dossiers de l'utilisateur
			JFileChooser imageChoosed = new JFileChooser();
			imageChoosed.setDialogTitle("Choisir une image");
			imageChoosed.setAcceptAllFileFilterUsed(false);
			imageChoosed.setFileFilter(imagesFilter);

			int returnVal = imageChoosed.showOpenDialog(FentrePrincipale.this);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				//On éssaye de lire le fichier choisi par l'utilisateur
				try {
					Image image = ImageIO.read(new File(imageChoosed.getSelectedFile().getPath()));
					imageGrid.setImage(image);
				} catch (IOException ex) {
					//Renvoie un message d'erreur si le fichier n'est pas valide
					JOptionPane.showMessageDialog(FentrePrincipale.this, "Impossible d'ouvrir le fichier", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	
		menuBar.add(menuFichier);
		menuBar.add(menuAffichage);
		setJMenuBar(menuBar);
	}
    /**
	 * Création du contenu de la fenêtre de jeu
	 */
	private void createMainUI() {
		setLayout(new CardLayout());
		newGame(DEFAULT_NBCOL, DEFAULT_NBLI);

		this.add(imageGrid, IMAGE_GRID);
		this.add(numberGrid, NUMBER_GRID);
        //showTaquinGrid(NUMBER_GRID);
		showTaquinGrid(IMAGE_GRID);
	}

	/**
	 * Lancement d'une nouvelle partie
	 * @param w la largeur de la grille du taquin
	 * @param h la hauteur de la grille du taquin
	 */
	protected void newGame(int w, int h) {
		this.taquin = new Taquin(w, h);
		this.taquin.ajouterTaquinListener(this);

		//Creation du mode "Nombre" s'il existe pas
		if (numberGrid == null) {
			numberGrid = new NumberGrid(this.taquin);

		} else {
			numberGrid.setTaquinGrid(this.taquin);
		}
		if (imageGrid == null) {
			ImageIcon myimage=new ImageIcon("res/disney.jpg"); 
            Image img=myimage.getImage();
            imageGrid = new ImageGrid(this.taquin,img);
		} else {
			
			imageGrid.setTaquinGrid(this.taquin);
		}

		imageGrid.repaint();
		numberGrid.repaint();
	}

	/**
	 * Affichage de la bonne grille en fonction qu'il s'agisse du mode "Image" ou "Nombre"
	 * @param gridType le type d'affichage
	 */
	private void showTaquinGrid(String gridType)
	{
		CardLayout cl = (CardLayout)(getContentPane().getLayout());
		cl.show(getContentPane(), gridType);

		if(gridType == NUMBER_GRID) {
			numberGrid.requestFocus();
		} else if(gridType == IMAGE_GRID) {
			imageGrid.requestFocus();
		}
	}
	@Override
	public void move() {
        if (this.taquin.estRanger() == true){
			imageGrid.repaint();
			numberGrid.repaint();

			int answer = JOptionPane.showConfirmDialog(this,"Vous avez gagne ! Voulez vous rejouer ?", "VICTOIRE !", JOptionPane.YES_NO_OPTION);
			if(answer == JOptionPane.YES_OPTION) {
				this.taquin.randomGrid();
			}
		}
	}

        
    /**
     * méthode qui lance le jeu en mode graphique
     * @param  args arguments
     * */    
    public static void main (String [] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                
                FentrePrincipale f = new FentrePrincipale("Jeu Taquin");
               
            }
        });
    }
    
}
