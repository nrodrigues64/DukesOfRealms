package Game;

public class Troops {
	private String name;
	private int productionCost;
	private int productionTime;
	private int speed;
	private int health;
	private int damages;
	
	public Troops(String name, int productionCost, int productionTime, int speed, int health, int damages) {
		super();
		this.name = name;
		this.productionCost = productionCost;
		this.productionTime = productionTime;
		this.speed = speed;
		this.health = health;
		this.damages = damages;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getProductionCost() {
		return productionCost;
	}
	public void setProductionCost(int productionCost) {
		this.productionCost = productionCost;
	}
	public int getProductionTime() {
		return productionTime;
	}
	public void setProductionTime(int productionTime) {
		this.productionTime = productionTime;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getDamages() {
		return damages;
	}
	public void setDamages(int damages) {
		this.damages = damages;
	}
	
	
}
