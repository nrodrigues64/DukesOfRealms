package Game;

import java.util.List;

/**
 * <b>Kingdom est la classe repr�sentant un royaume du jeu.</b>
 * <p>
 * Un royaume est caract�ris� par les informations suivantes :
 * <ul>
 * <li>Un ch�teau principal appartenant � un joueur</li>
 * <li>Une liste de ch�teaux ennemis</li>
 * <li>Une liste de ch�teaux libre</li>
 * </ul>
 * 
 * @see Castle
 * @author Nicolas RODRIGUES et Tristan PREVOST
 *
 */

public class Kingdom {
	/**
	 * Le ch�teau principale du joueur DUC
	 * 
	 * @see Kingdom#getHome()
	 * @see Kingdom#setHome(Castle)
	 */
	private Castle home;
	/**
	 * Liste des ch�teaux ennemis
	 * 
	 * @see Kingdom#getCastles()
	 * @see Kingdom#setCastles(List)
	 */
	private List<Castle> castles;
	/**
	 * Liste des chateaux libres
	 * 
	 * @see Kingdom#getFreeZone()
	 * @see Kingdom#setFreeZone(List)
	 */
	private List<Castle> freeZone;
	
	/**
	 * Constructeur de Kingdom
	 * @param home
	 * 	Ch�teau du joueur
	 * @param castles
	 * 	Ch�teaux ennemis
	 */
	public Kingdom(Castle home, List<Castle> castles) {
		super();
		this.home = home;
		this.castles = castles;
	}
	
	/**
	 * R�cup�rer home
	 * @return le chateau du joueur
	 */
	public Castle getHome() {
		return home;
	}
	
	/**
	 * R�cup�rer castles
	 * @return la liste des ch�teaux ennemis
	 */
	public List<Castle> getCastles() {
		return castles;
	}
		
	/**
	 * R�cup�rer freeZone
	 * @return la liste des ch�teaux libres
	 */
	public List<Castle> getFreeZone() {
		return freeZone;
	}	
}