/*
    Name:       Rohan Vellamcheti
    Date:       (submission date)
    Period:     P1 APCS- Ferrante

    Is this lab fully working?  (Yes/No)  If not, explain:
    If resubmitting, explain what was wrong and what you fixed.
 */
package breakout;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Breakout extends Application {
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Breakout");
		stage.setWidth(800);
		stage.setHeight(600);
		
		BorderPane root2 = new BorderPane();
		Scene scene2 = new Scene(root2);
		
		// TITLE
		VBox root1 = new VBox();
		Button playBtn = new Button("Play");
		playBtn.setOnAction(e -> {
			stage.setScene(scene2);
			stage.show();
		});
		
		String path = getClass().getClassLoader().getResource("breakoutresources/title.png").toString();
		Image titleImg = new Image(path);
		ImageView titleView = new ImageView();
		titleView.setImage(titleImg);
		titleView.setFitWidth(600);
		titleView.setFitHeight(112.5);
		
		root1.getChildren().addAll(titleView, playBtn);
		root1.setStyle("-fx-background-color: black;");
		
		root1.setAlignment(Pos.CENTER);
		Scene scene1 = new Scene(root1);
		stage.setScene(scene1);
		stage.show();
		
		// BREAKOUT

		BallWorld ballWorld = new BallWorld(800,600, 10, stage, scene1);
		root2.setCenter(ballWorld);
		
	}

}
