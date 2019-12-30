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
	
	private int duke;
	private int treasur;
	private int level;
	
	private ProductionUnit productionUnit;
	private Order order;
	private int door;
	private int chevaliers = 0;
	private int waitinglist = 10; //le nombre d'unité en attente de formation
	private boolean formation = false;

	
	
	public Castle(Pane layer, Image image, double x, double y) {
		super(layer,image, x, y,500);
		
	}
	public Castle(Pane layer, Image image, double x, double y,int duke, int treasur, int level) {
		super(layer,image, x, y,500);
		this.duke = duke;
		this.treasur = treasur;
		this.level = level;
		
		
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
	public int getChevalier() {
		return chevaliers;
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
		        chevaliers++;
		        waitinglist--;}
		        }
		      formation = false;
		      }
		      
		    };
		    if (!formation)
		    	{formation = true;
		    	t.start();}
		  }

	public void incWaitingList(int nb) {
		waitinglist+=nb;
		incChevalier();
	}
	public void decChevalier() {
		chevaliers--;
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
