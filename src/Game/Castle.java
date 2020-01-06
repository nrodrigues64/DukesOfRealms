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
	 * ID du duc
	 */
	private int duke;
	/**
	 * 
	 */
	private Order ordreCourant = null;
	/**
	 * Trésor du château
	 */
	private int treasure;
	/**
	 * Level du château
	 */
	private int level;	
	/**
	 * Booléen qui indique si le château est sélectionner pour l'attaque
	 */
	private boolean selected = false;
	/**
	 * Liste des troupes du château
	 */
	private List<Troops> troops = new ArrayList<>();
	/**
	 * Troupes attaquantes du château
	 */
	private List<Troops> Atroops = new ArrayList<>();
	/**
	 * Troupes de renfort pour allié
	 */
	private List<Troops> Rtroops = new ArrayList<>();

	/**
	 * Nombre d'unité en attente de formation
	 */
	private int waitinglist = 0; //le nombre d'unité en attente de formation
	/**
	 * Booléen qui indique si des troupe sont en formation
	 */
	private boolean formation = false;
	
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
		Troops t = new Troops(this.getLayer(), new Image(getClass().getResource("/images/knight.png").toExternalForm(), 50, 50, true, true), this.getX(), this.getY(), 1.6, 50, 20,duke);
		troops.add(t);
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
	 * Récupérer level
	 * @return le niveau actuel du château
	 */
	public int getLevel() {
		return level;
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
	 * Récupérer ATroops
	 * @return la liste des troupes attaquantes
	 */
	public List<Troops> getATroops() {
		return Atroops;
	}	
	/**
	 * Récupérer RTroops
	 * @return la liste des troupes de renforts
	 */
	public List<Troops> getRTroops() {
		return Rtroops;
	}

	/**
	 * Créer une troupe et l'ajoute à la liste
	 */
	public void createTroop()
	{
		Troops t = new Troops(this.getLayer(), new Image(getClass().getResource("/images/knight.png").toExternalForm(), 50, 50, true, true), this.getX(), this.getY(),1.6, 50, 20,duke);
        troops.add(t);
	}
	
	/**
	 * Créer une troupe et l'ajouter au château
	 */
	public void incChevalier() {
		Thread t = new Thread() {
		      public void run() {
		    	  while(waitinglist>0) {
		    		  try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						//
						e.printStackTrace();
					}
		    	if (!Main.pause) {
		    	createTroop();
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
		//vérification de la possibilité financière de créer des troupes
		if(treasure >= 125*(nb+1)) {
			waitinglist+=nb;
			incChevalier();
			treasure -= 125*(nb+1);
		}else {
			System.out.println("Les réserves du trésor sont trop faibles pour créer autant de chevaliers");
		}
	}
	
	/**
	 * Récupérer chevaliers
	 * @return le nombre de chevaliers que contient le château
	 */
	public int getChevaliers() {
		return troops.size();
	}
	
	/**
	 * Attaquer le château c avec nbTroupe
	 * @param c
	 * 	Château à attaquer
	 * @param nbTroupe
	 * 	Nombre de troupes utilisées pour l'attaque
	 */
	public void attack2(Castle c, int nbTroupe)
	{
		ordreCourant = new Order(c, nbTroupe);
		if(ordreCourant.getNbTroops() > getChevaliers()) {
			System.out.println("Pas assez de troupe(s).");
			return;
		}
		else {
			for(int i = 0 ; i < ordreCourant.getNbTroops() ; i++)
			{
				System.out.println("A l'assaut ! Le château du Duc n°" + duke + " envoie " + nbTroupe + " troupe(s) pour assiéger le château du duc n°"+ c.getDuke()+".");
				int size = Atroops.size();
				Atroops.add(troops.get(0));
				troops.remove(0);
				Atroops.get(size).setCible(ordreCourant.getTarget());
				Atroops.get(size).addToLayer();
				
			}
		}
		ordreCourant = null;
		System.out.println("Ordre d'attaque terminé.");

	}
	
	/**
	 * Met à jours la liste des troupes attaquante
	 */
	public void UpdateTroopsA() {
		for (int i = 0; i<Atroops.size(); i++) 
		{
			//les toupes n'attaquent plus = elles sont arrivées a destination, il faut les supprimer et infliger les degats
			if(!Atroops.get(i).isAttacking()) {
				Atroops.get(i).makeDamages();
				Atroops.get(i).setMoved(true);
			}
			try {
				Atroops.get(i).move2();
				} catch (IndexOutOfBoundsException e) {
				//

				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Envoyer des troupes en renfort au château c
	 * @param c
	 * 	Château allié
	 * @param nbTroupe
	 * 	Nombre de troupe à envoyer
	 */
	public void renfort(Castle c, int nbTroupe)
	{
			ordreCourant = new Order(c, nbTroupe);
			if(ordreCourant.getNbTroops() > getChevaliers()) {
				System.out.println("Pas assez de troupe(s).");
				return;
			}
			else {
				for(int i = 0 ; i < ordreCourant.getNbTroops() ; i++)
				{
					System.out.println("En renfort ! Le château du Duc n°" + duke + " envoie " + nbTroupe + " troupe(s) pour renforcer un château allié.");
					int size = Rtroops.size();
					Rtroops.add(troops.get(0));
					troops.remove(0);
					Rtroops.get(size).setCible(ordreCourant.getTarget());
					Rtroops.get(size).addToLayer();
					
				}
			}	
	}
	
	/**
	 * Met à jours la liste des troupes de renforts
	 */
	public void UpdateTroopsR() {
		for (int i = 0; i<Rtroops.size(); i++) 
		{
			//les toupes n'attaquent plus = elles sont arrivées a destination, il faut les supprimer et infliger les degats
			if(!Rtroops.get(i).isAttacking()) {
				Rtroops.get(i).makeReinforcement();
				Rtroops.get(i).setMoved(true);
			}
			try {
				Rtroops.get(i).move2();
				} catch (IndexOutOfBoundsException e) {
				//

				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * Augmenter le château d'un niveau
	 */
	public void levelUp()
	{
		if(treasure < 1000*(level+1))
		{
			System.out.println("Trésor insuffisant. Il vous faut: "+ (1000*(this.level+1)) +" florins.");
		} else {
			Thread t = new Thread() {
			      
				public void run() {	  
						try {
							System.out.println("L'amélioration du château sera fini dans " + (5*level) + " secondes. Cela vous a couté " + 1000 * (level+1) + "florins, il vous reste: " + (treasure-1000 * (level+1)) + "florin(s)." );
							treasure = (treasure - (1000 * (level+1)));
							Thread.sleep(5000 * level);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    	
						if (!Main.pause) {
			    		
							level++;
							
						}
					}
				
			}; 
			t.start();	
		}
	}
	
	/**
	 *Augmenter la trésorie de 50 + 30/level en plus 
	 */
	public void incMoney() {
		treasure += 50 +(int)((level-1)*30) ;
	}
}
