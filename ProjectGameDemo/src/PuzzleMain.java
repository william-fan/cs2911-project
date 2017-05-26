import javafx.application.Application;

/**
 * This is the class that holds the Main function. It opens the PuzzleHome
 * class.
 *
 * This program is a sokoban puzzle program which gives the user a fun and
 * challenging experience of solving the puzzle. It offers the user single
 * player mode or 2 player mode. In single player mode, users are presented
 * with a small map which can be prone to deadlocks if the user makes a 
 * mistake. In 2 player mode, users must utilise teamwork in order to solve
 * the sokoban puzzle. There are differing difficulties for all users of
 * different levels. Additionally, this program offers a puzzle maker
 * where users can create their own sokoban puzzle and share with other
 * users for them to try.
 *
 * @authors 
 * William Fan, Bob Guo, Charles Lu, Alexander Ong, Allan Wu
 */

public class PuzzleMain {

	public static void main(String[] args) {
		Application.launch(PuzzleHome.class, args);
	}

}
