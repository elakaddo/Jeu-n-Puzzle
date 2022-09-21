package vue.vueConsole;



import controller.TaquinListener;
import java.util.Scanner;
import model.Taquin;


/**
 * Class ConsoleVue jeu en mode console.
 * @author Mohamed Akaddar
 * Date 20 mars 2022
 */
public class ConsoleVue implements TaquinListener {

  private Taquin taquin;

  /**
   * @author Akaddar Mohamed
   * ConsoleVue : constructeur avec paramétres 
   * @param w : la largeur du taquin
   * @param h : la hauteur du taquin
   * */
  public ConsoleVue(int w, int h)
  {
    this(new Taquin(w,h));
  }
  
  /**
   * @author Akaddar Mohamed
   * ConsoleVue : constructeur avec paramétres 
   * @param tq Le modèle Taquin
   * */
  public ConsoleVue(Taquin tq)
  {
    this.taquin = tq;
    this.taquin.ajouterTaquinListener(this);
  }

  /**
   * Méthode pour lancer le jeur en mode console.
   * @author Akaddar Mohamed
   * */
  public void Jeu()
  {
    Scanner sc = new Scanner(System.in);
    //On s'intéresse pas aux événement au moment du mélange
    this.taquin.supprimerTaquinListener(this);

    //On melange le taquin :
    System.out.println("                  = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
    System.out.println("= = = = = = = = = =                        Jeu Taquin                         = = = = = = = = = = = = =");
    System.out.println("                  = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");

    System.out.println("\nTaquin à  résoudre : ");
    this.taquin.melanger(20);
    //Affichage du Taquin
    this.taquin.afficherEtat();
    

    
    //On écoute aux événements :
    this.taquin.ajouterTaquinListener(this);

    //Commencons a  jouer tant que la partie n'est pas terminée
    int i=0;
    while(!this.taquin.estRanger())
    {
      System.out.println("\n* * * * * * * * * * *  Tour : "+(++i)+" * * * * * * * * * *");
      System.out.println("Donner le prochain deplacement :\n  Haut   : 'H' \n  Bas    : 'B' \n  Gauche : 'G' \n  Droite : 'D' ");
      char c = (char) sc.next().charAt(0);

      while(!(c=='H' || c=='B' || c=='G' || c=='D'  ))
      {
        System.out.println("Mouvement invalide !!!");
        System.out.println("Re-donner le prochain deplacement :\n  Haut   : 'H' \n  Bas    : 'B' \n  Gauche : 'G' \n  Droite : 'D' ");
        c = (char) sc.next().charAt(0);
      }
      
      this.taquin.deplacer(c);
     
        

    }
    System.out.println("Wow Partie terminée !!");
  }

  /**
   * Méthode qui affiche le Taquin apres chaque mouvement
   * @author Akaddar Mohamed
   */
  @Override
  public void move()
  {
    afficherGrille();
  }

  /**
   * Méthode qui affiche le Taquin
   * @author Akaddar Mohamed
   */
  public void afficherGrille()
  {
    this.taquin.afficherEtat();
  }
}
