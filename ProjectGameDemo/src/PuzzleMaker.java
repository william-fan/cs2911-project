import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This is the PuzzleMaker class, in charge of the puzzle making function.
 * It opens the PuzzleHome class.
 * 
 * @authors 
 * William Fan, Bob Guo, Charles Lu, Alexander Ong, Allan Wu
 */

public class PuzzleMaker {
	private int sizeX = 0;
	private int sizeY = 0;
	private GridPane gamePane = new GridPane();

	/**
	 * Sets the scene for the puzzle maker including buttons, text box for size
	 * input and the puzzle when set size is clicked. 
	 * @param primaryStage The pane for the puzzle window
	 * @param menu The layout of the menu
	 * @return The UI of the puzzle maker
	 */
	public Scene PuzzleMakerHome(Stage primaryStage, Scene menu) {
		// Add ui boxes
		// Background style
		String background = "-fx-background-image: url(file:images/background.png);" + "\n"
				+ "-fx-background-size: stretch;" + "\n" + "-fx-background-repeat: no-repeat;";
		resetMap();
		this.gamePane.setAlignment(Pos.CENTER);
		this.gamePane.setVgap(0.5);
		this.gamePane.setHgap(0.5);

		BorderPane center = new BorderPane();
		center.setPrefSize(960, 800);

		GridPane gameUI = new GridPane();

		// Title
		HBox title = new HBox();
		Label titleLabel = new Label("Puzzle Maker");
		titleLabel.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
				+ "-fx-font-size: 40;" + "\n");
		title.setAlignment(Pos.CENTER);
		title.setPadding(new Insets(20, 0, 0, 0));
		title.getChildren().add(titleLabel);
		title.setFocusTraversable(false);

		center.setCenter(this.gamePane);
		center.setBottom(gameUI);
		center.setTop(title);
		center.setStyle(background);

		// Text fields, buttons

		Label xLabel = new Label("Width: (max 18)");
		xLabel.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
				+ "-fx-font-size: 18;" + "\n");
		TextField xTextField = new TextField();
		xTextField.setMaxWidth(50);
		xTextField.setStyle("-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n" + "-fx-font-size: 18;" + "\n"
				+ "-fx-control-inner-background: #3F3F3F;" + "\n" + "-fx-focus-color: transparent;" + "\n"
				+ "-fx-text-box-border: transparent;" + "\n" + "-fx-background-insets: 0;" + "\n");

		Label yLabel = new Label("Height: (max 13)");
		yLabel.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
				+ "-fx-font-size: 18;" + "\n");
		TextField yTextField = new TextField();
		yTextField.setMaxWidth(50);
		yTextField.setStyle("-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n" + "-fx-font-size: 18;" + "\n"
				+ "-fx-control-inner-background: #3F3F3F;" + "\n" + "-fx-focus-color: transparent;" + "\n"
				+ "-fx-text-box-border: transparent;" + "\n" + "-fx-background-insets: 0;" + "\n");

		Button exportMap = new Button("Export map");
		exportMap.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
				+ "-fx-font-size: 18;" + "\n" + "-fx-background-color: #000000;" + "\n");

		Button setSizeButton = new Button("Set Size");
		setSizeButton.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
				+ "-fx-font-size: 18;" + "\n" + "-fx-background-color: #000000;" + "\n");

		Button mainMenu = new Button("Main Menu");
		mainMenu.setStyle("-fx-text-fill: white;" + "\n" + "-fx-font-family: \"Fixedsys Excelsior 3.01\";" + "\n"
				+ "-fx-font-size: 18;" + "\n" + "-fx-background-color: #000000;" + "\n");

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

		// xTextField, yLabel, yTextField, setSizeButton, exportMap, mainMenu);

		// Add text field restrictions, no text, cannot be > 20
		xTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			/**
			 * Checks if the size has changed
			 */
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					Platform.runLater(() -> { // prevent
												// IllegalArgumentException
						xTextField.setText(newValue.replaceAll("[^\\d]", ""));
					});
				} else if (newValue.length() > 0 && Integer.parseInt(newValue) > 18) {
					xTextField.setText(oldValue);
				}
			}
		});

		yTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					Platform.runLater(() -> { // prevent
												// IllegalArgumentException
						yTextField.setText(newValue.replaceAll("[^\\d]", ""));
					});
				} else if (newValue.length() > 0 && Integer.parseInt(newValue) > 13) {
					yTextField.setText(oldValue);
				}
			}
		});

		setSizeButton.setOnAction(actionEvent -> {
			// Set grid pane to set size and add buttons to each node
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
						tile.setFocusTraversable(false);
						this.gamePane.add(tile, x, y);
					}
				}
			}
		});

		xTextField.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				// Set grid pane to set size and add buttons to each node
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
							tile.setBackground(new Background(
									new BackgroundImage(new Image(new File("images/ground.png").toURI().toString()),
											BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
											BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
							tile.setOnMouseClicked(new MapButtonHandler());
							tile.setPrefSize(32, 32);
							tile.setFocusTraversable(false);
							this.gamePane.add(tile, x, y);
						}
					}
				}
			}
		});

		yTextField.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				// Set grid pane to set size and add buttons to each node
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
							tile.setBackground(new Background(
									new BackgroundImage(new Image(new File("images/ground.png").toURI().toString()),
											BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
											BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
							tile.setOnMouseClicked(new MapButtonHandler());
							tile.setPrefSize(32, 32);
							tile.setFocusTraversable(false);
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

	/**
	 * Resets the map when puzzle maker is exited.
	 */
	private void resetMap() {
		this.sizeX = 0;
		this.sizeY = 0;
		this.gamePane = new GridPane();
	}

	/**
	 * Exports the puzzle generated to a .map file. Also has checks if the map
	 * contains at least 1 player tile, blocks and goal tiles and whether the
	 * number of blocks and goals are the same.
	 * @param primaryStage The pane for the puzzle window
	 */
	private void exportMap(Stage primaryStage) {
		// Format export string
		String map = "";
		int blockCount = 0;
		int targetCount = 0;
		int playerCount = 0;
		// Start from -1 as we want to add a surrounding wall around map
		for (int y = -1; y < this.sizeY + 1; y++) {
			for (int x = -1; x < this.sizeX + 1; x++) {
				if (x == -1 || x == this.sizeX || y == -1 || y == this.sizeY) {
					if (x != -1) {
						map += " ";
					}
					map += "4";
				} else {

					Button tempButton = ((Button) getNode(this.gamePane, x, y));
					if (x != -1) {
						map += " ";
					}
					if (tempButton.getText().equals("0")) {
						map += "0";
					} else if (tempButton.getText().equals("1")) {
						map += "4"; // 1 is used as void
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
			map += " #\r\n"; // Add new line
		}

		// Add requirements
		if (playerCount != 2) {
			Alert confirmExit = new Alert(AlertType.ERROR, "Must be only two starting player positions", ButtonType.OK);
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

		// Open file chooser, only allow text file saving
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Map File");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Map files", "*.map");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog(primaryStage);

		// Write to file if available
		if (file != null) {
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

	/**
	 * Finds the node in the specified position within the grid pane.
	 * @param gridPane The specified grid pane.
	 * @param col The integer column.
	 * @param row The integer row.
	 * @return The node at the specific location.
	 */
	private Node getNode(GridPane gridPane, int col, int row) {
		for (Node node : gridPane.getChildren()) {
			if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
				return node;
			}
		}
		return null;
	}

	/**
	 * This allows the user to click on the tile to change its type from a goal
	 * to a player tile. Users can use left clicks or right clicks to change the
	 * tile type.
	 * @param event Any mouse clicks when clicking the tiles
	 */
	private class MapButtonHandler implements EventHandler<MouseEvent> {
		public void handle(MouseEvent event) {
			String oldText = ((Button) event.getSource()).getText();
			int newVal = Integer.parseInt(oldText);
			// change direction of next image tile based on mouse clicks
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

			// set button image
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
						new Image(new File("images/sprite_pm.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
			} else if (newVal == 4) {
				buttonBackground = new Background(new BackgroundImage(
						new Image(new File("images/target.png").toURI().toString()), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
			}

			// set new text
			((Button) event.getSource()).setText(Integer.toString(newVal));
			((Button) event.getSource()).setBackground(buttonBackground);
		}
	}
}
