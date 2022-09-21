package vue.vueGraphique;

import java.awt.*;
import java.awt.event.*;

import model.Taquin;

/**
 * Class ImageGrid
 * */
public class ImageGrid extends AbstracTaquin{
	
	/** image */
    private Image image;

    /**
	 * Constructeur avec 
	 * @param taquinGrid le modele taquin
	 * @param image Image
	 * */
	public ImageGrid(Taquin taquinGrid, Image image) {
		super(taquinGrid);
		setImage(image);
	}

	/**
	 * Dessin du taquin avec une image
	 * @param g l'objet avec lequel on dessine
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int cellSize = getCellSize();
		
		float factor = Math.min((float)image.getWidth(null) / (float)(getTaquinGrid().getnbcolonnes() * cellSize), (float)image.getHeight(null) / (float)(getTaquinGrid().getnblignes() * cellSize));

		for(int i = 0 ; i < getTaquinGrid().getnblignes() ; i++) {
			for(int j = 0 ; j < getTaquinGrid().getnbcolonnes() ; j++) {
				int caseValue = getTaquinGrid().getSquare(j, i);
				if(caseValue == 0)
					continue;

				int xCaseValue = (caseValue-1) % getTaquinGrid().getnbcolonnes();
				int yCaseValue = (caseValue-1) / getTaquinGrid().getnbcolonnes();

				g.drawImage(image,
					j * cellSize, i * cellSize, (j + 1) * cellSize, (i+ 1) * cellSize,
					(int)((yCaseValue * cellSize) * factor), (int)((xCaseValue * cellSize) * factor), (int)(((yCaseValue + 1) * cellSize) * factor), (int)(((xCaseValue + 1) * cellSize) * factor),
					null);
			}
		}

		drawSelectedSquare(g);
		drawGrid(g);
	}

	/**
	 * Récupère l'image
	 * @return l'image du taquin
	 */
	public Image getImage() {
		return this.image;
	}

	/**
	 * Modifie l'image
	 * @param image la nouvelle image
	 */
	public void setImage(Image image) {
		this.image = image;
		this.repaint();
	}
    
}
