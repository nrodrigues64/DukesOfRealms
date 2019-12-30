package Game;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import SampleGame.Enemy;
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






public class Main extends Application {
	private Random rnd = new Random();

	private Pane playfieldLayer;
	
	private Image castleImage;
	private Image castleEnemy;
	private Image enemyImage;
	private Kingdom k;
	private Castle player;
	private List<Troops> enemies = new ArrayList<>();
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
		
		//Detection touche press�e pour la pause
		scene.setOnKeyPressed(event -> {
            String codeString = event.getCode().toString();
            if (!currentlyActiveKeys.containsKey(codeString)) {
                currentlyActiveKeys.put(codeString, true);
            }
        });
        scene.setOnKeyReleased(event -> 
            currentlyActiveKeys.remove(event.getCode().toString())
        );
        //test
		
		
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
		List<Castle> lc = new ArrayList<>();
		int n =  random.nextInt(20);
		
		
		//cr�ation du player avant tout et ajout a la liste lc 
		double x1 = (Settings.SCENE_WIDTH -150) / 2.0;
		double y1 = Settings.SCENE_HEIGHT * 0.7;
		player = new Castle(playfieldLayer, castleImage, x1, y1,666, 99999, 1);
		lc.add(player);
		int i = 0;
		while(i<5)
		{
			//changement de l'al�atoire
			double x = random.nextInt((1130) + 1);
			double y = random.nextInt((650) + 1);
			//double x = (Settings.SCENE_WIDTH - castleImage.getWidth())/ random.nextInt(10);
			//double y = (Settings.SCENE_HEIGHT - castleImage.getHeight())/ random.nextInt(10);
			if(check_castle(lc, x, y)) {
					lc.add(0, new Castle(playfieldLayer,castleEnemy, x, y,i, random.nextInt(1000000), 1));
					i++;}
		}
		double x = (Settings.SCENE_WIDTH -150) / 2.0;
		double y = Settings.SCENE_HEIGHT * 0.7;
		//player = new Castle(playfieldLayer, castleImage, x, y,666, 99999, 1);
		k = new Kingdom(player,lc);
		player.getView().setOnMousePressed(e -> {
			System.out.println("Click on player");
			e.consume();
		});
		lc.forEach(sprite -> sprite.getView().setOnContextMenuRequested(e -> {
				ContextMenu contextMenu = new ContextMenu();
				String Duke = "Owner : ";
				Duke = Duke.concat(Integer.toString(sprite.getDuke()));
				String Treasure = "Treasure : ";
				Treasure = Treasure.concat(Integer.toString(sprite.getTreasur()));
				String Level = "Level : ";
				Level = Level.concat(Integer.toString(sprite.getLevel()));
				MenuItem duke = new MenuItem(Duke);
				MenuItem treasure= new MenuItem(Treasure);
				MenuItem level= new MenuItem(Level);
				contextMenu.getItems().addAll(duke, treasure, level);
				if(sprite.getDuke() == player.getDuke())
				{
					MenuItem ally = new MenuItem("Amie");
					contextMenu.getItems().add(ally);
				} else {
					MenuItem attack = new MenuItem("Attaquer");
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
			String chevaliers = "chevaliers : ";
			chevaliers = chevaliers.concat(Integer.toString(player.getChevalier()));
			MenuItem duke = new MenuItem(Duke);
			MenuItem treasure= new MenuItem(Treasure);
			MenuItem level= new MenuItem(Level);
			MenuItem chevalier= new MenuItem(chevaliers);
			
		   
	        
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
			contextMenu.getItems().addAll(duke, treasure, level, chevalier,former,former5, former10);
			contextMenu.show(player.getView(), e.getScreenX(), e.getScreenY());
		});
	}

	//check if a castle can be add at this position
	private boolean check_castle(List<Castle> lc, double x, double y) {
		boolean in = true;
		//250 au lieu de 150 pour avoir de l'espace entre chateau
		double x1 = x + 150;
		double y1 = y + 150;

		double xmid = (x + x1 )/2;
		double ymid = (y + y1)/2;
		for(int i = 0; i < lc.size(); i++){
			//ici aussi les -100 et + 250 c'est pour avoir une marge
			double xx = lc.get(i).getX()-100;
			double xx1 = xx +250;
			double yy = lc.get(i).getY()-100;
			double yy1 = yy + 250;
			
		boolean a = x <= xx1 && x >= xx && y <= yy1 && y >= yy;
		boolean b = x1 <= xx1 && x1 >= xx && y <= yy1 && y >= yy;
		boolean c = x <= xx1 && x >= xx && y1 <= yy1 && y1 >= yy;
		boolean d = x1 <= xx1 && x1 >= xx && y1 <= yy1 && y1 >= yy;
		boolean mid = xmid <= xx1 && xmid >= xx && ymid <= yy1 && ymid >= yy;
		
		//si l'un des coin de la nouvelle image est dans une des images deja pr�sent, on retourne faux et l'affichage ne se fait pas
		in = a || b || c || d || mid;
		if (in) { return false; }
		}
		
		return true;
	}
	
	private void spawnEnemies(boolean random) {
		if (random && rnd.nextInt(Settings.ENEMY_SPAWN_RANDOMNESS) != 0) {
			return;
		}
		double speed = rnd.nextDouble() * 3 + 1.0;
		double x = rnd.nextDouble() * (Settings.SCENE_WIDTH - enemyImage.getWidth());
		double y = -enemyImage.getHeight()*2;
		Troops enemy = new Troops(playfieldLayer, enemyImage, x, y, "Chevalier", 5, 2,speed, 50, 20);
		enemies.add(enemy);
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
