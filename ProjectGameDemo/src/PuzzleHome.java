
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
	
	public FlowPane selectDifficulty() { 
		Label label = new Label("This is the difficulty screen\nTEST");
		FlowPane pane2 = new FlowPane();
		pane2.getChildren().addAll(label);
		return pane2;
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
        PuzzleGame test = new PuzzleGame();
        
        //Events
        playButton.setOnAction(actionEvent ->  {
        	test.resetGame();
        	mainWindow.setScene(test.Game(new Cell[100][100],primaryStage, menuScene, 1));
            //primaryStage.setFullScreen(true);
        });
        
        helpButton.setOnAction(actionEvent ->  {
        	mainWindow.setScene(helpScene);    
            //primaryStage.setFullScreen(true);
        });
        
        menuButton.setOnAction(actionEvent ->  {
        	mainWindow.setScene(menuScene);
        	//mainWindow.getScene().setRoot(newContent);
            //primaryStage.setFullScreen(true);
        });
        
        
        //Show final
        primaryStage.setTitle("Puzzle Game");
        primaryStage.setResizable(false);
        primaryStage.setScene(menuScene);        
        primaryStage.setMaximized(true);
        primaryStage.show();

    }
}
