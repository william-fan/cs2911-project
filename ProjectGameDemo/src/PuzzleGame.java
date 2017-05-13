import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.animation.AnimationTimer;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PuzzleGame {
	private Player player1 = new Player(1, 0, 0);
	private Player player2 = new Player(2, 0, 0);
	private ArrayList <Block> blockList = new ArrayList <Block>();
	
	//blockCount only for generation purposes
	public Scene Game(Cell[][] grid, Stage primaryStage, Scene menu, int playerCount, int blockCount, File inputFile) {
		Label timeElapsed = new Label("ADSSDSDSDSDAA");
		timeElapsed.setStyle("-fx-text-fill: white;");
		AnimationTimer timer = new AnimationTimer() {
		    private long timestamp;
		    private int seconds = 0;
			private int minutes = 0;
		    private int frames = 0; //60 fps
		    
			@Override
			public void handle(long now) {
				long newTime = System.currentTimeMillis();
		        if (timestamp + 1000 <= newTime) {
		        	frames ++;
 			    if (frames == 60) {
 			    	frames = 0;
 			    	seconds ++;
 			    }
 			    if (seconds == 60) {
 			    	minutes ++;
 			    	seconds = 0;
 			    }
 			    String timerString = "" + seconds;
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
		
		FlowPane gameUI = new FlowPane();
		gameUI.getChildren().add(timeElapsed);
		gameUI.setAlignment(Pos.TOP_RIGHT);
		gameUI.setOrientation(Orientation.VERTICAL);
		
		
        GridPane gamePane = new GridPane();
        gamePane.setMinSize(gameWidth, gameHeight);
        gamePane.setVgap(1);
        gamePane.setHgap(1);
		
		//Rectangle2D screenSize = Screen.getPrimary().getVisualBounds(); //Get screen size
		if (inputFile.exists() && !inputFile.isDirectory()) {
			findMapFeatures(inputFile);
		} else {
			for (int c = blockCount; c != 0; c--) {
				Block tempBlock = new Block(1, c, 3);
				this.blockList.add(tempBlock);
			}
		}

        for(int y = 0; y < gameHeight; y++) {
        	for(int x = 0; x < gameWidth; x++) {
        		HBox a = new HBox();
        		ImageView ground = new ImageView(grid[x][y].getFloorImage());
        		ground.setFitHeight(64);
        		ground.setFitWidth(64);
                a.getChildren().add(ground);
        		gamePane.add(a, x, y);
        	}
        }

		if (playerCount == 2) {
        	gamePane.add(this.player2.getPlayerImage(), this.player2.getX(), this.player2.getY());
		}
        gamePane.add(this.player1.getPlayerImage(), this.player1.getX(), this.player1.getY());


        for (Block block : this.blockList) {
        	gamePane.add(block.getBlockImage(), block.getX(), block.getY());
        }
        
        
        BorderPane center = new BorderPane();
        //center.setPrefWidth(screenSize.getWidth());
        //center.setPrefHeight(screenSize.getHeight());
        center.setCenter(gamePane); 
        center.setRight(gameUI);
        center.setStyle("-fx-background-color: #3F3F3F;");
        

        Scene Game = new Scene(center);//, screenSize.getWidth(), screenSize.getHeight());
        
        
        Game.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.DOWN) {
            	if (this.player1.moveDown(this.getBlockList(), this.player2, grid)) {
            		gamePane.getChildren().remove(this.player1.getPlayerImage());
                	gamePane.add(this.player1.getPlayerImage(), this.player1.getX(), this.player1.getY());
                    for (Block block : this.blockList) {
                    	gamePane.getChildren().remove(block.getBlockImage());
                    	gamePane.add(block.getBlockImage(), block.getX(), block.getY());
                    }
            	}
            }
            else if (event.getCode() == KeyCode.UP) {
            	if (this.player1.moveUp(this.getBlockList(), player2, grid)) {
            		gamePane.getChildren().remove(this.player1.getPlayerImage());
                	gamePane.add(this.player1.getPlayerImage(), this.player1.getX(), this.player1.getY());
                    for (Block block : this.blockList) {
                    	gamePane.getChildren().remove(block.getBlockImage());
                    	gamePane.add(block.getBlockImage(), block.getX(), block.getY());
                    }
            	}
            }
            else if (event.getCode() == KeyCode.LEFT) {
            	if (this.player1.moveLeft(this.getBlockList(), this.player2, grid)) {
            		gamePane.getChildren().remove(this.player1.getPlayerImage());
                	gamePane.add(this.player1.getPlayerImage(), this.player1.getX(), this.player1.getY());
                    for (Block block : this.blockList) {
                    	gamePane.getChildren().remove(block.getBlockImage());
                    	gamePane.add(block.getBlockImage(), block.getX(), block.getY());
                    }
            	}
            }
            else if (event.getCode() == KeyCode.RIGHT) {
            	if (this.player1.moveRight(this.getBlockList(), player2, grid)) {
            		gamePane.getChildren().remove(this.player1.getPlayerImage());
                	gamePane.add(this.player1.getPlayerImage(), this.player1.getX(), this.player1.getY());
                    for (Block block : this.blockList) {
                    	gamePane.getChildren().remove(block.getBlockImage());
                    	gamePane.add(block.getBlockImage(), block.getX(), block.getY());
                    }
            	}
            }
            else if (event.getCode() == KeyCode.ESCAPE) {
            	primaryStage.setScene(menu);
                primaryStage.setTitle("Puzzle Game");
                primaryStage.setResizable(false);       
                //primaryStage.setMaximized(true);
                primaryStage.show();
            } 
            if (playerCount == 2) {
	            if (event.getCode() == KeyCode.S) {
	            	if (this.player2.moveDown(this.getBlockList(), this.player1, grid)) {
	            		gamePane.getChildren().remove(this.player2.getPlayerImage());
	                	gamePane.add(this.player2.getPlayerImage(), this.player2.getX(), this.player2.getY());
	                    for (Block block : this.blockList) {
	                    	gamePane.getChildren().remove(block.getBlockImage());
	                    	gamePane.add(block.getBlockImage(), block.getX(), block.getY());
	                    }
	            	}
	            }
	            else if (event.getCode() == KeyCode.W) {
	            	if (this.player2.moveUp(this.getBlockList(), this.player1, grid)) {
	            		gamePane.getChildren().remove(this.player2.getPlayerImage());
	                	gamePane.add(this.player2.getPlayerImage(), this.player2.getX(), this.player2.getY());
	                    for (Block block : this.blockList) {
	                    	gamePane.getChildren().remove(block.getBlockImage());
	                    	gamePane.add(block.getBlockImage(), block.getX(), block.getY());
	                    }
	            	}
	            }
	            else if (event.getCode() == KeyCode.A) {
	            	if (this.player2.moveLeft(this.getBlockList(), this.player1, grid)) {
	            		gamePane.getChildren().remove(this.player2.getPlayerImage());
	                	gamePane.add(this.player2.getPlayerImage(), this.player2.getX(), this.player2.getY());
	                    for (Block block : this.blockList) {
	                    	gamePane.getChildren().remove(block.getBlockImage());
	                    	gamePane.add(block.getBlockImage(), block.getX(), block.getY());
	                    }
	            	}
	            }
	            else if (event.getCode() == KeyCode.D) {
	            	if (this.player2.moveRight(this.getBlockList(), this.player1, grid)) {
	            		gamePane.getChildren().remove(this.player2.getPlayerImage());
	                	gamePane.add(this.player2.getPlayerImage(), this.player2.getX(), this.player2.getY());
	                    for (Block block : this.blockList) {
	                    	gamePane.getChildren().remove(block.getBlockImage());
	                    	gamePane.add(block.getBlockImage(), block.getX(), block.getY());
	                    }
	            	}
	            }
            }
        });
        
		return Game;
		
	}
	
	//reset stuff here
	public void resetGame() {
		
	}

	public ArrayList <Block> getBlockList() {
		return this.blockList;
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
	
	public void findMapFeatures(File inputFile) {
		if (inputFile != null) {
			Scanner sc = scanFile(inputFile.getPath());
	        int x = 0;
	        int y = 0;
			while(sc.hasNext()) {
	        	String next = sc.next();
	        	if(next.equals("3")) {
	        		if (this.player1.getX() == 0 && this.player1.getY() == 0) { //assume 0, 0 is always a wall and invalid player location
				    	this.player1.setX(x);
				    	this.player1.setY(y);
	        		} else {
				    	this.player2.setX(x);
				    	this.player2.setY(y);
	        		}
	        	}
	        	if(next.equals("2")) {
			    	Block block = new Block(1, x, y);
			    	this.blockList.add(block);
	        	}
	        	if(next.equals("#") && sc.hasNext()) {
					sc.nextLine();
					y++;
					x = 0;
	        	} else if (next.equals("#")) {
	        		
	        	} else {
				    x++;
	        	}
			}
		}
	}
}
