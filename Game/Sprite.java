package Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Sprite {

    private ImageView imageView;

    private Pane layer;
    private int health;
    protected double x;
    protected double y;
    private boolean moved = false;
    protected double dx;
    protected double dy;

    private boolean removable = false;

    private double w;
    private double h;

    public Sprite(Pane layer, Image image, double x, double y, int health) {

    	this.layer = layer;
        this.x = x;
        this.y = y;

        this.health = health;
        

        this.imageView = new ImageView(image);
        this.imageView.relocate(x, y);

        this.w = image.getWidth(); 
        this.h = image.getHeight(); 

        

    }
    
    public Pane getLayer() {
		return layer;
	}

	public void setLayer(Pane layer) {
		this.layer = layer;
	}
	public boolean isMoved() {
		return moved;
	}
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }
	public void addToLayer(Pane layer)
	{
		
	}
    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void move() {
        x += dx;
        y += dy;
    }

    protected ImageView getView() {
        return imageView;
    }

    public void updateUI() {
        imageView.relocate(x, y);
    }

    public double getWidth() {
        return w;
    }

    public double getHeight() {
        return h;
    }

    public double getCenterX() {
        return x + w * 0.5;
    }

    public double getCenterY() {
        return y + h * 0.5;
    }

    // TODO: per-pixel-collision
    public boolean collidesWith(Sprite sprite) {
    	return getView().getBoundsInParent().intersects(sprite.getView().getBoundsInParent());
    }

    public void remove() {
        this.removable = true;
    }

    public abstract void checkRemovability();
    
    public boolean isAlive() {
        return health > 0;
    }
}