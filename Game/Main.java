package Game;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler; 
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;


public class Main extends Application {
	private Pane playfieldLayer;
	
	private Image castleImage;
	private Image castleEnemy;
	private Kingdom k;
	private Castle player;
	private List<Castle> lc = new ArrayList<>();
	private Scene scene;
	double xTarget;
	double yTarget;
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

					k.getHome().UpdateTroops();
					for (int i = 0; i< k.getCastles().size(); i++)
					{
						k.getCastles().get(i).UpdateTroops();
					}
					
					// update sprites in scene
					k.getHome().updateUI();
					
					k.getHome().getTroops().forEach(sprite -> sprite.updateUI());
					
					//player.checkRemovability();
					removeSprites(k.getHome().getTroops());
					removeSprites(k.getHome().getATroops());
					k.getCastles().forEach(sprite -> 
					{
						if(sprite.getDuke() == k.getHome().getDuke())
						{
							removeSprites(sprite.getATroops());
						}
					});
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
		castleEnemy = new Image(getClass().getResource("/images/white_castle.jpg").toExternalForm(), 100,100,true,true);
		createPlayer();
	}


	private void createPlayer() {
		Random random = new Random();
		//le random est fait dans les limites de la taille de l'écran
				double x = random.nextInt((int)(Settings.SCENE_WIDTH -  castleImage.getWidth()) + 1);
				double y = random.nextInt((int)(Settings.SCENE_HEIGHT - castleImage.getHeight()) + 1);
				player = new Castle(playfieldLayer, castleImage, x, y,666, 500, 1);
				lc.add(player);
				
				int i = 0;
				while(i < 5)
				{
					int j = i+1;
					double x1 = random.nextInt((int)(Settings.SCENE_WIDTH -  castleImage.getWidth()) + 1);
					double y1 = random.nextInt((int)(Settings.SCENE_HEIGHT - castleImage.getHeight()) + 1);
					if(check_castle(lc,x1,y1)) {
						Castle c = new Castle(playfieldLayer,castleEnemy, x1, y1, random.nextInt(1000), 500, 1,j);
						lc.add(c);
						i++;
					}
					
				}
		player = lc.remove(0);
		moneyDelay();
		
		k = new Kingdom(player,lc);
		
		k.getCastles().forEach(sprite -> sprite.getView().setOnContextMenuRequested(e -> {
				ContextMenu contextMenu = new ContextMenu();
				String Duke = "Owner : ";
				Duke = Duke.concat(Integer.toString(sprite.getDuke()));
				String Treasure = "Treasure : ";
				Treasure = Treasure.concat(Integer.toString(sprite.getTreasur()));
				String Level = "Level : ";
				Level = Level.concat(Integer.toString(sprite.getLevel()));
				String chevaliers = "chevaliers : ";
				chevaliers = chevaliers.concat(Integer.toString(sprite.getChevaliers()));
				MenuItem chevalier= new MenuItem(chevaliers);
				MenuItem duke = new MenuItem(Duke);
				MenuItem treasure= new MenuItem(Treasure);
				MenuItem level= new MenuItem(Level);
				contextMenu.getItems().addAll(duke, treasure, level, chevalier);
				
				if(sprite.getDuke() == k.getHome().getDuke())
				{
					
					MenuItem levelup = new MenuItem("Level Up");
					levelup.setOnAction(evt -> sprite.levelUp());
					contextMenu.getItems().add(levelup);
					MenuItem ally = new MenuItem("Amie");
					contextMenu.getItems().add(ally);
					MenuItem former = new MenuItem("Former un chevalier");
					former.setOnAction(new EventHandler<ActionEvent>() {
						 public void handle(ActionEvent e) {
								
								sprite.incChevalier();
							e.consume();
						}});
					MenuItem former5 = new MenuItem("Former 5 chevaliers");
					former5.setOnAction(new EventHandler<ActionEvent>() {
						 public void handle(ActionEvent e) {
								
								sprite.incWaitingList(4);;
							e.consume();
						}});
					MenuItem former10 = new MenuItem("Former 10 chevaliers");
					former10.setOnAction(new EventHandler<ActionEvent>() {
						 public void handle(ActionEvent e) {
								
								sprite.incWaitingList(9);;
							e.consume();
						}});
					MenuItem select = new MenuItem("Select");
					select.setOnAction(evt -> selected(sprite));
					contextMenu.getItems().addAll(former,former5, former10,select);
				} else {
					MenuItem attack = new MenuItem("Attaquer");
					attack.setOnAction(evt -> getSelected().attack2(sprite, 1));
					contextMenu.getItems().add(attack);
					
				}
				
				contextMenu.show(sprite.getView(), e.getScreenX(), e.getScreenY());
		}));
		k.getHome().getView().setOnContextMenuRequested(e -> {
			ContextMenu contextMenu = new ContextMenu();
			String Duke = "Owner : ";
			Duke = Duke.concat(Integer.toString(player.getDuke()));
			String Treasure = "Treasure : ";
			Treasure = Treasure.concat(Integer.toString(player.getTreasur()));
			String Level = "Level : ";
			Level = Level.concat(Integer.toString(player.getLevel()));
			String chevaliers = "chevaliers : ";
			chevaliers = chevaliers.concat(Integer.toString(player.getChevaliers()));
			MenuItem select = new MenuItem("Select");
			MenuItem duke = new MenuItem(Duke);
			MenuItem treasure= new MenuItem(Treasure);
			MenuItem level= new MenuItem(Level);
			MenuItem chevalier = new MenuItem(chevaliers);
			MenuItem levelup = new MenuItem("Level Up");
			select.setOnAction(evt -> selected(player));
			levelup.setOnAction(evt -> player.levelUp());
			
			contextMenu.getItems().addAll(duke, treasure, level,levelup,chevalier);
			MenuItem former = new MenuItem("Former un chevalier");
			former.setOnAction(new EventHandler<ActionEvent>() {
				 public void handle(ActionEvent e) {
						
						k.getHome().incChevalier();
					e.consume();
				}});
			MenuItem former5 = new MenuItem("Former 5 chevaliers");
			former5.setOnAction(new EventHandler<ActionEvent>() {
				 public void handle(ActionEvent e) {
						
						k.getHome().incWaitingList(4);;
					e.consume();
				}});
			MenuItem former10 = new MenuItem("Former 10 chevaliers");
			former10.setOnAction(new EventHandler<ActionEvent>() {
				 public void handle(ActionEvent e) {
						
						k.getHome().incWaitingList(9);;
					e.consume();
				}});
			contextMenu.getItems().addAll(former,former5, former10,select);
			contextMenu.show(k.getHome().getView(), e.getScreenX(), e.getScreenY());
		});
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
	private void selected(Castle c)
	{
		if(c == k.getHome())
		{
			k.getHome().setSelected(true);
			k.getCastles().forEach(sprite -> sprite.setSelected(false));
		} else {
			if(k.getHome().isSelected())
				k.getHome().setSelected(false);
			k.getCastles().forEach(sprite -> sprite.setSelected(false));
			c.setSelected(true);
		}
		
	}
	private Castle getSelected()
	{
		
		for(int i = 0; i < k.getCastles().size(); i++)
		{
			if(k.getCastles().get(i).getDuke() == k.getHome().getDuke())
				if(k.getCastles().get(i).isSelected())
					return k.getCastles().get(i);
		}
		return k.getHome();
	}
	private void removeSprites(List<? extends Sprite> spriteList) {
		Iterator<? extends Sprite> iter = spriteList.iterator();
		while (iter.hasNext()) {
			Sprite sprite = iter.next();

			if (sprite.isMoved()) {
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
	public void moneyDelay() {
		Thread t = new Thread() {
		      public void run() {
		    	  while(true) {
		    		  try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	if (!Main.pause) {
		    		k.getHome().incMoney();
		    		for(int i = 0; i< k.getCastles().size(); i++) {
		    			k.getCastles().get(i).incMoney();
		    		}
		        }
		    }}};

		  t.start();

	}
	
	public static void main(String[] args) {
		launch(args);
	}

}