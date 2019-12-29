package Game;

import java.util.List;

import SampleGame.Settings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Castle extends Sprite {
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	
	private int duke;
	private int treasur;
	private int level;
	
	private ProductionUnit productionUnit;
	private Order order;
	private int door;
	public Castle(Pane layer, Image image, double x, double y) {
		super(layer,image, x, y,500);
		init();
	}
	public Castle(Pane layer, Image image, double x, double y,int duke, int treasur, int level) {
		super(layer,image, x, y,500);
		this.duke = duke;
		this.treasur = treasur;
		this.level = level;
		init();
		
	}
	private void init() {
		// calculate movement bounds of the player ship
		// allow half of the player to be outside of the screen
		minX = 0 + getHeight()*2;
		maxX = Settings.SCENE_WIDTH - getWidth();
		minY = 0;
		maxY = Settings.SCENE_HEIGHT - getHeight()*2;
	}
	public void checkBounds() {
		// vertical
		y = y < minY ? minY : y;
		y = y > maxY ? maxY : y;

		// horizontal
		x = x < minX ? minX : x;
		x = x > maxX ? maxX : x;
	}
	public int getDuke() {
		return duke;
	}
	public void setDuke(int duke) {
		this.duke = duke;
	}
	public int getTreasur() {
		return treasur;
	}
	public void setTreasur(int treasur) {
		this.treasur = treasur;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	
	public ProductionUnit getProductionUnit() {
		return productionUnit;
	}
	public void setProductionUnit(ProductionUnit productionUnit) {
		this.productionUnit = productionUnit;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public int getDoor() {
		return door;
	}
	public void setDoor(int door) {
		this.door = door;
	}
	public void checkRemovability() {
	}
	
	public void attack(Castle c, int nbTroupe)
	{
		System.out.println(this.getDuke() + " attaque " + c.getDuke() + " avec " + nbTroupe);
	}
	
	public void levelUp()
	{
		if(this.getTreasur() < 1000*(this.getLevel()+1))
		{
			System.out.println("Trésor insuffisant.");
		} else {
			int tmp = 100+50*(this.getLevel()+1);
			System.out.println("L'amélioration prendra " + tmp + "tours.");
			this.setLevel(this.getLevel()+1);
			this.setTreasur(this.getTreasur()-1000*(this.getLevel()+1));
		}
	}
}
