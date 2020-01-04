package Game;

import java.util.ArrayList;







import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


/**
 * <b>Castle est une classe repr�sentant un ch�teau.</b>
 * <p>
 * Un ch�teau est caract�ris� par les informations suivantes :
 * <ul>
 * <li>H�rite des m�me attributs qu'un sprite</li>
 * <li>Un duc qui est un ID pour identifier le ch�teau du joueur</li>
 * <li>Un tr�sor</li>
 * <li>Un niveau</li>
 * </ul>
 *
 * @see Sprite
 * @author Nicolas RODRIGUES et Tristan PREVOST
 *
 */

public class Castle extends Sprite {
	/**
	 * Minimum range x
	 */
	private double minX;
	/**
	 * Maximum range x
	 */
	private double maxX;
	/**
	 * Minimum range y
	 */
	private double minY;
	/**
	 * Maximum range y
	 */
	private double maxY;
	/**
	 * ID du duc
	 */
	private int duke;
	/**
	 * Tr�sor du ch�teau
	 */
	private int treasure;
	/**
	 * Level du ch�teau
	 */
	private int level;	
	/**
	 * Nombre de troupes dans le ch�teau
	 */
	private int chevaliers = 0;
	/**
	 * Bool�en qui indique si une attaque est en cours
	 */
	private boolean isAttacking = false;
	/**
	 * Bool�en qui indique si le ch�teau est s�lectionner pour l'attaque
	 */
	private boolean selected = false;
	/**
	 * Liste des troupes du ch�teau
	 */
	private List<Troops> troops = new ArrayList<>();
	/**
	 * Nombre d'unit� en attente de formation
	 */
	private int waitinglist = 0; //le nombre d'unit� en attente de formation
	/**
	 * Bool�en qui indique si des troupe sont en formation
	 */
	private boolean formation = false;
	
	/**
	 * Constructeur de Castle
	 * @param layer
	 * Plan
	 * @param image
	 * 	Image du ch�teau
	 * @param x
	 * 	Coordonn�es x du ch�teau
	 * @param y
	 * 	Coordonn�es y du ch�teau
	 */
	public Castle(Pane layer, Image image, double x, double y) {
		super(layer,image, x, y);
		init();
		addToLayer();
	}
	
	/**
	 * Constructeur du ch�teau
	 * @param layer
	 * Plan 
	  * @param image
	 * 	Image du ch�teau
	 * @param x
	 * 	Coordonn�es x du ch�teau
	 * @param y
	 * 	Coordonn�es y du ch�teau
	 * @param duke
	 * ID du duke
	 * @param treasure
	 * Tr�sor
	 * @param level
	 * Level
	 */
	public Castle(Pane layer, Image image, double x, double y,int duke, int treasure, int level) {
		super(layer,image, x, y);
		this.duke = duke;
		this.treasure = treasure;
		this.level = level;
		init();
		addToLayer();
		
	}
	
	/**
	 * Initialise minX, maxX, minY, maxY, met une torupe dans le ch�teau
	 */
	private void init() {
		Troops t = new Troops(this.getLayer(), new Image(getClass().getResource("/images/knight.png").toExternalForm(), 50, 50, true, true), this.getX(), this.getY(), 1.6, 50, 20);
		troops.add(t);
		chevaliers = troops.size();
		// calculate movement bounds of the player ship
		// allow half of the player to be outside of the screen
		minX = 0 + getWidth()*2;
		maxX = Settings.SCENE_WIDTH - getWidth()*2;
		minY = 0 + getHeight()*2;
		maxY = Settings.SCENE_HEIGHT - getHeight()*2;
	}
	
	/**
	 * R�cup�rer selected
	 * @return selected, true si le ch�teau est s�lectionn� false sinon
	 */
	public boolean isSelected() {
		return selected;
	}
	
	/**
	 * Change la valeur de selected
	 * @param selected
	 * Bool�en qui indique si le ch�teau est s�lectionn�
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	/**
	 * R�cup�rer troops
	 * @return la liste de troupes
	 */
	public List<Troops> getTroops() {
		return troops;
	}
	
	/**
	 * Changer de liste de troupes
	 * @param troops
	 * 	Liste des troupes du ch�teau
	 */
	public void setTroops(List<Troops> troops) {
		this.troops = troops;
	}
	
	/**
	 * R�cup�rer isAttacking
	 * @return isAttacking true si le ch�teau est en train d'attaquer false sinon
	 */
	public boolean isAttacking() {
		return isAttacking;
	}
	
	/**
	 * Change la valeur de isAttacking 
	 * @param isAttacking
	 * 	Bool�en qui indique si une attaque est en cours
	 */
	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}
	
	/**
	 * Teste les coordonn�es du ch�teau pour pas qu'il soit hors champs
	 */
	public void checkBounds() {
		// vertical
		y = y < minY ? minY : y;
		y = y > maxY ? maxY : y;

		// horizontal
		x = x < minX ? minX : x;
		x = x > maxX ? maxX : x;
	}
	
	/**
	 * Cr�er une troupe et l'ajoute � la liste
	 */
	public void createTroop()
	{
		Troops t = new Troops(this.getLayer(), new Image(getClass().getResource("/images/knight.png").toExternalForm(), 50, 50, true, true), this.getX(), this.getY(),1.6, 50, 20);
        troops.add(t);
	}
	
	/**
	 * 
	 */
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
	
	/**
	 * Incr�menter la nombre de troupe en attente de formation
	 * @param nb
	 * 	Nombre � incr�menter � waitinglist
	 */
	public void incWaitingList(int nb) {
		waitinglist+=nb;
		incChevalier();
	}
	
	/**
	 * 
	 */
	public void decChevalier() {
		chevaliers--;
	}
	
	/**
	 * R�cup�rer chevaliers
	 * @return le nombre de chevaliers que contient le ch�teau
	 */
	public int getChevaliers() {
		return chevaliers;
	}
	
	/**
	 * Changer la valeurs de chevaliers
	 * @param chevaliers
	 * Nombre de chevaliers dans le ch�teau
	 */
	public void setChevaliers(int chevaliers) {
		this.chevaliers = chevaliers;
	}
	
	/**
	 * R�cup�rer waitinglist
	 * @return le nombre de troupe en attente de formation
	 */
	public int getWaitinglist() {
		return waitinglist;
	}
	
	/**
	 * Changer la valeur de waitinglist
	 * @param waitinglist
	 * 	Nombre de troupe en attente de formation
	 */
	public void setWaitinglist(int waitinglist) {
		this.waitinglist = waitinglist;
	}
	
	/**
	 * R�cup�rer formation
	 * @return formation, true si une formation est en cours false sinon
	 */
	public boolean isFormation() {
		return formation;
	}
	
	/**
	 *Changer la valeur de formation 
	 * @param formation
	 * 	Bool�en qui indique si une formation est en cours
	 */
	public void setFormation(boolean formation) {
		this.formation = formation;
	}
	
	/**
	 * R�cup�rer duke
	 * @return l'ID du duc
	 */
	public int getDuke() {
		return duke;
	}
	
	/**
	 * Changer l'ID du duc
	 * @param duke
	 * 	ID du duc
	 */
	public void setDuke(int duke) {
		this.duke = duke;
	}
	
	/**
	 * R�cup�rer treasure
	 * @return la valeur du tr�sor du chateau
	 */
	public int getTreasure() {
		return treasure;
	}
	
	/**
	 * Changer la valeur du tr�sor
	 * @param treasure
	 * 	Tr�sor du ch�teau
	 */
	public void setTreasure(int treasure) {
		this.treasure = treasure;
	}
	
	/**
	 * R�cup�rer level
	 * @return le niveau actuel du ch�teau
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Changer le level du ch�teau
	 * @param level
	 * 	Niveau du ch�teau
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	/**
	 * Attaquer le ch�teau c avec nbTroupe
	 * @param c
	 * 	Ch�teau � attaquer
	 * @param nbTroupe
	 * 	Nombre de troupes utilis�es pour l'attaque
	 */
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
	
	/**
	 * Augmenter le ch�teau d'un niveau
	 */
	public void levelUp()
	{
		if(this.getTreasure() < 1000*(this.getLevel()+1))
		{
			System.out.println("Tr�sor insuffisant.");
		} else {
			int tmp = 100+50*(this.getLevel()+1);
			System.out.println("L'am�lioration prendra " + tmp + "tours.");
			this.setLevel(this.getLevel()+1);
			this.setTreasure(this.getTreasure()-1000*(this.getLevel()+1));
		}
	}
}
