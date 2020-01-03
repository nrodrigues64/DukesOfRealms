package Game;

public class Order {
	Castle target;
	int nbTroops;
	
	public Order(Castle target, int nbTroops) {
		super();
		this.target = target;
		this.nbTroops = nbTroops;
	}
	public Castle getTarget() {
		return target;
	}
	public void setTarget(Castle target) {
		this.target = target;
	}
	public int getNbTroops() {
		return nbTroops;
	}
	public void setNbTroops(int nbTroops) {
		this.nbTroops = nbTroops;
	}
	
}
