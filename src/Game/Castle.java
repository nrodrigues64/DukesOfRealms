package Game;

import java.util.List;

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
	private List<Piquier> piquiers;
	private List<Chevalier> chevaliers;
	private List<Onagre> onagres;
	private ProductionUnit productionUnit;
	private Order order;
	private int door;
	public Castle(Pane layer, Image image, double x, double y) {
		super(layer,image, x, y);
		
	}
	public Castle(Pane layer, Image image, double x, double y,int duke, int treasur, int level, List<Piquier> piquiers, List<Chevalier> chevaliers,
			List<Onagre> onagres, ProductionUnit productionUnit, Order order, int door) {
		super(layer,image, x, y);
		this.duke = duke;
		this.treasur = treasur;
		this.level = level;
		this.piquiers = piquiers;
		this.chevaliers = chevaliers;
		this.onagres = onagres;
		this.productionUnit = productionUnit;
		this.order = order;
		this.door = door;
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
	
	public List<Piquier> getPiquiers() {
		return piquiers;
	}
	public void setPiquiers(List<Piquier> piquiers) {
		this.piquiers = piquiers;
	}
	public List<Chevalier> getChevaliers() {
		return chevaliers;
	}
	public void setChevaliers(List<Chevalier> chevaliers) {
		this.chevaliers = chevaliers;
	}
	public List<Onagre> getOnagres() {
		return onagres;
	}
	public void setOnagres(List<Onagre> onagres) {
		this.onagres = onagres;
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
		
}
