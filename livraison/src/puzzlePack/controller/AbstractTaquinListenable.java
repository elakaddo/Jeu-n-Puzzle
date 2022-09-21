package controller;

/**
 * Interface AbstractTaquinListenable
 * @author Mohamed Akaddar
 * Date 18 mars 2022
 */

public interface AbstractTaquinListenable{
	
	/**
	 * M�thode qui ajoute un ecouteur � la liste des listeners.
	 *@author Mohamed Akaddar
	 * @param listener TaquinListener a ajouter
	 */
  public void ajouterTaquinListener(TaquinListener listener);

  /**
     *M�thode qui supprime un ecouteur dans la liste des listeners.
	 *@author Mohamed Akaddar
	 * @param listener TaquinListener a supprimer
	 */
  public void supprimerTaquinListener(TaquinListener listener);

  /**
     * M�thode qui effectue un mouvement pour chaque listener ajouter a la liste des listeners.
	 *@author Mohamed Akaddar
	 */
  public void infoEstDeplace();
}
