import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PuzzleMaker {
	private int sizeX = 0;
	private int sizeY = 0;
	private GridPane gamePane = new GridPane();

	public Scene PuzzleMakerHome(Stage primaryStage, Scene menu) {
		// Load font
		try {
			Font.loadFont(new FileInputStream(new File("fonts/FSEX300.ttf")), 20);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Add ui boxes
		// Background style
		String background = "-fx-background-image: url(file:images/background.png);" + "\n"
		   					+ "-fx-background-size: stretch;" + "\n"
		   					+ "-fx-background-repeat: no-repeat;";
		resetMap();
		this.gamePane.setAlignment(Pos.CENTER);
		this.gamePane.setVgap(0.5);
		this.gamePane.setHgap(0.5);
		
		BorderPane center = new BorderPane();
		center.setPrefSize(960, 800);
		
		GridPane gameUI = new GridPane();
		
		center.setCenter(this.gamePane);
		center.setBottom(gameUI);
		center.setStyle(background);
		
		// Text fields, buttons
		
		Label xLabel = new Label("Width: (max 20)");
		xLabel.setStyle("-fx-text-fill: white;" + "\n" +
				 "-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
				 "-fx-font-size: 18;" + "\n");
		TextField xTextField = new TextField();
		xTextField.setMaxWidth(50);		
		xTextField.setStyle("-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
				 "-fx-font-size: 18;" + "\n" +
				 "-fx-control-inner-background: #3F3F3F;" + "\n" +
				 "-fx-focus-color: transparent;" + "\n" +
				 "-fx-text-box-border: transparent;" + "\n" +
				 "-fx-background-insets: 0;" + "\n");
		
		Label yLabel = new Label("Height: (max 20)");
		yLabel.setStyle("-fx-text-fill: white;" + "\n" +
				 "-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
				 "-fx-font-size: 18;" + "\n");
		TextField yTextField = new TextField();
		yTextField.setMaxWidth(50);
		yTextField.setStyle("-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
				 "-fx-font-size: 18;" + "\n" +
				 "-fx-control-inner-background: #3F3F3F;" + "\n" +
				 "-fx-focus-color: transparent;" + "\n" +
				 "-fx-text-box-border: transparent;" + "\n" +
				 "-fx-background-insets: 0;" + "\n");

		Button exportMap = new Button("Export map");
		exportMap.setStyle("-fx-text-fill: white;" + "\n" +
				 "-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
				 "-fx-font-size: 18;" + "\n" +
				 "-fx-background-color: #000000;" + "\n");
		
		Button setSizeButton = new Button("Set Size");
		setSizeButton.setStyle("-fx-text-fill: white;" + "\n" +
				 "-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
				 "-fx-font-size: 18;" + "\n" +
				 "-fx-background-color: #000000;" + "\n");
		
		Button mainMenu = new Button("Main Menu");
		mainMenu.setStyle("-fx-text-fill: white;" + "\n" +
				 "-fx-font-family: \"Fixedsys Excelsior 3.01\";"  + "\n" +
				 "-fx-font-size: 18;" + "\n" +
				 "-fx-background-color: #000000;" + "\n");
		
		exportMap.setFocusTraversable(false);
		setSizeButton.setFocusTraversable(false);
		mainMenu.setFocusTraversable(false);
		
		gameUI.setAlignment(Pos.CENTER);
		gameUI.add(xLabel, 3, 0);
		gameUI.add(xTextField, 4, 0);
		gameUI.add(yLabel, 5, 0);
		gameUI.add(yTextField, 6, 0);
		gameUI.add(setSizeButton, 7, 0);
		gameUI.add(exportMap, 8, 0);
		gameUI.add(mainMenu, 9, 0);
		gameUI.add(new Label(""), 1, 2);
		gameUI.setVgap(10);
		gameUI.setHgap(10);
		
				//xTextField, yLabel, yTextField, setSizeButton, exportMap, mainMenu);
		
		//Add text field restrictions, no text, cannot be > 20
		xTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					Platform.runLater(() -> { // prevent IllegalArgumentException
						xTextField.setText(newValue.replaceAll("[^\\d]", ""));
					});
				} else if (newValue.length() > 0 && Integer.parseInt(newValue) > 20) {
					xTextField.setText(oldValue);
				}
			}
		});

		yTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					Platform.runLater(() -> { // prevent IllegalArgumentException
						yTextField.setText(newValue.replaceAll("[^\\d]", ""));
					});
				} else if (newValue.length() > 0 && Integer.parseInt(newValue) > 20) {
					yTextField.setText(oldValue);
				}
			}
		});
		
		setSizeButton.setOnAction(actionEvent -> {
			//Set grid pane to set size and add buttons to each node
			this.sizeX = 0;
			this.sizeY = 0;
			this.gamePane.getChildren().clear();
			if (xTextField.getLength() == 0 || yTextField.getLength() == 0) {
				return;
			}
			this.sizeX = Integer.parseInt(xTextField.getText());
			this.sizeY = Integer.parseInt(yTextField.getText());
			if (this.sizeX == 0 || this.sizeY == 0) {
				return;
			}
			this.gamePane.setMinSize(this.sizeX, this.sizeY);

			if (this.sizeX != 0 && this.sizeY != 0) {
				for (int x = 0; x < this.sizeX; x++) {
					for (int y = 0; y < this.sizeY; y++) {
						Button tile = new Button("0");
						tile.setStyle("-fx-text-fill: transparent");
						tile.setBackground(new Background(new BackgroundImage(
								new Image(new File("images/ground.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
								BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
						tile.setOnMouseClicked(new MapButtonHandler());
						tile.setPrefSize(32, 32);
						this.gamePane.add(tile, x, y);
					}
				}
			}
		});
		
		yTextField.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				//Set grid pane to set size and add buttons to each node
				this.sizeX = 0;
				this.sizeY = 0;
				this.gamePane.getChildren().clear();
				if (xTextField.getLength() == 0 || yTextField.getLength() == 0) {
					return;
				}
				this.sizeX = Integer.parseInt(xTextField.getText());
				this.sizeY = Integer.parseInt(yTextField.getText());
				if (this.sizeX == 0 || this.sizeY == 0) {
					return;
				}
				this.gamePane.setMinSize(this.sizeX, this.sizeY);
	
				if (this.sizeX != 0 && this.sizeY != 0) {
					for (int x = 0; x < this.sizeX; x++) {
						for (int y = 0; y < this.sizeY; y++) {
							Button tile = new Button("0");
							tile.setStyle("-fx-text-fill: transparent");
							tile.setBackground(new Background(new BackgroundImage(
									new Image(new File("images/ground.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
									BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
							tile.setOnMouseClicked(new MapButtonHandler());
							tile.setPrefSize(32, 32);
							this.gamePane.add(tile, x, y);
						}
					}
				}
			}
		});
		
		yTextField.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				//Set grid pane to set size and add buttons to each node
				this.sizeX = 0;
				this.sizeY = 0;
				this.gamePane.getChildren().clear();
				if (xTextField.getLength() == 0 || yTextField.getLength() == 0) {
					return;
				}
				this.sizeX = Integer.parseInt(xTextField.getText());
				this.sizeY = Integer.parseInt(yTextField.getText());
				if (this.sizeX == 0 || this.sizeY == 0) {
					return;
				}
				this.gamePane.setMinSize(this.sizeX, this.sizeY);
	
				if (this.sizeX != 0 && this.sizeY != 0) {
					for (int x = 0; x < this.sizeX; x++) {
						for (int y = 0; y < this.sizeY; y++) {
							Button tile = new Button("0");
							tile.setStyle("-fx-text-fill: transparent");
							tile.setBackground(new Background(new BackgroundImage(
									new Image(new File("images/ground.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
									BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
							tile.setOnMouseClicked(new MapButtonHandler());
							tile.setPrefSize(32, 32);
							this.gamePane.add(tile, x, y);
						}
					}
				}
			}
		});
		
		exportMap.setOnAction(actionEvent -> {
			exportMap(primaryStage);
		});
		
		mainMenu.setOnAction(actionEvent -> {
			primaryStage.setScene(menu);
		});
		
		Scene Game = new Scene(center);
		return Game;

	}
	
	private void resetMap() {
		this.sizeX = 0;
		this.sizeY = 0;
		this.gamePane = new GridPane();
	}
	
	private void exportMap(Stage primaryStage) {
		//Format export string
		String map = "";
		int blockCount = 0;
		int targetCount = 0;
		int playerCount = 0;
		//Start from -1 as we want to add a surrounding wall around map
		for (int y = -1; y < this.sizeY + 1; y++) {
			for (int x = -1; x < this.sizeX + 1; x++) {
				if (x == -1 || x == this.sizeX || y == -1 || y == this.sizeY) {
					if (x != -1) {
						map += " ";
					}
					map += "4";
				} else {
					Button tempButton = ((Button)gamePane.getChildren().get(x*this.sizeX+y));
					if (x != -1) {
						map += " ";
					}
					if (tempButton.getText().equals("0")) {
						map += "0";
					} else if (tempButton.getText().equals("1")) {
						map += "4"; //1 is used as void
					} else if (tempButton.getText().equals("2")) {
						map += "2";
						blockCount++;
					} else if (tempButton.getText().equals("3")) {
						map += "3";
						playerCount++;
					} else if (tempButton.getText().equals("4")) {
						map += "5";
						targetCount++;
					}
				}
			}
			map += " #\r\n"; //Add new line
		}
		
		//Add requirements
		if (playerCount == 0) {
        	Alert confirmExit = new Alert(AlertType.ERROR, "Must be at least one player tile", ButtonType.OK);
        	confirmExit.setTitle("Exit Game");
        	confirmExit.showAndWait();
        	return;
		}
		
		if (blockCount == 0 || targetCount == 0) {
        	Alert confirmExit = new Alert(AlertType.ERROR, "Must be at least one block or target tile", ButtonType.OK);
        	confirmExit.setTitle("Exit Game");
        	confirmExit.showAndWait();
        	return;
		}
		
		if (blockCount != targetCount) {
        	Alert confirmExit = new Alert(AlertType.ERROR, "Blocks amount must equal target amount.", ButtonType.OK);
        	confirmExit.setTitle("Exit Game");
        	confirmExit.showAndWait();
        	return;
		}
		
		//Open file chooser, only allow text file saving
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Map File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Map files", "*.map");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);
        
        //Write to file if available
        if(file != null){
    	    try {
                FileWriter fileWriter = null;
                fileWriter = new FileWriter(file);
                fileWriter.write(map);
                fileWriter.close();
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
        }
	}
	
	private class MapButtonHandler implements EventHandler<MouseEvent> {
		public void handle(MouseEvent event) {
			String oldText = ((Button) event.getSource()).getText();
			int newVal = Integer.parseInt(oldText);
			//change direction of next image tile based on mouse clicks
			if (event.getButton() == MouseButton.PRIMARY) {
				newVal++;
			} else if (event.getButton() == MouseButton.SECONDARY) {
				newVal--;
			}
			if (newVal == -1) {
				newVal = 4;
			}
			if (newVal == 5) {
				newVal = 0;
			}
			Background buttonBackground = null;
			
			//set button image
			if (newVal == 0) {
				buttonBackground = new Background(new BackgroundImage(
						new Image(new File("images/ground.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
			} else if (newVal == 1) {
				buttonBackground = new Background(new BackgroundImage(
						new Image(new File("images/wall.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
			} else if (newVal == 2) {
				buttonBackground = new Background(new BackgroundImage(
						new Image(new File("images/block1.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
			} else if (newVal == 3) {
				buttonBackground = new Background(new BackgroundImage(
						new Image(new File("images/sprite1.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
			} else if (newVal == 4) {
				buttonBackground = new Background(new BackgroundImage(
						new Image(new File("images/target.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
			}
			
			//set new text
			((Button) event.getSource()).setText(Integer.toString(newVal));
			((Button) event.getSource()).setBackground(buttonBackground);
		}
	}
}
