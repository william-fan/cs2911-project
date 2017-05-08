import java.io.File;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class PuzzleGameDemo extends Application {
	
    public static void main(String[] args) {
    	Application.launch(args);
    }
    
	public void start(Stage primaryStage) throws Exception {
		//Init
		PuzzleGame test = new PuzzleGame();
		Stage window = primaryStage;
		FlowPane pane1 = new FlowPane();
		Player player1 = new Player();
		Block block1 = new Block();
		
        primaryStage.setTitle("Puzzle Game");
        primaryStage.setResizable(false);
        
        //Play button
    	Button button1 = new Button("Play Game");
    	//button1.setPrefSize(100, 100);
    	pane1.getChildren().addAll(button1);
    	
    	//Help button
    	Button button2 = new Button("Help");
    	pane1.getChildren().addAll(button2);
    	
    	//Images
    	File file = new File("images/sprite.png");
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        
    	File file1 = new File("images/block.png");
        Image image1 = new Image(file1.toURI().toString());
        ImageView imageView1 = new ImageView(image1);
        imageView1.setTranslateX(220);
        imageView1.setTranslateY(380);
        
        FlowPane game = test.Game();
        game.getChildren().addAll(imageView);
        game.getChildren().addAll(imageView1);

        FlowPane help = test.Help();
        Button mainMenu = new Button("Main menu");
        help.getChildren().addAll(mainMenu);
        		
        //Set scene
        Scene Menu = new Scene(pane1, 511, 816);
        Scene Game = new Scene(game, 511, 816);
        Scene Help = new Scene(help, 511, 816);

        //Events
        button1.setOnAction(actionEvent ->  {
            window.setScene(Game);    
        });
        
        button2.setOnAction(actionEvent ->  {
            window.setScene(Help);    
        });
        
        mainMenu.setOnAction(actionEvent ->  {
            window.setScene(Menu);    
        });
        
        button1.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                	window.setScene(Game); 
                }
            }
        });
        
        //This works but is terrible
        //Need to add player properly
        Game.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.DOWN) {
            	if (imageView.getTranslateY() < Game.getHeight()-36) {
            		if (block1.getX() == player1.getX() && block1.getY()+1 == player1.getY() && imageView1.getTranslateY() < Game.getHeight()-56) {
		            	imageView.setTranslateY(imageView.getTranslateY()+20);
		            	imageView1.setTranslateY(imageView1.getTranslateY()+20);
		            	player1.moveDown();
		            	block1.moveDown();
		            	System.out.println(event.getCode());
            		} else {
		            	imageView.setTranslateY(imageView.getTranslateY()+20);
		            	player1.moveDown();
		            	System.out.println(event.getCode());
            		}
            	}
            }
            else if (event.getCode() == KeyCode.UP) {
            	if (imageView.getTranslateY() > 0) {
            		if (block1.getX() == player1.getX() && block1.getY()-1 == player1.getY() && imageView1.getTranslateY() > -20) {
		            	imageView.setTranslateY(imageView.getTranslateY()-20);
		            	imageView1.setTranslateY(imageView1.getTranslateY()-20);
		            	player1.moveUp();
		            	block1.moveUp();
		            	System.out.println(event.getCode());
            		} else {
		            	imageView.setTranslateY(imageView.getTranslateY()-20);
		            	player1.moveUp();
		            	System.out.println(event.getCode());
            		}
            	}
            }
            else if (event.getCode() == KeyCode.LEFT) {
            	if (imageView.getTranslateX() > 0) {
            		if (block1.getX() == player1.getX()-1 && block1.getY() == player1.getY() && imageView1.getTranslateX() > -20) {
		            	imageView.setTranslateX(imageView.getTranslateX()-20);
		            	imageView1.setTranslateX(imageView1.getTranslateX()-20);
		            	player1.moveLeft();
		            	block1.moveLeft();
		            	System.out.println(event.getCode());
            		} else {
		            	imageView.setTranslateX(imageView.getTranslateX()-20);
		            	player1.moveLeft();
		            	System.out.println(event.getCode());
            		}
            	}
            }
            else if (event.getCode() == KeyCode.RIGHT) {
            	if (imageView.getTranslateX() < Game.getWidth()-31) {
            		if (block1.getX() == player1.getX()+1 && block1.getY() == player1.getY() && imageView1.getTranslateX() < Game.getWidth()-62) {
		            	imageView.setTranslateX(imageView.getTranslateX()+20);
		            	imageView1.setTranslateX(imageView1.getTranslateX()+20);
		            	player1.moveRight();
		            	block1.moveRight();
		            	System.out.println(event.getCode());
            		} else {
		            	imageView.setTranslateX(imageView.getTranslateX()+20);
		            	player1.moveRight();
		            	System.out.println(event.getCode());
            		}
            	}
            }
            else if (event.getCode() == KeyCode.ESCAPE) {
            	primaryStage.setScene(Menu);
            }
        });
        
        //Show final
        primaryStage.setScene(Menu);
        primaryStage.show();

    }
    
}
