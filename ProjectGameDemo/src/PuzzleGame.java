import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
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
		Player player1 = new Player(1, 5, 5);
		if (playerCount == 2) {
			//Player player2 = new Player(1, 5, 5);
		}
		for (int c = blockCount; c != 0; c--) {
			Block tempBlock = new Block(1, c, 3);
			blockList.add(tempBlock);
		}
		int gameWidth = grid[0].length;
		int gameHeight = grid.length;


        
        //Image ground = new Image(new File("images/ground.png").toURI().toString());
		
        GridPane gamePane = new GridPane();
        gamePane.setMinSize(gameWidth, gameHeight);
        gamePane.setVgap(1);
        gamePane.setHgap(1);
        
        for(int y = 0; y < gamePane.getMinHeight(); y++) {
        	for(int x = 0; x < gamePane.getMinWidth(); x++) {
        		HBox a = new HBox();
        		ImageView ground = new ImageView(grid[x][y].getFloorImage());
        		ground.setFitHeight(64);
        		ground.setFitWidth(64);
                a.getChildren().add(ground);
        		gamePane.add(a, x, y);
        	}
        }
        gamePane.add(player1.getPlayerImage(), player1.getX(), player1.getY());
        for (Block block : blockList) {
        	gamePane.add(block.getBlockImage(), block.getX(), block.getY());
        }
        FlowPane center = new FlowPane();
        center.setPrefWidth(screenSize.getWidth());
        center.setPrefHeight(screenSize.getHeight());
        center.getChildren().add(gamePane);
        
        center.setAlignment(Pos.CENTER);
        center.setStyle("-fx-background-color: #3F3F3F;");
        Scene Game = new Scene(center, screenSize.getWidth(), screenSize.getHeight());
        
        Game.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.DOWN) {
            	if (player1.moveDown(getBlockList(), grid)) {
            		gamePane.getChildren().remove(player1.getPlayerImage());
                	gamePane.add(player1.getPlayerImage(), player1.getX(), player1.getY());
                    for (Block block : blockList) {
                    	gamePane.getChildren().remove(block.getBlockImage());
                    	gamePane.add(block.getBlockImage(), block.getX(), block.getY());
                    }
            	}
            }
            else if (event.getCode() == KeyCode.UP) {
            	if (player1.moveUp(getBlockList(), grid)) {
            		gamePane.getChildren().remove(player1.getPlayerImage());
                	gamePane.add(player1.getPlayerImage(), player1.getX(), player1.getY());
                    for (Block block : blockList) {
                    	gamePane.getChildren().remove(block.getBlockImage());
                    	gamePane.add(block.getBlockImage(), block.getX(), block.getY());
                    }
            	}

            }
            else if (event.getCode() == KeyCode.LEFT) {
            	if (player1.moveLeft(getBlockList(), grid)) {
            		gamePane.getChildren().remove(player1.getPlayerImage());
                	gamePane.add(player1.getPlayerImage(), player1.getX(), player1.getY());
                    for (Block block : blockList) {
                    	gamePane.getChildren().remove(block.getBlockImage());
                    	gamePane.add(block.getBlockImage(), block.getX(), block.getY());
                    }
            	}
            }
            else if (event.getCode() == KeyCode.RIGHT) {
            	if (player1.moveRight(getBlockList(), grid)) {
            		gamePane.getChildren().remove(player1.getPlayerImage());
                	gamePane.add(player1.getPlayerImage(), player1.getX(), player1.getY());
                    for (Block block : blockList) {
                    	gamePane.getChildren().remove(block.getBlockImage());
                    	gamePane.add(block.getBlockImage(), block.getX(), block.getY());
                    }
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
