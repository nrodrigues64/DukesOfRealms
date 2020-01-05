package Game;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.HashMap;

import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Group;
import javafx.scene.Scene;
 

/**
 * <b>Classe Principale Main, exécute l'application.</b>
 * @author Nicolas RODRIGUES et Tristan PREVOST
 *
 */
public class Main extends Application {
	
	private Pane playfieldLayer;
	private Stage dialogAttack = new Stage();
	private Stage dialogForm = new Stage();
	private Stage dialogPseudo = new Stage();
	private Image castleImage;
	private Image castleEnemy;
	private Kingdom k;
	private Castle player;
	private List<Castle> lc = new ArrayList<>();
	private Scene scene;
	double xTarget;
	private int nbT;
	double yTarget;
	private AnimationTimer gameLoop;
	private HashMap<String, Boolean> currentlyActiveKeys = new HashMap<>();
	static boolean pause = false;
	private Button addNbTroupeAttack = new Button();
	private TextField textFieldAttack = new TextField();
	private Button addNbTroupeForm = new Button();
	private TextField textFieldForm = new TextField();
	private Button choixPseudo = new Button();
	private TextField textFieldPseudo = new TextField();
	private boolean pseudo = false;
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
				
				if(pseudo) {
				
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
		
		initStagePseudo();
		
	}

	/**
	 * Créer le Royaume du jeu
	 */
	private void createPlayer() {
		Random random = new Random();
		//Coordonnées choisis aléatoirement dans les limites de la taille de l'écran
		double x = random.nextInt((int)(Settings.SCENE_WIDTH -  castleImage.getWidth()) + 1);
		double y = random.nextInt((int)(Settings.SCENE_HEIGHT - castleImage.getHeight()) + 1);
		
		//Création du château du duc
		player = new Castle(playfieldLayer, castleImage, x, y,Integer.parseInt(textFieldPseudo.getText()), 500, 1);
		//Le château est sélectionner par défaut pour l'attaque
		player.setSelected(true);
		//Ajout à la liste pour vérification ultérieur
		lc.add(0, player);
		
		int i = 0;
		
		//Création au minimum de 5 château qui respecte les condition établie
		while(i < 5)
		{
			//Coordonnées choisis aléatoirement dans les limites de la taille de l'écran
			double x1 = random.nextInt((int)(Settings.SCENE_WIDTH -  castleEnemy.getWidth()) + 1);
			double y1 = random.nextInt((int)(Settings.SCENE_HEIGHT - castleEnemy.getHeight()) + 1);
			//Test de nom chevauchement de château
			if(check_castle(lc,x1,y1)) {
				Castle c = new Castle(playfieldLayer,castleEnemy, x1, y1, random.nextInt(1000), random.nextInt(1000000), 1);
				lc.add(c);
				i++;
			}
			
		}
		//Retrait du château du duc de la liste
		player = lc.remove(0);
		moneyDelay();
		
		//Création du Royaume
		k = new Kingdom(player,lc);
		
		//Création du menu des château des ennemies
		k.getCastles().forEach(sprite -> sprite.getView().setOnContextMenuRequested(e -> {
				ContextMenu contextMenu = new ContextMenu();
				
				//Création des items du menu
				String Duke = "Owner : ";
				Duke = Duke.concat(Integer.toString(sprite.getDuke()));
				String Treasure = "Treasure : ";
				Treasure = Treasure.concat(Integer.toString(sprite.getTreasure()));
				String Level = "Level : ";
				Level = Level.concat(Integer.toString(sprite.getLevel()));
				String chevaliers = "chevaliers : ";
				chevaliers = chevaliers.concat(Integer.toString(sprite.getChevaliers()));

				MenuItem chevalier= new MenuItem(chevaliers);
				MenuItem duke = new MenuItem(Duke);
				MenuItem treasure= new MenuItem(Treasure);
				MenuItem level= new MenuItem(Level);
				
				//Ajout des items
				contextMenu.getItems().addAll(duke, treasure, level, chevalier);
				
				//Test si château est allié
				if(sprite.getDuke() == k.getHome().getDuke())
				{
					//
					MenuItem levelup = new MenuItem("Level Up");
					levelup.setOnAction(evt -> sprite.levelUp());
					MenuItem former = new MenuItem("Former un chevalier");
					former.setOnAction(new EventHandler<ActionEvent>() {
						 public void handle(ActionEvent e) {
								
								initButtonOkForm(sprite);
							e.consume();
						}});				
					MenuItem select = new MenuItem("Select");
					if(sprite.isSelected())
						select.setText("Selected");
					select.setOnAction(evt -> selected(sprite));
					
					contextMenu.getItems().addAll(levelup, former, select);
				} else {
					
					MenuItem attack = new MenuItem("Attaquer");
					attack.setOnAction(evt -> initButtonOkAttack(getSelected(),sprite));
					
					contextMenu.getItems().add(attack);
					
				}
				
				contextMenu.show(sprite.getView(), e.getScreenX(), e.getScreenY());
		}));
		
		//Création du menu du château du duc
		k.getHome().getView().setOnContextMenuRequested(e -> {
			ContextMenu contextMenu = new ContextMenu();
			
			//Création des items
			String Duke = "Owner : ";
			Duke = Duke.concat(Integer.toString(k.getHome().getDuke()));
			String Treasure = "Treasure : ";
			Treasure = Treasure.concat(Integer.toString(k.getHome().getTreasure()));
			String Level = "Level : ";
			Level = Level.concat(Integer.toString(k.getHome().getLevel()));
			String chevaliers = "chevaliers : ";
			chevaliers = chevaliers.concat(Integer.toString(k.getHome().getChevaliers()));
			
			
			MenuItem duke = new MenuItem(Duke);
			MenuItem treasure= new MenuItem(Treasure);
			MenuItem level= new MenuItem(Level);
			MenuItem chevalier = new MenuItem(chevaliers);
			MenuItem levelup = new MenuItem("Level Up");
			
			levelup.setOnAction(evt -> k.getHome().levelUp());
			
			MenuItem former = new MenuItem("Former un chevalier");
			former.setOnAction(new EventHandler<ActionEvent>() {
				 public void handle(ActionEvent e) {
						initButtonOkForm(k.getHome());
						
					e.consume();
				}});
			
			MenuItem select = new MenuItem("Select");
			if(k.getHome().isSelected())
				select.setText("Selected");
			select.setOnAction(evt -> selected(k.getHome()));
			
			contextMenu.getItems().addAll(duke, treasure, level, levelup, chevalier, former, select);
			contextMenu.show(k.getHome().getView(), e.getScreenX(), e.getScreenY());
		});
	}
	
	/**
	 * Augmente la trésorie des châteaux de 50 toutes les secondes
	 */
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
				}
			}
		};  
		t.start();

	}
	
	/**
	 * Verifie que le château peut être ajouté à cette position
	 * @param lc
	 * 	Liste des châteaux
	 * @param x
	 * 	Coordonnée x où le chateau va être ajouté
	 * @param y
	 * 	Coordonnée y où le chateau va être ajouté
	 * @return true si le château peut être ajouté à ses coordonnées, false sinon
	 */
	private boolean check_castle(List<Castle> lc, double x, double y) {
		boolean in = true;
		
		// 250 au lieu de 150 pour avoir de l'espace entre châteaux
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
	
	/**
	 * Sélectionner un château pour attaquer
	 * @param c
	 * 	Château attaquant
	 */
	private void selected(Castle c)
	{
		//Si c est le château du duc :
		if(c == k.getHome())
		{
			//Duc est sélectionné
			k.getHome().setSelected(true);
			//Tous les autres châteaux sont déselectionnés
			k.getCastles().forEach(sprite -> sprite.setSelected(false));
		} else {
			//Si duc sélectionné
			if(k.getHome().isSelected())
				//Duc déselectionné
				k.getHome().setSelected(false);
			//Tous les châteaux sont déselectionné
			k.getCastles().forEach(sprite -> sprite.setSelected(false));
			//Sélection du château voulu
			c.setSelected(true);
		}
		
	}
	
	/**
	 *Récupérer le château sélectionné
	 * @return le château sélectionné pour l'attaque
	 */
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
	
	/**
	 * Initialiser le PopUp (Fenêtre) pour saisie de troupe attaquante
	 */
	public void initStageAttack()
	{
		//Configuration de la grille du la fenêtre
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));
        
        //Création des items présents dans la fenêtre
		Label label = new Label("Saisissez un nombre de troupe pour l'attaque");
		addNbTroupeAttack.setText("OK");	
		
		//Ajout des items à la grille
		gridPane.add(label, 0, 0);
        gridPane.add(textFieldAttack, 0, 1);
        gridPane.add(addNbTroupeAttack, 0, 2, 2, 1);
        GridPane.setHalignment(addNbTroupeAttack, HPos.CENTER);       
        dialogAttack.initStyle(StageStyle.UTILITY);
        Scene scene = new Scene(gridPane);
        dialogAttack.setScene(scene);
	}
	
	/**
	 * Configurer le bouton OK du PopUp et afficher le PopUp
	 * @param c
	 * 	Château attaquant
	 * @param c1
	 * 	Château cible
	 */
	public void initButtonOkAttack(Castle c,Castle c1)
	{
		addNbTroupeAttack.setOnAction(evt -> saisieTroupe(c, c1));
		dialogAttack.show();	
	}
	
	/**
	 * Récupérer le nombre de troupe pour l'attaque et attaquer
	 * @param c
	 * 	Château attaquant
	 * @param c1
	 * 	Château cible
	 */
	public void saisieTroupe(Castle c, Castle c1)
	{	
		nbT = Integer.parseInt(textFieldAttack.getText());
		dialogAttack.close();
		c.attack2(c1,nbT);
	}
	
	/**
	 * Initialiser le PopUp (Fenêtre) pour saisie des troupes à former
	 */
	public void initStageForm()
	{
		//Configuration de la grille de la fenêtre
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));
        
        //Ajout des items à la grille
		Label label = new Label("Saisissez un nombre de troupe à former");
		addNbTroupeForm.setText("OK");	
		gridPane.add(label, 0, 0);
        gridPane.add(textFieldForm, 0, 1);
        gridPane.add(addNbTroupeForm, 0, 2, 2, 1);
        GridPane.setHalignment(addNbTroupeForm, HPos.CENTER);       
        dialogForm.initStyle(StageStyle.UTILITY);
        Scene scene = new Scene(gridPane);
        dialogForm.setScene(scene);
	}
	
	/**
	 * Configurer le bouton OK du PopUp et afficher le PopUp
	 * @param c
	 * 	Château attaquant
	 */
	public void initButtonOkForm(Castle c)
	{
		addNbTroupeForm.setOnAction(evt -> saisieTroupeForm(c));
		dialogForm.show();	
	}
	
	/**
	 * Récupérer le nombre de troupe à former et former
	 * @param c
	 * 	Château attaquant
	 */
	public void saisieTroupeForm(Castle c)
	{	
		nbT = Integer.parseInt(textFieldForm.getText());
		dialogForm.close();
		c.incWaitingList(nbT-1);
	}
	
	/**
	 * Initialiser le PopUp (Fenêtre) pour saisie des troupes à former
	 */
	public void initStagePseudo()
	{
		//Configuration de la grille de la fenêtre
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));
        
        //Ajout des items à la grille
		Label label = new Label("Choisissez un Pseudo :");
		choixPseudo.setText("OK");	
		gridPane.add(label, 0, 0);
        gridPane.add(textFieldPseudo, 0, 1);
        gridPane.add(choixPseudo, 0, 2, 2, 1);
        GridPane.setHalignment(choixPseudo, HPos.CENTER);       
        dialogPseudo.initStyle(StageStyle.UTILITY);
        Scene scene = new Scene(gridPane);
        dialogPseudo.setScene(scene);
        initButtonOkPseudo();
	}
	
	/**
	 * Configurer le bouton OK du PopUp et afficher le PopUp
	 * @param c
	 * 	Château attaquant
	 */
	public void initButtonOkPseudo()
	{
		choixPseudo.setOnAction(evt -> {
			createPlayer();
			initStageAttack();
			initStageForm();
			pseudo = true;
			dialogPseudo.close();
		});
		dialogPseudo.show();	
	}
	/*private void checkCollisions() {
		collision = false;

		for (Enemy enemy : enemies) {
			for (Missile missile : missiles) {
				if (missile.collidesWith(enemy)) {
					enemy.damagedBy(missile);
					missile.remove();
					collision = true;
					scoreValue += 10 + (Settings.SCENE_HEIGHT - k.getHome().getY()) / 10;
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
	/**
	 * Supprimer un liste de sprite du plateau de jeu
	 * @param spriteList
	 * 	Liste de sprite à supprimer
	 */
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
	
	public static void main(String[] args) {
		launch(args);
	}

}
