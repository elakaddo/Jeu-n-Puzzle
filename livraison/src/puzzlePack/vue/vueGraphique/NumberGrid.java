package vue.vueGraphique;
import java.awt.*;
import java.text.BreakIterator;

import javax.swing.*;

import javax.swing.text.AbstractDocument.BranchElement;

import model.Taquin;

/**
 * Class Numbergrid
 * */

public class NumberGrid extends AbstracTaquin{
	
	/**
	 * Constructeur avec 
	 * @param taquinGrid le modele Taquin
	 * */
    public NumberGrid(Taquin taquinGrid) {
		super(taquinGrid);
	}

	/**
	 * Dessin du taquin avec des nombres
	 * @param g l'objet avec lequel on dessine
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int cellSize = getCellSize();
		int fontSize = getCellSize()/2;
		
		for(int i = 0 ; i< getTaquinGrid().getnblignes(); i++) {
			for(int j = 0 ; j < getTaquinGrid().getnbcolonnes() ; j++) {
				if(getTaquinGrid().getSquare(j,i) != 0){
					g.setColor(new Color(255,87,51));
					g.setFont(new Font("verdana",Font.CENTER_BASELINE,fontSize));
					FontMetrics fontMetrics = g.getFontMetrics();
					String text = ""+getTaquinGrid().getSquare(j,i);
					g.drawString(text, cellSize*j + (cellSize - (fontMetrics.stringWidth(text)))/2, cellSize*(i+1)-(cellSize/3));
				}
				if(getTaquinGrid().getSquare(j,i) == 0){
					g.setColor(new Color(0,0,0));
					g.setFont(new Font("verdana",Font.CENTER_BASELINE,fontSize));
					FontMetrics fontMetrics = g.getFontMetrics();
					String text = "-";
					g.drawString(text, cellSize*j + (cellSize - (fontMetrics.stringWidth(text)))/2, cellSize*(i+1)-(cellSize/3));
				}
			}
		}

		drawSelectedSquare(g);
		drawGrid(g);
	}

    
}
