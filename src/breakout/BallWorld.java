/*
    Name:       Rohan Vellamcheti
    Date:       (submission date)
    Period:     P1 APCS- Ferrante

    Is this lab fully working?  (Yes/No)  If not, explain:
    If resubmitting, explain what was wrong and what you fixed.
 */
package breakout;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import engine.World;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class BallWorld extends World {
	private Score score;
	private int level;
	private int numBricks;
	private Stage stage;
	private Scene scene;

	public BallWorld(int w, int h, int numBricks, Stage stage, Scene scene) {
		setPrefSize(w,h);
		level = 3;
		this.numBricks = numBricks;
		this.stage = stage;
		this.scene = scene;
	}

	@Override
	public void act(long now) {
		if (getObjects(Brick.class).size() == 0) {
			level++;
			readBricks();
		}
		if (level > 2) {
			stage.setScene(scene);
		}
	}

	@Override
	public void onDimensionsInitialized() {
		// Ball
		Ball ball = new Ball();
		add(ball);
		ball.setX(getWidth() / 2 - ball.getWidth() / 2);
		ball.setY(getHeight() / 2 - ball.getHeight() /2);

		// Paddle
		Paddle paddle = new Paddle();
		add(paddle);
		paddle.setX(getWidth() / 2 - paddle.getWidth() / 2);
		paddle.setY(getHeight() * 0.8 - paddle.getHeight() / 2);

		// Paddle movement
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				paddle.setX(event.getX() - paddle.getWidth() / 2);
			}});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				paddle.setX(event.getX() - paddle.getWidth() / 2);
			}});

		// Brick
		readBricks();

		// Score
		score = new Score();
		score.setX(getWidth() - 100);
		score.setY(25);
		getChildren().add(score);		

		start();
	}
	
	public void readBricks() {
		File file = new File("src/breakoutresources/bricks.txt");
		Scanner scnr;
		try {
			scnr = new Scanner(file);

			int[][] bricks = new int[scnr.nextInt()][scnr.nextInt()];
			scnr.nextLine();
			
			int row = 0;
			while (scnr.hasNextLine()) {
				String lineStr = scnr.nextLine();
				if (lineStr.equals("BREAK")) {
					break;
				}
				String[] brickStrs = lineStr.split(" ");
				for (int i = 0; i < brickStrs.length; i++) {
					bricks[row][i] = Integer.parseInt(brickStrs[i]);
				}
				row++;
			}
			
			for (int i = 0; i < bricks.length; i++) {
				for (int j = 0; j < bricks[0].length; j++) {
					Brick brick = new Brick();
					
					if (bricks[i][j] == 1) {
						add(brick);
						brick.setX((j * brick.getWidth()) + (getWidth() / 2 - ((bricks.length)*brick.getWidth())) - brick.getWidth()/2);
						brick.setY((i * (brick.getHeight()) + (getHeight() / 2 - (bricks[0].length/2)*brick.getWidth())));
						numBricks++;
					} else if (bricks[i][j] == 2) {
						String path = getClass().getClassLoader().getResource("breakoutresources/brick2.png").toString();
						brick.setImage(new Image(path));
						add(brick);
						brick.setX((j * brick.getWidth()) + (getWidth() / 2 - ((bricks.length)*brick.getWidth())) - brick.getWidth()/2);
						brick.setY((i * (brick.getHeight()) + (getHeight() / 2 - (bricks[0].length/2)*brick.getWidth())));
						numBricks++;
					}
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Score getScore() {
		return score;
	}

}
