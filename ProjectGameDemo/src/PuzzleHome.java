import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PuzzleHome extends Application {
	
	public Scene helpPage(Stage primaryStage, Scene menu) { 
		Label helpText = new Label("This is the help screen\nTEST");
		FlowPane helpPane = new FlowPane();
		helpPane.getChildren().addAll(helpText);
        Button menuButton = new Button("Main menu");
        helpPane.getChildren().addAll(menuButton);
        Scene helpScene = new Scene(helpPane);//, screenSize.getWidth(), screenSize.getHeight());
        menuButton.setOnAction(actionEvent ->  {
        	primaryStage.setScene(menu);
        });
		return helpScene;
	}
	
	public Scene selectPlayers(Stage primaryStage, Scene menu) { 
		Label playersText = new Label("This is the select screen\nTEST");
		FlowPane selectPane = new FlowPane();
        Button onePlayer = new Button("One Player");
        Button twoPlayer = new Button("Two Player");
        Button menuButton = new Button("Main Menu");
        selectPane.getChildren().addAll(playersText, onePlayer, twoPlayer, menuButton);
        Scene helpScene = new Scene(selectPane);//, screenSize.getWidth(), screenSize.getHeight());
        onePlayer.setOnAction(actionEvent ->  {
        	int playerCount = 1;
        	primaryStage.setScene(selectDifficulty(primaryStage, menu, playerCount));
        });
        twoPlayer.setOnAction(actionEvent ->  {
        	int playerCount = 2;
        	primaryStage.setScene(selectDifficulty(primaryStage, menu, playerCount));
        });
        menuButton.setOnAction(actionEvent ->  {
        	primaryStage.setScene(menu);
        });
		return helpScene;
	}
	
	public Cell[][] generateMap(int difficulty) {
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
	
	public Cell[][] readMap(File f) {
		Cell[][] map = new Cell[20][15];
		Scanner sc = scanFile(f.getPath());
        int x = 0;
        int y = 0;
        while(sc.hasNext()) {
        	String next = sc.next();
        	Cell current = new Cell(x, y, 0);
        	if(!next.equals("#")) {
		    	current.setType(0);
        	}	
        	if(next.equals("5")) {
		    	current.setType(2);
        	}
        	if(next.equals("4")) {
		    	current.setType(1);
        	}
        	if(next.equals("#") && sc.hasNext()) {
				sc.nextLine();
				y++;
				x = 0;
        	} else if (next.equals("#")) {
        		
        	} else {
			    map[x][y] = current;
			    x++;
        	}
        }
        return map;
	}
	
	public Scene selectDifficulty(Stage primaryStage, Scene menu, int playerCount) { 
		//Rectangle2D screenSize = Screen.getPrimary().getVisualBounds(); //Get screen size
		Label label = new Label("This is the difficulty screen\nTEST");
		FlowPane difficultyPane = new FlowPane();
		difficultyPane.getChildren().addAll(label);
		
        Button easyButton = new Button("Easy");
        Button menuButton = new Button("Main Menu");
        difficultyPane.getChildren().addAll(menuButton, easyButton);
        
        PuzzleGame game = new PuzzleGame();
        easyButton.setOnAction(actionEvent ->  {
        	game.resetGame();
        	File f = new File("maps/easy_"+playerCount+"_1.txt");
        	if (f.exists() && !f.isDirectory()) {
        		primaryStage.setScene(game.Game(readMap(f), primaryStage, menu, playerCount, 3, f));
        	} else {
        		primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, playerCount, 3, null));
        	}
        });
        
        menuButton.setOnAction(actionEvent ->  {
        	primaryStage.setScene(menu);
        });
        Scene difficultyScene = new Scene(difficultyPane);//, screenSize.getWidth(), screenSize.getHeight());
		return difficultyScene;
	}
	
	public void start(Stage primaryStage) throws Exception {
		//Init
		Stage mainWindow = primaryStage;
		FlowPane menuPane = new FlowPane();
		//Rectangle2D screenSize = Screen.getPrimary().getVisualBounds(); //Get screen size
        
        //Play button
    	Button playButton = new Button("Play Game");
    	menuPane.getChildren().addAll(playButton);
    	
    	//Help button
    	Button helpButton = new Button("Help");
    	menuPane.getChildren().addAll(helpButton);
        
    	//Quit button
    	Button quitButton = new Button("Quit");
    	menuPane.getChildren().addAll(quitButton);
    	
        //Set scene
        Scene menuScene = new Scene(menuPane);//, screenSize.getWidth(), screenSize.getHeight());
        
        //Events
        playButton.setOnAction(actionEvent ->  {
        	mainWindow.setScene(selectPlayers(mainWindow, menuScene));
        });
        helpButton.setOnAction(actionEvent ->  {
        	mainWindow.setScene(helpPage(mainWindow, menuScene));    
        }); 
        quitButton.setOnAction(actionEvent ->  {
            System.exit(0);
        });

        //Show final
        primaryStage.setTitle("Puzzle Game");
        primaryStage.setResizable(false);
        primaryStage.setScene(menuScene);        
        //primaryStage.setMaximized(true);
        primaryStage.show();
    }
	
	/**
	 * Read the input file as a scanner.
	 * @param input The input file name.
	 * @return The scanner object of the input file.
	 */
	public Scanner scanFile(String input) {
	    Scanner sc = null;
	    try
	    {
	    	sc = new Scanner(new FileReader(input));   
	    }
	    catch (FileNotFoundException e) {
	    	System.err.println("File not found");
	    }
        return sc;
	}
}
