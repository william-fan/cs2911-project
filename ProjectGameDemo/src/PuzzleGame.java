import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PuzzleGame {
	private Player player1 = new Player(1, 0, 0); // assume 0, 0 is a wall and is invalid
	private Player player2 = new Player(2, 0, 0);
	private ArrayList<Block> blockList = new ArrayList<Block>();
	private String timerString;
	private GridPane gamePane = new GridPane();

	// Background style
	private String background = "-fx-background-image: url(file:images/background.png);" + "\n"
			+ "-fx-background-size: stretch;" + "\n" + "-fx-background-repeat: no-repeat;";

	// blockCount only for generation purposes
	public Scene Game(Cell[][] grid, Stage primaryStage, Scene menu, int playerCount, int blockCount, File inputFile) {
		try {
			Font.loadFont(new FileInputStream(new File("fonts/FSEX300.ttf")), 36);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.gamePane.setAlignment(Pos.CENTER);
		this.gamePane.setPrefSize(960, 720);
		Label timeElapsed = new Label("");
		timeElapsed.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
				+ "-fx-font-size: 72;" + "\n");
		
		// every time a frame updates, we add to frame counter.
		AnimationTimer timer = new AnimationTimer() { 
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

		/**
		 * Charlie's additions
		 */

		HBox centerBox = new HBox();
		// Adding time elapsed label to a HBox

		Button popUpMenu = new Button("Menu");
		popUpMenu.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
				+ "-fx-font-size: 20;" + "\n" + "-fx-background-color: #000000;");
		popUpMenu.setText("Menu");
		popUpMenu.setTranslateX(70);
		popUpMenu.setTranslateY(20);

		popUpMenu.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			/**
			 * Handles the functionality of the in game gui menu button
			 */
			public void handle(ActionEvent event) {
				final Stage inGameMenu = new Stage();
				timer.stop();
				inGameMenu.initModality(Modality.APPLICATION_MODAL);
				inGameMenu.initOwner(primaryStage);
				VBox inGameMenuOptions = new VBox(2);

				String background = "-fx-background-image: url(file:images/background.png);" + "\n"
						+ "-fx-background-size: stretch;" + "\n" + "-fx-background-repeat: no-repeat;";

				// Title -- Game Paused!
				Label title = new Label();
				title.setText("Game Paused!");
				title.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
						+ "-fx-font-size: 65;" + "\n");

				BorderPane topSection = new BorderPane();
				topSection.setCenter(title);
				title.setTranslateY(-20);

				// Buttons

				Button resume = new Button("");

				BackgroundImage resumeBackground = new BackgroundImage(
						new Image(new File("images/resume.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
				Background resumeImage = new Background(resumeBackground);
				resume.setBackground(resumeImage);
				resume.setPrefSize(300, 80);

				BackgroundImage resumeBackgroundHover = new BackgroundImage(
						new Image(new File("images/resume_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
				Background resumeImageHover = new Background(resumeBackgroundHover);

				resume.setOnMouseEntered(actionEvent -> {
					resume.setBackground(resumeImageHover);
				});

				resume.setOnMouseExited(actionEvent -> {
					resume.setBackground(resumeImage);
				});

				Button restart = new Button("");

				BackgroundImage restartBackground = new BackgroundImage(
						new Image(new File("images/restart.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
				Background restartImage = new Background(restartBackground);
				restart.setBackground(restartImage);
				restart.setPrefSize(300, 80);

				BackgroundImage restartBackgroundHover = new BackgroundImage(
						new Image(new File("images/restart_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
				Background restartImageHover = new Background(restartBackgroundHover);

				restart.setOnMouseEntered(actionEvent -> {
					restart.setBackground(restartImageHover);
				});

				restart.setOnMouseExited(actionEvent -> {
					restart.setBackground(restartImage);
				});

				Button changeLevel = new Button("");

				BackgroundImage changeLevelBackground = new BackgroundImage(
						new Image(new File("images/ingame_change_level.png").toURI().toString()),
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
						BackgroundSize.DEFAULT);
				Background changeLevelImage = new Background(changeLevelBackground);
				changeLevel.setBackground(changeLevelImage);
				changeLevel.setPrefSize(370, 80);

				BackgroundImage changeLevelBackgroundHover = new BackgroundImage(
						new Image(new File("images/ingame_change_level_arrow.png").toURI().toString()),
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
						BackgroundSize.DEFAULT);
				Background changeLevelImageHover = new Background(changeLevelBackgroundHover);

				changeLevel.setOnMouseEntered(actionEvent -> {
					changeLevel.setBackground(changeLevelImageHover);
				});

				changeLevel.setOnMouseExited(actionEvent -> {
					changeLevel.setBackground(changeLevelImage);
				});

				Button mainMenu = new Button("");

				BackgroundImage mainMenuBackground = new BackgroundImage(
						new Image(new File("images/ingame_main.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
				Background mainMenuImage = new Background(mainMenuBackground);
				mainMenu.setBackground(mainMenuImage);
				mainMenu.setPrefSize(300, 80);

				BackgroundImage mainMenuBackgroundHover = new BackgroundImage(
						new Image(new File("images/ingame_main_arrow.png").toURI().toString()),
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
						BackgroundSize.DEFAULT);
				Background mainMenuImageHover = new Background(mainMenuBackgroundHover);

				mainMenu.setOnMouseEntered(actionEvent -> {
					mainMenu.setBackground(mainMenuImageHover);
				});

				mainMenu.setOnMouseExited(actionEvent -> {
					mainMenu.setBackground(mainMenuImage);
				});

				AnchorPane.setTopAnchor(mainMenu, 490d);
				AnchorPane.setLeftAnchor(mainMenu, 310d);

				Button quit = new Button("");

				BackgroundImage quitBackground = new BackgroundImage(
						new Image(new File("images/ingame_quit.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
				Background quitImage = new Background(quitBackground);
				quit.setBackground(quitImage);
				quit.setPrefSize(300, 80);

				BackgroundImage quitBackgroundHover = new BackgroundImage(
						new Image(new File("images/ingame_quit_arrow.png").toURI().toString()),
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
						BackgroundSize.DEFAULT);
				Background quitImageHover = new Background(quitBackgroundHover);

				quit.setOnMouseEntered(actionEvent -> {
					quit.setBackground(quitImageHover);
				});

				quit.setOnMouseExited(actionEvent -> {
					quit.setBackground(quitImage);
				});

				// Button functionality
				resume.setOnAction(actionEvent -> {
					timer.start();
					inGameMenu.close();
				});

				restart.setOnAction(actionEvent -> {
					resetGame(grid, primaryStage, menu, playerCount, blockCount, inputFile);
					inGameMenu.close();
				});

				changeLevel.setOnAction(actionEvent -> {
					PuzzleHome levelSelect = new PuzzleHome();

					// Using the file name to indicate whether game is easy,
					// medium or hard.
					// Default to easy if custom map.
					String levelDifficulty = inputFile.getName();
					if (levelDifficulty.charAt(0) == 'e') {
						primaryStage.setScene(levelSelect.easyDifficulty(primaryStage, menu, playerCount));
					} else if (levelDifficulty.charAt(0) == 'm') {
						primaryStage.setScene(levelSelect.mediumDifficulty(primaryStage, menu, playerCount));
					} else if (levelDifficulty.charAt(0) == 'h') {
						primaryStage.setScene(levelSelect.hardDifficulty(primaryStage, menu, playerCount));
					}
					inGameMenu.close();
				});

				mainMenu.setOnAction(actionEvent -> {
					primaryStage.setScene(menu);
					inGameMenu.close();
				});

				quit.setOnAction(actionEvent -> {
					Alert confirmExit = new Alert(AlertType.CONFIRMATION, "Would you like to exit Wacky Warehouse?",
							ButtonType.OK, ButtonType.CANCEL);
					confirmExit.setTitle("Exit Game");
					confirmExit.showAndWait();

					if (confirmExit.getResult() == ButtonType.OK) {
						Platform.exit();
					}
				});

				// Create a VBox to store the buttons.

				VBox buttons = new VBox();
				buttons.getChildren().addAll(resume, restart, changeLevel, mainMenu, quit);
				// buttons.setSpacing(10);
				// buttons.setTranslateY(20);
				// buttons.setTranslateX(10);

				buttons.setAlignment(Pos.CENTER);
				inGameMenuOptions.getChildren().addAll(topSection, buttons);
				inGameMenuOptions.setAlignment(Pos.CENTER);

				// Set the style of the VBox
				inGameMenuOptions.setStyle(background);
				Scene inGameMenuScene = new Scene(inGameMenuOptions, 500, 600);

				inGameMenu.setScene(inGameMenuScene);
				inGameMenu.show();

				inGameMenu.setOnCloseRequest(actionEvent -> {
					timer.start();
				});

				inGameMenu.setTitle("Game Menu");
				try {
					inGameMenu.getIcons().add(new Image(new FileInputStream(new File("images/sprite1.png"))));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				// Turned off full screen mode for inGameMenu
				inGameMenu.setResizable(false);

			}

		});

		centerBox.getChildren().addAll(timeElapsed, popUpMenu);
		centerBox.setAlignment(Pos.TOP_CENTER);

		// UI elements
		BorderPane gameUI = new BorderPane();
		BorderPane leftStats = new BorderPane();
		BorderPane rightStats = new BorderPane();
		Label tempP1Stats = new Label(
				"Moves: " + this.player1.getMoveCount() + "\nPushes: " + this.player1.getBlockMoveCount());
		tempP1Stats.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
				+ "-fx-font-size: 24;" + "\n");
		Label tempP2Stats = new Label(
				"Moves: " + this.player2.getMoveCount() + "\nPushes: " + this.player2.getBlockMoveCount());
		tempP2Stats.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
				+ "-fx-font-size: 24;" + "\n");

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

		if (playerCount == 1) { // hide player 2 stats if only one player
			tempP2Stats.setStyle("-fx-text-fill: transparent;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";"
					+ "\n" + "-fx-font-size: 24;" + "\n");
			uiSprite2.setVisible(false);
		}

		gameUI.setCenter(centerBox);
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
			for (int c = blockCount; c != 0; c--) { // generate blocks if no
													// input given
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

		Scene Game = new Scene(center);// , screenSize.getWidth(),
										// screenSize.getHeight());

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
				final Stage inGameMenu = new Stage();
				timer.stop();
				inGameMenu.initModality(Modality.APPLICATION_MODAL);
				inGameMenu.initOwner(primaryStage);
				VBox inGameMenuOptions = new VBox(2);

				String background = "-fx-background-image: url(file:images/background.png);" + "\n"
						+ "-fx-background-size: stretch;" + "\n" + "-fx-background-repeat: no-repeat;";

				// Title -- Game Paused!
				Label title = new Label();
				title.setText("Game Paused!");
				title.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
						+ "-fx-font-size: 65;" + "\n");

				BorderPane topSection = new BorderPane();
				topSection.setCenter(title);
				title.setTranslateY(-20);

				// Buttons

				Button resume = new Button("");

				BackgroundImage resumeBackground = new BackgroundImage(
						new Image(new File("images/resume.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
				Background resumeImage = new Background(resumeBackground);
				resume.setBackground(resumeImage);
				resume.setPrefSize(300, 80);

				BackgroundImage resumeBackgroundHover = new BackgroundImage(
						new Image(new File("images/resume_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
				Background resumeImageHover = new Background(resumeBackgroundHover);

				resume.setOnMouseEntered(actionEvent -> {
					resume.setBackground(resumeImageHover);
				});

				resume.setOnMouseExited(actionEvent -> {
					resume.setBackground(resumeImage);
				});

				Button restart = new Button("");

				BackgroundImage restartBackground = new BackgroundImage(
						new Image(new File("images/restart.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
				Background restartImage = new Background(restartBackground);
				restart.setBackground(restartImage);
				restart.setPrefSize(300, 80);

				BackgroundImage restartBackgroundHover = new BackgroundImage(
						new Image(new File("images/restart_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
				Background restartImageHover = new Background(restartBackgroundHover);

				restart.setOnMouseEntered(actionEvent -> {
					restart.setBackground(restartImageHover);
				});

				restart.setOnMouseExited(actionEvent -> {
					restart.setBackground(restartImage);
				});

				Button changeLevel = new Button("");

				BackgroundImage changeLevelBackground = new BackgroundImage(
						new Image(new File("images/ingame_change_level.png").toURI().toString()),
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
						BackgroundSize.DEFAULT);
				Background changeLevelImage = new Background(changeLevelBackground);
				changeLevel.setBackground(changeLevelImage);
				changeLevel.setPrefSize(370, 80);

				BackgroundImage changeLevelBackgroundHover = new BackgroundImage(
						new Image(new File("images/ingame_change_level_arrow.png").toURI().toString()),
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
						BackgroundSize.DEFAULT);
				Background changeLevelImageHover = new Background(changeLevelBackgroundHover);

				changeLevel.setOnMouseEntered(actionEvent -> {
					changeLevel.setBackground(changeLevelImageHover);
				});

				changeLevel.setOnMouseExited(actionEvent -> {
					changeLevel.setBackground(changeLevelImage);
				});

				Button mainMenu = new Button("");

				BackgroundImage mainMenuBackground = new BackgroundImage(
						new Image(new File("images/ingame_main.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
				Background mainMenuImage = new Background(mainMenuBackground);
				mainMenu.setBackground(mainMenuImage);
				mainMenu.setPrefSize(300, 80);

				BackgroundImage mainMenuBackgroundHover = new BackgroundImage(
						new Image(new File("images/ingame_main_arrow.png").toURI().toString()),
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
						BackgroundSize.DEFAULT);
				Background mainMenuImageHover = new Background(mainMenuBackgroundHover);

				mainMenu.setOnMouseEntered(actionEvent -> {
					mainMenu.setBackground(mainMenuImageHover);
				});

				mainMenu.setOnMouseExited(actionEvent -> {
					mainMenu.setBackground(mainMenuImage);
				});

				AnchorPane.setTopAnchor(mainMenu, 490d);
				AnchorPane.setLeftAnchor(mainMenu, 310d);

				Button quit = new Button("");

				BackgroundImage quitBackground = new BackgroundImage(
						new Image(new File("images/ingame_quit.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
				Background quitImage = new Background(quitBackground);
				quit.setBackground(quitImage);
				quit.setPrefSize(300, 80);

				BackgroundImage quitBackgroundHover = new BackgroundImage(
						new Image(new File("images/ingame_quit_arrow.png").toURI().toString()),
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
						BackgroundSize.DEFAULT);
				Background quitImageHover = new Background(quitBackgroundHover);

				quit.setOnMouseEntered(actionEvent -> {
					quit.setBackground(quitImageHover);
				});

				quit.setOnMouseExited(actionEvent -> {
					quit.setBackground(quitImage);
				});

				// Button functionality
				resume.setOnAction(actionEvent -> {
					timer.start();
					inGameMenu.close();
				});

				restart.setOnAction(actionEvent -> {
					resetGame(grid, primaryStage, menu, playerCount, blockCount, inputFile);
					inGameMenu.close();
				});

				changeLevel.setOnAction(actionEvent -> {
					PuzzleHome levelSelect = new PuzzleHome();

					// Using the file name to indicate whether game is easy,
					// medium or hard.
					String levelDifficulty = inputFile.getName();
					if (levelDifficulty.charAt(0) == 'e') {
						primaryStage.setScene(levelSelect.easyDifficulty(primaryStage, menu, playerCount));
					} else if (levelDifficulty.charAt(0) == 'm') {
						primaryStage.setScene(levelSelect.mediumDifficulty(primaryStage, menu, playerCount));
					} else if (levelDifficulty.charAt(0) == 'h') {
						primaryStage.setScene(levelSelect.hardDifficulty(primaryStage, menu, playerCount));
					}

					inGameMenu.close();
				});

				mainMenu.setOnAction(actionEvent -> {
					primaryStage.setScene(menu);
					inGameMenu.close();
				});

				quit.setOnAction(actionEvent -> {
					Alert confirmExit = new Alert(AlertType.CONFIRMATION, "Would you like to exit Wacky Warehouse?",
							ButtonType.OK, ButtonType.CANCEL);
					confirmExit.setTitle("Exit Game");
					confirmExit.showAndWait();

					if (confirmExit.getResult() == ButtonType.OK) {
						Platform.exit();
					}
				});

				// Create a VBox to store the buttons.

				VBox buttons = new VBox();
				buttons.getChildren().addAll(resume, restart, changeLevel, mainMenu, quit);
				// buttons.setSpacing(10);
				// buttons.setTranslateY(20);
				// buttons.setTranslateX(10)

				buttons.setAlignment(Pos.CENTER);
				inGameMenuOptions.getChildren().addAll(topSection, buttons);
				inGameMenuOptions.setAlignment(Pos.CENTER);

				// Set the style of the VBox
				inGameMenuOptions.setStyle(background);
				Scene inGameMenuScene = new Scene(inGameMenuOptions, 500, 600);

				inGameMenu.setScene(inGameMenuScene);
				inGameMenu.show();

				inGameMenu.setOnCloseRequest(actionEvent -> {
					timer.start();
				});

				inGameMenu.setTitle("Game Menu");
				try {
					inGameMenu.getIcons().add(new Image(new FileInputStream(new File("images/sprite1.png"))));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				// Turned off full screen mode for inGameMenu
				inGameMenu.setResizable(false);
			} else if (event.getCode() == KeyCode.R) {
				resetGame(grid, primaryStage, menu, playerCount, blockCount, inputFile);
			} else if (event.getCode() == KeyCode.U) {
				undoGame(tempP1Image, tempP2Image);
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
			// refresh ui elements
			Label tempP1 = new Label(
					"Moves: " + this.player1.getMoveCount() + "\nPushes: " + this.player1.getBlockMoveCount());
			tempP1.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
					+ "-fx-font-size: 24;" + "\n");
			Label tempP2 = new Label(
					"Moves: " + this.player2.getMoveCount() + "\nPushes: " + this.player2.getBlockMoveCount());
			tempP2.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
					+ "-fx-font-size: 24;" + "\n");

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

			if (playerCount == 1) { // hide player 2 stats if only one player
				tempP2.setStyle("-fx-text-fill: transparent;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";"
						+ "\n" + "-fx-font-size: 24;" + "\n");
				uiSprite2.setVisible(false);
			}
			gameUI.setLeft(leftStats);
			gameUI.setRight(rightStats);
			checkVictory(primaryStage, menu, grid, this.blockList, playerCount, inputFile, grid, blockCount);
		});
		return Game;

	}

	private void checkVictory(Stage primaryStage, Scene menu, Cell[][] map, ArrayList<Block> blockList, int playerCount,
			File inputFile, Cell[][] grid, int blockCount) {
		int gameWidth = map.length;
		int gameHeight = map[0].length;
		ArrayList<Block> tempList = new ArrayList<Block>(blockList);

		// set block colors
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

		// check if each target has a block on it, and remove the block from the
		// temp list if there is
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

		// if list is empty, show the victory screen
		if (tempList.isEmpty()) {
			primaryStage.setScene(victoryScreen(primaryStage, menu, playerCount, inputFile, grid, blockCount));
		}
	}

	private Scene victoryScreen(Stage primaryStage, Scene menu, int playerCount, File inputFile, Cell[][] grid,
			int blockCount) {
		AnchorPane victoryPane = new AnchorPane();

		Label victoryText = new Label("Level Complete!");
		victoryText.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
				+ "-fx-font-size: 72;" + "\n"); // ADDED

		AnchorPane.setTopAnchor(victoryText, 75d);
		AnchorPane.setLeftAnchor(victoryText, 220d);

		Label summaryText = new Label("Summary");
		summaryText.setUnderline(true);
		summaryText.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
				+ "-fx-font-size: 72;" + "\n"); // ADDED

		AnchorPane.setTopAnchor(summaryText, 175d);
		AnchorPane.setLeftAnchor(summaryText, 360d);

		timerString = timerString.replaceAll("\\s+", "");

		Label timeText = new Label("Time Taken: " + timerString);
		timeText.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
				+ "-fx-font-size: 72;" + "\n"); // ADDED

		AnchorPane.setTopAnchor(timeText, 280d);
		AnchorPane.setLeftAnchor(timeText, 145d);

		Label moveText = new Label("Moves: " + player1.getMoveCount());
		moveText.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
				+ "-fx-font-size: 72;" + "\n"); // ADDED

		AnchorPane.setTopAnchor(moveText, 360d);
		AnchorPane.setLeftAnchor(moveText, 150d);

		Label pushesText = new Label("Pushes: " + player1.getBlockMoveCount());
		pushesText.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
				+ "-fx-font-size: 72;" + "\n"); // ADDED

		AnchorPane.setTopAnchor(pushesText, 440d);
		AnchorPane.setLeftAnchor(pushesText, 150d);

		// play again button
		Button replayButton = new Button("");
		BackgroundImage replayButtonBackground = new BackgroundImage(
				new Image(new File("images/replay.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background replayButtonImage = new Background(replayButtonBackground);
		replayButton.setBackground(replayButtonImage);
		replayButton.setPrefSize(300, 100);

		BackgroundImage replayButtonBackgroundHover = new BackgroundImage(
				new Image(new File("images/replay_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
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
		BackgroundImage levelButtonBackground = new BackgroundImage(
				new Image(new File("images/level_select.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background levelButtonImage = new Background(levelButtonBackground);
		levelButton.setBackground(levelButtonImage);
		levelButton.setPrefSize(300, 100);

		BackgroundImage levelButtonBackgroundHover = new BackgroundImage(
				new Image(new File("images/level_select_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
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
		BackgroundImage menuButtonBackground = new BackgroundImage(
				new Image(new File("images/main.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background menuButtonImage = new Background(menuButtonBackground);
		menuButton.setBackground(menuButtonImage);
		menuButton.setPrefSize(300, 100);

		BackgroundImage menuButtonBackgroundHover = new BackgroundImage(
				new Image(new File("images/main_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
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

		victoryPane.getChildren().addAll(victoryText, timeText, moveText, pushesText, summaryText, replayButton,
				levelButton, menuButton);

		Scene helpScene = new Scene(victoryPane, 960, 800);
		victoryPane.setStyle(background);

		return helpScene;
	}

	// reset values to original
	private void resetGame(Cell[][] grid, Stage primaryStage, Scene menu, int playerCount, int blockCount,
			File inputFile) {
		this.player1 = new Player(1, 0, 0); // assume 0, 0 is a wall and is
											// invalid
		this.player2 = new Player(2, 0, 0);
		this.blockList = new ArrayList<Block>();
		this.timerString = "";
		primaryStage.setScene(Game(grid, primaryStage, menu, playerCount, blockCount, inputFile));
	}

	private void undoGame(ImageView tempP1Image, ImageView tempP2Image) {
		LinkedList<LinkedList<Integer>> test = player1.getPosList();
		if (!test.isEmpty()) {
			player1.setX(test.getLast().get(0));
			player1.setY(test.getLast().get(1));
			this.gamePane.getChildren().remove(tempP1Image);
			this.gamePane.add(this.player1.getPlayerImage(), this.player1.getX(), this.player1.getY());
			test.getLast().removeFirst();
			test.getLast().removeFirst();
			if (test.getLast().peek() != 0) {
				player2.setX(test.getLast().get(0));
				player2.setY(test.getLast().get(1));
				this.gamePane.getChildren().remove(tempP2Image);
				this.gamePane.add(this.player2.getPlayerImage(), this.player2.getX(), this.player2.getY());
			}
			test.getLast().removeFirst();
			test.getLast().removeFirst();
			for (Block block : this.blockList) {
				block.setX(test.getLast().get(0));
				block.setY(test.getLast().get(1));
				this.gamePane.getChildren().remove(block.getBlockImage());
				this.gamePane.add(block.getBlockImage(), block.getX(), block.getY());
				test.getLast().removeFirst();
				test.getLast().removeFirst();
			}
			test.removeLast();
		}
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
					// 0, 0 is the default location
					if (this.player1.getX() == 0 && this.player1.getY() == 0) {
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
