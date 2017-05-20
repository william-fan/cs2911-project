import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PuzzleMaker {
	private int sizeX = 0;
	private int sizeY = 0;
	private GridPane gamePane = new GridPane();

	public Scene PuzzleMakerHome(Stage primaryStage, Scene menu) {
		// Add ui boxes
		BorderPane center = new BorderPane();
		center.setPrefSize(800, 800);
		FlowPane gameUI = new FlowPane();
		center.setCenter(this.gamePane);
		center.setBottom(gameUI);

		// Text fields, buttons
		Label xLabel = new Label("x size: (Must be less than 20)");
		TextField xTextField = new TextField();
		Label yLabel = new Label("y size: (Must be less than 20)");
		TextField yTextField = new TextField();
		
		Button exportMap = new Button("Export map");
		Button setSizeButton = new Button("Set Size");
		gameUI.getChildren().addAll(xLabel, xTextField, yLabel, yTextField, setSizeButton, exportMap);
		
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
						tile.setStyle("-fx-text-fill: white");
						tile.setBackground(new Background(new BackgroundImage(
								new Image(new File("images/ground.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
								BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
						tile.setOnMouseClicked(new MapButtonHandler());
						this.gamePane.add(tile, x, y);
					}
				}
			}
		});
		
		exportMap.setOnAction(actionEvent -> {
			exportMap(primaryStage);
		});
		
		Scene Game = new Scene(center);
		return Game;

	}

	private void exportMap(Stage primaryStage) {
		//Format export string
		String map = "";
		int blockCount = 0;
		int targetCount = 0;
		int playerCount = 0;
		for (int y = -1; y < this.sizeY+1; y++) {
			for (int x = -1; x < this.sizeX+1; x++) {
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
						map += "1";
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
			map += " #\r\n";
		}
		
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
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
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
			//set new button image when clicked
			String oldText = ((Button) event.getSource()).getText();
			int newVal = Integer.parseInt(oldText);
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
			((Button) event.getSource()).setText(Integer.toString(newVal));
			((Button) event.getSource()).setBackground(buttonBackground);
		}
	}
}
