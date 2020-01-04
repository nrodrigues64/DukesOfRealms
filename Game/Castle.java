package Game;

import java.util.ArrayList;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import java.time.Duration;
import java.time.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;







import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Castle extends Sprite {
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	private int num ;
	
	private int duke;
	private int treasur;
	private int level;
	
	private ProductionUnit productionUnit;
	private Order order;
	private int door;
	private int chevaliers = 0;
	private boolean isAttacking = false;
	private List<Troops> troops = new ArrayList<>();
	private int waitinglist = 0; //le nombre d'unit� en attente de formation
	private boolean formation = false;
	public Castle(Pane layer, Image image, double x, double y) {
		super(layer,image, x, y,500);
		init();
		addToLayer();
	}
	public Castle(Pane layer, Image image, double x, double y,int duke, int treasur, int level, int num) {
		super(layer,image, x, y,500);
		this.duke = duke;
		this.treasur = treasur;
		this.level = level;
		this.num = num;
		init();
		addToLayer();
		
	}
	public Castle(Pane layer, Image image, double x, double y,int duke, int treasur, int level) {
		super(layer,image, x, y,500);
		this.duke = duke;
		this.treasur = treasur;
		this.level = level;
		num = 0;
		init();
		addToLayer();
		
	}
	private void init() {
		Troops t = new Troops(this.getLayer(), new Image(getClass().getResource("/images/knight.png").toExternalForm(), 50, 50, true, true), this.getX(), this.getY(), "Chevalier", 5, 2,1.6, 50, 20);
		troops.add(t);
		chevaliers = troops.size();
		// calculate movement bounds of the player ship
		// allow half of the player to be outside of the screen
		minX = 0 + getWidth()*2;
		maxX = Settings.SCENE_WIDTH - getWidth()*2;
		minY = 0 + getHeight()*2;
		maxY = Settings.SCENE_HEIGHT - getHeight()*2;
	}
	
	public List<Troops> getTroops() {
		return troops;
	}
	public void setTroops(List<Troops> troops) {
		this.troops = troops;
	}
	public boolean isAttacking() {
		return isAttacking;
	}
	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}
	public void checkBounds() {
		// vertical
		y = y < minY ? minY : y;
		y = y > maxY ? maxY : y;

		// horizontal
		x = x < minX ? minX : x;
		x = x > maxX ? maxX : x;
	}
	public void createTroop()
	{
		Troops t = new Troops(this.getLayer(), new Image(getClass().getResource("/images/knight.png").toExternalForm(), 50, 50, true, true), this.getX(), this.getY(), "Chevalier", 5, 2,1.6, 50, 20);
        troops.add(t);
	}
	
	public void incChevalier() {
		waitinglist++;
		Thread t = new Thread() {
		      public void run() {
		    	  while(waitinglist>0) {
		    		  try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	if (!Main.pause) {
		    	createTroop();
		    	chevaliers++;
		        waitinglist--;}
		        }
		      formation = false;
		      }

		    };
		    if (!formation)
		    {
		    	formation = true;
		    	t.start();
		    }
		  }

	public void incWaitingList(int nb) {
		waitinglist+=nb;
		incChevalier();
	}
	public void decChevalier() {
		chevaliers--;
	}
	public int getChevaliers() {
		return chevaliers;
	}
	public void setChevaliers(int chevaliers) {
		this.chevaliers = chevaliers;
	}
	public int getWaitinglist() {
		return waitinglist;
	}
	public void setWaitinglist(int waitinglist) {
		this.waitinglist = waitinglist;
	}
	public boolean isFormation() {
		return formation;
	}
	public void setFormation(boolean formation) {
		this.formation = formation;
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
	public int getNum() {return num;}
	
	
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
		
		for(int i = 0; i < troops.size(); i++) {
			
			troops.get(i).checkRemovability();
			
		}
		if(isAttacking() && chevaliers == 0)
		{
			setAttacking(false);
		}
	}
	public void removeTroop(Troops t)
	{
		t.removeFromLayer();
		troops.remove(t);
	}
	public void attack(Castle c, int nbTroupe)
	{
		if(nbTroupe > chevaliers) {
			System.out.println("pas assez de troupe");
			return;
		}
		for(int i = 0 ; i < nbTroupe ; i++)
		{
			troops.get(i).setxTarget(c.getX());
			troops.get(i).setyTarget(c.getY());
			troops.get(i).addToLayer();
			troops.get(i).move();
		}
		int nbEnnemy = c.getChevaliers();
		while(nbEnnemy > 0 && nbTroupe > 0) {
				int h = c.getTroops().get(0).getHealth();
				int n = troops.get(nbTroupe-1).getDamages();
				c.getTroops().get(0).setHealth( c.getTroops().get(0).getHealth() - n);
				if(c.getTroops().get(0).getHealth() <= 0) {
					c.getTroops().remove(0);
					c.setChevaliers(c.getChevaliers()-1);
					nbEnnemy--;
				}
				if(h >= troops.get(nbTroupe-1).getDamages())
				{
					chevaliers--;
					nbTroupe--;
				} else {
					troops.get(nbTroupe-1).setDamages(troops.get(nbTroupe-1).getDamages() - h);
				}	
		}
		System.out.println(nbEnnemy);
		if(nbEnnemy == 0) {
			System.out.println("test");
			c.setDuke(getDuke());
		}
	}
	
	public void levelUp()
	{
		if(this.getTreasur() < 1000*(this.getLevel()+1))
		{
			System.out.println("Tr�sor insuffisant.");
		} else {
			int tmp = 100+50*(this.getLevel()+1);
			System.out.println("L'am�lioration prendra " + tmp + "tours.");
			this.setLevel(this.getLevel()+1);
			this.setTreasur(this.getTreasur()-1000*(this.getLevel()+1));
		}
	}
}