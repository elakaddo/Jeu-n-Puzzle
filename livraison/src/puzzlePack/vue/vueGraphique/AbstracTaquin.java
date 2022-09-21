package vue.vueGraphique;

import javax.swing.*;
import java.awt.event.*;
import java.net.http.WebSocket.Listener;
import java.awt.*;

import controller.*;
import model.*;

/**
 * Class AbstracTaquin
 * */
public class AbstracTaquin extends JPanel implements TaquinListener,MouseListener
{
	/** le modele Taquin*/
   	private Taquin taquinGrid;
   	
   	/** indique la codonn�e x de la case survol�e*/
	private int xMouse;
	/** indique la codonn�e y de la case survol�e*/
	private int yMouse;

	/** indique la codonn�e x de la case cliqu�e*/
	private int mousePressedX;
	/** indique la codonn�e y de la case cliqu�e*/
	private int mousePressedY;

	/**
	 * Constructeur avec 
	 * @param taquinGrid le mod�le Taquin 
	 * */
	public AbstracTaquin(Taquin taquinGrid) {
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {

				xMouse = e.getX();
				yMouse = e.getY();

				repaint();
			}
			@Override
			public void mouseDragged(MouseEvent e) {

			}
		});
		addMouseListener(this);

		setFocusable(true);

		this.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent k) {
			}

			public void keyPressed(KeyEvent k) {
				if(k.getKeyCode() == KeyEvent.VK_UP){
					
					AbstracTaquin.this.taquinGrid.deplacer('B');
				}
				else if(k.getKeyCode() ==  KeyEvent.VK_LEFT){
					AbstracTaquin.this.taquinGrid.deplacer('D');
				}
				else if(k.getKeyCode() == KeyEvent.VK_DOWN){
					AbstracTaquin.this.taquinGrid.deplacer('H');
				}
				else if(k.getKeyCode() == KeyEvent.VK_RIGHT){
					AbstracTaquin.this.taquinGrid.deplacer('G');
				}
				if(k.getKeyCode() == KeyEvent.VK_ENTER)
				{
					AbstracTaquin.this.taquinGrid.randomGrid();

				}
				repaint();
			}

			public void keyReleased(KeyEvent k) {
			}
		});

		this.taquinGrid = taquinGrid;
		this.taquinGrid.ajouterTaquinListener(this);
	}

	/**
	 * R�cup�re la taille d'une case
	 * @return la taille de la case
	 */
	protected int getCellSize() {
		return Math.min(getHeight() / getTaquinGrid().getnblignes(), getWidth() / getTaquinGrid().getnbcolonnes());
	}

	/**
	 * Dessine la grille
	 * @param g l'objet avec lequel on dessine
	 */
	protected void drawGrid(Graphics g) {
		g.setColor(new Color(93,83,94));
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(7f, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND));
		g2.setPaint(Color.BLACK);
		g2.setBackground(Color.WHITE);

		int cellSize = getCellSize();

		for(int i = 0 ; i < getTaquinGrid().getnblignes() + 1; i++) {
			for(int j = 0 ; j < getTaquinGrid().getnbcolonnes() + 1 ; j++) {
				g2.drawLine(j * cellSize, 0, j * cellSize, getTaquinGrid().getnblignes() * cellSize);
			}
			g2.drawLine(0, i * cellSize, getTaquinGrid().getnbcolonnes() * cellSize, i * cellSize);
		}
		
	}

	/**
	 * Dessine la case selectionnee
	 * @param g l'objet avec lequel on dessine
	 */
	protected void drawSelectedSquare(Graphics g) {
		int cellSize = getCellSize();

		// Passage en alpha pour la couleur
		Graphics2D g2d = (Graphics2D) g;
		Composite originalComposite = g2d.getComposite();

		float alpha = 0.50f;
		AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		g2d.setComposite(composite);

		g2d.setPaint(Color.black);

		int mouseCaseX = getXMouse() / cellSize;
		int mouseCaseY = getYMouse() / cellSize;
		if(isCaseValid(mouseCaseX, mouseCaseY))
			g2d.fillRect(mouseCaseX * cellSize, mouseCaseY * cellSize, cellSize, cellSize);

		g2d.setComposite(originalComposite);
	}

	/**
	 * V�rifie si la case peut �tre deplacee
	 *	@param x la coordonn�e x de la case
	 *	@param y la coordonn�e y de la case
	 *	@return true si la case peut �tre deplac�e, sinon false
	 */
	protected boolean isCaseValid(int j, int i) {
		int [] coord=this.taquinGrid.posCaseVide();
		int xPosVide = coord[0];
		int yPosVide = coord[1];

		return (j == xPosVide + 1 || j == xPosVide - 1) && i == yPosVide && j < taquinGrid.getnbcolonnes()
			|| (i == yPosVide + 1 || i == yPosVide - 1) && j == xPosVide && i< taquinGrid.getnblignes();
	}

	/**
	 * R�cup�re les coordonn�es de la case selectionn�e
	 * @param e l'�v�nement clic de la souris
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		this.mousePressedX = e.getX()/getCellSize();
		this.mousePressedY = e.getY()/getCellSize();
		//JOptionPane.showMessageDialog(null,"CC"+mousePressedX+","+mousePressedY);

	}

	/**
	 * Deplace la case selectionn�e apres relachement de la souris
	 * @param e l'evenement clic de la souris relach�e
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		int cellSize = getCellSize();

		int mouseReleasedX = e.getX()/cellSize;
		int mouseReleasedY = e.getY()/cellSize;

		if (mouseReleasedX == this.mousePressedX && mouseReleasedY == this.mousePressedY){

			int mouseCaseX = getXMouse() / cellSize;
			int mouseCaseY = getYMouse() / cellSize;
			int [] coord=this.taquinGrid.posCaseVide();
			int xPosVide = coord[0];
			int yPosVide = coord[1];
			
			//D�placement de la case
			if(isCaseValid(mouseCaseX, mouseCaseY)) {
				if(mouseCaseX == xPosVide +1)
				{
					this.taquinGrid.deplacer('D');
				}
				else if(mouseCaseX == xPosVide - 1)
				{
					this.taquinGrid.deplacer('G');}
				else if(mouseCaseY == yPosVide - 1)
				{
					this.taquinGrid.deplacer('H');}
				else if(mouseCaseY == yPosVide + 1)
				{
					this.taquinGrid.deplacer('B');}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	/**
	 * Redessine l'affichage apr�s un mouvement
	 */

	@Override
	public void move() {
		this.repaint();
	}

	/**
	 *	Change le mod�le du taquin
	 *	@param taquinGrid le taquin
	 */
	public void setTaquinGrid(Taquin taquinGrid) {
		this.taquinGrid.supprimerTaquinListener(this);
		this.taquinGrid = taquinGrid;
		this.taquinGrid.ajouterTaquinListener(this);
	}

	/**
	 * R�cup�re le modele du taquin
	 * @return le modele du taquin
	 */
	public Taquin getTaquinGrid() {
		return this.taquinGrid;
	}

	/**
	 * R�cup�re la position X de la souris
	 * @return la position X de la souris
	 */
	public int getXMouse() {
		return this.xMouse;
	}

	/**
	 * R�cup�re la position Y de la souris
	 * @return la position Y de la souris
	 */
	public int getYMouse() {
		return this.yMouse;
	}
}