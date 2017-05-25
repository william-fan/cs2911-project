import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PuzzleGame {
	private Player player1 = new Player(1, 0, 0); // assume 0, 0 is a wall and is invalid
	private Player player2 = new Player(2, 0, 0);
	private ArrayList<Block> blockList = new ArrayList<Block>();
	private String timerString;
	private GridPane gamePane = new GridPane();
	
	// Background style
	private String background = "-fx-background-image: url(file:images/background.png);" + "\n"
	   					 + "-fx-background-size: stretch;" + "\n"
	   					 + "-fx-background-repeat: no-repeat;";
	
	// blockCount only for generation purposes
	public Scene Game(Cell[][] grid, Stage primaryStage, Scene menu, int playerCount, int blockCount, File inputFile) {
		try {
			Font.loadFont(new FileInputStream(new File("fonts/FSEX300.ttf")), 36);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.gamePane.setAlignment(Pos.CENTER);
		Label timeElapsed = new Label("");
		timeElapsed.setStyle("-fx-text-fill: white;" + "\n" +
							 "-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
							 "-fx-font-size: 72;" + "\n");
		
		AnimationTimer timer = new AnimationTimer() { // every time a frame updates, we add to frame counter.
			long timestamp;
			int seconds = 0;
			int minutes = 0;
			int frames = 0;

			public void handle(long now) { // Every 60 frames, add one second
				long newTime = System.currentTimeMillis();
				if (timestamp + 1000 <= newTime) {
					frames++;
					if (frames == 60) {
						frames = 0;
						seconds++;
					}
					if (seconds == 60) {
						minutes++;
						seconds = 0;
					}
					timerString = "" + seconds;
					if (seconds < 10) {
						timerString = "0" + timerString;
					}
					timerString = minutes + " : " + timerString;
					if (minutes < 10) {
						timerString = "0" + timerString;
					}
					timeElapsed.setText(timerString);
				}
			}
		};

		timer.start();

		int gameWidth = grid.length;
		int gameHeight = grid[0].length;
		
		// UI elements
		BorderPane gameUI = new BorderPane();
		BorderPane leftStats = new BorderPane();
		BorderPane rightStats = new BorderPane();
		Label tempP1Stats = new Label("Moves: " + this.player1.getMoveCount() + "\nPushes: " + this.player1.getBlockMoveCount());
		tempP1Stats.setStyle("-fx-text-fill: white;" + "\n" +
				 "-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
				 "-fx-font-size: 24;" + "\n");
		Label tempP2Stats = new Label("Moves: " + this.player2.getMoveCount() + "\nPushes: " + this.player2.getBlockMoveCount());
		tempP2Stats.setStyle("-fx-text-fill: white;" + "\n" +
				 "-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
				 "-fx-font-size: 24;" + "\n");
		
		ImageView uiSprite1 = new ImageView(new Image(new File("images/sprite1.png").toURI().toString()));
		leftStats.setLeft(uiSprite1);
		BorderPane.setMargin(uiSprite1, new Insets(0, 10, 0, 0));
		BorderPane.setAlignment(uiSprite1, Pos.CENTER);
		BorderPane.setAlignment(tempP1Stats, Pos.CENTER);
		leftStats.setRight(tempP1Stats);
		
		ImageView uiSprite2 = new ImageView(new Image(new File("images/sprite2.png").toURI().toString()));
		rightStats.setLeft(uiSprite2);
		BorderPane.setMargin(uiSprite2, new Insets(0, 10, 0, 0));
		BorderPane.setAlignment(uiSprite2, Pos.CENTER);
		BorderPane.setAlignment(tempP2Stats, Pos.CENTER);
		rightStats.setRight(tempP2Stats);
		
		if (playerCount == 1) {  //hide player 2 stats if only one player
			tempP2Stats.setStyle("-fx-text-fill: transparent;" + "\n" +
					 "-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
					 "-fx-font-size: 24;" + "\n");
			uiSprite2.setVisible(false);
		}
		
		gameUI.setCenter(timeElapsed);
		gameUI.setPadding(new Insets(0, 10, 0, 10));
		gameUI.setLeft(leftStats);
		gameUI.setRight(rightStats);
		
		
		this.gamePane.setMinSize(gameWidth, gameHeight);
		// this.gamePane.setVgap(1);
		// this.gamePane.setHgap(1);

		// Rectangle2D screenSize = Screen.getPrimary().getVisualBounds(); //Get
		// screen size
		if (inputFile != null) {
			findMapFeatures(grid);
		} else {
			for (int c = blockCount; c != 0; c--) { // generate blocks if no input given
				Block tempBlock = new Block(1, c, 3);
				this.blockList.add(tempBlock);
			}
		}

		for (int y = 0; y < gameHeight; y++) { // add images to gridpane
			for (int x = 0; x < gameWidth; x++) {
				HBox a = new HBox();
				ImageView ground = new ImageView(grid[x][y].getFloorImage());
				ground.setFitHeight(48);
				ground.setFitWidth(48);
				a.getChildren().add(ground);
				this.gamePane.add(a, x, y);
			}
		}

		if (playerCount == 2) {
			this.gamePane.add(this.player2.getPlayerImage(), this.player2.getX(), this.player2.getY());
		}
		this.gamePane.add(this.player1.getPlayerImage(), this.player1.getX(), this.player1.getY());

		for (Block block : this.blockList) {
			this.gamePane.add(block.getBlockImage(), block.getX(), block.getY());
		}

		BorderPane center = new BorderPane();
		// center.setPrefWidth(screenSize.getWidth());
		// center.setPrefHeight(screenSize.getHeight());
		center.setCenter(this.gamePane);
		center.setTop(gameUI);
		center.setStyle("-fx-background-color: #3F3F3F;");

		Scene Game = new Scene(center);// , screenSize.getWidth(), screenSize.getHeight());

		Game.setOnKeyPressed((event) -> {
			ImageView tempP1Image = this.player1.getPlayerImage();
			ImageView tempP2Image = this.player2.getPlayerImage();

			if (event.getCode() == KeyCode.DOWN) {
				if (this.player1.moveDown(this.getBlockList(), this.player2, grid)) {
					this.gamePane.getChildren().remove(tempP1Image);
					this.gamePane.add(this.player1.getPlayerImage(), this.player1.getX(), this.player1.getY());
					for (Block block : this.blockList) {
						this.gamePane.getChildren().remove(block.getBlockImage());
						this.gamePane.add(block.getBlockImage(), block.getX(), block.getY());
					}
				}
			} else if (event.getCode() == KeyCode.UP) {
				if (this.player1.moveUp(this.getBlockList(), this.player2, grid)) {
					this.gamePane.getChildren().remove(tempP1Image);
					this.gamePane.add(this.player1.getPlayerImage(), this.player1.getX(), this.player1.getY());
					for (Block block : this.blockList) {
						this.gamePane.getChildren().remove(block.getBlockImage());
						this.gamePane.add(block.getBlockImage(), block.getX(), block.getY());
					}
				}
			} else if (event.getCode() == KeyCode.LEFT) {
				if (this.player1.moveLeft(this.getBlockList(), this.player2, grid)) {
					this.gamePane.getChildren().remove(tempP1Image);
					this.gamePane.add(this.player1.getPlayerImage(), this.player1.getX(), this.player1.getY());
					for (Block block : this.blockList) {
						this.gamePane.getChildren().remove(block.getBlockImage());
						this.gamePane.add(block.getBlockImage(), block.getX(), block.getY());
					}
				}
			} else if (event.getCode() == KeyCode.RIGHT) {
				if (this.player1.moveRight(this.getBlockList(), this.player2, grid)) {
					this.gamePane.getChildren().remove(tempP1Image);
					this.gamePane.add(this.player1.getPlayerImage(), this.player1.getX(), this.player1.getY());
					for (Block block : this.blockList) {
						this.gamePane.getChildren().remove(block.getBlockImage());
						this.gamePane.add(block.getBlockImage(), block.getX(), block.getY());
					}
				}
			} else if (event.getCode() == KeyCode.ESCAPE) {
				primaryStage.setScene(menu);
				primaryStage.setResizable(false);
				// primaryStage.setMaximized(true);
				primaryStage.show();
			} else if (event.getCode() == KeyCode.R) {
				resetGame(grid, primaryStage, menu, playerCount, blockCount, inputFile);
			}
			if (playerCount == 2) {
				if (event.getCode() == KeyCode.S) {
					if (this.player2.moveDown(this.getBlockList(), this.player1, grid)) {
						this.gamePane.getChildren().remove(tempP2Image);
						this.gamePane.add(this.player2.getPlayerImage(), this.player2.getX(), this.player2.getY());
						for (Block block : this.blockList) {
							this.gamePane.getChildren().remove(block.getBlockImage());
							this.gamePane.add(block.getBlockImage(), block.getX(), block.getY());
						}
					}
				} else if (event.getCode() == KeyCode.W) {
					if (this.player2.moveUp(this.getBlockList(), this.player1, grid)) {
						this.gamePane.getChildren().remove(tempP2Image);
						this.gamePane.add(this.player2.getPlayerImage(), this.player2.getX(), this.player2.getY());
						for (Block block : this.blockList) {
							this.gamePane.getChildren().remove(block.getBlockImage());
							this.gamePane.add(block.getBlockImage(), block.getX(), block.getY());
						}
					}
				} else if (event.getCode() == KeyCode.A) {
					if (this.player2.moveLeft(this.getBlockList(), this.player1, grid)) {
						this.gamePane.getChildren().remove(tempP2Image);
						this.gamePane.add(this.player2.getPlayerImage(), this.player2.getX(), this.player2.getY());
						for (Block block : this.blockList) {
							this.gamePane.getChildren().remove(block.getBlockImage());
							this.gamePane.add(block.getBlockImage(), block.getX(), block.getY());
						}
					}
				} else if (event.getCode() == KeyCode.D) {
					if (this.player2.moveRight(this.getBlockList(), this.player1, grid)) {
						this.gamePane.getChildren().remove(tempP2Image);
						this.gamePane.add(this.player2.getPlayerImage(), this.player2.getX(), this.player2.getY());
						for (Block block : this.blockList) {
							this.gamePane.getChildren().remove(block.getBlockImage());
							this.gamePane.add(block.getBlockImage(), block.getX(), block.getY());
						}
					}
				}
			}
			//refresh ui elements
			Label tempP1 = new Label("Moves: " + this.player1.getMoveCount() + "\nPushes: " + this.player1.getBlockMoveCount());
			tempP1.setStyle("-fx-text-fill: white;" + "\n" +
					 "-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
					 "-fx-font-size: 24;" + "\n");
			Label tempP2 = new Label("Moves: " + this.player2.getMoveCount() + "\nPushes: " + this.player2.getBlockMoveCount());
			tempP2.setStyle("-fx-text-fill: white;" + "\n" +
					 "-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
					 "-fx-font-size: 24;" + "\n");
			
			leftStats.setLeft(uiSprite1);
			BorderPane.setMargin(uiSprite1, new Insets(0, 10, 0, 0));
			BorderPane.setAlignment(uiSprite1, Pos.CENTER);
			BorderPane.setAlignment(tempP1, Pos.CENTER);
			leftStats.setRight(tempP1);
			
			rightStats.setLeft(uiSprite2);
			BorderPane.setMargin(uiSprite2, new Insets(0, 10, 0, 0));
			BorderPane.setAlignment(uiSprite2, Pos.CENTER);
			BorderPane.setAlignment(tempP2, Pos.CENTER);
			rightStats.setRight(tempP2);
			
			if (playerCount == 1) {  //hide player 2 stats if only one player
				tempP2.setStyle("-fx-text-fill: transparent;" + "\n" +
						 "-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
						 "-fx-font-size: 24;" + "\n");
				uiSprite2.setVisible(false);
			}
			gameUI.setLeft(leftStats);
			gameUI.setRight(rightStats);
			checkVictory(primaryStage, menu, grid, this.blockList, playerCount, inputFile, grid, blockCount);
		});
		return Game;

	}

	private void checkVictory(Stage primaryStage, Scene menu, Cell[][] map, ArrayList<Block> blockList, int playerCount, File inputFile, Cell[][] grid, int blockCount) {
		int gameWidth = map.length;
		int gameHeight = map[0].length;
		ArrayList<Block> tempList = new ArrayList<Block>(blockList);
		
		//set block colors
		for (Block tempBlock : blockList) {
			if (map[tempBlock.getX()][tempBlock.getY()].getType() != 2) {
				this.gamePane.getChildren().remove(tempBlock.getBlockImage());
				tempBlock.setID(1);
				this.gamePane.add(tempBlock.getBlockImage(), tempBlock.getX(), tempBlock.getY());
			} else {
				this.gamePane.getChildren().remove(tempBlock.getBlockImage());
				tempBlock.setID(2);
				this.gamePane.add(tempBlock.getBlockImage(), tempBlock.getX(), tempBlock.getY());
			}
		}
		
		//check if each target has a block on it, and remove the block from the temp list if there is
		for (int y = 0; y < gameHeight; y++) {
			for (int x = 0; x < gameWidth; x++) {
				if (map[x][y].getType() == 2) {
					Block block = findBlock(tempList, x, y);
					if (block != null) {
						tempList.remove(block);
					} else {
						return; // failed, no blocks on target
					}
				}
			}
		}
		
		//if list is empty, show the victory screen
		if (tempList.isEmpty()) {
			primaryStage.setScene(victoryScreen(primaryStage, menu, playerCount, inputFile, grid, blockCount));
		}
	}

	private Scene victoryScreen(Stage primaryStage, Scene menu, int playerCount, File inputFile, Cell[][] grid, int blockCount) {
		AnchorPane victoryPane = new AnchorPane();
		
		Label victoryText = new Label("Level Complete!");
		victoryText.setStyle("-fx-text-fill: white;" + "\n" +
				 			 "-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
				 			 "-fx-font-size: 72;" + "\n"); // ADDED
		
		AnchorPane.setTopAnchor(victoryText, 75d);
		AnchorPane.setLeftAnchor(victoryText, 220d);
		
		Label summaryText = new Label("Summary");
		summaryText.setUnderline(true);
		summaryText.setStyle("-fx-text-fill: white;" + "\n" +
				 			 "-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
				 			 "-fx-font-size: 72;" + "\n"); // ADDED
		
		AnchorPane.setTopAnchor(summaryText, 175d);
		AnchorPane.setLeftAnchor(summaryText, 360d);
		
		timerString = timerString.replaceAll("\\s+", "");

		Label timeText = new Label("Time Taken: " + timerString);
		timeText.setStyle("-fx-text-fill: white;" + "\n" +
				 			 "-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
				 			 "-fx-font-size: 72;" + "\n"); // ADDED
		
		AnchorPane.setTopAnchor(timeText, 280d);
		AnchorPane.setLeftAnchor(timeText, 145d);
		
		Label moveText = new Label("Moves: "+ player1.getMoveCount());
		moveText.setStyle("-fx-text-fill: white;" + "\n" +
				 			 "-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
				 			 "-fx-font-size: 72;" + "\n"); // ADDED
		
		AnchorPane.setTopAnchor(moveText, 360d);
		AnchorPane.setLeftAnchor(moveText, 150d);
		
		Label pushesText = new Label("Pushes: "+ player1.getBlockMoveCount());
		pushesText.setStyle("-fx-text-fill: white;" + "\n" +
				 			 "-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
				 			 "-fx-font-size: 72;" + "\n"); // ADDED
		
		AnchorPane.setTopAnchor(pushesText, 440d);
		AnchorPane.setLeftAnchor(pushesText, 150d);
		
		// play again button
		Button replayButton = new Button("");
	    BackgroundImage replayButtonBackground = new BackgroundImage(new Image(new File("images/replay.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background replayButtonImage = new Background(replayButtonBackground);
	    replayButton.setBackground(replayButtonImage);
	    replayButton.setPrefSize(300, 100);
	    
	    BackgroundImage replayButtonBackgroundHover = new BackgroundImage(new Image(new File("images/replay_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background replayButtonImageHover = new Background(replayButtonBackgroundHover);
	    replayButton.setBackground(replayButtonImage);
	    
	    replayButton.setOnAction(actionEvent -> {
	    	resetGame(grid, primaryStage, menu, playerCount, blockCount, inputFile);
	    });
	    
	    replayButton.setOnMouseEntered(actionEvent -> {
	    	replayButton.setBackground(replayButtonImageHover);
		});
	    
	    replayButton.setOnMouseExited(actionEvent -> {
	    	replayButton.setBackground(replayButtonImage);
		});
	    
    	AnchorPane.setBottomAnchor(replayButton, 20d);
	    AnchorPane.setLeftAnchor(replayButton, 80d);
	    
	    // level select button
	    Button levelButton = new Button("");
	    BackgroundImage levelButtonBackground = new BackgroundImage(new Image(new File("images/level_select.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background levelButtonImage = new Background(levelButtonBackground);
	    levelButton.setBackground(levelButtonImage);
	    levelButton.setPrefSize(300, 100);
	    
	    BackgroundImage levelButtonBackgroundHover = new BackgroundImage(new Image(new File("images/level_select_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background levelButtonImageHover = new Background(levelButtonBackgroundHover);
	    levelButton.setBackground(levelButtonImage);
	    
	    levelButton.setOnAction(actionEvent -> {
	    	PuzzleHome levelSelect = new PuzzleHome();
			primaryStage.setScene(levelSelect.easyDifficulty(primaryStage, menu, playerCount));
	    });
	    
	    levelButton.setOnMouseEntered(actionEvent -> {
	    	levelButton.setBackground(levelButtonImageHover);
		});
	    
	    levelButton.setOnMouseExited(actionEvent -> {
	    	levelButton.setBackground(levelButtonImage);
		});
	    
    	AnchorPane.setBottomAnchor(levelButton, 18d);
	    AnchorPane.setLeftAnchor(levelButton, 345d);
	    
	    // menu select button
		Button menuButton = new Button("");
	    BackgroundImage menuButtonBackground = new BackgroundImage(new Image(new File("images/main.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background menuButtonImage = new Background(menuButtonBackground);
	    menuButton.setBackground(menuButtonImage);
	    menuButton.setPrefSize(300, 100);
	    
	    BackgroundImage menuButtonBackgroundHover = new BackgroundImage(new Image(new File("images/main_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background menuButtonImageHover = new Background(menuButtonBackgroundHover);
	    menuButton.setBackground(menuButtonImage);

	    menuButton.setOnAction(actionEvent -> {
	    	primaryStage.setScene(menu);
	    });
	    
	    menuButton.setOnMouseEntered(actionEvent -> {
	    	menuButton.setBackground(menuButtonImageHover);
		});
	    
	    menuButton.setOnMouseExited(actionEvent -> {
	    	menuButton.setBackground(menuButtonImage);
		});
	    
    	AnchorPane.setBottomAnchor(menuButton, 20d);
	    AnchorPane.setLeftAnchor(menuButton, 650d);

	    victoryPane.getChildren().addAll(victoryText, timeText, moveText, pushesText, summaryText, replayButton, levelButton, menuButton);
	    
		Scene helpScene = new Scene(victoryPane, 960, 800);
		victoryPane.setStyle(background);
		
		return helpScene;
	}

	// reset values to original
	private void resetGame(Cell[][] grid, Stage primaryStage, Scene menu, int playerCount, int blockCount,
			File inputFile) {
		this.player1 = new Player(1, 0, 0); // assume 0, 0 is a wall and is invalid
		this.player2 = new Player(2, 0, 0);
		this.blockList = new ArrayList<Block>();
		this.timerString = "";
		primaryStage.setScene(Game(grid, primaryStage, menu, playerCount, blockCount, inputFile));
	}

	public ArrayList<Block> getBlockList() {
		return this.blockList;
	}

	private void findMapFeatures(Cell[][] map) {
		int gameWidth = map.length;
		int gameHeight = map[0].length;
		for (int y = 0; y < gameHeight; y++) {
			for (int x = 0; x < gameWidth; x++) {
				if (map[x][y].getType() == 3) {
					if (this.player1.getX() == 0 && this.player1.getY() == 0) { //0, 0 is the default location
						this.player1.setX(x);
						this.player1.setY(y);
					} else {
						this.player2.setX(x);
						this.player2.setY(y);
					}
				}
				if (map[x][y].getType() == 4) {
					Block block = new Block(1, x, y);
					this.blockList.add(block);
				}
			}
		}
	}

	private Block findBlock(ArrayList<Block> tempBlockList, int x, int y) {
		for (Block block : tempBlockList) {
			if (block.getX() == x && block.getY() == y) {
				return block;
			}
		}
		return null;
	}

}
