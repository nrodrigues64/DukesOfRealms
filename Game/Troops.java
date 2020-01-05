package Game;

import SampleGame.Settings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Troops extends Sprite {
	private int owner;
	private String name;
	private int productionCost;
	private int productionTime;
	private double speed;
	private int health;
	private int damages;
	private double maxY;
	private double xTarget;
	private double yTarget;
	private boolean attacking = true;
	private Castle cible;
	
	
	public void setCible(Castle c) {
		this.cible = c;
	}
	public void setxTarget(double xTarget) {
		this.xTarget = xTarget;
	}
	public void setyTarget(double yTarget) {
		this.yTarget = yTarget;
	}
	
	public boolean isAttacking() {
		return attacking;
	}
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
	
	public Troops(Pane layer, Image image, double x, double y, String name, int productionCost, int productionTime,
			double speed, int health, int damages, int owner) {
		super(layer, image, x, y,50);
		this.name = name;
		this.productionCost = productionCost;
		this.productionTime = productionTime;
		this.speed = speed;
		this.health = health;
		this.damages = damages;
		this.owner = owner;
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
		Thread t = new Thread() {
		      public void run() {
		    	  while(x > (xTarget + 1) || x < (xTarget - 1)) {
		    		  try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	if (!Main.pause) {
		    		if(x < xTarget && x + speed < xTarget)
					{
						x += speed;
						updateUI();
					} else if(x < xTarget && x + speed > xTarget) {
						x++;
						updateUI();
					}  else if(x > xTarget && x + speed > xTarget) {
						x -= speed;
						updateUI();
					} else if(x < xTarget && x + speed > xTarget) {
						x--;
						updateUI();
					}
		        }
		    	
		      }
		    	  while(y > (yTarget + 1) || y < (yTarget - 1)) {
		    		  try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	if (!Main.pause) {
		    		if(y < yTarget && y + speed < yTarget)
					{
						y += speed;
						updateUI();
					} else if(y < yTarget && y + speed > yTarget) {
						y++;
						updateUI();
					} else if(y > yTarget && y + speed > yTarget) {
						y -= speed;
						updateUI();
					} else if(y < yTarget && y + speed > yTarget) {
						y--;
						updateUI();
					}
		        }
		    	
		      }
		    	  try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	  attacking = false;
		    	  setMoved(true);
		   };
		
		/*while(x > (xTarget + 1) || x < (xTarget - 1)) {
			if(x < this.xTarget && x + speed < this.xTarget)
			{
				x += speed;
				updateUI();
			} else if(x < this.xTarget && x + speed > this.xTarget) {
				x++;
				updateUI();
			}  else if(x > this.xTarget && x + speed > this.xTarget) {
				x -= speed;
				updateUI();
			} else if(x < this.xTarget && x + speed > this.xTarget) {
				x--;
				updateUI();
			}
		}
		while(y > (yTarget + 1) || y < (yTarget - 1)) {
			if(y < this.yTarget && y + speed < this.yTarget)
			{
				y += speed;
				updateUI();
			} else if(y < this.yTarget && y + speed > this.yTarget) {
				y++;
				updateUI();
			} else if(y > this.yTarget && y + speed > this.yTarget) {
				y -= speed;
				updateUI();
			} else if(y < this.yTarget && y + speed > this.yTarget) {
				y--;
				updateUI();
			}
		}*/
		};
		if(!attacking)
		{
			attacking = true;
			t.start();
		}
	}
	
	public void move2()
	{
		if(!isMoved()){
			
			    	  if(x > (xTarget + 1) || x < (xTarget - 1)) {
			    		
			    	if (!Main.pause) {
			    		if(x < xTarget && x + speed < xTarget)
						{
							x += speed;
							updateUI();
						} else if(x < xTarget && x + speed > xTarget) {
							x++;
							updateUI();
						}  else if(x > xTarget && x + speed > xTarget) {
							x -= speed;
							updateUI();
						} else if(x < xTarget && x + speed > xTarget) {
							x--;
							updateUI();
							
						}
			        }
			    	
			      }
			    	  else if(y > (yTarget + 1) || y < (yTarget - 1)) {
			    		
			    	if (!Main.pause) {
			    		if(y < yTarget && y + speed < yTarget)
						{
							y += speed;
							updateUI();
						} else if(y < yTarget && y + speed > yTarget) {
							y++;
							updateUI();
						} else if(y > yTarget && y + speed > yTarget) {
							y -= speed;
							updateUI();
						} else if(y < yTarget && y + speed > yTarget) {
							y--;
							updateUI();
						}
			        }
			    	
			      }
			    	
			    	  else {
			    	System.out.println("test");
			    	attacking = false;
			    }
			    	  
			   
			
		}
		
	}
	
	public void makeDamages() {
		int enemy = cible.getChevaliers();
		if (enemy == 0) {
			cible.setDuke(this.owner);
		}
		else {
			cible.decChevalier();
		}
	}
}