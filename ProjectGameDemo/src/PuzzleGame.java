import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class PuzzleGame {
	
	public FlowPane Game() { 
		//Label label = new Label("This is the game screen");
		FlowPane pane2 = new FlowPane();
    	//pane2.getChildren().addAll(label);
    	
        
		return pane2;
		
	}
	
	public FlowPane Help() { 
		Label label = new Label("This is the help screen\nTEST");
		FlowPane pane2 = new FlowPane();
    	
		pane2.getChildren().addAll(label);
		
	    
		return pane2;
	}

}
