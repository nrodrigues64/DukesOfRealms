package Game;

import java.util.ArrayList;







import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


/**
 * <b>Castle est une classe représentant un château.</b>
 * <p>
 * Un château est caractérisé par les informations suivantes :
 * <ul>
 * <li>Hérite des même attributs qu'un sprite</li>
 * <li>Un duc qui est un ID pour identifier le château du joueur</li>
 * <li>Un trésor</li>
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
	 * Trésor du château
	 */
	private int treasure;
	/**
	 * Level du château
	 */
	private int level;	
	/**
	 * Nombre de troupes dans le château
	 */
	private int chevaliers = 0;
	/**
	 * Booléen qui indique si une attaque est en cours
	 */
	private boolean isAttacking = false;
	/**
	 * Booléen qui indique si le château est sélectionner pour l'attaque
	 */
	private boolean selected = false;
	/**
	 * Liste des troupes du château
	 */
	private List<Troops> troops = new ArrayList<>();
	/**
	 * Nombre d'unité en attente de formation
	 */
	private int waitinglist = 0; //le nombre d'unité en attente de formation
	/**
	 * Booléen qui indique si des troupe sont en formation
	 */
	private boolean formation = false;
	
	/**
	 * Constructeur de Castle
	 * @param layer
	 * Plan
	 * @param image
	 * 	Image du château
	 * @param x
	 * 	Coordonnées x du château
	 * @param y
	 * 	Coordonnées y du château
	 */
	public Castle(Pane layer, Image image, double x, double y) {
		super(layer,image, x, y);
		init();
		addToLayer();
	}
	
	/**
	 * Constructeur du château
	 * @param layer
	 * Plan 
	  * @param image
	 * 	Image du château
	 * @param x
	 * 	Coordonnées x du château
	 * @param y
	 * 	Coordonnées y du château
	 * @param duke
	 * ID du duke
	 * @param treasure
	 * Trésor
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
	 * Initialise minX, maxX, minY, maxY, met une torupe dans le château
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
	 * Récupérer selected
	 * @return selected, true si le château est sélectionné false sinon
	 */
	public boolean isSelected() {
		return selected;
	}
	
	/**
	 * Change la valeur de selected
	 * @param selected
	 * Booléen qui indique si le château est sélectionné
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	/**
	 * Récupérer troops
	 * @return la liste de troupes
	 */
	public List<Troops> getTroops() {
		return troops;
	}
	
	/**
	 * Changer de liste de troupes
	 * @param troops
	 * 	Liste des troupes du château
	 */
	public void setTroops(List<Troops> troops) {
		this.troops = troops;
	}
	
	/**
	 * Récupérer isAttacking
	 * @return isAttacking true si le château est en train d'attaquer false sinon
	 */
	public boolean isAttacking() {
		return isAttacking;
	}
	
	/**
	 * Change la valeur de isAttacking 
	 * @param isAttacking
	 * 	Booléen qui indique si une attaque est en cours
	 */
	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}
	
	/**
	 * Teste les coordonnées du château pour pas qu'il soit hors champs
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
	 * Créer une troupe et l'ajoute à la liste
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
	 * Incrémenter la nombre de troupe en attente de formation
	 * @param nb
	 * 	Nombre à incrémenter à waitinglist
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
	 * Récupérer chevaliers
	 * @return le nombre de chevaliers que contient le château
	 */
	public int getChevaliers() {
		return chevaliers;
	}
	
	/**
	 * Changer la valeurs de chevaliers
	 * @param chevaliers
	 * Nombre de chevaliers dans le château
	 */
	public void setChevaliers(int chevaliers) {
		this.chevaliers = chevaliers;
	}
	
	/**
	 * Récupérer waitinglist
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
	 * Récupérer formation
	 * @return formation, true si une formation est en cours false sinon
	 */
	public boolean isFormation() {
		return formation;
	}
	
	/**
	 *Changer la valeur de formation 
	 * @param formation
	 * 	Booléen qui indique si une formation est en cours
	 */
	public void setFormation(boolean formation) {
		this.formation = formation;
	}
	
	/**
	 * Récupérer duke
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
	 * Récupérer treasure
	 * @return la valeur du trésor du chateau
	 */
	public int getTreasure() {
		return treasure;
	}
	
	/**
	 * Changer la valeur du trésor
	 * @param treasure
	 * 	Trésor du château
	 */
	public void setTreasure(int treasure) {
		this.treasure = treasure;
	}
	
	/**
	 * Récupérer level
	 * @return le niveau actuel du château
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Changer le level du château
	 * @param level
	 * 	Niveau du château
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	/**
	 * Attaquer le château c avec nbTroupe
	 * @param c
	 * 	Château à attaquer
	 * @param nbTroupe
	 * 	Nombre de troupes utilisées pour l'attaque
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
	 * Augmenter le château d'un niveau
	 */
	public void levelUp()
	{
		if(this.getTreasure() < 1000*(this.getLevel()+1))
		{
			System.out.println("Trésor insuffisant.");
		} else {
			int tmp = 100+50*(this.getLevel()+1);
			System.out.println("L'amélioration prendra " + tmp + "tours.");
			this.setLevel(this.getLevel()+1);
			this.setTreasure(this.getTreasure()-1000*(this.getLevel()+1));
		}
	}
}
