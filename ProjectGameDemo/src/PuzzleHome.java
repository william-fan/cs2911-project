import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class PuzzleHome extends Application {

	// Background style
	private String background = "-fx-background-image: url(file:images/background.png);" + "\n"
	   					+ "-fx-background-size: stretch;" + "\n"
	   					+ "-fx-background-repeat: no-repeat;";
	
	private Scene helpPage(Stage primaryStage, Scene menu) {
		try {
			Font.loadFont(new FileInputStream(new File("fonts/FSEX300.ttf")), 11);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Create helpPane
		BorderPane helpPane = new BorderPane();
		// Create the scene to display the helpPane
		Scene helpScene = new Scene(helpPane);
		// Set the size of the windows
		helpPane.setPrefSize(960, 800);
		// Add black background with orange border
		helpPane.setStyle(background);
		
		// Create title called instructions
		Text title = new Text();
		title.setText("Instructions\n");
		title.setFont(Font.font ("Fixedsys Excelsior 3.01", 80));
		title.setFill(Color.WHITE);
		title.setTranslateY(30);
		
		// Position the title at the top
		helpPane.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);
		
		
		// Left pane will contain the Player 1 control keys
		VBox leftBox = new VBox(4);
		Text onePlayer = new Text();
		onePlayer.setText(" Player 1: \n Arrow Keys \n ");
		onePlayer.setFont(Font.font ("Fixedsys Excelsior 3.01", 30));
		onePlayer.setFill(Color.WHITE);
		
			// Store the onePlayerSprite as image
		Image onePlayerSprite = new Image("File:images/sprite1.png");
		
			// Store the onePlayerControls image
		Image onePlayerControls = new Image("File:images/1Player_controls.png");
		
			// Add the onePlayer text and image to the leftBox
		leftBox.getChildren().addAll(new ImageView(onePlayerSprite), onePlayer, new ImageView(onePlayerControls));
		leftBox.setAlignment(Pos.TOP_CENTER);
		leftBox.setSpacing(10);
		
		// Right pane will contain the Player 2 control keys
		VBox rightBox = new VBox(2);
		Text twoPlayer = new Text();
		
			// Store the twoPlayerSprite image
		Image twoPlayerSprite = new Image("File:images/sprite2.png");
		
			// Player 2 help instructions
		twoPlayer.setText("Player 2: \nWASD keys \n");
		twoPlayer.setFont(Font.font ("Fixedsys Excelsior 3.01", 30));
		twoPlayer.setFill(Color.WHITE);
		
			// W 
		Text twoPlayerControlUp = new Text();
		twoPlayerControlUp.setText("W");
		twoPlayerControlUp.setFont(Font.font ("Fixedsys Excelsior 3.01", 50));
		twoPlayerControlUp.setFill(Color.WHITE);
		twoPlayerControlUp.setTextAlignment(TextAlignment.CENTER);
		
			// A S D
		Text twoPlayerControlsLeftDownRight = new Text();
		twoPlayerControlsLeftDownRight.setText("A S D\n");
		twoPlayerControlsLeftDownRight.setFont(Font.font ("Fixedsys Excelsior 3.01", 50));
		twoPlayerControlsLeftDownRight.setFill(Color.WHITE);
		twoPlayerControlsLeftDownRight.setTextAlignment(TextAlignment.CENTER);
		
			// Add the twoPlayer instructions to the rightBox
		rightBox.getChildren().addAll(new ImageView(twoPlayerSprite), twoPlayer, twoPlayerControlUp, twoPlayerControlsLeftDownRight);
		rightBox.setAlignment(Pos.TOP_CENTER);
		rightBox.setSpacing(10);
		
		// Create a HBox with three columns that will contain the leftBox, centerBox and rightBox
		HBox centerBox = new HBox(3);
	
		// Create a VBox that will serve as the container for content for the center
		VBox centerContent = new VBox(2);
		
		// Center image
		Image centerImage = new Image("File:images/title.png");
		ImageView centerImageNode = new ImageView(centerImage);
		
		Text info = new Text();
		info.setText("\n \n \n Your goal is to push all the boxes to the goal squares.\n \n Be careful, because you cannot pull boxes.");
		info.setFont(Font.font ("Fixedsys Excelsior 3.01", 30));
		info.setFill(Color.WHITE);
		info.setTextAlignment(TextAlignment.LEFT);
		
		
		centerContent.getChildren().add(0, centerImageNode);
		centerContent.getChildren().add(1, info);
		centerContent.setAlignment(Pos.TOP_CENTER);
		centerContent.setSpacing(10);
		
		centerBox.getChildren().addAll(leftBox, centerContent, rightBox);
		
		// Modifying the margins for the three VBoxes that are contained within the centerBox.
		HBox.setMargin(leftBox, new Insets(-10, -160, 12, 50));
		HBox.setMargin(centerBox, new Insets(12, 15, 12, 15));
		HBox.setMargin(rightBox, new Insets(-10, 50, 12, -160));
		helpPane.setCenter(centerBox);
		
		// Adding the main menu button to the instruction page
		HBox bottomBox = new HBox();
		
		
		Button mainMenuButton = new Button("");
    	BackgroundImage mainMenuBackgroundWithoutArrow = new BackgroundImage( new Image(new File("images/mainMenu.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background mainMenuWithoutArrow = new Background(mainMenuBackgroundWithoutArrow);
        BackgroundImage mainMenuBackgroundWithArrow = new BackgroundImage( new Image(new File("images/mainMenu_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background mainMenuWithArrow = new Background(mainMenuBackgroundWithArrow);
    	mainMenuButton.setPrefSize(177, 59);
    	//HBox.setMargin(bottomBox, new Insets(100, 50, -10, 160));
    	mainMenuButton.setBackground(mainMenuWithoutArrow);
    	
    	mainMenuButton.setOnMouseEntered(actionEvent -> {
    		mainMenuButton.setBackground(mainMenuWithArrow);
    	});
    	
    	mainMenuButton.setOnMouseExited(actionEvent -> {
    		mainMenuButton.setBackground(mainMenuWithoutArrow);
    	});
    	
    	mainMenuButton.setOnAction(actionEvent -> {
    		primaryStage.setScene(menu);
    	});
    	
		bottomBox.getChildren().add(mainMenuButton);
		
		helpPane.setBottom(bottomBox);
		//System.out.println("bottomBox X layout = " + bottomBox.getLayoutX() + " bottomBox Y layout = " + bottomBox.getLayoutY());
		bottomBox.setTranslateX(756);
		bottomBox.setTranslateY(-40);
		//System.out.println("bottomBox X layout = " + bottomBox.getLayoutX() + " bottomBox Y layout = " + bottomBox.getLayoutY());
		return helpScene;
	}

	private Cell[][] generateMap(int difficulty) {
		Cell[][] map = null;
		if (difficulty == 0) {
			map = new Cell[10][10];
			for (int x = 0; x != 10; x++) {
				for (int y = 0; y != 10; y++) {
					Cell cell = new Cell(x, y, 0);
					if (x == 0) {
						cell.setType(1);
					}
					if (y == 0) {
						cell.setType(1);
					}
					if (y == 9) {
						cell.setType(1);
					}
					if (x == 9) {
						cell.setType(1);
					}
					if (x == 8 && y == 8) {
						cell.setType(2);
					}
					map[x][y] = cell;
				}
			}
		}
		return map;
	}

	private Cell[][] readMap(File f) {
		Scanner scanChars = scanFile(f.getPath());
		Scanner scanLines = scanFile(f.getPath());
		Scanner scanMap = scanFile(f.getPath());
		int charCount = 0;
		
		// Get how many characters in a line
		while (scanChars.hasNext()) {
			String next = scanChars.next();
			if (next.equals("#")) {
				break;
			}
			charCount++;
		}
		
		// Get number of lines
		int lineCount = 0;
		while (scanLines.hasNext()) {
			String next = scanLines.next();
			if (next.equals("#")) {
				lineCount++;
			}
		}
		Cell[][] map = new Cell[charCount][lineCount];
		int x = 0;
		int y = 0;
		// Set cell types
		while (scanMap.hasNext()) {
			String next = scanMap.next();
			Cell current = new Cell(x, y, 0);
			if (!next.equals("#")) {
				current.setType(0);
			}
			if (next.equals("5")) {
				current.setType(2);
			}
			if (next.equals("4")) {
				current.setType(1);
			}
			if (next.equals("3")) {
				current.setType(3);
			}
			if (next.equals("2")) {
				current.setType(4);
			}
			if (next.equals("#") && scanMap.hasNext()) {
				scanMap.nextLine();
				y++;
				x = 0;
			} else if (next.equals("#")) {

			} else {
				try {
					map[x][y] = current;
				} catch (ArrayIndexOutOfBoundsException exception) {
		        	return null;
				}
				x++;
			}
		}
		scanChars.close();
		scanLines.close();
		scanMap.close();
		return map;
	}

	private Scene selectDifficulty(Stage primaryStage, Scene menu, int playerCount) {
		PuzzleGame game = new PuzzleGame();
		
		AnchorPane difficultyPane = new AnchorPane();
		difficultyPane.setStyle(background);
		
		if (playerCount == 1) {
			Image singleTitle = new Image(new File("images/1Player_title.png").toURI().toString());
			ImageView singleTitleShow = new ImageView(singleTitle);
			singleTitleShow.setFitWidth(280);
			singleTitleShow.setPreserveRatio(true);
			
			AnchorPane.setTopAnchor(singleTitleShow, 85d);
		    AnchorPane.setLeftAnchor(singleTitleShow, 340d);	    
		    difficultyPane.getChildren().add(singleTitleShow); 
		    
			Image singlePicture = new Image(new File("images/player1_difficulty.png").toURI().toString());
			ImageView singlePictureShow = new ImageView(singlePicture);
			singlePictureShow.setFitWidth(320);
			singlePictureShow.setPreserveRatio(true);
	    	
		    AnchorPane.setTopAnchor(singlePictureShow, 180d);
		    AnchorPane.setLeftAnchor(singlePictureShow, 320d);	    
		    difficultyPane.getChildren().add(singlePictureShow); 	
		} else {
			Image multiTitle = new Image(new File("images/2Player_title.png").toURI().toString());
			ImageView multiTitleShow = new ImageView(multiTitle);
			multiTitleShow.setFitWidth(280);
			multiTitleShow.setPreserveRatio(true);
			
		    AnchorPane.setTopAnchor(multiTitleShow, 85d);
		    AnchorPane.setLeftAnchor(multiTitleShow, 340d);	    
		    difficultyPane.getChildren().add(multiTitleShow); 
		    
			Image multiPicture = new Image(new File("images/2player_difficulty.png").toURI().toString());
			ImageView multiPictureShow = new ImageView(multiPicture);
			multiPictureShow.setFitWidth(320);
			multiPictureShow.setPreserveRatio(true);
	    	
		    AnchorPane.setTopAnchor(multiPictureShow, 180d);
		    AnchorPane.setLeftAnchor(multiPictureShow, 320d);	    
		    difficultyPane.getChildren().add(multiPictureShow); 
	    }
	    
		Button easyButton = new Button("");
		BackgroundImage easyButtonBackground = new BackgroundImage(new Image(new File("images/easy_selection.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background easyButtonImage = new Background(easyButtonBackground);
	    easyButton.setBackground(easyButtonImage);
	    easyButton.setPrefSize(300, 100);
	    
	    BackgroundImage easyButtonBackgroundHover = new BackgroundImage(new Image(new File("images/easy_selection_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background easyButtonImageHover = new Background(easyButtonBackgroundHover);
    	
		easyButton.setOnAction(actionEvent -> {
			primaryStage.setScene(easyDifficulty(primaryStage, menu, playerCount));
		});

     	easyButton.setOnMouseEntered(actionEvent -> {
     		easyButton.setBackground(easyButtonImageHover);
		});
	    
     	easyButton.setOnMouseExited(actionEvent -> {
     		easyButton.setBackground(easyButtonImage);
		});
	    
	    AnchorPane.setTopAnchor(easyButton, 420d);
	    AnchorPane.setLeftAnchor(easyButton, 310d);	    
	    difficultyPane.getChildren().add(easyButton); 
	    
	    Button mediumButton = new Button("");
		BackgroundImage mediumButtonBackground = new BackgroundImage(new Image(new File("images/medium_selection.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background mediumButtonImage = new Background(mediumButtonBackground);
	    mediumButton.setBackground(mediumButtonImage);
	    mediumButton.setPrefSize(300, 100);
	    
	    BackgroundImage mediumButtonBackgroundHover = new BackgroundImage(new Image(new File("images/medium_selection_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background mediumButtonImageHover = new Background(mediumButtonBackgroundHover);
    	
	    mediumButton.setOnAction(actionEvent -> {
			primaryStage.setScene(mediumDifficulty(primaryStage, menu, playerCount));
		});

	    mediumButton.setOnMouseEntered(actionEvent -> {
	    	mediumButton.setBackground(mediumButtonImageHover);
		});
	    
	    mediumButton.setOnMouseExited(actionEvent -> {
	    	mediumButton.setBackground(mediumButtonImage);
		});
	    
	    AnchorPane.setTopAnchor(mediumButton, 490d);
	    AnchorPane.setLeftAnchor(mediumButton, 310d);	    
	    difficultyPane.getChildren().add(mediumButton); 
	    
	    Button hardButton = new Button("");
		BackgroundImage hardButtonBackground = new BackgroundImage(new Image(new File("images/hard_selection.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background hardButtonImage = new Background(hardButtonBackground);
	    hardButton.setBackground(hardButtonImage);
	    hardButton.setPrefSize(300, 100);
	    
	    BackgroundImage hardButtonBackgroundHover = new BackgroundImage(new Image(new File("images/hard_selection_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background hardButtonImageHover = new Background(hardButtonBackgroundHover);
    	
	    hardButton.setOnAction(actionEvent -> {
			primaryStage.setScene(hardDifficulty(primaryStage, menu, playerCount));
		});

	    hardButton.setOnMouseEntered(actionEvent -> {
	    	hardButton.setBackground(hardButtonImageHover);
		});
	    
	    hardButton.setOnMouseExited(actionEvent -> {
	    	hardButton.setBackground(hardButtonImage);
		});
	    
	    AnchorPane.setTopAnchor(hardButton, 555d);
	    AnchorPane.setLeftAnchor(hardButton, 310d);	    
	    difficultyPane.getChildren().add(hardButton); 
	    
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
	    	menuButton.setPrefSize(300, 100);
		});
	    
	    menuButton.setOnMouseExited(actionEvent -> {
	    	menuButton.setBackground(menuButtonImage);
		});
	    
    	AnchorPane.setBottomAnchor(menuButton, 20d);
	    AnchorPane.setLeftAnchor(menuButton, 680d);
	    difficultyPane.getChildren().add(menuButton);
	    
		Button customMap = new Button("");
		BackgroundImage customMapBackground = new BackgroundImage(new Image(new File("images/custom_map_select.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background customMapImage = new Background(customMapBackground);
		customMap.setBackground(customMapImage);
		customMap.setPrefSize(320, 100);
		
		BackgroundImage customMapBackgroundHover = new BackgroundImage(new Image(new File("images/custom_map_select_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background customMapImageHover = new Background(customMapBackgroundHover);
	    customMap.setBackground(customMapImage);
	    
		customMap.setOnAction(actionEvent -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Map File");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Map Files", "*.map"));
			File f = fileChooser.showOpenDialog(primaryStage);
			
			if (f != null) {
				if (readMap(f) == null ) {
		        	Alert confirmExit = new Alert(AlertType.ERROR, "File must be of correct type", ButtonType.OK);
		        	confirmExit.setTitle("Exit Game");
		        	confirmExit.showAndWait();
				} else {
					primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
				}
			}
		});
		
		customMap.setOnMouseEntered(actionEvent -> {
			customMap.setBackground(customMapImageHover);
		});
	    
		customMap.setOnMouseExited(actionEvent -> {
			customMap.setBackground(customMapImage);
		});
		
	    AnchorPane.setTopAnchor(customMap, 620d);
	    AnchorPane.setLeftAnchor(customMap, 310d);	
	    difficultyPane.getChildren().add(customMap);
	    
		Scene difficultyScene = new Scene(difficultyPane, 960, 800);
		return difficultyScene;
	}
	
	// 
	// DIFFICULTY SELECTION SCREENS
	//
	public Scene easyDifficulty(Stage primaryStage, Scene menu, int playerCount) {
		
		PuzzleGame game = new PuzzleGame();
	
		Image easyTitle = new Image(new File("images/easy.png").toURI().toString());
		ImageView easyTitleShow = new ImageView(easyTitle);
	    AnchorPane.setTopAnchor(easyTitleShow, 40d);
	    AnchorPane.setLeftAnchor(easyTitleShow, 400d);
	    
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
	    AnchorPane.setLeftAnchor(menuButton, 360d);
	    	
		HBox levelSelect = new HBox();
		levelSelect.setSpacing(10);
		
		Button levelOne = new Button("");
	    BackgroundImage levelOneBackground = new BackgroundImage(new Image(new File("images/level1.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background levelOneImage = new Background(levelOneBackground);

	    levelOne.setPrefSize(200, 200);
	    levelOne.setBackground(levelOneImage);
	    
	    levelOne.setOnAction(actionEvent -> {
			File f = new File("maps/easy_" + playerCount + "_1.txt");		
			if (f.exists() && !f.isDirectory()) {
				primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
			} else {
				primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, playerCount, 3, null));
			}
		});
	    
		Button levelTwo = new Button("");
	    BackgroundImage levelTwoBackground = new BackgroundImage(new Image(new File("images/level2.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background levelTwoImage = new Background(levelTwoBackground);
	    
	    levelTwo.setPrefSize(200, 200);
	    levelTwo.setBackground(levelTwoImage);
	    
	    levelTwo.setOnAction(actionEvent -> {
			File f = new File("maps/easy_" + playerCount + "_2.txt");		
			if (f.exists() && !f.isDirectory()) {
				primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
			} else {
				primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, playerCount, 3, null));
			}
		});
	    
		Button levelThree = new Button("");
	    BackgroundImage levelThreeBackground = new BackgroundImage(new Image(new File("images/level3.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background levelThreeImage = new Background(levelThreeBackground);
	    
	    levelThree.setPrefSize(200, 200);
	    levelThree.setBackground(levelThreeImage);
	    
	    levelThree.setOnAction(actionEvent -> {
			File f = new File("maps/easy_" + playerCount + "_3.txt");		
			if (f.exists() && !f.isDirectory()) {
				primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
			} else {
				primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, playerCount, 3, null));
			}
		});
	    
		Button levelFour = new Button("");
	    BackgroundImage levelFourBackground = new BackgroundImage(new Image(new File("images/level4.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background levelFourImage = new Background(levelFourBackground);
	    
	    levelFour.setPrefSize(200, 200);
	    levelFour.setBackground(levelFourImage);
	    
	    levelFour.setOnAction(actionEvent -> {
			File f = new File("maps/easy_" + playerCount + "_4.txt");		
			if (f.exists() && !f.isDirectory()) {
				primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
			} else {
				primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, playerCount, 3, null));
			}
		});
	    
		Button levelFive = new Button("");
	    BackgroundImage levelFiveBackground = new BackgroundImage(new Image(new File("images/level5.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background levelFiveImage = new Background(levelFiveBackground);
	    
	    levelFive.setPrefSize(200, 200);
	    levelFive.setBackground(levelFiveImage);

	    levelFive.setOnAction(actionEvent -> {
			File f = new File("maps/easy_" + playerCount + "_5.txt");		
			if (f.exists() && !f.isDirectory()) {
				primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
			} else {
				primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, playerCount, 3, null));
			}
		});
	    
		AnchorPane easyPane = new AnchorPane();
		easyPane.setStyle(background);
		
		if (playerCount == 1) {
			easyPane.getChildren().addAll(easyTitleShow, levelOne, levelTwo, levelThree, levelFour, levelFive, menuButton);
		} else {
			easyPane.getChildren().addAll(easyTitleShow, levelOne, levelTwo, levelThree, menuButton);
		}
		
		if (playerCount == 1) {
			AnchorPane.setTopAnchor(levelOne, 125d);
	    	AnchorPane.setLeftAnchor(levelOne, 160d);
		} else {
			AnchorPane.setTopAnchor(levelOne, 125d);
	    	AnchorPane.setLeftAnchor(levelOne, 290d);
		}
		
	    Image levelOneMap = new Image(new File("images/easy_1_preview.png").toURI().toString());
    	ImageView levelOnePreview = new ImageView(levelOneMap);
    	levelOnePreview.setFitWidth(500);
    	levelOnePreview.setPreserveRatio(true);
    	
    	Image levelOneMap2P = new Image(new File("images/easy2p_1_preview.png").toURI().toString());
     	ImageView levelOnePreview2P = new ImageView(levelOneMap2P);
     	levelOnePreview2P.setFitWidth(500);
     	levelOnePreview2P.setPreserveRatio(true);
    	
	    levelOne.setOnMouseEntered(actionEvent -> {
	    	if (playerCount == 1) {
	    		easyPane.getChildren().add(levelOnePreview);
	    		AnchorPane.setBottomAnchor(levelOnePreview, 150d);
		    	AnchorPane.setLeftAnchor(levelOnePreview, 245d);
	    	} else {
	    		easyPane.getChildren().add(levelOnePreview2P);
	    		AnchorPane.setBottomAnchor(levelOnePreview2P, 150d);
		    	AnchorPane.setLeftAnchor(levelOnePreview2P, 240d);
	    	}
		});
	    
	    levelOne.setOnMouseExited(actionEvent -> {
	    	if (playerCount == 1) {
	    		easyPane.getChildren().remove(levelOnePreview);
	    	} else {
	    		easyPane.getChildren().remove(levelOnePreview2P);
	    	}
		});
	    
	    if (playerCount == 1) {
	    	AnchorPane.setTopAnchor(levelTwo, 125d);
	    	AnchorPane.setLeftAnchor(levelTwo, 295d);
	    } else {
	    	AnchorPane.setTopAnchor(levelTwo, 125d);
	    	AnchorPane.setLeftAnchor(levelTwo, 420d);
	    }
	    
	    Image levelTwoMap = new Image(new File("images/easy_2_preview.png").toURI().toString());
    	ImageView levelTwoPreview = new ImageView(levelTwoMap);
    	levelTwoPreview.setFitWidth(500);
    	levelTwoPreview.setPreserveRatio(true);
    	
    	Image levelTwoMap2P = new Image(new File("images/easy2p_2_preview.png").toURI().toString());
     	ImageView levelTwoPreview2P = new ImageView(levelTwoMap2P);
     	levelTwoPreview2P.setFitWidth(500);
     	levelTwoPreview2P.setPreserveRatio(true);
    	
	    levelTwo.setOnMouseEntered(actionEvent -> {
	    	if (playerCount == 1) {
	    		easyPane.getChildren().add(levelTwoPreview);
	    		AnchorPane.setBottomAnchor(levelTwoPreview, 150d);
		    	AnchorPane.setLeftAnchor(levelTwoPreview, 245d);
	    	} else {
	    	    easyPane.getChildren().add(levelTwoPreview2P);
	    		AnchorPane.setBottomAnchor(levelTwoPreview2P, 150d);
		    	AnchorPane.setLeftAnchor(levelTwoPreview2P, 240d);
	    	}
		});
	    
	    levelTwo.setOnMouseExited(actionEvent -> {
	    	if (playerCount == 1) {
	    		easyPane.getChildren().remove(levelTwoPreview);
	    	} else {
	    		easyPane.getChildren().remove(levelTwoPreview2P);
	    	}
		});
	    
	    if (playerCount == 1) {
	    	AnchorPane.setTopAnchor(levelThree, 125d);
	    	AnchorPane.setLeftAnchor(levelThree, 430d);
	    } else {
	    	AnchorPane.setTopAnchor(levelThree, 125d);
	    	AnchorPane.setLeftAnchor(levelThree, 550d);
	    }
	    
	    Image levelThreeMap = new Image(new File("images/easy_3_preview.png").toURI().toString());
    	ImageView levelThreePreview = new ImageView(levelThreeMap);
    	levelThreePreview.setFitWidth(500);
    	levelThreePreview.setPreserveRatio(true);
    	
    	Image levelThreeMap2P = new Image(new File("images/easy2p_3_preview.png").toURI().toString());
     	ImageView levelThreePreview2P = new ImageView(levelThreeMap2P);
     	levelThreePreview2P.setFitWidth(500);
     	levelThreePreview2P.setPreserveRatio(true);
    	
	    levelThree.setOnMouseEntered(actionEvent -> {
	    	if (playerCount == 1) {
	    		easyPane.getChildren().add(levelThreePreview);
	    		AnchorPane.setBottomAnchor(levelThreePreview, 150d);
		    	AnchorPane.setLeftAnchor(levelThreePreview, 245d);
	    	} else {
	    		easyPane.getChildren().add(levelThreePreview2P);
	    		AnchorPane.setBottomAnchor(levelThreePreview2P, 150d);
		    	AnchorPane.setLeftAnchor(levelThreePreview2P, 240d);
	    	}
		});
	    
	    levelThree.setOnMouseExited(actionEvent -> {
	    	if (playerCount == 1) {
	    		easyPane.getChildren().remove(levelThreePreview);
	    	} else {
	    		easyPane.getChildren().remove(levelThreePreview2P);
	    	}
		});
	    
	    
	    AnchorPane.setTopAnchor(levelFour, 125d);
	    AnchorPane.setLeftAnchor(levelFour, 565d);
	    
	    Image levelFourMap = new Image(new File("images/easy_4_preview.png").toURI().toString());
    	ImageView levelFourPreview = new ImageView(levelFourMap);
    	levelFourPreview.setFitWidth(500);
    	levelFourPreview.setPreserveRatio(true);
    	
	    levelFour.setOnMouseEntered(actionEvent -> {
	    	if (playerCount == 1) {
	    		easyPane.getChildren().add(levelFourPreview);
	    		AnchorPane.setBottomAnchor(levelFourPreview, 150d);
		    	AnchorPane.setLeftAnchor(levelFourPreview, 245d);
	    	} 
		});
	    
	    levelFour.setOnMouseExited(actionEvent -> {
	    	if (playerCount == 1) {
	    		easyPane.getChildren().remove(levelFourPreview);
	    	}
		});
	    
	    AnchorPane.setTopAnchor(levelFive, 125d);
	    AnchorPane.setLeftAnchor(levelFive, 700d);
	    
	    Image levelFiveMap = new Image(new File("images/easy_5_preview.png").toURI().toString());
    	ImageView levelFivePreview = new ImageView(levelFiveMap);
    	levelFivePreview.setFitWidth(500);
    	levelFivePreview.setPreserveRatio(true);
    	
	    levelFive.setOnMouseEntered(actionEvent -> {
	    	if (playerCount == 1) {
	    		easyPane.getChildren().add(levelFivePreview);
	    		AnchorPane.setBottomAnchor(levelFivePreview, 150d);
		    	AnchorPane.setLeftAnchor(levelFivePreview, 245d);
	    	}
		});
	    
	    levelFive.setOnMouseExited(actionEvent -> {
	    	if (playerCount == 1) {
	    		easyPane.getChildren().remove(levelFivePreview);
	    	}
		});
	    
		Scene layoutDisplay = new Scene(easyPane, 960, 800);
		return layoutDisplay;
	}
	
	public Scene mediumDifficulty(Stage primaryStage, Scene menu, int playerCount) {
		
		PuzzleGame game = new PuzzleGame();
	
		Image mediumTitle = new Image(new File("images/medium.png").toURI().toString());
		ImageView mediumTitleShow = new ImageView(mediumTitle);
	    AnchorPane.setTopAnchor(mediumTitleShow, 40d);
	    AnchorPane.setLeftAnchor(mediumTitleShow, 360d);
	    
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
	    AnchorPane.setLeftAnchor(menuButton, 370d);
	    	
		HBox levelSelect = new HBox();
		levelSelect.setSpacing(10);
		
		Button levelOne = new Button("");
	    BackgroundImage levelOneBackground = new BackgroundImage(new Image(new File("images/level1.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background levelOneImage = new Background(levelOneBackground);

	    levelOne.setPrefSize(200, 200);
	    levelOne.setBackground(levelOneImage);
	    
	    levelOne.setOnAction(actionEvent -> {
			File f = new File("maps/medium_" + playerCount + "_1.txt");		
			if (f.exists() && !f.isDirectory()) {
				primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
			} else {
				primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, playerCount, 3, null));
			}
		});
	    
		Button levelTwo = new Button("");
	    BackgroundImage levelTwoBackground = new BackgroundImage(new Image(new File("images/level2.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background levelTwoImage = new Background(levelTwoBackground);
	    
	    levelTwo.setPrefSize(200, 200);
	    levelTwo.setBackground(levelTwoImage);
	    
	    levelTwo.setOnAction(actionEvent -> {
			File f = new File("maps/medium_" + playerCount + "_2.txt");		
			if (f.exists() && !f.isDirectory()) {
				primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
			} else {
				primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, playerCount, 3, null));
			}
		});
	    
		Button levelThree = new Button("");
	    BackgroundImage levelThreeBackground = new BackgroundImage(new Image(new File("images/level3.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background levelThreeImage = new Background(levelThreeBackground);
	    
	    levelThree.setPrefSize(200, 200);
	    levelThree.setBackground(levelThreeImage);
	    
	    levelThree.setOnAction(actionEvent -> {
			File f = new File("maps/medium_" + playerCount + "_3.txt");		
			if (f.exists() && !f.isDirectory()) {
				primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
			} else {
				primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, playerCount, 3, null));
			}
		});
	    
		Button levelFour = new Button("");
	    BackgroundImage levelFourBackground = new BackgroundImage(new Image(new File("images/level4.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background levelFourImage = new Background(levelFourBackground);
	    
	    levelFour.setPrefSize(200, 200);
	    levelFour.setBackground(levelFourImage);
	    
	    levelFour.setOnAction(actionEvent -> {
			File f = new File("maps/medium_" + playerCount + "_4.txt");		
			if (f.exists() && !f.isDirectory()) {
				primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
			} else {
				primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, playerCount, 3, null));
			}
		});
	    
		Button levelFive = new Button("");
	    BackgroundImage levelFiveBackground = new BackgroundImage(new Image(new File("images/level5.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background levelFiveImage = new Background(levelFiveBackground);
	    
	    levelFive.setPrefSize(200, 200);
	    levelFive.setBackground(levelFiveImage);

	    levelFive.setOnAction(actionEvent -> {
			File f = new File("maps/medium_" + playerCount + "_5.txt");		
			if (f.exists() && !f.isDirectory()) {
				primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
			} else {
				primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, playerCount, 3, null));
			}
		});
	    
		AnchorPane mediumPane = new AnchorPane();
		mediumPane.setStyle(background);
		
		if (playerCount == 1) {
			mediumPane.getChildren().addAll(mediumTitleShow, levelOne, levelTwo, levelThree, levelFour, levelFive, menuButton);
		} else {
			mediumPane.getChildren().addAll(mediumTitleShow, levelOne, levelTwo, levelThree, menuButton);
		}
		
		if (playerCount == 1) {
			AnchorPane.setTopAnchor(levelOne, 125d);
	    	AnchorPane.setLeftAnchor(levelOne, 160d);
		} else {
			AnchorPane.setTopAnchor(levelOne, 125d);
	    	AnchorPane.setLeftAnchor(levelOne, 290d);
		}
		
	    Image levelOneMap = new Image(new File("images/medium_1_preview.png").toURI().toString());
    	ImageView levelOnePreview = new ImageView(levelOneMap);
    	levelOnePreview.setFitWidth(500);
    	levelOnePreview.setPreserveRatio(true);
    	
    	Image levelOneMap2P = new Image(new File("images/medium2p_1_preview.png").toURI().toString());
     	ImageView levelOnePreview2P = new ImageView(levelOneMap2P);
     	levelOnePreview2P.setFitWidth(500);
     	levelOnePreview2P.setPreserveRatio(true);
    	
	    levelOne.setOnMouseEntered(actionEvent -> {
	    	if (playerCount == 1) {
	    		mediumPane.getChildren().add(levelOnePreview);
	    		AnchorPane.setBottomAnchor(levelOnePreview, 150d);
		    	AnchorPane.setLeftAnchor(levelOnePreview, 245d);
	    	} else {
	    		mediumPane.getChildren().add(levelOnePreview2P);
	    		AnchorPane.setBottomAnchor(levelOnePreview2P, 150d);
		    	AnchorPane.setLeftAnchor(levelOnePreview2P, 240d);
	    	}
		});
	    
	    levelOne.setOnMouseExited(actionEvent -> {
	    	if (playerCount == 1) {
	    		mediumPane.getChildren().remove(levelOnePreview);
	    	} else {
	    		mediumPane.getChildren().remove(levelOnePreview2P);
	    	}
		});
	    
	    if (playerCount == 1) {
	    	AnchorPane.setTopAnchor(levelTwo, 125d);
	    	AnchorPane.setLeftAnchor(levelTwo, 295d);
	    } else {
	    	AnchorPane.setTopAnchor(levelTwo, 125d);
	    	AnchorPane.setLeftAnchor(levelTwo, 420d);
	    }
	    
	    Image levelTwoMap = new Image(new File("images/medium_2_preview.png").toURI().toString());
    	ImageView levelTwoPreview = new ImageView(levelTwoMap);
    	levelTwoPreview.setFitWidth(500);
    	levelTwoPreview.setPreserveRatio(true);
    	
    	Image levelTwoMap2P = new Image(new File("images/medium2p_2_preview.png").toURI().toString());
     	ImageView levelTwoPreview2P = new ImageView(levelTwoMap2P);
     	levelTwoPreview2P.setFitWidth(500);
     	levelTwoPreview2P.setPreserveRatio(true);
    	
	    levelTwo.setOnMouseEntered(actionEvent -> {
	    	if (playerCount == 1) {
	    		mediumPane.getChildren().add(levelTwoPreview);
	    		AnchorPane.setBottomAnchor(levelTwoPreview, 150d);
		    	AnchorPane.setLeftAnchor(levelTwoPreview, 245d);
	    	} else {
	    		mediumPane.getChildren().add(levelTwoPreview2P);
	    		AnchorPane.setBottomAnchor(levelTwoPreview2P, 150d);
		    	AnchorPane.setLeftAnchor(levelTwoPreview2P, 240d);
	    	}
		});
	    
	    levelTwo.setOnMouseExited(actionEvent -> {
	    	if (playerCount == 1) {
	    		mediumPane.getChildren().remove(levelTwoPreview);
	    	} else {
	    		mediumPane.getChildren().remove(levelTwoPreview2P);
	    	}
		});
	    
	    if (playerCount == 1) {
	    	AnchorPane.setTopAnchor(levelThree, 125d);
	    	AnchorPane.setLeftAnchor(levelThree, 430d);
	    } else {
	    	AnchorPane.setTopAnchor(levelThree, 125d);
	    	AnchorPane.setLeftAnchor(levelThree, 550d);
	    }
	    
	    Image levelThreeMap = new Image(new File("images/medium_3_preview.png").toURI().toString());
    	ImageView levelThreePreview = new ImageView(levelThreeMap);
    	levelThreePreview.setFitWidth(500);
    	levelThreePreview.setPreserveRatio(true);
    	
    	Image levelThreeMap2P = new Image(new File("images/medium2p_3_preview.png").toURI().toString());
     	ImageView levelThreePreview2P = new ImageView(levelThreeMap2P);
     	levelThreePreview2P.setFitWidth(500);
     	levelThreePreview2P.setPreserveRatio(true);
    	
	    levelThree.setOnMouseEntered(actionEvent -> {
	    	if (playerCount == 1) {
	    		mediumPane.getChildren().add(levelThreePreview);
	    		AnchorPane.setBottomAnchor(levelThreePreview, 150d);
		    	AnchorPane.setLeftAnchor(levelThreePreview, 245d);
	    	} else {
	    		mediumPane.getChildren().add(levelThreePreview2P);
	    		AnchorPane.setBottomAnchor(levelThreePreview2P, 150d);
		    	AnchorPane.setLeftAnchor(levelThreePreview2P, 240d);
	    	}
		});
	    
	    levelThree.setOnMouseExited(actionEvent -> {
	    	if (playerCount == 1) {
	    		mediumPane.getChildren().remove(levelThreePreview);
	    	} else {
	    		mediumPane.getChildren().remove(levelThreePreview2P);
	    	}
		});
	    
	    AnchorPane.setTopAnchor(levelFour, 125d);
	    AnchorPane.setLeftAnchor(levelFour, 565d);
	    
	    Image levelFourMap = new Image(new File("images/medium_4_preview.png").toURI().toString());
    	ImageView levelFourPreview = new ImageView(levelFourMap);
    	levelFourPreview.setFitWidth(500);
    	levelFourPreview.setPreserveRatio(true);

	    levelFour.setOnMouseEntered(actionEvent -> {
	    	if (playerCount == 1) {
	    		mediumPane.getChildren().add(levelFourPreview);
	    		AnchorPane.setBottomAnchor(levelFourPreview, 150d);
		    	AnchorPane.setLeftAnchor(levelFourPreview, 245d);
	    	} 
		});
	    
	    levelFour.setOnMouseExited(actionEvent -> {
	    	if (playerCount == 1) {
	    		mediumPane.getChildren().remove(levelFourPreview);
	    	}
		});
	    
	    AnchorPane.setTopAnchor(levelFive, 125d);
	    AnchorPane.setLeftAnchor(levelFive, 700d);
	    
	    Image levelFiveMap = new Image(new File("images/medium_5_preview.png").toURI().toString());
    	ImageView levelFivePreview = new ImageView(levelFiveMap);
    	levelFivePreview.setFitWidth(500);
    	levelFivePreview.setPreserveRatio(true);
    	
	    levelFive.setOnMouseEntered(actionEvent -> {
	    	if (playerCount == 1) {
	    		mediumPane.getChildren().add(levelFivePreview);
	    		AnchorPane.setBottomAnchor(levelFivePreview, 150d);
		    	AnchorPane.setLeftAnchor(levelFivePreview, 245d);
	    	} 
		});
	    
	    levelFive.setOnMouseExited(actionEvent -> {
	    	if (playerCount == 1) {
	    		mediumPane.getChildren().remove(levelFivePreview);
	    	} 
		});
	    
		Scene layoutDisplay = new Scene(mediumPane, 960, 800);
		return layoutDisplay;
	}
	
	public Scene hardDifficulty(Stage primaryStage, Scene menu, int playerCount) {
		
		PuzzleGame game = new PuzzleGame();
	
		Image hardTitle = new Image(new File("images/hard.png").toURI().toString());
		ImageView hardTitleShow = new ImageView(hardTitle);
	    AnchorPane.setTopAnchor(hardTitleShow, 40d);
	    AnchorPane.setLeftAnchor(hardTitleShow, 400d);
	    
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
	    AnchorPane.setLeftAnchor(menuButton, 370d);
	    	
		HBox levelSelect = new HBox();
		levelSelect.setSpacing(10);
		
		Button levelOne = new Button("");
	    BackgroundImage levelOneBackground = new BackgroundImage(new Image(new File("images/level1.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background levelOneImage = new Background(levelOneBackground);

	    levelOne.setPrefSize(200, 200);
	    levelOne.setBackground(levelOneImage);
	    
	    levelOne.setOnAction(actionEvent -> {
			File f = new File("maps/hard_" + playerCount + "_1.txt");		
			if (f.exists() && !f.isDirectory()) {
				primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
			} else {
				primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, playerCount, 3, null));
			}
		});
	    
		Button levelTwo = new Button("");
	    BackgroundImage levelTwoBackground = new BackgroundImage(new Image(new File("images/level2.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background levelTwoImage = new Background(levelTwoBackground);
	    
	    levelTwo.setPrefSize(200, 200);
	    levelTwo.setBackground(levelTwoImage);
	    
	    levelTwo.setOnAction(actionEvent -> {
			File f = new File("maps/hard_" + playerCount + "_2.txt");		
			if (f.exists() && !f.isDirectory()) {
				primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
			} else {
				primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, playerCount, 3, null));
			}
		});
	    
		Button levelThree = new Button("");
	    BackgroundImage levelThreeBackground = new BackgroundImage(new Image(new File("images/level3.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background levelThreeImage = new Background(levelThreeBackground);
	    
	    levelThree.setPrefSize(200, 200);
	    levelThree.setBackground(levelThreeImage);
	    
	    levelThree.setOnAction(actionEvent -> {
			File f = new File("maps/hard_" + playerCount + "_3.txt");		
			if (f.exists() && !f.isDirectory()) {
				primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
			} else {
				primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, playerCount, 3, null));
			}
		});
	    
		Button levelFour = new Button("");
	    BackgroundImage levelFourBackground = new BackgroundImage(new Image(new File("images/level4.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background levelFourImage = new Background(levelFourBackground);
	    
	    levelFour.setPrefSize(200, 200);
	    levelFour.setBackground(levelFourImage);
	    
	    levelFour.setOnAction(actionEvent -> {
			File f = new File("maps/hard_" + playerCount + "_4.txt");		
			if (f.exists() && !f.isDirectory()) {
				primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
			} else {
				primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, playerCount, 3, null));
			}
		});
	    
		Button levelFive = new Button("");
	    BackgroundImage levelFiveBackground = new BackgroundImage(new Image(new File("images/level5.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background levelFiveImage = new Background(levelFiveBackground);
	    
	    levelFive.setPrefSize(200, 200);
	    levelFive.setBackground(levelFiveImage);

	    levelFive.setOnAction(actionEvent -> {
			File f = new File("maps/hard_" + playerCount + "_5.txt");		
			if (f.exists() && !f.isDirectory()) {
				primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
			} else {
				primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, playerCount, 3, null));
			}
		});
	    
		AnchorPane hardPane = new AnchorPane();
		hardPane.setStyle(background);
		
		if (playerCount == 1) {
			hardPane.getChildren().addAll(hardTitleShow, levelOne, levelTwo, levelThree, levelFour, levelFive, menuButton);
		} else {
			hardPane.getChildren().addAll(hardTitleShow, levelOne, levelTwo, levelThree, menuButton);
		}
		
		if (playerCount == 1) {
			AnchorPane.setTopAnchor(levelOne, 125d);
	    	AnchorPane.setLeftAnchor(levelOne, 160d);
		} else {
			AnchorPane.setTopAnchor(levelOne, 125d);
	    	AnchorPane.setLeftAnchor(levelOne, 290d);
		}
		
	    Image levelOneMap = new Image(new File("images/hard_1_preview.png").toURI().toString());
    	ImageView levelOnePreview = new ImageView(levelOneMap);
    	levelOnePreview.setFitWidth(500);
    	levelOnePreview.setPreserveRatio(true);
    	
    	Image levelOneMap2P = new Image(new File("images/hard2p_1_preview.png").toURI().toString());
     	ImageView levelOnePreview2P = new ImageView(levelOneMap2P);
     	levelOnePreview2P.setFitWidth(500);
     	levelOnePreview2P.setPreserveRatio(true);
    	
	    levelOne.setOnMouseEntered(actionEvent -> {
	    	if (playerCount == 1) {
	    		hardPane.getChildren().add(levelOnePreview);
	    		AnchorPane.setBottomAnchor(levelOnePreview, 150d);
		    	AnchorPane.setLeftAnchor(levelOnePreview, 245d);
	    	} else {
	    		hardPane.getChildren().add(levelOnePreview2P);
	    		AnchorPane.setBottomAnchor(levelOnePreview2P, 150d);
		    	AnchorPane.setLeftAnchor(levelOnePreview2P, 240d);
	    	}
		});
	    
	    levelOne.setOnMouseExited(actionEvent -> {
	    	if (playerCount == 1) {
	    		hardPane.getChildren().remove(levelOnePreview);
	    	} else {
	    		hardPane.getChildren().remove(levelOnePreview2P);
	    	}
		});
	    
	    if (playerCount == 1) {
	    	AnchorPane.setTopAnchor(levelTwo, 125d);
	    	AnchorPane.setLeftAnchor(levelTwo, 295d);
	    } else {
	    	AnchorPane.setTopAnchor(levelTwo, 125d);
	    	AnchorPane.setLeftAnchor(levelTwo, 420d);
	    }
	    
	    Image levelTwoMap = new Image(new File("images/hard_2_preview.png").toURI().toString());
    	ImageView levelTwoPreview = new ImageView(levelTwoMap);
    	levelTwoPreview.setFitWidth(500);
    	levelTwoPreview.setPreserveRatio(true);
    	
    	Image levelTwoMap2P = new Image(new File("images/hard2p_2_preview.png").toURI().toString());
     	ImageView levelTwoPreview2P = new ImageView(levelTwoMap2P);
     	levelTwoPreview2P.setFitWidth(500);
     	levelTwoPreview2P.setPreserveRatio(true);
    	
	    levelTwo.setOnMouseEntered(actionEvent -> {
	    	if (playerCount == 1) {
	    		hardPane.getChildren().add(levelTwoPreview);
	    		AnchorPane.setBottomAnchor(levelTwoPreview, 150d);
		    	AnchorPane.setLeftAnchor(levelTwoPreview, 245d);
	    	} else {
	    		hardPane.getChildren().add(levelTwoPreview2P);
	    		AnchorPane.setBottomAnchor(levelTwoPreview2P, 150d);
		    	AnchorPane.setLeftAnchor(levelTwoPreview2P, 240d);
	    	}
		});
	    
	    levelTwo.setOnMouseExited(actionEvent -> {
	    	if (playerCount == 1) {
	    		hardPane.getChildren().remove(levelTwoPreview);
	    	} else {
	    		hardPane.getChildren().remove(levelTwoPreview2P);
	    	}
		});
	    
	    if (playerCount == 1) {
	    	AnchorPane.setTopAnchor(levelThree, 125d);
	    	AnchorPane.setLeftAnchor(levelThree, 430d);
	    } else {
	    	AnchorPane.setTopAnchor(levelThree, 125d);
	    	AnchorPane.setLeftAnchor(levelThree, 550d);
	    }
	    
	    Image levelThreeMap = new Image(new File("images/hard_3_preview.png").toURI().toString());
    	ImageView levelThreePreview = new ImageView(levelThreeMap);
    	levelThreePreview.setFitWidth(500);
    	levelThreePreview.setPreserveRatio(true);
    	
    	Image levelThreeMap2P = new Image(new File("images/hard2p_3_preview.png").toURI().toString());
     	ImageView levelThreePreview2P = new ImageView(levelThreeMap2P);
     	levelThreePreview2P.setFitWidth(500);
     	levelThreePreview2P.setPreserveRatio(true);
    	
	    levelThree.setOnMouseEntered(actionEvent -> {
	    	if (playerCount == 1) {
	    		hardPane.getChildren().add(levelThreePreview);
	    		AnchorPane.setBottomAnchor(levelThreePreview, 150d);
		    	AnchorPane.setLeftAnchor(levelThreePreview, 245d);
	    	} else {
	    		hardPane.getChildren().add(levelThreePreview2P);
	    		AnchorPane.setBottomAnchor(levelThreePreview2P, 150d);
		    	AnchorPane.setLeftAnchor(levelThreePreview2P, 240d);
	    	}
		});
	    
	    levelThree.setOnMouseExited(actionEvent -> {
	    	if (playerCount == 1) {
	    		hardPane.getChildren().remove(levelThreePreview);
	    	} else {
	    		hardPane.getChildren().remove(levelThreePreview2P);
	    	}
		});
	    
	    AnchorPane.setTopAnchor(levelFour, 125d);
	    AnchorPane.setLeftAnchor(levelFour, 565d);
	    
	    Image levelFourMap = new Image(new File("images/hard_4_preview.png").toURI().toString());
    	ImageView levelFourPreview = new ImageView(levelFourMap);
    	levelFourPreview.setFitWidth(500);
    	levelFourPreview.setPreserveRatio(true);
    	
	    levelFour.setOnMouseEntered(actionEvent -> {
	    	if (playerCount == 1) {
	    		hardPane.getChildren().add(levelFourPreview);
	    		AnchorPane.setBottomAnchor(levelFourPreview, 150d);
		    	AnchorPane.setLeftAnchor(levelFourPreview, 245d);
	    	}
		});
	    
	    levelFour.setOnMouseExited(actionEvent -> {
	    	if (playerCount == 1) {
	    		hardPane.getChildren().remove(levelFourPreview);
	    	}
		});
	    
	    AnchorPane.setTopAnchor(levelFive, 125d);
	    AnchorPane.setLeftAnchor(levelFive, 700d);
	    
	    Image levelFiveMap = new Image(new File("images/hard_5_preview.png").toURI().toString());
    	ImageView levelFivePreview = new ImageView(levelFiveMap);
    	levelFivePreview.setFitWidth(500);
    	levelFivePreview.setPreserveRatio(true);
    	
	    levelFive.setOnMouseEntered(actionEvent -> {
	    	if (playerCount == 1) {
	    		hardPane.getChildren().add(levelFivePreview);
	    		AnchorPane.setBottomAnchor(levelFivePreview, 150d);
		    	AnchorPane.setLeftAnchor(levelFivePreview, 245d);
	    	}
		});
	    
	    levelFive.setOnMouseExited(actionEvent -> {
	    	if (playerCount == 1) {
	    		hardPane.getChildren().remove(levelFivePreview);
	    	}
		});
    
		Scene layoutDisplay = new Scene(hardPane, 960, 800);
		return layoutDisplay;
	}

	public void start(Stage primaryStage) throws Exception {
		// Init
		try {
			Font.loadFont(new FileInputStream(new File("fonts/FSEX300.ttf")), 11);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Stage mainWindow = primaryStage;
		
		GridPane gridMain = new GridPane();
		gridMain.setAlignment(Pos.CENTER);
		gridMain.setHgap(10);
		gridMain.setVgap(10);
		
		/**
		 * Adding title - "Wacky Warehouse"
		 */
		Text title = new Text();
		title.setText("Wacky Warehouse");
		title.setFont(Font.font ("Fixedsys Excelsior 3.01", 60));
		title.setFill(Color.WHITE);
		gridMain.add(title, 1, 2);
	    GridPane.setHalignment(gridMain, HPos.CENTER);

		
		/**
		 * Adding main image to gridMain
		 */
		
		ImageView mainImage = new ImageView(new Image("File:images/title.png"));
		gridMain.add(mainImage, 1, 3);
	    GridPane.setHalignment(mainImage, HPos.CENTER);
		
		/** 
		 * 	Adding my additions -- hover over buttons
		 */
	
	    Button onePlayerButton = new Button("");
		BackgroundImage backgroundImage1 = new BackgroundImage( new Image(new File("images/1Player.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background1 = new Background(backgroundImage1);
        BackgroundImage backgroundImage2 = new BackgroundImage( new Image(new File("images/1Player_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background2 = new Background(backgroundImage2);
                
	    onePlayerButton.setPrefSize(183, 59);
	    onePlayerButton.setBackground(background1);
	    gridMain.add(onePlayerButton, 1, 4);
	    GridPane.setHalignment(onePlayerButton, HPos.CENTER);
	    
	    onePlayerButton.setOnMouseEntered(actionEvent -> {
	        onePlayerButton.setBackground(background2);
		});
	    onePlayerButton.setOnMouseExited(actionEvent -> {
	        onePlayerButton.setBackground(background1);
		});
	    
	    /**
	     * Adding my additions -- 2 player mode
	     */
	    
	    // Creating the player two button
	    Button twoPlayerButton = new Button ("");
	    //  the player two button has a different display when the mouse hovers or does not hover over the button
	    BackgroundImage twoPlayerBackgroundWithoutArrow = new BackgroundImage(new Image(new File("images/2Player.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background twoPlayerWithoutArrow = new Background(twoPlayerBackgroundWithoutArrow);
	    
	    BackgroundImage twoPlayerBackgroundWithArrow = new BackgroundImage(new Image(new File("images/2Player_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	    Background twoPlayerWithArrow = new Background(twoPlayerBackgroundWithArrow);
	    
	    twoPlayerButton.setPrefSize(183, 59);
	    gridMain.add(twoPlayerButton, 1, 5);
	    twoPlayerButton.setBackground(twoPlayerWithoutArrow);
	    GridPane.setHalignment(twoPlayerButton, HPos.CENTER);
	    
	    twoPlayerButton.setOnMouseEntered(actionEvent -> {
	    	twoPlayerButton.setBackground(twoPlayerWithArrow);
	    });
	    
	    twoPlayerButton.setOnMouseExited(actionEvent -> {
	    	twoPlayerButton.setBackground(twoPlayerWithoutArrow);
	    });
		
		/**
		 * Adding instructions page
		 */
    	Button helpButton = new Button("");
    	BackgroundImage instructionBackgroundWithoutArrow = new BackgroundImage( new Image(new File("images/instructions.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background instructionWithoutArrow = new Background(instructionBackgroundWithoutArrow);
        BackgroundImage instructionBackgroundWithArrow = new BackgroundImage( new Image(new File("images/instructions_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background instructionWithArrow = new Background(instructionBackgroundWithArrow);
                
	    helpButton.setBackground(instructionWithoutArrow);
	    helpButton.setPrefSize(241, 59);
    	gridMain.add(helpButton, 1, 6);
	    GridPane.setHalignment(helpButton, HPos.CENTER);
    	
	    helpButton.setOnMouseEntered(actionEvent -> {
	        helpButton.setBackground(instructionWithArrow);
		});
	    helpButton.setOnMouseExited(actionEvent -> {
	        helpButton.setBackground(instructionWithoutArrow);
		});
    	   	
		// Puzzle Maker button
		Button puzzleMakerButton = new Button("");
		puzzleMakerButton.setPrefSize(224, 59);
		
		BackgroundImage puzzleMakerBackgroundWithoutArrow = new BackgroundImage( new Image(new File("images/puzzleMaker.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background puzzleMakerWithoutArrow = new Background(puzzleMakerBackgroundWithoutArrow);
        BackgroundImage puzzleMakerBackgroundWithArrow = new BackgroundImage( new Image(new File("images/puzzleMaker_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background puzzleMakerWithArrow = new Background(puzzleMakerBackgroundWithArrow);
    	puzzleMakerButton.setBackground(puzzleMakerWithoutArrow);
		
    	puzzleMakerButton.setOnMouseEntered(actionEvent -> {
    		puzzleMakerButton.setBackground(puzzleMakerWithArrow);
    	});
    	
    	puzzleMakerButton.setOnMouseExited(actionEvent -> {
    		puzzleMakerButton.setBackground(puzzleMakerWithoutArrow);
    	});
    	
		PuzzleMaker puzzleMaker = new PuzzleMaker();
		gridMain.add(puzzleMakerButton, 1, 7);
	    GridPane.setHalignment(puzzleMakerButton, HPos.CENTER);

		// Quit button
    	Button quitButton = new Button("");
    	BackgroundImage quitBackgroundWithoutArrow = new BackgroundImage( new Image(new File("images/quit.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background quitWithoutArrow = new Background(quitBackgroundWithoutArrow);
        BackgroundImage quitBackgroundWithArrow = new BackgroundImage( new Image(new File("images/quit_arrow.png").toURI().toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background quitWithArrow = new Background(quitBackgroundWithArrow);
    	quitButton.setPrefSize(106, 59);
    	quitButton.setBackground(quitWithoutArrow);
    	
    	quitButton.setOnMouseEntered(actionEvent -> {
    		quitButton.setBackground(quitWithArrow);
    	});
    	
    	quitButton.setOnMouseExited(actionEvent -> {
    		quitButton.setBackground(quitWithoutArrow);
    	});
 
    	gridMain.add(quitButton, 1, 8);
	    GridPane.setHalignment(quitButton, HPos.CENTER);
    	
    	
    	
    	// Set background (Main Menu)
    	gridMain.setStyle(background);
    	
		// Set scene
		Scene menuScene = new Scene(gridMain, 960, 800);// , screenSize.getWidth(), screenSize.getHeight());	

		/**
		 * Handle Events for the above buttons
		 */
		// When onePlayerButton is clicked, then move to difficulty screen
		onePlayerButton.setOnAction(actionEvent -> {
			mainWindow.setScene(selectDifficulty(mainWindow, menuScene, 1));
		});
		
		// When twoPlayerButton is clicked, then move to difficulty screen
		twoPlayerButton.setOnAction(actionEvent -> {
			mainWindow.setScene(selectDifficulty(mainWindow, menuScene, 2));
		});
		
		// When helpButton is clicked, then move to instructions screen
		helpButton.setOnAction(actionEvent -> {
			mainWindow.setScene(helpPage(mainWindow, menuScene));
		});
		
		
		puzzleMakerButton.setOnAction(actionEvent -> {
			mainWindow.setScene(puzzleMaker.PuzzleMakerHome(mainWindow, menuScene));
		});
		
		
		quitButton.setOnAction(actionEvent -> {
        	Alert confirmExit = new Alert(AlertType.CONFIRMATION, "Would you like to exit Wacky Warehouse?", ButtonType.OK, ButtonType.CANCEL);
        	confirmExit.setTitle("Exit Game");
        	confirmExit.showAndWait();
        	
        	if (confirmExit.getResult() == ButtonType.OK) {
        		Platform.exit();
        	}
		});

		// Show final
		mainWindow.setTitle("Wacky Warehouse");
		mainWindow.getIcons().add(new Image(new FileInputStream(new File("images/sprite1.png"))));
		mainWindow.setResizable(false);
		mainWindow.setScene(menuScene);
		mainWindow.sizeToScene();
		mainWindow.show();
	}

	/**
	 * Read the input file as a scanner.
	 * @param input The input file name.
	 * @return The scanner object of the input file.
	 */
	private Scanner scanFile(String input) {
		Scanner sc = null;
		try {
			sc = new Scanner(new FileReader(input));
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		}
		return sc;
	}
}
