package Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * <b>Sprite est une classe abstraite représentant un n'importe quel élément visible du plateau de jeu.</b>
 * <p>
 * Un sprite est caractérisé par les informations suivantes :
 * <ul>
 * <li>ImageView un Node utilisé pour déssiner l'image chargé depuis la classe Image</li>
 * <li>Pane</li>
 * <li>Une coordonnées x pour placer l'image sur le plateau</li>
 * <li>Une coordonnées y pour placer l'image sur le plateau</li>
 * </ul>
 *
 * @see javafx.scene.image.ImageView
 * @see javafx.scene.layout.Pane
 * @author Nicolas RODRIGUES et Tristan PREVOST
 *
 */
public abstract class Sprite {
	/**
	 * ImageView
	 */
    private ImageView imageView;
    /**
     * Plan
     * 
     * @see Sprite#getLayer()
     * @see Sprite#setLayer(Pane)
     */
    private Pane layer;
    /**
     * Coordonnées x du sprite
     * 
     * @see Sprite#getX()
     * @see Sprite#setX(double)
     */
    protected double x;
    /**
     * Coordonnées y du sprite
     * 
     * @see Sprite#getY()
     * @see Sprite#setY(double)
     */
    protected double y;
    /**
     * Booléen moved qui indique si un sprite a fini de bouger
     * 
     * @see Sprite#isMoved()
     * @see Sprite#setMoved(boolean)
     */
    private boolean moved = false;
    /**
     * Largeur de l'image du sprite
     */
    private double w;
    /**
     * Hauteur de l'image du sprite
     */
    private double h;
   
    /**
     * Constructeur de Sprite
     * @param layer
     * Pane
     * @param image
     * 	Image du sprite
     * @param x
     * 	Coordonnées x du sprite
     * @param y
     * 	Coordonnées y du sprite
     */
    public Sprite(Pane layer, Image image, double x, double y) {

    	this.layer = layer;
        this.x = x;
        this.y = y;
        

        this.imageView = new ImageView(image);
        this.imageView.relocate(x, y);

        this.w = image.getWidth(); 
        this.h = image.getHeight(); 

        

    }
    
    /**
     * Récupérer layer
     * @return le Plan du sprite
     */
    public Pane getLayer() {
		return layer;
	}
    
    /**
     * Changer le Plan du sprite
     * @param layer
     * Plan
     */
	public void setLayer(Pane layer) {
		this.layer = layer;
	}
	
	/**
	 * Récupérer moved
	 * @return moved, true si le sprite a terminé de bouger false sinon
	 */
	public boolean isMoved() {
		return moved;
	}
	
	/**
	 * Changer la valeur de moved
	 * @param moved
	 * 	Booléen qui indique si le sprite a terminé de bouger
	 */
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	
	/**
	 * Ajouter le sprite au plan le rendant visible sur le plateau
	 */
	public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }
	
	/**
	 * Supprimer le sprite du plan l'enlevant du plateau
	 */
    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }
    
    /**
     * Récupérer x
     * @return la coordonnées x du sprite
     */
    public double getX() {
        return x;
    }
    
    /**
     * Changer la valeur de x
     * @param x
     * 	Coordonnées x du sprite
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Récupérer y
     * @return la coordonnées y du sprite
     */
    public double getY() {
        return y;
    }

    /**
     * Changer la valeur de y
     * @param y
     * 	Coordonnées y du sprite
     */
    public void setY(double y) {
        this.y = y;
    }
    
    /**
     * Récupérer imageView
     * @return la vue de l'image 
     */
    protected ImageView getView() {
        return imageView;
    }

    /**
     * Mettre à jour l'emplacement de l'image du sprite sur le plateau
     */
    public void updateUI() {
        imageView.relocate(x, y);
    }
    
    /**
     * Récupérer w
     * @return la largeur de l'image
     */
    public double getWidth() {
        return w;
    }
    
    /**
     * Récupérer h
     * @return la hauteur de l'image
     */
    public double getHeight() {
        return h;
    }
    /*
    // TODO: per-pixel-collision
    public boolean collidesWith(Sprite sprite) {
    	return getView().getBoundsInParent().intersects(sprite.getView().getBoundsInParent());
    }*/

}