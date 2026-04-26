/*
    Name:       Rohan Vellamcheti
    Date:       (submission date)
    Period:     P1 APCS- Ferrante

    Is this lab fully working?  (Yes/No)  If not, explain:
    If resubmitting, explain what was wrong and what you fixed.
 */
package breakout;

import engine.Actor;
import engine.Sound;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Ball extends Actor {
	private double dx, dy;
	private Sound bounceSound = new Sound("ballbounceresources/ball_bounce.wav");
	private Sound brickSound = new Sound("ballbounceresources/brick_hit.wav");
	

	public Ball() {
		String path = getClass().getClassLoader().getResource("breakoutresources/ball.png").toString();
		Image ballImg = new Image(path);
		setImage(ballImg);
		dx = 5;
		dy = -5;
	}

	@Override
	public void act(long now) {
		if (((BallWorld) getWorld()).isPaused()) {

		} else {
			move(dx, dy);
			if (getX() > getWorld().getWidth() - getWidth()|| getX() < 0) {
				dx = -dx;
				bounceSound.play();
			}

			if (getY() > getWorld().getHeight() - getHeight() ) {
				dy = -dy;
				((BallWorld) getWorld()).getScore().setScore(((BallWorld) getWorld()).getScore().getScore() - 1000);
				((BallWorld) getWorld()).updateLives();
				bounceSound.play();
			}

			if (getY() < 0) {
				dy = -dy;
				if (getX() > getWorld().getWidth() - getWidth()|| getX() < 0) {
					bounceSound.play();
				}
			}

			if (getOneIntersectingObject(Paddle.class) != null) {
				dy = -dy;
				bounceSound.play();
			}

			if (getOneIntersectingObject(Brick.class) != null && !getOneIntersectingObject(Brick.class).isDying()) {
				Brick brick = getOneIntersectingObject(Brick.class);
				brick.setDying(false);
				if (getX() < brick.getX() + brick.getWidth() && getX() > brick.getX()) {
					dy = -dy;
				} else if (getY() < brick.getY() + brick.getHeight() && getY() > brick.getY()) {
					dx = -dx;
				} else {
					dy = -dy;
					dx = -dx;
				}
				((BallWorld) getWorld()).getScore().setScore(((BallWorld) getWorld()).getScore().getScore() + 100);
				
				
				FadeTransition ft = new FadeTransition(Duration.millis(300), brick);
				ft.setFromValue(1.0);
				ft.setToValue(0.0);
				ft.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						getWorld().remove(brick);
					}
				});
				ft.play();
				brickSound.play();
			}
		}

	}

}
