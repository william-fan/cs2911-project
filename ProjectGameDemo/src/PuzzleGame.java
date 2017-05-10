import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PuzzleGame {
	
	public Scene Game(Cell[][] grid, Stage primaryStage, Scene menu, int playerCount) {
		Rectangle2D screenSize = Screen.getPrimary().getVisualBounds(); //Get screen size
		Player player1 = new Player(1);
		Block block1 = new Block(1);
		
    	//Images
        ImageView imageView = new ImageView(player1.getPlayerImage());    //temporary
        //ImageView imageView1 = new ImageView(block1.getBlockImage());
        
		
        GridPane gamePane = new GridPane();
        gamePane.setMinSize(10, 10);
        gamePane.setPadding(new Insets(10, 10, 10, 10));
        gamePane.setVgap(1);
        gamePane.setHgap(1);
        
        for(int y = 0; y < gamePane.getMinHeight(); y++) {
        	for(int x = 0; x < gamePane.getMinWidth(); x++) {
        		HBox a = new HBox();
                a.getChildren().add(new ImageView(block1.getBlockImage()));
        		gamePane.add(a, x, y);
        	}
        }
        gamePane.add(imageView, 6, 6);
        
        //gamePane.add(imageView, 1, 1);
        //gamePane.add(imageView1, 50, 50);
		
        FlowPane center = new FlowPane();
        center.setPrefWidth(screenSize.getWidth());
        center.setPrefHeight(screenSize.getHeight());
        center.getChildren().add(gamePane);
        
        center.setAlignment(Pos.CENTER);
        center.setStyle("-fx-background-color: #000000;");
        Scene Game = new Scene(center, screenSize.getWidth(), screenSize.getHeight());
        
        Game.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.DOWN) {

            }
            else if (event.getCode() == KeyCode.UP) {

            }
            else if (event.getCode() == KeyCode.LEFT) {
            	player1.moveLeft();
            	block1.moveLeft();

            }
            else if (event.getCode() == KeyCode.RIGHT) {
            	System.out.println(event.getCode());
            }
            else if (event.getCode() == KeyCode.ESCAPE) {
            	primaryStage.setScene(menu);
                primaryStage.setTitle("Puzzle Game");
                primaryStage.setResizable(false);       
                primaryStage.setMaximized(true);
                primaryStage.show();
            }
        });
        
		return Game;
		
	}
	
	//reset stuff here
	public void resetGame() {
		
	}
	

	

}
