package Game;

public class ProductionUnit {
	Troops troop;
	int am�lioration;
	int nubTurn;
	public ProductionUnit(Troops troop, int am�lioration, int nubTurn) {
		super();
		this.troop = troop;
		this.am�lioration = am�lioration;
		this.nubTurn = nubTurn;
	}
	public Troops getTroop() {
		return troop;
	}
	public void setTroop(Troops troop) {
		this.troop = troop;
	}
	public int getAm�lioration() {
		return am�lioration;
	}
	public void setAm�lioration(int am�lioration) {
		this.am�lioration = am�lioration;
	}
	public int getNubTurn() {
		return nubTurn;
	}
	public void setNubTurn(int nubTurn) {
		this.nubTurn = nubTurn;
	}
	
}
