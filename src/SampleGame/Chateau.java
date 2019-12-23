package SampleGame;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;



public class Chateau extends Sprite{
	private String owner;
	private int gold;
	private int army;
	
	
	public Chateau(Pane layer, Image image, double x, double y, int health, double damage, String owner, int gold,
			int army) {
		super(layer, image, x, y, health, damage);
		this.owner = owner;
		this.gold = gold;
		this.army = army;
	}

	public String getOwner() {
		return owner;
	}
	public int getGold() {
		return gold;
	}
	public int getArmy() {
		return army;
	}
	
	
	//TODO
	public void checkRemovability() {

		if (getY() < 0) {
			remove();
		}
	}
	

}
