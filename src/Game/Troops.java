package Game;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;



/**
 * <b>Troops est la classe repr�sentant une troupe du jeu.</b>
 * <p>
 * Une troupe est caract�ris�e par les informations suivantes :
 * <ul>
 * <li>Une vitesse de d�placement</li>
 * <li>Un nombre de dommages</li>
 * <li>H�rite des m�mes attributs que Sprite</li>
 * </ul>
 * @see Sprite
 * @author Nicolas RODRIGUES et Tristan PREVOST
 *
 */
public class Troops extends Sprite {
	/**
	 * Nombre de point de vie d'une troupe
	 * 
	 * @see Troops#getHealth()
	 * @see Troops#setHealth(int)
	 */
	private int health;
	/**
	 * Vitesse de d�placement de la troupe
	 * 
	 * @see Troops#getSpeed()
	 * @see Troops#setSpeed(double)
	 */
	private double speed;
	/**
	 * Force de la troupe
	 * 
	 * @see Troops#getDamages()
	 * @see Troops#setDamages(int
	 */
	private int damages;
	/**
	 * Coordonn�es x de la cible de la troupe
	 * 
	 * @see Troops#setxTarget(double)
	 */
	private double xTarget;
	/**
	 * Coordonn�es y de la cible de la troupe
	 * 
	 * @see Troops#setyTarget(double)
	 */
	private double yTarget;
	/**
	 * Bool�en qui indique si la troupe attaque
	 * 
	 * @see Troops#isAttacking()
	 * @see Troops#setAttacking(boolean)
	 */
	private boolean attacking = true;
	/**
	 * 
	 */
	private int owner;
	/**
	 * 
	 */
	private Castle cible;
	/**
	 * Changer xTarget
	 * @param xTarget
	 * 	Coordonn�es x de la cible
	 */
	public void setxTarget(double xTarget) {
		this.xTarget = xTarget;
	}
	
	/**
	 * Changer yTarget
	 * @param yTarget
	 * 	Coordonn�es y de la cible
	 */
	public void setyTarget(double yTarget) {
		this.yTarget = yTarget;
	}
	
	/**
	 * 
	 * @param c
	 */
	public void setCible(Castle c) {
		this.cible = c;
	}
	
	/**
	 * R�cup�rer attacking
	 * @return attacking, true si la troupe attaque false sinon 
	 */
	public boolean isAttacking() {
		return attacking;
	}
	
	/**
	 * Changer la valeur de attacking
	 * @param attacking
	 * 	Bool�en qui indique si la troupe attaque
	 */
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
	
	/**
	 * Constructeur de Troops
	 * @param layer
	 * 	Plan
	 * @param image
	 * 	Image affich�e de la troupe
	 * @param x
	 * 	Coordonn�es x de la troupe
	 * @param y
	 * 	Coordonn�es y de la troupe
	 * @param speed
	 * 	Vitesse de d�placement de la troupe
	 * @param health
	 * 	Sant� de la troupe
	 * @param damages
	 * 	Force de la troupe
	 */
	public Troops(Pane layer, Image image, double x, double y, double speed, int health, int damages, int owner) {
		super(layer, image, x, y);
		this.health = health;
		this.speed = speed;
		this.damages = damages;
		this.owner = owner;
	}
	
	/**
	 * R�cup�rer health
	 * @return le nombre de point de vie actuel de la troupe
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Changer la valeur de health
	 * @param health
	 * 	Sant� de la troupe
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * R�cup�rer speed
	 * @return la vitesse de d�placement de la troupe
	 */
	public double getSpeed() {
		return speed;
	}
	
	/**
	 * Changer la valeur de speed
	 * @param speed
	 * 	Vitesse de la troupe
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	/**
	 * R�cup�rer damages
	 * @return la force de la troupe
	 */
	public int getDamages() {
		return damages;
	}
	
	/**
	 * Changer la valeur de damages
	 * @param damages
	 * 	Force de la troupe
	 */
	public void setDamages(int damages) {
		this.damages = damages;
	}
	
	/**
	 * 
	 */
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
	
	/**
	 * 
	 */
	public void makeDamages() {
		int enemy = cible.getChevaliers();
		if (enemy == 0) {
			cible.setDuke(this.owner);
		}
		else {
			cible.decChevalier();
		}
	}
	
	/**
	 * D�placer la troupe jusqu'� sa cible
	 */
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
		    	  attacking = false;
		    	  setMoved(true);
		   };
		
		};
		if(!attacking)
		{
			attacking = true;
			t.start();
		}
	}
}
