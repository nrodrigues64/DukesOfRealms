package Game;

import SampleGame.Settings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Troops extends Sprite {
	private String name;
	private int productionCost;
	private int productionTime;
	private double speed;
	private int health;
	private int damages;
	private double maxY;
	private double xTarget;
	private double yTarget;
	public void setxTarget(double xTarget) {
		this.xTarget = xTarget;
	}
	public void setyTarget(double yTarget) {
		this.yTarget = yTarget;
	}
	public Troops(Pane layer, Image image, double x, double y, String name, int productionCost, int productionTime,
			double speed, int health, int damages) {
		super(layer, image, x, y,50);
		this.name = name;
		this.productionCost = productionCost;
		this.productionTime = productionTime;
		this.speed = speed;
		this.health = health;
		this.damages = damages;
		maxY = Settings.SCENE_HEIGHT - image.getHeight();
	}
	public double getMaxY() {
		return maxY;
	}
	public void setMaxY(double maxY) {
		this.maxY = maxY;
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
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
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
	
	public void checkRemovability() {

		if ( !(x > (xTarget + 1) || x < (xTarget - 1)) && !(y > (yTarget + 1) || y < (yTarget - 1)) )
			
			remove();
	}
	
	public void move()
	{
		
		if(x > (xTarget + 1) || x < (xTarget - 1)) {
			if(x < this.xTarget && x + speed < this.xTarget)
			{
				x += speed;
			} else if(x < this.xTarget && x + speed > this.xTarget) {
				x++;
			}  else if(x > this.xTarget && x + speed > this.xTarget) {
				x -= speed;
			} else if(x < this.xTarget && x + speed > this.xTarget) {
				x--;
			}
		}
		if(y > (yTarget + 1) || y < (yTarget - 1)) {
			if(y < this.yTarget && y + speed < this.yTarget)
			{
				y += speed;
			} else if(y < this.yTarget && y + speed > this.yTarget) {
				y++;
			} else if(y > this.yTarget && y + speed > this.yTarget) {
				y -= speed;
			} else if(y < this.yTarget && y + speed > this.yTarget) {
				y--;
			}
		}
	}
}
