package Game;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonBase;
import javafx.event.EventHandler;

import java.awt.Frame;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextField;

import SampleGame.Settings;
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
	private Image castleEnemy;
	private Image enemyImage;
	private Kingdom k;
	private Castle player;
	private List<Troops> enemies = new ArrayList<>();
	private List<Castle> lc = new ArrayList<>();
	private Scene scene;
	private AnimationTimer gameLoop;
	private HashMap<String, Boolean> currentlyActiveKeys = new HashMap<>();
	static boolean pause = false;
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
		scene.setOnKeyPressed(event -> {
            String codeString = event.getCode().toString();
            if (!currentlyActiveKeys.containsKey(codeString)) {
                currentlyActiveKeys.put(codeString, true);
            }
        });
        scene.setOnKeyReleased(event -> 
            currentlyActiveKeys.remove(event.getCode().toString())
        );
		loadGame();
		
		gameLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				

				
				if (removeActiveKey("P")) {
                    System.out.println("game paused");
                    pause = !pause;
                }
				if (!pause) {
					spawnEnemies(true);
					// movement
					player.move();
					enemies.forEach(sprite -> sprite.move());
					
	
					// update sprites in scene
					player.updateUI();
					enemies.forEach(sprite -> sprite.updateUI());
					
					enemies.forEach(sprite -> sprite.checkRemovability());
					removeSprites(enemies);
				}
				
			}
			

		
			

		};
		gameLoop.start();
	}
	
	
	private boolean removeActiveKey(String codeString) {
        Boolean isActive = currentlyActiveKeys.get(codeString);

        if (isActive != null && isActive) {
            currentlyActiveKeys.put(codeString, false);
            return true;
        } else {
            return false;
        }
    }
	
	private void loadGame() {
		castleImage = new Image(getClass().getResource("/images/redcastle.png").toExternalForm(), 100, 100, true, true);
		enemyImage = new Image(getClass().getResource("/images/knight.png").toExternalForm(), 50, 50, true, true);
		castleEnemy = new Image(getClass().getResource("/images/white_castle.jpg").toExternalForm(), 100,100,true,true);
		createPlayer();
	}


	private void createPlayer() {
		Random random = new Random();
		double x = random.nextInt((int)Settings.SCENE_WIDTH);
		double y = random.nextInt((int)Settings.SCENE_HEIGHT);
		player = new Castle(playfieldLayer, castleImage, x, y,666, 99999, 1);
		player.checkBounds();
		lc.add(0, player);
		
		int i = 0;
		while(i < 5)
		{
			double x1 = (Settings.SCENE_WIDTH - castleImage.getWidth())/ random.nextInt(10);
			double y1 = Settings.SCENE_HEIGHT * random.nextDouble();
			if(check_castle(lc,x1,y1)) {
				Castle c = new Castle(playfieldLayer,castleEnemy, x1, y1, random.nextInt(1000), random.nextInt(1000000), 1);
				c.checkBounds();
				lc.add(c);
				i++;
			}
			
		}
		player = lc.remove(0);
		
		k = new Kingdom(player,lc);
		
		lc.forEach(sprite -> sprite.getView().setOnContextMenuRequested(e -> {
				ContextMenu contextMenu = new ContextMenu();
				String Duke = "Owner : ";
				Duke = Duke.concat(Integer.toString(sprite.getDuke()));
				String Treasure = "Treasure : ";
				Treasure = Treasure.concat(Integer.toString(sprite.getTreasur()));
				String Level = "Level : ";
				Level = Level.concat(Integer.toString(sprite.getLevel()));
				String chevaliers = "chevaliers : ";
				chevaliers = chevaliers.concat(Integer.toString(player.getChevaliers()));
				MenuItem chevalier= new MenuItem(chevaliers);
				MenuItem duke = new MenuItem(Duke);
				MenuItem treasure= new MenuItem(Treasure);
				MenuItem level= new MenuItem(Level);
				contextMenu.getItems().addAll(duke, treasure, level, chevalier);
				
				if(sprite.getDuke() == player.getDuke())
				{
					MenuItem levelup = new MenuItem("Level Up");
					levelup.setOnAction(evt -> sprite.levelUp());
					contextMenu.getItems().add(levelup);
					MenuItem ally = new MenuItem("Amie");
					contextMenu.getItems().add(ally);
					MenuItem former = new MenuItem("Former un chevalier");
					former.setOnAction(new EventHandler<ActionEvent>() {
						 public void handle(ActionEvent e) {
								ContextMenu contextMenu2 = new ContextMenu();
								player.incChevalier();
							e.consume();
						}});
					MenuItem former5 = new MenuItem("Former 5 chevaliers");
					former5.setOnAction(new EventHandler<ActionEvent>() {
						 public void handle(ActionEvent e) {
								ContextMenu contextMenu2 = new ContextMenu();
								player.incWaitingList(4);;
							e.consume();
						}});
					MenuItem former10 = new MenuItem("Former 10 chevaliers");
					former10.setOnAction(new EventHandler<ActionEvent>() {
						 public void handle(ActionEvent e) {
								ContextMenu contextMenu2 = new ContextMenu();
								player.incWaitingList(9);;
							e.consume();
						}});
					contextMenu.getItems().addAll(former,former5, former10);
				} else {
					MenuItem attack = new MenuItem("Attaquer");
					attack.setOnAction(evt -> player.attack(sprite, 0));
					contextMenu.getItems().add(attack);
					
				}
				
				contextMenu.show(player.getView(), e.getScreenX(), e.getScreenY());
		}));
		player.getView().setOnContextMenuRequested(e -> {
			ContextMenu contextMenu = new ContextMenu();
			String Duke = "Owner : ";
			Duke = Duke.concat(Integer.toString(player.getDuke()));
			String Treasure = "Treasure : ";
			Treasure = Treasure.concat(Integer.toString(player.getTreasur()));
			String Level = "Level : ";
			Level = Level.concat(Integer.toString(player.getLevel()));
			MenuItem duke = new MenuItem(Duke);
			MenuItem treasure= new MenuItem(Treasure);
			MenuItem level= new MenuItem(Level);
			MenuItem levelup = new MenuItem("Level Up");
			levelup.setOnAction(evt -> player.levelUp());
			contextMenu.getItems().add(levelup);
			contextMenu.getItems().addAll(duke, treasure, level,levelup);
			MenuItem former = new MenuItem("Former un chevalier");
			former.setOnAction(new EventHandler<ActionEvent>() {
				 public void handle(ActionEvent e) {
						ContextMenu contextMenu2 = new ContextMenu();
						player.incChevalier();
					e.consume();
				}});
			MenuItem former5 = new MenuItem("Former 5 chevaliers");
			former5.setOnAction(new EventHandler<ActionEvent>() {
				 public void handle(ActionEvent e) {
						ContextMenu contextMenu2 = new ContextMenu();
						player.incWaitingList(4);;
					e.consume();
				}});
			MenuItem former10 = new MenuItem("Former 10 chevaliers");
			former10.setOnAction(new EventHandler<ActionEvent>() {
				 public void handle(ActionEvent e) {
						ContextMenu contextMenu2 = new ContextMenu();
						player.incWaitingList(9);;
					e.consume();
				}});
			contextMenu.getItems().addAll(former,former5, former10);
			contextMenu.show(player.getView(), e.getScreenX(), e.getScreenY());
		});
	}

	
	private void spawnEnemies(boolean random) {
		if (random && rnd.nextInt(Settings.ENEMY_SPAWN_RANDOMNESS) != 0) {
			return;
		}
		System.out.println("f");
		double speed = rnd.nextDouble() * 3 + 1.0;
		double x =  player.x;
		double y = player.y;
		Troops enemy = new Troops(playfieldLayer, enemyImage, x, y, "Chevalier", 5, 2,speed, 50, 20);
		enemy.setxTarget(lc.get(0).getX());
		enemy.setyTarget(lc.get(0).getY());
		enemies.add(enemy);
	}
	
	//check if a castle can be add at this position
	private boolean check_castle(List<Castle> lc, double x, double y) {
		boolean in = true;
		
		// 250 au lieu de 150 pour avoir de l'espace entre chateau
		double x1 = x + 150;
		double y1 = y + 150;

		double xmid = (x + x1) / 2;
		double ymid = (y + y1) / 2;
		for (int i = 0; i < lc.size(); i++) {
			
			// ici aussi les -100 et + 250 c'est pour avoir une marge
			double xx = lc.get(i).getX() - 100;
			double xx1 = xx + 250;
			double yy = lc.get(i).getY() - 100;
			double yy1 = yy + 250;

			boolean a = x <= xx1 && x >= xx && y <= yy1 && y >= yy;
			boolean b = x1 <= xx1 && x1 >= xx && y <= yy1 && y >= yy;
			boolean c = x <= xx1 && x >= xx && y1 <= yy1 && y1 >= yy;
			boolean d = x1 <= xx1 && x1 >= xx && y1 <= yy1 && y1 >= yy;
			boolean mid = xmid <= xx1 && xmid >= xx && ymid <= yy1 && ymid >= yy;

			in = a || b || c || d || mid;
			if (in) {
				return false;
			}
		}

		return true;
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
