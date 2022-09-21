package model;
import java.awt.*;
import javax.swing.*;

/**
 * Class Tuile
 * */
public class Tuile
{
  
	/**
	 * @param chemin de l'image
	 * @return image
	 * */
   public ImageIcon lireImage(String chemin)
   {
    Toolkit tk=Toolkit.getDefaultToolkit();
    Image src1=tk.getImage(chemin);
    return new ImageIcon(src1);
   }
   
   /**
	 * @param chemin de l'image
	 * @param width la largeur
	 * @param height la hauteur
	 * @return image
	 * */
   public ImageIcon lireImageTaille(String chemin,int width,int height )
   {
      Toolkit tk=Toolkit.getDefaultToolkit();
      Image src1=tk.getImage(chemin);
      Image src2=src1.getScaledInstance(width, height, Image.SCALE_DEFAULT);
      return new ImageIcon(src2);

    }
    


}
