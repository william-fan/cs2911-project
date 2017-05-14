import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PuzzleMaker {
	private int sizeX = 0;
	private int sizeY = 0;
	
	public Scene PuzzleMakerHome(Stage primaryStage, Scene menu) {
		//Add ui boxes
		BorderPane center = new BorderPane();
		GridPane gamePane = new GridPane();
		FlowPane gameUI = new FlowPane();
		center.setCenter(gamePane);
		center.setRight(gameUI);
		
		//Text fields, buttons
		Label xLabel = new Label("x size: (Must be less than 20)");
        TextField xTextField = new TextField();
		Label yLabel = new Label("y size: (Must be less than 20)");
        TextField yTextField = new TextField();
        
		Button setSizeButton = new Button("Set Size");
        gameUI.getChildren().addAll(xLabel, xTextField, yLabel, yTextField, setSizeButton);
        
        xTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	if (!newValue.matches("\\d*")) {
            		Platform.runLater(() -> {  //prevent IllegalArgumentException
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
            		Platform.runLater(() -> {  //prevent IllegalArgumentException
            			yTextField.setText(newValue.replaceAll("[^\\d]", ""));
            		});
                } else if (newValue.length() > 0 && Integer.parseInt(newValue) > 20) {
                	yTextField.setText(oldValue);
                } 
            }
        });
             
        setSizeButton.setOnAction(actionEvent -> {
        	if (xTextField.getLength() == 0 || yTextField.getLength() == 0) {
        		return;
        	}
			this.sizeX = Integer.parseInt(xTextField.getText());
			this.sizeY = Integer.parseInt(yTextField.getText());
			if (this.sizeX == 0 || this.sizeY == 0) {
				return;
			}
			
		});
        	
        	
        Scene Game = new Scene(center);
		return Game;
		

	}
	

}
