package Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * <b>Sprite est une classe abstraite repr�sentant un n'importe quel �l�ment visible du plateau de jeu.</b>
 * <p>
 * Un sprite est caract�ris� par les informations suivantes :
 * <ul>
 * <li>ImageView un Node utilis� pour d�ssiner l'image charg� depuis la classe Image</li>
 * <li>Pane</li>
 * <li>Une coordonn�es x pour placer l'image sur le plateau</li>
 * <li>Une coordonn�es y pour placer l'image sur le plateau</li>
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
     * Coordonn�es x du sprite
     * 
     * @see Sprite#getX()
     * @see Sprite#setX(double)
     */
    protected double x;
    /**
     * Coordonn�es y du sprite
     * 
     * @see Sprite#getY()
     * @see Sprite#setY(double)
     */
    protected double y;
    /**
     * Bool�en moved qui indique si un sprite a fini de bouger
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
     * 	Coordonn�es x du sprite
     * @param y
     * 	Coordonn�es y du sprite
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
     * R�cup�rer layer
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
	 * R�cup�rer moved
	 * @return moved, true si le sprite a termin� de bouger false sinon
	 */
	public boolean isMoved() {
		return moved;
	}
	
	/**
	 * Changer la valeur de moved
	 * @param moved
	 * 	Bool�en qui indique si le sprite a termin� de bouger
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
     * R�cup�rer x
     * @return la coordonn�es x du sprite
     */
    public double getX() {
        return x;
    }
    
    /**
     * Changer la valeur de x
     * @param x
     * 	Coordonn�es x du sprite
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * R�cup�rer y
     * @return la coordonn�es y du sprite
     */
    public double getY() {
        return y;
    }

    /**
     * Changer la valeur de y
     * @param y
     * 	Coordonn�es y du sprite
     */
    public void setY(double y) {
        this.y = y;
    }
    
    /**
     * R�cup�rer imageView
     * @return la vue de l'image 
     */
    protected ImageView getView() {
        return imageView;
    }

    /**
     * Mettre � jour l'emplacement de l'image du sprite sur le plateau
     */
    public void updateUI() {
        imageView.relocate(x, y);
    }
    
    /**
     * R�cup�rer w
     * @return la largeur de l'image
     */
    public double getWidth() {
        return w;
    }
    
    /**
     * R�cup�rer h
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