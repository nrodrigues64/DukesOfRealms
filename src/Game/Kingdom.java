package Game;

import java.util.List;

/**
 * <b>Kingdom est la classe représentant un royaume du jeu.</b>
 * <p>
 * Un royaume est caractérisé par les informations suivantes :
 * <ul>
 * <li>Un château principal appartenant à un joueur</li>
 * <li>Une liste de châteaux ennemis</li>
 * <li>Une liste de châteaux libre</li>
 * </ul>
 * 
 * @see Castle
 * @author Nicolas RODRIGUES et Tristan PREVOST
 *
 */

public class Kingdom {
	/**
	 * Le château principale du joueur DUC
	 * 
	 * @see Kingdom#getHome()
	 * @see Kingdom#setHome(Castle)
	 */
	private Castle home;
	/**
	 * Liste des châteaux ennemis
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
	 * 	Château du joueur
	 * @param castles
	 * 	Châteaux ennemis
	 */
	public Kingdom(Castle home, List<Castle> castles) {
		super();
		this.home = home;
		this.castles = castles;
	}
	
	/**
	 * Récupérer home
	 * @return le chateau du joueur
	 */
	public Castle getHome() {
		return home;
	}
	
	/**
	 * Changer la valeur de home
	 * @param home
	 * 	Château du joueur
	 */
	public void setHome(Castle home) {
		this.home = home;
	}
	
	/**
	 * Récupérer castles
	 * @return la liste des châteaux ennemis
	 */
	public List<Castle> getCastles() {
		return castles;
	}
	
	/**
	 * Changer la valeur de castles
	 * @param castles
	 * 	Liste des châteaux ennemis
	 */
	public void setCastles(List<Castle> castles) {
		this.castles = castles;
	}
	
	/**
	 * Récupérer freeZone
	 * @return la liste des châteaux libres
	 */
	public List<Castle> getFreeZone() {
		return freeZone;
	}
	
	/**
	 * Changer la valeurs de freeZone
	 * @param freeZone
	 * 	Liste des châteaux libres
	 */
	public void setFreeZone(List<Castle> freeZone) {
		this.freeZone = freeZone;
	}
	
	
}
