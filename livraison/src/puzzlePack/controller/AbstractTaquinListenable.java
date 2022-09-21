package controller;

/**
 * Interface AbstractTaquinListenable
 * @author Mohamed Akaddar
 * Date 18 mars 2022
 */

public interface AbstractTaquinListenable{
	
	/**
	 * Méthode qui ajoute un ecouteur à la liste des listeners.
	 *@author Mohamed Akaddar
	 * @param listener TaquinListener a ajouter
	 */
  public void ajouterTaquinListener(TaquinListener listener);

  /**
     *Méthode qui supprime un ecouteur dans la liste des listeners.
	 *@author Mohamed Akaddar
	 * @param listener TaquinListener a supprimer
	 */
  public void supprimerTaquinListener(TaquinListener listener);

  /**
     * Méthode qui effectue un mouvement pour chaque listener ajouter a la liste des listeners.
	 *@author Mohamed Akaddar
	 */
  public void infoEstDeplace();
}
