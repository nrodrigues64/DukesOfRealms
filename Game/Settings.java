package Game;

import java.awt.Toolkit;
import java.awt.geom.Dimension2D;
public class Settings {
	static Dimension2D screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final double SCENE_WIDTH = screenSize.getWidth();
    public static final double SCENE_HEIGHT = screenSize.getHeight() - 70;
    public static final int ENEMY_SPAWN_RANDOMNESS = 5;
	/*public static final double STATUS_BAR_HEIGHT = 50;


    public static final double PLAYER_SPEED = 4.0;
    public static final int    PLAYER_HEALTH = 3;
    public static final double PLAYER_DAMAGE = 1;

    public static final double MISSILE_SPEED = 4.0;
    public static final int    MISSILE_HEALTH = 0;
    public static final double MISSILE_DAMAGE = 1.0;

    
    
    public static final int FIRE_FREQUENCY_LOW = 1000 * 1000 * 1000; // 1 second in nanoseconds
    public static final int FIRE_FREQUENCY_MEDIUM = 500 * 1000 * 1000; // 0.5 second in nanoseconds
    public static final int FIRE_FREQUENCY_HIGH = 100 * 1000 * 1000; // 0.1 second in nanoseconds*/

}