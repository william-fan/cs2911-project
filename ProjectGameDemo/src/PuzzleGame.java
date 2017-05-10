import java.io.File;
import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PuzzleGame {
	private ArrayList <Player> playerList = new ArrayList <Player>();
	private ArrayList <Block> blockList = new ArrayList <Block>();
	
	public Scene Game(Cell[][] grid, Stage primaryStage, Scene menu, int playerCount, int blockCount) {
		Rectangle2D screenSize = Screen.getPrimary().getVisualBounds(); //Get screen size
		Player player1 = new Player(1);
		Block block1 = new Block(1);
		
		int gameWidth = grid[0].length;
		int gameHeight = grid.length;

    	//Images
        ImageView imageView = new ImageView(player1.getPlayerImage());    //temporary
        ImageView imageView1 = new ImageView(block1.getBlockImage());
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        
        Image ground = new Image(new File("images/ground.png").toURI().toString());
		
        GridPane gamePane = new GridPane();
        gamePane.setMinSize(gameWidth, gameHeight);
        gamePane.setVgap(1);
        gamePane.setHgap(1);
        
        for(int y = 0; y < gamePane.getMinHeight(); y++) {
        	for(int x = 0; x < gamePane.getMinWidth(); x++) {
        		HBox a = new HBox();
                a.getChildren().add(new ImageView(ground));
        		gamePane.add(a, x, y);
        	}
        }
        gamePane.add(imageView, 0, 0);
        gamePane.add(imageView1, 4, 3);
		
        FlowPane center = new FlowPane();
        center.setPrefWidth(screenSize.getWidth());
        center.setPrefHeight(screenSize.getHeight());
        center.getChildren().add(gamePane);
        
        center.setAlignment(Pos.CENTER);
        center.setStyle("-fx-background-color: #000000;");
        Scene Game = new Scene(center, screenSize.getWidth(), screenSize.getHeight());
        
        Game.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.DOWN) {
            	if (player1.moveDown(getBlockList(), gameWidth, gameHeight)) {
            		gamePane.getChildren().remove(imageView);
                	gamePane.add(imageView, player1.getX(), player1.getY());
            	}
            }
            else if (event.getCode() == KeyCode.UP) {
            	if (player1.moveUp(getBlockList(), gameWidth, gameHeight)) {
            		gamePane.getChildren().remove(imageView);
                	gamePane.add(imageView, player1.getX(), player1.getY());
            	}

            }
            else if (event.getCode() == KeyCode.LEFT) {
            	if (player1.moveLeft(getBlockList(), gameWidth, gameHeight)) {
            		gamePane.getChildren().remove(imageView);
            		gamePane.add(imageView, player1.getX(), player1.getY());
            	}
            }
            else if (event.getCode() == KeyCode.RIGHT) {
            	if (player1.moveRight(getBlockList(), gameWidth, gameHeight)) {
	            	gamePane.getChildren().remove(imageView);
	            	gamePane.add(imageView, player1.getX(), player1.getY());
            	}
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

	public ArrayList <Player> getPlayerList() {
		return this.playerList;
	}

	public ArrayList <Block> getBlockList() {
		return this.blockList;
	}

}
