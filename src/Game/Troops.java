package Game;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;



/**
 * <b>Troops est la classe représentant une troupe du jeu.</b>
 * <p>
 * Une troupe est caractérisée par les informations suivantes :
 * <ul>
 * <li>Une vitesse de déplacement</li>
 * <li>Un nombre de dommages</li>
 * <li>Hérite des mêmes attributs que Sprite</li>
 * </ul>
 * @see Sprite
 * @author Nicolas RODRIGUES et Tristan PREVOST
 *
 */
public class Troops extends Sprite {
	/**
	 * Nombre de point(s) de vie(s) d'une troupe
	 * 
	 * @see Troops#getHealth()
	 * @see Troops#setHealth(int)
	 */
	private int health;
	/**
	 * Vitesse de déplacement de la troupe
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
	 * Booléen qui indique si la troupe attaque
	 * 
	 * @see Troops#isAttacking()
	 * @see Troops#setAttacking(boolean)
	 */
	private boolean attacking = true;
	/**
	 * Propriétaire de la cible
	 */
	private int owner;
	/**
	 * Château cible
	 */
	private Castle cible;

	/**
	 * Constructeur de Troops
	 * @param layer
	 * 	Plan
	 * @param image
	 * 	Image affichée de la troupe
	 * @param x
	 * 	Coordonnées x de la troupe
	 * @param y
	 * 	Coordonnées y de la troupe
	 * @param speed
	 * 	Vitesse de déplacement de la troupe
	 * @param health
	 * 	Santé de la troupe
	 * @param damages
	 * 	Force de la troupe
	 * @param owner
	 * 	Propriétaire de la troupe
	 */
	public Troops(Pane layer, Image image, double x, double y, double speed, int health, int damages, int owner) {
		super(layer, image, x, y);
		this.health = health;
		this.speed = speed;
		this.damages = damages;
		this.owner = owner;
	}

	/**
	 * Récupérer health
	 * @return le nombre de point(s) de vie(s) actuel de la troupe
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Changer la valeur de health
	 * @param health
	 * 	Santé de la troupe
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Récupérer attacking
	 * @return attacking, true si la troupe attaque false sinon 
	 */
	public boolean isAttacking() {
		return attacking;
	}

	/**
	 * Changer la cible de la troupe
	 * @param c
	 * 	Château cible
	 */
	public void setCible(Castle c) {
		this.cible = c;
	}

	/**
	 * Bouger la troupe vers sa cible
	 */
	public void move2()
	{
		if(!isMoved()){

			if(x > (cible.getX() + 1) || x < (cible.getX() - 1)) {

				if (!Main.pause) {
					if(x < cible.getX() && x + speed < cible.getX())
					{
						x += speed;
						updateUI();
					} else if(x < cible.getX() && x + speed > cible.getX()) {
						x++;
						updateUI();
					}  else if(x > cible.getX() && x + speed > cible.getX()) {
						x -= speed;
						updateUI();
					} else if(x < cible.getX() && x + speed > cible.getX()) {
						x--;
						updateUI();

					}
				}

			}
			else if(y > (cible.getY() + 1) || y < (cible.getY() - 1)) {

				if (!Main.pause) {
					if(y < cible.getY() && y + speed < cible.getY())
					{
						y += speed;
						updateUI();
					} else if(y < cible.getY() && y + speed > cible.getY()) {
						y++;
						updateUI();
					} else if(y > cible.getY() && y + speed > cible.getY()) {
						y -= speed;
						updateUI();
					} else if(y < cible.getY() && y + speed > cible.getY()) {
						y--;
						updateUI();
					}
				}

			}

			else {
				attacking = false;
			}



		}

	}

	/**
	 * Appliquer les dommages de l'attaque au château cible
	 * @param duc
	 * Pour empêcher l'affichage de messages
	 */
	public void makeDamages(int duc) {
		int enemy = cible.getChevaliers();
		if (enemy == 0) {
			if(owner != cible.getDuke())
				System.out.println("Le chateau du baron n°"+ owner +" a pris possession du chateau n°" + cible.getDuke());
			cible.setDuke(this.owner);
		}
		else {
			if(duc == owner)
				System.out.println("Attaquant : " + damages + " Défenseur : " + cible.getTroops().get(0).getHealth());
			if(damages > cible.getTroops().get(0).getHealth())
				cible.getTroops().remove(0);
			else
				cible.getTroops().get(0).setHealth(cible.getTroops().get(0).getHealth()-damages);
		}
	}

	/**
	 * Créer la troupe de renfort
	 */
	public void makeReinforcement() {
		cible.createTroop();
	}


}
