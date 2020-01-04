package Game;

import java.util.List;

public class Kingdom {
	private Castle home;
	private List<Castle> castles;
	private List<Castle> freeZone;
	
	public Kingdom(Castle home, List<Castle> castles) {
		super();
		this.home = home;
		this.castles = castles;
	}
	public Castle getHome() {
		return home;
	}
	public void setHome(Castle home) {
		this.home = home;
	}
	public List<Castle> getCastles() {
		return castles;
	}
	public void setCastles(List<Castle> castles) {
		this.castles = castles;
	}
	public List<Castle> getFreeZone() {
		return freeZone;
	}
	public void setFreeZone(List<Castle> freeZone) {
		this.freeZone = freeZone;
	}
	
	
}
