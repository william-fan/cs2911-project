
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PuzzleHome extends Application {
	
	public FlowPane helpPage() { 
		Label label = new Label("This is the help screen\nTEST");
		FlowPane pane2 = new FlowPane();
		pane2.getChildren().addAll(label);
		return pane2;
	}
	
	public Cell[][] generateMap(int difficulty) {
		Cell[][] map = null;
		if (difficulty == 0) {
			map = new Cell[10][10];
			for (int x = 0; x != 10; x++) {
				for (int y = 0; y != 10; y++) {
					Cell cell = new Cell(x, y, false);
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
	
	public Scene selectDifficulty(Stage primaryStage, Scene menu) { 
		Rectangle2D screenSize = Screen.getPrimary().getVisualBounds(); //Get screen size
		Label label = new Label("This is the difficulty screen\nTEST");
		FlowPane difficultyPane = new FlowPane();
		difficultyPane.getChildren().addAll(label);
		
        Button easyButton = new Button("Easy");
        Button menuButton = new Button("Main Menu");
        difficultyPane.getChildren().addAll(menuButton, easyButton);
        
        PuzzleGame game = new PuzzleGame();
        easyButton.setOnAction(actionEvent ->  {
        	game.resetGame();
        	primaryStage.setScene(game.Game(generateMap(0), primaryStage, menu, 1, 3));
        });
        
        menuButton.setOnAction(actionEvent ->  {
        	primaryStage.setScene(menu);
        });
        Scene difficultyScene = new Scene(difficultyPane, screenSize.getWidth(), screenSize.getHeight());
		return difficultyScene;
	}
	
	public void start(Stage primaryStage) throws Exception {
		//Init
		Stage mainWindow = primaryStage;
		FlowPane menuPane = new FlowPane();
		Rectangle2D screenSize = Screen.getPrimary().getVisualBounds(); //Get screen size
        
        //Play button
    	Button playButton = new Button("Play Game");
    	menuPane.getChildren().addAll(playButton);
    	
    	//Help button
    	Button helpButton = new Button("Help");
    	menuPane.getChildren().addAll(helpButton);
    	
        
        FlowPane helpPane = this.helpPage();
        Button menuButton = new Button("Main menu");
        helpPane.getChildren().addAll(menuButton);
        
        
        //Set scene
        Scene menuScene = new Scene(menuPane, screenSize.getWidth(), screenSize.getHeight());
        Scene helpScene = new Scene(helpPane, screenSize.getWidth(), screenSize.getHeight());
        
        //Events
        playButton.setOnAction(actionEvent ->  {
        	mainWindow.setScene(selectDifficulty(mainWindow, menuScene));
        });

        helpButton.setOnAction(actionEvent ->  {
        	mainWindow.setScene(helpScene);    
        });
        
        menuButton.setOnAction(actionEvent ->  {
        	mainWindow.setScene(menuScene);
        });
        
        
        //Show final
        primaryStage.setTitle("Puzzle Game");
        primaryStage.setResizable(false);
        primaryStage.setScene(menuScene);        
        primaryStage.setMaximized(true);
        primaryStage.show();

    }
}
