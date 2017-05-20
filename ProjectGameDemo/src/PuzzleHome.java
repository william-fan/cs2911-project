import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class PuzzleHome extends Application {

	private Scene helpPage(Stage primaryStage, Scene menu) {
		Label helpText = new Label("This is the help screen\nTEST");
		FlowPane helpPane = new FlowPane();
		helpPane.getChildren().addAll(helpText);
		Button menuButton = new Button("Main menu");
		helpPane.getChildren().addAll(menuButton);
		Scene helpScene = new Scene(helpPane);// , screenSize.getWidth(), screenSize.getHeight());
		menuButton.setOnAction(actionEvent -> {
			primaryStage.setScene(menu);
		});
		return helpScene;
	}

	private Scene selectPlayers(Stage primaryStage, Scene menu) {
		Label playersText = new Label("This is the select screen\nTEST");
		FlowPane selectPane = new FlowPane();
		Button onePlayer = new Button("One Player");
		Button twoPlayer = new Button("Two Player");
		Button menuButton = new Button("Main Menu");
		selectPane.getChildren().addAll(playersText, onePlayer, twoPlayer, menuButton);
		Scene helpScene = new Scene(selectPane);// , screenSize.getWidth(), screenSize.getHeight());
		
		onePlayer.setOnAction(actionEvent -> {
			primaryStage.setScene(selectDifficulty(primaryStage, menu, 1));
		});
		
		twoPlayer.setOnAction(actionEvent -> {
			primaryStage.setScene(selectDifficulty(primaryStage, menu, 2));
		});
		
		menuButton.setOnAction(actionEvent -> {
			primaryStage.setScene(menu);
		});
		
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
		// Rectangle2D screenSize = Screen.getPrimary().getVisualBounds(); 
		// Get screen size
		Label label = new Label("This is the difficulty screen\nTEST");
		FlowPane difficultyPane = new FlowPane();
		difficultyPane.getChildren().addAll(label);

		Button easyButton = new Button("Easy");
		Button customMap = new Button("Custom Map");
		Button menuButton = new Button("Main Menu");
		difficultyPane.getChildren().addAll(menuButton, easyButton, customMap);

		PuzzleGame game = new PuzzleGame();
		easyButton.setOnAction(actionEvent -> {
			File f = new File("maps/easy_" + playerCount + "_1.txt");
			if (f.exists() && !f.isDirectory()) {
				if (readMap(f) == null ) {
		        	Alert confirmExit = new Alert(AlertType.ERROR, "File must be of correct type", ButtonType.OK);
		        	confirmExit.setTitle("Exit Game");
		        	confirmExit.showAndWait();
				} else {
					primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
				}
			} else {
				primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, playerCount, 3, null));
			}
		});

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

		menuButton.setOnAction(actionEvent -> {
			primaryStage.setScene(menu);
		});

		Scene difficultyScene = new Scene(difficultyPane);// , screenSize.getWidth(),screenSize.getHeight());
		return difficultyScene;
	}

	public void start(Stage primaryStage) throws Exception {
		// Init
				Stage mainWindow = primaryStage;
				
				// Background style
				
				/* Background has been replaced with full black
				String background = "-fx-background-image: url(file:images/background.jpg);" + "\n"
				   					+ "-fx-background-repeat: stretch;" + "\n"
				   					+ "-fx-background-size: 1000 1000";
				   					
				*/
				
				GridPane gridMain = new GridPane();
				gridMain.setAlignment(Pos.CENTER);
				gridMain.setHgap(25);
				gridMain.setVgap(25);
				
				/**
				 * Adding title - "Wacky Warehouse"
				 */
				Text title = new Text();
				title.setText("Wacky Warehouse");
				title.setFont(Font.font ("Fixedsys Excelsior 3.01", 60));
				title.setFill(Color.WHITE);
				gridMain.add(title, 1, 2);
				
				/**
				 * Adding main image to gridMain
				 */
				
				Image main = new Image("File:images/main.png");
				gridMain.add(new ImageView(main), 1, 3);
				
				
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
		    	
			    helpButton.setOnMouseEntered(actionEvent -> {
			        helpButton.setBackground(instructionWithArrow);
				});
			    helpButton.setOnMouseExited(actionEvent -> {
			        helpButton.setBackground(instructionWithoutArrow);
				});
		    	
		    	
		/* Custom Map creation is not used
				// Test button
				Button puzzleMakerButton = new Button("Puzzle Maker");
				puzzleMakerButton.setPrefSize(150, 100);
				PuzzleMaker puzzleMaker = new PuzzleMaker();
				gridMain.add(puzzleMakerButton, 1, 11);
		*/
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
		 
		    	gridMain.add(quitButton, 1, 7);
		    	
		    	
		    	
		    	// Set background (Main Menu)
		    	gridMain.setStyle("-fx-background-color: #000000");
		    	
				// Set scene
				Scene menuScene = new Scene(gridMain, 960, 800);// , screenSize.getWidth(), screenSize.getHeight());	

				/**
				 * Handle Events for the above buttons
				 */
				// When onePlayerButton is clicked, then move to difficulty screen
				onePlayerButton.setOnAction(actionEvent -> {
					primaryStage.setScene(selectDifficulty(primaryStage, menuScene, 1));
				});
				
				// When twoPlayerButton is clicked, then move to difficulty screen
				twoPlayerButton.setOnAction(actionEvent -> {
					primaryStage.setScene(selectDifficulty(primaryStage, menuScene, 2));
				});
				
				// When helpButton is clicked, then move to instructions screen
				helpButton.setOnAction(actionEvent -> {
					mainWindow.setScene(helpPage(mainWindow, menuScene));
				});
				
				/*
				puzzleMakerButton.setOnAction(actionEvent -> {
					mainWindow.setScene(puzzleMaker.PuzzleMakerHome(mainWindow, menuScene));
				});
				*/
				
				quitButton.setOnAction(actionEvent -> {
		        	Alert confirmExit = new Alert(AlertType.CONFIRMATION, "Would you like to exit Wacky Warehouse?", ButtonType.OK, ButtonType.CANCEL);
		        	confirmExit.setTitle("Exit Game");
		        	confirmExit.showAndWait();
		        	
		        	if (confirmExit.getResult() == ButtonType.OK) {
		        		Platform.exit();
		        	}
				});

				// Show final
				primaryStage.setTitle("Puzzle Game");
				primaryStage.setResizable(false);
				primaryStage.setScene(menuScene);
				// primaryStage.setMaximized(true);
				primaryStage.show();
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
