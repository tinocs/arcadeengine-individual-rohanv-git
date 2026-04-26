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

import engine.Sound;
import engine.World;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BallWorld extends World {
	private Score score;
	private int level;
	private int numBricks;
	private Stage stage;
	private Scene scene;
	private int lives;
	private boolean isPaused;
	private Text paused;
	private Text livesText;
	private Ball ball;
	private Paddle paddle;
	private Sound lostSound = new Sound("ballbounceresources/game_lost.wav");
	private Sound lifeSound = new Sound("ballbounceresources/lose_life.wav");
	private Sound wonSound = new Sound("ballbounceresources/game_won.wav");
	private boolean isGameOver = false;

	public BallWorld(int w, int h, int numBricks, Stage stage, Scene scene) {
		setPrefSize(w,h);
		level = 1;
		this.numBricks = numBricks;
		this.stage = stage;
		this.scene = scene;
		lives = 3;
		isPaused = true;

	}



	@Override
	public void act(long now) {
		if (isKeyPressed(KeyCode.SPACE) && isPaused) {
			isPaused = false;
			setEffect(null);
			getChildren().remove(paused);
		}
		if (getObjects(Brick.class).size() == 0) {
			level++;
			readBricks();
		}
		if (level > 2 && !isGameOver) {
		    isGameOver = true;
		    stop();
		    wonSound.play();
		    stage.setScene(scene);
		}
		if (lives < 1 && !isGameOver) {
		    isGameOver = true;
		    stop();
		    lostSound.play();
		    stage.setScene(scene);
		}
	}

	public void updateLives() {
		lives--;
		livesText.setText("Lives: " + lives);
		setPaused(true);
	    getChildren().add(paused);
	    ball.setX(getWidth() / 2 - ball.getWidth() / 2);
		ball.setY(getHeight() * 0.75 - ball.getHeight() / 2);
		paddle.setX(getWidth() / 2 - paddle.getWidth() / 2);
		paddle.setY(getHeight() * 0.8 - paddle.getHeight() / 2);
		lifeSound.play();
		
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	@Override
	public void onDimensionsInitialized() {
		// Ball
		ball = new Ball();
		add(ball);
		ball.setX(getWidth() / 2 - ball.getWidth() / 2);
		ball.setY(getHeight() * 0.7 - ball.getHeight() / 2);

		// Paddle
		paddle = new Paddle();
		add(paddle);
		paddle.setX(getWidth() / 2 - paddle.getWidth() / 2);
		paddle.setY(getHeight() * 0.8 - paddle.getHeight() / 2);

		// Paddle movement
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!isPaused) {
					paddle.setX(event.getX() - paddle.getWidth() / 2);
				}
			}});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!isPaused) {
					paddle.setX(event.getX() - paddle.getWidth() / 2);
				}
			}});

		// Brick
		readBricks();


		// Lives
		livesText = new Text("Lives:	" + lives);
		livesText.setFont(new Font(livesText.getFont().getSize() + 5));
		livesText.setX(getWidth() - 200);
		livesText.setY(25);
		getChildren().add(livesText);
		
		//Score
		score = new Score();
		score.setX(getWidth() - 100);
		score.setY(25);
		getChildren().add(score);		

		start();
		
		//Paused
		//setEffect(new GaussianBlur(10));
		paused = new Text("Game paused \nClick space to start");
		paused.setFont(Font.font("Courier New", 48));
		paused.setX(50);
		paused.setY(getHeight() / 2);
		getChildren().add(paused);

	}

	public Text getPausedText() {
		return paused;
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
