package Game;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Main extends Application {
	private Random rnd = new Random();

	private Pane playfieldLayer;
	
	private Image castleImage;
	private Kingdom k;
	private Castle player;
	
	
	private Scene scene;
	private AnimationTimer gameLoop;
	
	Group root;

	@Override
	public void start(Stage primaryStage) {

		root = new Group();
		scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		// create layers
		playfieldLayer = new Pane();
		root.getChildren().add(playfieldLayer);
		
		loadGame();
		
		gameLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				

				

				
				// movement
				player.move();
				
				

				// update sprites in scene
				player.updateUI();
				
				

				
			}

			

		};
		gameLoop.start();
	}

	private void loadGame() {
		castleImage = new Image(getClass().getResource("/images/redcastle.png").toExternalForm(), 100, 100, true, true);
		
		createPlayer();
		
		
		scene.setOnMousePressed(e -> {
			player.setX(e.getX() - (player.getWidth() / 2));
			player.setY(e.getY() - (player.getHeight() / 2));
		});
	}


	private void createPlayer() {
		Random random = new Random();
		List<Castle> lc = new ArrayList<>();
		lc.add(0, new Castle(playfieldLayer,castleImage, (Settings.SCENE_WIDTH - castleImage.getWidth())/ random.nextInt(10), Settings.SCENE_HEIGHT * random.nextDouble()));
		lc.add(0, new Castle(playfieldLayer,castleImage, (Settings.SCENE_WIDTH - castleImage.getWidth())/ random.nextInt(10), Settings.SCENE_HEIGHT * random.nextDouble()));
		lc.add(0, new Castle(playfieldLayer,castleImage, (Settings.SCENE_WIDTH - castleImage.getWidth())/ random.nextInt(10), Settings.SCENE_HEIGHT * random.nextDouble()));
		lc.add(0, new Castle(playfieldLayer,castleImage, (Settings.SCENE_WIDTH - castleImage.getWidth())/ random.nextInt(10), Settings.SCENE_HEIGHT * random.nextDouble()));
		double x = (Settings.SCENE_WIDTH - castleImage.getWidth()) / 2.0;
		double y = Settings.SCENE_HEIGHT * 0.7;
		player = new Castle(playfieldLayer, castleImage, x, y);
		k = new Kingdom(player,lc);
		player.getView().setOnMousePressed(e -> {
			System.out.println("Click on player");
			e.consume();
		});
		
	}

	

	

	private void removeSprites(List<? extends Sprite> spriteList) {
		Iterator<? extends Sprite> iter = spriteList.iterator();
		while (iter.hasNext()) {
			Sprite sprite = iter.next();

			if (sprite.isRemovable()) {
				// remove from layer
				sprite.removeFromLayer();
				// remove from list
				iter.remove();
			}
		}
	}

	/*private void checkCollisions() {
		collision = false;

		for (Enemy enemy : enemies) {
			for (Missile missile : missiles) {
				if (missile.collidesWith(enemy)) {
					enemy.damagedBy(missile);
					missile.remove();
					collision = true;
					scoreValue += 10 + (Settings.SCENE_HEIGHT - player.getY()) / 10;
				}
			}

			if (player.collidesWith(enemy)) {
				collision = true;
				enemy.remove();
				player.damagedBy(enemy);
				if (player.getHealth() < 1)
					gameOver();
			}
		}

	}*/

	
	public static void main(String[] args) {
		launch(args);
	}

}
