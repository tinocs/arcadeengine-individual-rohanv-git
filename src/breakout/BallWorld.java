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
import java.util.ArrayList;
import java.util.Scanner;

import engine.Actor;
import engine.Sound;
import engine.World;
import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BallWorld extends World {
	private Score score;
	private int level;
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
	private ArrayList<Animation> animations = new ArrayList<>();
	private Image bgImg = new Image("breakoutresources/background.png");
	private ImageView bgView = new ImageView(bgImg);
	private int w, h;

	public BallWorld(int w, int h, Stage stage, Scene scene) {
		setPrefSize(w,h);
		this.w = w;
		this.h = h;
		level = 1;
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
			lives = 4;
			updateLives();
		}
		if (level > 2 && !isGameOver) {
			isGameOver = true;
			stop();
			wonSound.play();
			stage.setScene(scene);
			showGameOver(true);
		}
		if (lives < 1 && !isGameOver) {
			isGameOver = true;
			stop();
			lostSound.play();
			showGameOver(false);
		}
	}

	public void showGameOver(boolean won) {
		VBox root = new VBox(20);
		root.setStyle("-fx-background-color: black;");
		root.setAlignment(Pos.CENTER);

		Text msg = new Text(won ? "You Win!" : "Game Over");
		msg.setFill(Color.RED);
		msg.setFont(Font.font("Courier New", 64));

		Button menuBtn = new Button("Return to Menu");
		menuBtn.setOnAction(e -> stage.setScene(scene));

		root.getChildren().addAll(msg, menuBtn);

		Scene gameOverScene = new Scene(root, 800, 600);
		stage.setScene(gameOverScene);
		stage.show();
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
		stopAllAnimations();
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	@Override
	public void onDimensionsInitialized() {
		//Background
		getChildren().add(bgView);
		bgView.setX(-(bgImg.getWidth() - getWidth())/ 2);
		bgView.setY(0);

		// Paddle
		paddle = new Paddle();
		add(paddle);
		paddle.setX(getWidth() / 2 - paddle.getWidth() / 2);
		paddle.setY(getHeight() * 0.8 - paddle.getHeight() / 2);

		// Ball
		ball = new Ball(paddle);
		add(ball);
		ball.setX(getWidth() / 2 - ball.getWidth() / 2);
		ball.setY(getHeight() * 0.7 - ball.getHeight() / 2);

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
		livesText.setX(100);
		livesText.setY(25);
		getChildren().add(livesText);

		//Score
		score = new Score();
		score.setX(getWidth() - 200);
		score.setY(25);
		getChildren().add(score);		

		start();

		//Paused
		//setEffect(new GaussianBlur(10));
		paused = new Text("Game paused. Click space to start");
		paused.setFont(Font.font("Courier New", 24));
		paused.setX(getWidth() / 2 - paused.getLayoutBounds().getWidth() / 2);
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
					} else if (bricks[i][j] == 2) {
						String path = getClass().getClassLoader().getResource("breakoutresources/brick2.png").toString();
						brick.setImage(new Image(path));
						add(brick);
						brick.setX((j * brick.getWidth()) + (getWidth() / 2 - ((bricks.length)*brick.getWidth())) - brick.getWidth()/2);
						brick.setY((i * (brick.getHeight()) + (getHeight() / 2 - (bricks[0].length/2)*brick.getWidth())));
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

	public void addAnimation(Animation a) {
		animations.add(a);
	}

	public void stopAllAnimations() {
		for (int i = animations.size() - 1; i >= 0; i --) {
			animations.get(i).stop();
			animations.remove(i);
		}
	}

	public void removeAnimation(Animation a) {
		animations.remove(animations.indexOf(a));
	}

	public void scroll(double dx) {
		if (bgView.getX() - dx <= 0 && bgView.getX() - dx >= w - bgImg.getWidth()) {
			bgView.setX(bgView.getX() - dx);

			for (Actor a : getObjects(Actor.class)) {
				a.setX(a.getX() - dx);
			}
		}

	}

	public Stage getStage() {
		return stage;
	}
}
