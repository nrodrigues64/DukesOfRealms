package Game;

public class ProductionUnit {
	Troops troop;
	int amélioration;
	int nubTurn;
	public ProductionUnit(Troops troop, int amélioration, int nubTurn) {
		super();
		this.troop = troop;
		this.amélioration = amélioration;
		this.nubTurn = nubTurn;
	}
	public Troops getTroop() {
		return troop;
	}
	public void setTroop(Troops troop) {
		this.troop = troop;
	}
	public int getAmélioration() {
		return amélioration;
	}
	public void setAmélioration(int amélioration) {
		this.amélioration = amélioration;
	}
	public int getNubTurn() {
		return nubTurn;
	}
	public void setNubTurn(int nubTurn) {
		this.nubTurn = nubTurn;
	}
	
}
