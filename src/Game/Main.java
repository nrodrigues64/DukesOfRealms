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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Group;
import javafx.scene.Scene;
 

/**
 * <b>Classe Principale Main, ex�cute l'application.</b>
 * @author Nicolas RODRIGUES et Tristan PREVOST
 *
 */
public class Main extends Application {
	
	private Pane playfieldLayer;
	private Stage dialogAttack = new Stage();
	private Stage dialogForm = new Stage();
	private Stage dialogPseudo = new Stage();
	private Stage dialogRenfort = new Stage();
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
	private Button addNbTroupeRenfort = new Button();
	private TextField textFieldRenfort = new TextField();
	private Button addNbTroupeForm = new Button();
	private TextField textFieldForm = new TextField();
	private Button choixPseudo = new Button();
	private TextField textFieldPseudo = new TextField();
	private boolean pseudo = false;
	GridPane gridPaneForm = new GridPane();
	GridPane gridPanePseudo = new GridPane();
	GridPane gridPaneAttack = new GridPane();
	GridPane gridPaneRenfort = new GridPane();
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
						
						k.getHome().UpdateTroopsA();
						k.getHome().UpdateTroopsR();
						for (int i = 0; i< k.getCastles().size(); i++)
						{
							k.getCastles().get(i).UpdateTroopsA();
							k.getCastles().get(i).UpdateTroopsR();
						}
						// update sprites in scene
						k.getHome().updateUI();
						
						k.getHome().getTroops().forEach(sprite -> sprite.updateUI());
						
						//player.checkRemovability();
						removeSprites(k.getHome().getTroops());
						removeSprites(k.getHome().getATroops());
						removeSprites(k.getHome().getRTroops());
						k.getCastles().forEach(sprite -> 
						{
							if(sprite.getDuke() == k.getHome().getDuke())
							{
								removeSprites(sprite.getATroops());
								removeSprites(sprite.getRTroops());
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
	 * Cr�er le Royaume du jeu
	 */
	private void createPlayer() {
		Random random = new Random();
		//Coordonn�es choisis al�atoirement dans les limites de la taille de l'�cran
		double x = random.nextInt((int)(Settings.SCENE_WIDTH -  castleImage.getWidth()) + 1);
		double y = random.nextInt((int)(Settings.SCENE_HEIGHT - castleImage.getHeight()) + 1);
		
		//Cr�ation du ch�teau du duc
		player = new Castle(playfieldLayer, castleImage, x, y,Integer.parseInt(textFieldPseudo.getText()), 500, 1);
		//Le ch�teau est s�lectionner par d�faut pour l'attaque
		player.setSelected(true);
		//Ajout � la liste pour v�rification ult�rieur
		lc.add(0, player);
		
		int i = 0;
		
		//Cr�ation au minimum de 5 ch�teau qui respecte les condition �tablie
		while(i < 5)
		{
			//Coordonn�es choisis al�atoirement dans les limites de la taille de l'�cran
			double x1 = random.nextInt((int)(Settings.SCENE_WIDTH -  castleEnemy.getWidth()) + 1);
			double y1 = random.nextInt((int)(Settings.SCENE_HEIGHT - castleEnemy.getHeight()) + 1);
			//Test de nom chevauchement de ch�teau
			if(check_castle(lc,x1,y1)) {
				Castle c = new Castle(playfieldLayer,castleEnemy, x1, y1, random.nextInt(1000), random.nextInt(1000000), 1);
				lc.add(c);
				i++;
			}
			
		}
		//Retrait du ch�teau du duc de la liste
		player = lc.remove(0);
		moneyDelay();
		
		//Cr�ation du Royaume
		k = new Kingdom(player,lc);
		
		//Cr�ation du menu des ch�teau des ennemies
		k.getCastles().forEach(sprite -> sprite.getView().setOnContextMenuRequested(e -> {
				ContextMenu contextMenu = new ContextMenu();
				
				//Cr�ation des items du menu
				String Duke = "Owner : ";
				Duke = Duke.concat(Integer.toString(sprite.getDuke()));
				String Treasure = "Treasure : ";
				Treasure = Treasure.concat(Integer.toString(sprite.getTreasure()));
				String Level = "Level : ";
				Level = Level.concat(Integer.toString(sprite.getLevel()));
				String chevaliers = "Troupe(s) : ";
				chevaliers = chevaliers.concat(Integer.toString(sprite.getChevaliers()));

				MenuItem chevalier= new MenuItem(chevaliers);
				MenuItem duke = new MenuItem(Duke);
				MenuItem treasure= new MenuItem(Treasure);
				MenuItem level= new MenuItem(Level);
				
				//Ajout des items
				contextMenu.getItems().addAll(duke, treasure, level, chevalier);
				
				//Test si ch�teau est alli�
				if(sprite.getDuke() == k.getHome().getDuke())
				{
					//
					MenuItem levelup = new MenuItem("Level Up");
					levelup.setOnAction(evt -> sprite.levelUp());
					MenuItem former = new MenuItem("Former troupe(s)");
					former.setOnAction(new EventHandler<ActionEvent>() {
						 public void handle(ActionEvent e) {
								
								initButtonOkForm(sprite);
							e.consume();
						}});
					contextMenu.getItems().addAll(levelup, former);
					if(getSelected() != sprite)
					{
						MenuItem renfort = new MenuItem("Recevoir/Envoyer renfort(s)");
						renfort.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent e) {
								initButtonOkRenfort(getSelected(),sprite);
								e.consume();
							}
						});
						
						contextMenu.getItems().add(renfort);
					}
					MenuItem select = new MenuItem("Select");
					if(sprite.isSelected())
						select.setText("Selected");
					select.setOnAction(evt -> selected(sprite));
					
					contextMenu.getItems().add(select);
				} else {
					
					MenuItem attack = new MenuItem("Attaquer");
					attack.setOnAction(evt -> initButtonOkAttack(getSelected(),sprite));
					
					contextMenu.getItems().add(attack);
					
				}
				
				contextMenu.show(sprite.getView(), e.getScreenX(), e.getScreenY());
		}));
		
		//Cr�ation du menu du ch�teau du duc
		k.getHome().getView().setOnContextMenuRequested(e -> {
			ContextMenu contextMenu = new ContextMenu();
			
			//Cr�ation des items
			String Duke = "Owner : ";
			Duke = Duke.concat(Integer.toString(k.getHome().getDuke()));
			String Treasure = "Treasure : ";
			Treasure = Treasure.concat(Integer.toString(k.getHome().getTreasure()));
			String Level = "Level : ";
			Level = Level.concat(Integer.toString(k.getHome().getLevel()));
			String chevaliers = "Troupe(s) : ";
			chevaliers = chevaliers.concat(Integer.toString(k.getHome().getChevaliers()));
			
			
			MenuItem duke = new MenuItem(Duke);
			MenuItem treasure= new MenuItem(Treasure);
			MenuItem level= new MenuItem(Level);
			MenuItem chevalier = new MenuItem(chevaliers);
			MenuItem levelup = new MenuItem("Level Up");
			
			levelup.setOnAction(evt -> k.getHome().levelUp());
			
			MenuItem former = new MenuItem("Former troupe(s)");
			former.setOnAction(new EventHandler<ActionEvent>() {
				 public void handle(ActionEvent e) {
						initButtonOkForm(k.getHome());
						
					e.consume();
				}});
			contextMenu.getItems().addAll(duke, treasure, level, levelup, chevalier, former);
			if(getSelected() != k.getHome())
			{
				MenuItem renfort = new MenuItem("Recevoir renfort(s)");
				renfort.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						initButtonOkRenfort(getSelected(),k.getHome());
						e.consume();
					}
				});
				
				contextMenu.getItems().add(renfort);
			}
			MenuItem select = new MenuItem("Select");
			if(k.getHome().isSelected())
				select.setText("Selected");
			select.setOnAction(evt -> selected(k.getHome()));
			
			contextMenu.getItems().add(select);
			contextMenu.show(k.getHome().getView(), e.getScreenX(), e.getScreenY());
		});
	}
	
	/**
	 * Augmente la tr�sorie des ch�teaux de 50 toutes les secondes
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
	 * Verifie que le ch�teau peut �tre ajout� � cette position
	 * @param lc
	 * 	Liste des ch�teaux
	 * @param x
	 * 	Coordonn�e x o� le chateau va �tre ajout�
	 * @param y
	 * 	Coordonn�e y o� le chateau va �tre ajout�
	 * @return true si le ch�teau peut �tre ajout� � ses coordonn�es, false sinon
	 */
	private boolean check_castle(List<Castle> lc, double x, double y) {
		boolean in = true;
		
		// 250 au lieu de 150 pour avoir de l'espace entre ch�teaux
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
	 * S�lectionner un ch�teau pour attaquer
	 * @param c
	 * 	Ch�teau attaquant
	 */
	private void selected(Castle c)
	{
		//Si c est le ch�teau du duc :
		if(c == k.getHome())
		{
			//Duc est s�lectionn�
			k.getHome().setSelected(true);
			//Tous les autres ch�teaux sont d�selectionn�s
			k.getCastles().forEach(sprite -> sprite.setSelected(false));
		} else {
			//Si duc s�lectionn�
			if(k.getHome().isSelected())
				//Duc d�selectionn�
				k.getHome().setSelected(false);
			//Tous les ch�teaux sont d�selectionn�
			k.getCastles().forEach(sprite -> sprite.setSelected(false));
			//S�lection du ch�teau voulu
			c.setSelected(true);
		}
		
	}
	
	/**
	 *R�cup�rer le ch�teau s�lectionn�
	 * @return le ch�teau s�lectionn� pour l'attaque
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
	 * Initialiser le PopUp (Fen�tre) pour saisie de troupe attaquante
	 */
	public void initStageAttack()
	{
		//Configuration de la grille du la fen�tre
        
        gridPaneAttack.setVgap(10);
        gridPaneAttack.setHgap(10);
        gridPaneAttack.setPadding(new Insets(10));
        
        //Cr�ation des items pr�sents dans la fen�tre
		Label label = new Label("Saisissez un nombre de troupe pour l'attaque");
		addNbTroupeAttack.setText("OK");	
		
		//Ajout des items � la grille
		gridPaneAttack.add(label, 0, 0);
        gridPaneAttack.add(textFieldAttack, 0, 1);
        gridPaneAttack.add(addNbTroupeAttack, 0, 3, 2, 1);
        GridPane.setHalignment(addNbTroupeAttack, HPos.CENTER);       
        dialogAttack.initStyle(StageStyle.UTILITY);
        Scene scene = new Scene(gridPaneAttack);
        dialogAttack.setScene(scene);
	}
	
	/**
	 * Configurer le bouton OK du PopUp et afficher le PopUp
	 * @param c
	 * 	Ch�teau attaquant
	 * @param c1
	 * 	Ch�teau cible
	 */
	public void initButtonOkAttack(Castle c,Castle c1)
	{
		addNbTroupeAttack.setOnAction(new EventHandler<ActionEvent>() {
			 public void handle(ActionEvent e) {
					try 
			        { 
			            // checking valid integer using parseInt() method 
			            Integer.parseInt(textFieldAttack.getText());		   
						saisieTroupe(c,c1);
						dialogAttack.close(); 
			        }catch (NumberFormatException e1){ 
			        	Label label = new Label("Saisissez un entier");
			        	gridPaneAttack.add(label, 0, 2);
			        	
				    } 
					
					
				e.consume();
			}});
		dialogAttack.show();	
	}
	
	/**
	 * R�cup�rer le nombre de troupe pour l'attaque et attaquer
	 * @param c
	 * 	Ch�teau attaquant
	 * @param c1
	 * 	Ch�teau cible
	 */
	public void saisieTroupe(Castle c, Castle c1)
	{	
		nbT = Integer.parseInt(textFieldAttack.getText());
		dialogAttack.close();
		c.attack2(c1,nbT);
	}
	
	/**
	 * Initialiser le PopUp (Fen�tre) pour saisie de troupe � envoyer en renfort
	 */
	public void initStageRenfort()
	{
		//Configuration de la grille du la fen�tre
        
        gridPaneRenfort.setVgap(10);
        gridPaneRenfort.setHgap(10);
        gridPaneRenfort.setPadding(new Insets(10));
        
        //Cr�ation des items pr�sents dans la fen�tre
		Label label = new Label("Saisissez un nombre de troupe � envoyer en renfort");
		addNbTroupeRenfort.setText("OK");	
		
		//Ajout des items � la grille
		gridPaneRenfort.add(label, 0, 0);
        gridPaneRenfort.add(textFieldRenfort, 0, 1);
        gridPaneRenfort.add(addNbTroupeRenfort, 0, 3, 2, 1);
        GridPane.setHalignment(addNbTroupeRenfort, HPos.CENTER);       
        dialogRenfort.initStyle(StageStyle.UTILITY);
        Scene scene = new Scene(gridPaneRenfort);
        dialogRenfort.setScene(scene);
	}
	
	/**
	 * Configurer le bouton OK du PopUp et afficher le PopUp
	 * @param c
	 * 	Ch�teau envoie renfort
	 * @param c1
	 * 	Ch�teau cible
	 */
	public void initButtonOkRenfort(Castle c,Castle c1)
	{
		addNbTroupeRenfort.setOnAction(new EventHandler<ActionEvent>() {
			 public void handle(ActionEvent e) {
					try 
			        { 
			            // checking valid integer using parseInt() method 
			            Integer.parseInt(textFieldRenfort.getText());		   
						saisieTroupeRenfort(c,c1);
						dialogForm.close(); 
			        }catch (NumberFormatException e1){ 
			        	Label label = new Label("Saisissez un entier");
			        	gridPaneForm.add(label, 0, 2);
			        	
				    } 
					
					
				e.consume();
			}});
		dialogRenfort.show();	
	}
	
	/**
	 * R�cup�rer le nombre de troupe pour l'attaque et attaquer
	 * @param c
	 * 	Ch�teau envoie renfort
	 * @param c1
	 * 	Ch�teau cible
	 */
	public void saisieTroupeRenfort(Castle c, Castle c1)
	{	
		nbT = Integer.parseInt(textFieldRenfort.getText());
		dialogRenfort.close();
		c.renfort(c1,nbT);
	}
	
	/**
	 * Initialiser le PopUp (Fen�tre) pour saisie des troupes � former
	 */
	public void initStageForm()
	{
		//Configuration de la grille de la fen�tre
        
        gridPaneForm.setVgap(10);
        gridPaneForm.setHgap(15);
        gridPaneForm.setPadding(new Insets(10));
        
        //Ajout des items � la grille
		Label label = new Label("Saisissez un nombre de troupe � former");
		addNbTroupeForm.setText("OK");	
		gridPaneForm.add(label, 0, 0);
        gridPaneForm.add(textFieldForm, 0, 1);
        gridPaneForm.add(addNbTroupeForm, 0, 3, 2, 1);
        GridPane.setHalignment(addNbTroupeForm, HPos.CENTER);       
        dialogForm.initStyle(StageStyle.UTILITY);
        Scene scene = new Scene(gridPaneForm);
        dialogForm.setScene(scene);
	}
	
	/**
	 * Configurer le bouton OK du PopUp et afficher le PopUp
	 * @param c
	 * 	Ch�teau attaquant
	 */
	public void initButtonOkForm(Castle c)
	{
		addNbTroupeForm.setOnAction( 
				new EventHandler<ActionEvent>() {
			 public void handle(ActionEvent e) {
					try 
			        { 
			            // checking valid integer using parseInt() method 
			            Integer.parseInt(textFieldForm.getText());		   
						saisieTroupeForm(c);
						dialogForm.close(); 
			        }catch (NumberFormatException e1){ 
			        	Label label = new Label("Saisissez un entier");
			        	gridPaneForm.add(label, 0, 2);
			        	
				    } 
					
					
				e.consume();
			}}
				);
		dialogForm.show();	
	}
	
	/**
	 * R�cup�rer le nombre de troupe � former et former
	 * @param c
	 * 	Ch�teau attaquant
	 */
	public void saisieTroupeForm(Castle c)
	{	
		nbT = Integer.parseInt(textFieldForm.getText());
		dialogForm.close();
		c.incWaitingList(nbT);
	}
	
	
	
	/**
	 * Initialiser le PopUp (Fen�tre) pour saisie des troupes � former
	 */
	public void initStagePseudo()
	{
		//Configuration de la grille de la fen�tre
        
        gridPanePseudo.setVgap(10);
        gridPanePseudo.setHgap(10);
        gridPanePseudo.setPadding(new Insets(10));
        
        //Ajout des items � la grille
		Label label = new Label("Choisissez un Pseudo(un entier):");
		choixPseudo.setText("OK");	
		gridPanePseudo.add(label, 0, 0);
		textFieldPseudo.textProperty().removeListener(new ChangeListener<String>() {
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            textFieldPseudo.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
        gridPanePseudo.add(textFieldPseudo, 0, 1);
        gridPanePseudo.add(choixPseudo, 0, 3, 2, 1);
        GridPane.setHalignment(choixPseudo, HPos.CENTER);       
        dialogPseudo.initStyle(StageStyle.UTILITY);
        Scene scene = new Scene(gridPanePseudo);
        dialogPseudo.setScene(scene);
        initButtonOkPseudo();
	}
	
	/**
	 * Configurer le bouton OK du PopUp et afficher le PopUp
	 * @param c
	 * 	Ch�teau attaquant
	 */
	public void initButtonOkPseudo()
	{
		choixPseudo.setOnAction(
				new EventHandler<ActionEvent>() {
					 public void handle(ActionEvent e) {
							try 
					        { 
					            // checking valid integer using parseInt() method 
					            Integer.parseInt(textFieldPseudo.getText());
					            createPlayer();
								initStageAttack();
								initStageForm();
								initStageRenfort();
					            pseudo = true;
								dialogPseudo.close(); 
					        }catch (NumberFormatException e1){ 
					        		
						            Label l = new Label("Saisissez un entier");
						            gridPanePseudo.add(l, 0, 2);
						    } 
							
							
						e.consume();
					}});
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
	 * 	Liste de sprite � supprimer
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
