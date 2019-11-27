package Game;

import java.util.List;

public class Royaume {
	private Castle originCastle;
	private	List<Castle> neutralCastle;
	private int freeZone;
	public Castle getOriginCastle() {
		return originCastle;
	}
	public void setOriginCastle(Castle originCastle) {
		this.originCastle = originCastle;
	}
	public List<Castle> getNeutralCastle() {
		return neutralCastle;
	}
	public void setNeutralCastle(List<Castle> neutralCastle) {
		this.neutralCastle = neutralCastle;
	}
	public int getFreeZone() {
		return freeZone;
	}
	public void setFreeZone(int freeZone) {
		this.freeZone = freeZone;
	}
	
		
}
