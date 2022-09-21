package model;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

import controller.AbstractTaquinListenable;
import controller.TaquinListener;
import vue.vueGraphique.AbstracTaquin;


/**
 * Class Taquin Le modèle
 * */
public class Taquin implements AbstractTaquinListenable{


	private Integer[][] board;
	private Integer[][] etatInitiale;
	private int nligne;
	private int nColonne;
	private int[] crdVide = new int[2];

	private List<TaquinListener> tqListeners;
	private boolean informer;
	
	/**
	 * @return une copie de la grille du taquin.
	 * */
	public Integer[][] CopyBoard(){
		Integer[][] d=new Integer[this.nColonne][this.nligne];
		for(int i=0;i<this.nligne;i++)
			for(int j=0;j<this.nColonne;j++)
				d[j][i]=this.board[j][i];
		return d;


	}
	/**
	 * Création de la grille du taquin
	 * @param m nombre de colonnes
	 * @param n nombre de ligne
	 */
	public Taquin(int m,int n)
	{  this.nligne=n;
	this.nColonne=m;
	int k=0;
	this.board= new Integer[m][n];
	this.etatInitiale=new Integer[m][n];
	//Affectation d'un chiffre Ã  chaque case du Taquin
	for(int i=0;i<n;i++)
		for(int j=0;j<m;j++)
		{
			this.board[j][i]=i+j*this.nColonne+1;	 
			k=this.board[j][i];
		}
	//Création de la case vide
	this.board[this.nColonne-1][this.nligne-1]=0;
	this.etatInitiale=this.CopyBoard();
	crdVide[0]=this.nColonne-1;
	crdVide[1]=this.nligne-1;

	this.tqListeners = new ArrayList<>();
	}
	/**
	 * RécupÃ¨re le nombre des lignes
	 * @return le nombre des lignes
	 */
	public int getnblignes()
	{
		return this.nligne;
	} 
	/**
	 * RécupÃ¨re le nombre de colonnes
	 * @return le nombre de colonnes
	 */
	public int getnbcolonnes()
	{
		return this.nColonne;
	}
	/**
	 * RécupÃ¨re un tableau contient 2 éléments correspondent au coordonnées de la case vide
	 * @return tableau indique la postion de la case vide
	 */
	public int[] posCaseVide()
	{
		return this.crdVide;
	}
	/**
	 * l'affichage de notre grille
	 */
	public void afficherEtat()
	{
		for(int i=0;i<nligne;i++)
		{ for(int j=0;j<nColonne;j++)
			System.out.print("       "+this.board[j][i]+" ");
		System.out.println("\n");
		}
		System.out.println("\nCase vide ( "+crdVide[0]+ "," +crdVide[1]+" )");
	}
	/**
	 * Déplacement d'une case Ã  gauche
	 * @return true si le mouvement Ã  été effectué, false sinon
	 */
	public boolean gauche()
	{  Integer a=this.board[crdVide[0]][crdVide[1]];
	if(crdVide[0]!=0)
	{this.board[crdVide[0]][crdVide[1]]= this.board[crdVide[0]-1][crdVide[1]];
	this.board[crdVide[0]-1][crdVide[1]]=a;
	crdVide[0]= crdVide[0]-1;
	return true;
	}
	return false;
	}
	/**
	 * Déplacement d'une case Ã  droite
	 * @return true si le mouvement Ã  été effectué, false sinon
	 */
	public boolean droite()
	{  Integer a=this.board[crdVide[0]][crdVide[1]];
	if(crdVide[0]!=this.nColonne-1)
	{this.board[crdVide[0]][crdVide[1]]= this.board[crdVide[0]+1][crdVide[1]];
	this.board[crdVide[0]+1][crdVide[1]]=a;
	crdVide[0]= crdVide[0]+1;
	return true;
	}
	return false;
	}
	/**
	 * Déplacement d'une case en haut
	 * @return true si le mouvement Ã  été effectué, false sinon
	 */
	public boolean haut()
	{  Integer a=this.board[crdVide[0]][crdVide[1]];
	if(crdVide[1]!=0)
	{this.board[crdVide[0]][crdVide[1]]= this.board[crdVide[0]][crdVide[1]-1];
	this.board[crdVide[0]][crdVide[1]-1]=a;
	crdVide[1]= crdVide[1]-1;
	return true;
	}
	return false;
	}

	/**
	 * Déplacement d'une case en bas
	 * @return true si le mouvement Ã  été effectué, false sinon
	 */
	public boolean bas()
	{  Integer a=this.board[crdVide[0]][crdVide[1]];
	if(crdVide[1]!=this.nligne-1)
	{
		this.board[crdVide[0]][crdVide[1]]= this.board[crdVide[0]][crdVide[1]+1];
		this.board[crdVide[0]][crdVide[1]+1]=a;
		crdVide[1]= crdVide[1]+1;
		return true;
	}
	return false;
	}

	/**
	 * Déplacement d'une case selon la direction
	 * @param c une direction donnée
	 * @return true si le mouvement Ã  été effectué, false sinon
	 */
	public boolean deplacer(char c)
	{
		Boolean b;
		switch (c) {
		case 'H' :
			b=this.haut();
			infoEstDeplace();
			return b;
		case 'G' :
			b=this.gauche();
			infoEstDeplace();
			return b;
		case 'D' :
			b=this.droite();
			infoEstDeplace();
			return b;
		case 'B' : 
			b=this.bas();
			infoEstDeplace();
			return b;

		default : 
			return false;
		}
	}
	/**
	 * génére un mélange
	 */
	public void randomGrid() {
		melanger(nColonne * nligne * 100);
	}
	
	/**
	 * mélange aléatoirement  selon qu'est ce que Ã  tiré aléatoirement
	 * @param n le nombre de fois de melange
	 */
	public void melanger(int n)
	{
		this.informer = false;
		char[] direc= {'H','B','G','D'};
		Random r =new Random();
		boolean k;
		int randome;
		for(int j=0;j<n;j++)

		{  do
		{  
			randome=r.nextInt(4);
			k=deplacer(direc[randome]);

		}while(k==false);

		}
		this.informer = true; 

	}

	/**
	 * vérifie si la grille est devenu un son état initial
	 * @return true si c'est vrai sinon false
	 */
	public boolean estRanger()
	{
		for(int i=0;i<nligne;i++)
			for(int j=0;j<nColonne;j++)
				if(this.etatInitiale[j][i]!= this.board[j][i])
					return false;
		return true;

	}
	/**
	 * Récupére la valeur de la case
	 * @param j la position j de la case
	 * @param i la position i de la case
	 * @return la valeur de la case
	 */
	public Integer getSquare(int j, int i) {
		if (j > this.nColonne-1 || i > this.nligne-1 || i < 0 || j < 0) {
			return null;
		}

		return board[j][i];
	}


	/**
	 *Méthode qui ajoute un écouteur à la liste des listeners.
	 */
	@Override
	public void ajouterTaquinListener(TaquinListener listener) {
		tqListeners.add(listener);
	}
	/**
	 * Méthode qui supprime un écouteur dans la liste des listeners.
	 */
	@Override
	public void supprimerTaquinListener(TaquinListener listener) {
		tqListeners.remove(listener);
	}
	
	/**
	 * Méthode qu'éffectue un mouvement pour chaque listener ajouté a la liste des listeners.
	 */
	@Override
	public void infoEstDeplace()
	{
		if(!this.informer)
			return;
		for(TaquinListener tq : tqListeners)
			tq.move();
	}
	
	/**
	 * Redessine l'affichage après un mouvement
	 * @param abstracTaquin TODO
	 */

	public void Deplace(AbstracTaquin abstracTaquin) {
		abstracTaquin.repaint();
	}

}
