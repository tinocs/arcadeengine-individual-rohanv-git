/*
    Name:       Rohan Vellamcheti
    Date:       (submission date)
    Period:     P1 APCS- Ferrante

    Is this lab fully working?  (Yes/No)  If not, explain:
    If resubmitting, explain what was wrong and what you fixed.
*/
package breakout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Breakout extends Application {
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Breakout");
		
		BorderPane root = new BorderPane();
		BallWorld ballWorld = new BallWorld(800,600);
		root.setCenter(ballWorld);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}

}
