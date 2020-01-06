package Game;
/**
 * <b>Order est la classe repr�sentant un ordre d'attaque.</b>
 * <p>
 * Un ordre est caract�ris�e par les informations suivantes :
 * <ul>
 * <li>Un ch�teau cible</li>
 * <li>Un nombre de troupes � envoyer</li>
 * </ul>
 * @author Nicolas RODRIGUES et Tristan PREVOST
 *
 */
public class Order {
	/**
	 * Ch�teau cible
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
	 * 	Ch�teau cible
	 * @param nbTroops
	 * 	Nombre de troupe(s) qui attaque(nt)
	 */
	public Order(Castle target, int nbTroops) {
		super();
		this.target = target;
		this.nbTroops = nbTroops;
	}
	
	/**
	 * R�cup�rer target
	 * @return le ch�teau cible de l'attaque
	 */
	public Castle getTarget() {
		return target;
	}
	
	/**
	 * R�cup�rer nbTroops
	 * @return le nombre de troupe(s) qui attanque(nt)
	 */
	public int getNbTroops() {
		return nbTroops;
	}
}
