package Game;

import java.awt.Toolkit;
import java.awt.geom.Dimension2D;
/**
 * <b>Settings est une classe qui sert à paramétrer certain aspect du jeu.</b>
 * <p>
 * Ces aspects sont :
 * <ul>
 * <li>La largeur du plateau</li>
 * <li>La hauteur du plateau</li>
 * </ul>
 * 
 * @author Nicolas RODRIGUES et Tristan PREVOST
 *
 */
public class Settings {
	/**
	 * Créer un objet Dimension2D qui permet de récupérer la taille de l'écran
	 */
	static Dimension2D screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	/**
	 * Largeur du plateau
	 */
	public static final double SCENE_WIDTH = screenSize.getWidth();
	/**
	 * Hauteur du plateau
	 */
    public static final double SCENE_HEIGHT = screenSize.getHeight() - 70;
}