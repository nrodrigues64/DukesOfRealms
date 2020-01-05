package Game;
/**
 * <b>Order est la classe représentant un ordre d'attaque.</b>
 * <p>
 * Un ordre est caractérisée par les informations suivantes :
 * <ul>
 * <li>Un château cible</li>
 * <li>Un nombre de troupes à envoyer</li>
 * </ul>
 * @author Nicolas RODRIGUES et Tristan PREVOST
 *
 */
public class Order {
	/**
	 * Château cible
	 * @see Order#getTarget()
	 */
	Castle target;
	/**
	 * Nombre de troupe(s) qui attaque(nt)
	 * @see Order#getNbTroops()
	 */
	int nbTroops;
	
	/**
	 * Constructeur de Order
	 * @param target
	 * 	Château cible
	 * @param nbTroops
	 * 	Nombre de troupe(s) qui attaque(nt)
	 */
	public Order(Castle target, int nbTroops) {
		super();
		this.target = target;
		this.nbTroops = nbTroops;
	}
	
	/**
	 * Récupérer target
	 * @return le château cible de l'attaque
	 */
	public Castle getTarget() {
		return target;
	}
	
	/**
	 * Récupérer nbTroops
	 * @return le nombre de troupe(s) qui attanque(nt)
	 */
	public int getNbTroops() {
		return nbTroops;
	}
}
