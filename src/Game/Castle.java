package Game;

import java.util.ArrayList;







import java.util.List;
import java.util.Random;

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
	 * ID du duc
	 */
	private int duke;
	/**
	 * 
	 */
	private Order ordreCourant = null;
	/**
	 * Tr�sor du ch�teau
	 */
	private int treasure;
	/**
	 * Level du ch�teau
	 */
	private int level;	
	/**
	 * Bool�en qui indique si le ch�teau est s�lectionner pour l'attaque
	 */
	private boolean selected = false;
	/**
	 * Liste des troupes du ch�teau
	 */
	private List<Troops> troops = new ArrayList<>();
	/**
	 * Troupes attaquantes du ch�teau
	 */
	private List<Troops> Atroops = new ArrayList<>();
	/**
	 * Troupes de renfort pour alli�
	 */
	private List<Troops> Rtroops = new ArrayList<>();

	/**
	 * Nombre d'unit� en attente de formation
	 */
	private int waitinglist = 0; //le nombre d'unit� en attente de formation
	/**
	 * Bool�en qui indique si des troupe sont en formation
	 */
	private boolean formation = false;
	/**
	 * Prochaine action � effectuer pour l'ia
	 */
	private int order=0;
	/**
	 * Cible � attaquer si l'ordre le demande
	 */
	private Castle cible;
	
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
		addToLayer();	
	}
	
	public Castle(Pane layer, Image image, double x, double y,int duke, int treasure, int level, int nbTroupe) {
		super(layer,image, x, y);
		this.duke = duke;
		this.treasure = treasure;
		this.level = level;
		init(nbTroupe);
		addToLayer();
	}
	
	/**
	 * Initialise met des troupes dans les ch�teaux neutres
	 */
	private void init(int nbTroupe) {
		for(int i = 0; i < nbTroupe; i++)
		{
			Troops t = new Troops(this.getLayer(), new Image(getClass().getResource("/images/knight.png").toExternalForm(), 50, 50, true, true), this.getX(), this.getY(), 1.6, 50, 20,duke);
			troops.add(t);
		}
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
	 * R�cup�rer level
	 * @return le niveau actuel du ch�teau
	 */
	public int getLevel() {
		return level;
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
	 * R�cup�rer ATroops
	 * @return la liste des troupes attaquantes
	 */
	public List<Troops> getATroops() {
		return Atroops;
	}	
	/**
	 * R�cup�rer RTroops
	 * @return la liste des troupes de renforts
	 */
	public List<Troops> getRTroops() {
		return Rtroops;
	}
	
	/**
	 * 
	 * @param order
	 */
	public void setOrder(int order) {
		this.order = order;
	}
	
	/**
	 * 
	 * @param cible
	 */
	public void setCible(Castle cible) {
		this.cible = cible;
	}

	/**
	 * Cr�er une troupe et l'ajoute � la liste
	 */
	public void createTroop()
	{
		Troops t = new Troops(this.getLayer(), new Image(getClass().getResource("/images/knight.png").toExternalForm(), 50, 50, true, true), this.getX(), this.getY(),1.6, 50, 20,duke);
        troops.add(t);
	}
	
	/**
	 * Cr�er une troupe et l'ajouter au ch�teau
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
	 * Incr�menter la nombre de troupe en attente de formation
	 * @param nb
	 * 	Nombre � incr�menter � waitinglist
	 */
	public void incWaitingList(int nb, int duc) {
		//v�rification de la possibilit� financi�re de cr�er des troupes
		if(treasure >= 125*(nb+1)) {
			waitinglist+=nb;
			incChevalier();
			treasure -= 125*(nb+1);
		}else {
			if(duke == duc)
				System.out.println("Les r�serves du tr�sor sont trop faibles pour cr�er autant de chevaliers");
		}
	}
	
	/**
	 * R�cup�rer chevaliers
	 * @return le nombre de chevaliers que contient le ch�teau
	 */
	public int getChevaliers() {
		return troops.size();
	}
	
	/**
	 * Attaquer le ch�teau c avec nbTroupe
	 * @param c
	 * 	Ch�teau � attaquer
	 * @param nbTroupe
	 * 	Nombre de troupes utilis�es pour l'attaque
	 */
	public void attack2(Castle c, int nbTroupe, int duc)
	{
		ordreCourant = new Order(c, nbTroupe);
		if(ordreCourant.getNbTroops() > getChevaliers()) {
			System.out.println("Pas assez de troupe(s).");
			return;
		}
		else {
			for(int i = 0 ; i < ordreCourant.getNbTroops() ; i++)
			{
				if(duc == duke)
					System.out.println("A l'assaut ! Le ch�teau du Duc n�" + duke + " envoie " + nbTroupe + " troupe(s) pour assi�ger le ch�teau n�"+ c.getDuke()+".");
				int size = Atroops.size();
				Atroops.add(troops.get(0));
				troops.remove(0);
				Atroops.get(size).setCible(ordreCourant.getTarget());
				Atroops.get(size).addToLayer();
				
			}
		}

	}
	
	/**
	 * Met � jours la liste des troupes attaquante
	 */
	public void UpdateTroopsA(int duc) {
		for (int i = 0; i<Atroops.size(); i++) 
		{
			//les toupes n'attaquent plus = elles sont arriv�es a destination, il faut les supprimer et infliger les degats
			if(!Atroops.get(i).isAttacking()) {
				Atroops.get(i).makeDamages(duc);
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
	 * Envoyer des troupes en renfort au ch�teau c
	 * @param c
	 * 	Ch�teau alli�
	 * @param nbTroupe
	 * 	Nombre de troupe � envoyer
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
					System.out.println("En renfort ! Le ch�teau du Duc n�" + duke + " envoie " + nbTroupe + " troupe(s) pour renforcer un ch�teau alli�.");
					int size = Rtroops.size();
					Rtroops.add(troops.get(0));
					troops.remove(0);
					Rtroops.get(size).setCible(ordreCourant.getTarget());
					Rtroops.get(size).addToLayer();
					
				}
			}	
	}
	
	/**
	 * Met � jours la liste des troupes de renforts
	 */
	public void UpdateTroopsR() {
		for (int i = 0; i<Rtroops.size(); i++) 
		{
			//les toupes n'attaquent plus = elles sont arriv�es a destination, il faut les supprimer et infliger les degats
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
	 * Augmenter le ch�teau d'un niveau
	 */
	public void levelUp(int duc)
	{
		if(treasure < 1000*(level+1))
		{
			if(duke == duc)
				System.out.println("Tr�sor insuffisant. Il vous faut: "+ (1000*(this.level+1)) +" florins.");
		} else {
			Thread t = new Thread() {
			      
				public void run() {	  
						try {
							if(duc == duke)
								System.out.println("L'am�lioration du ch�teau sera fini dans " + (5*level) + " secondes. Cela vous a cout� " + 1000 * (level+1) + "florins, il vous reste: " + (treasure-1000 * (level+1)) + "florin(s)." );
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
	 *Augmenter la tr�sorie de 50 + 30/level en plus 
	 */
	public void incMoney() {
		treasure += 50 +(int)((level-1)*30) ;
	}
	
	/**
	 * envoie l'ordre d'attaquer
	 */
	public void makeOrder(int duc) {
			Random random = new Random();
					//random.nextInt(this.getChevaliers())-2;
			int a = this.getChevaliers();
			if (a <1) 
			{
				a=1;
			}
			int nbtroupes = random.nextInt(a);
	
			if (order == 1) {
				for(int i = 0; i<nbtroupes; i++) {
					this.attack2(cible, 1,duc);
				}
				this.setOrder(0);
			}
	}
}
