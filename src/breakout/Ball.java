/*
    Name:       Rohan Vellamcheti
    Date:       (submission date)
    Period:     P1 APCS- Ferrante

    Is this lab fully working?  (Yes/No)  If not, explain:
    If resubmitting, explain what was wrong and what you fixed.
 */
package breakout;

import engine.Actor;
import javafx.scene.image.Image;

public class Ball extends Actor {
	private double dx, dy;

	public Ball() {
		String path = getClass().getClassLoader().getResource("breakoutresources/ball.png").toString();
		Image ballImg = new Image(path);
		setImage(ballImg);
		dx = 5;
		dy = 5;
	}

	@Override
	public void act(long now) {
		move(dx, dy);
		if (getX() > getWorld().getWidth() - getWidth()|| getX() < 0) {
			dx = -dx;
		}
		
		if (getY() > getWorld().getHeight() - getHeight() ) {
			dy = -dy;
			((BallWorld) getWorld()).getScore().setScore(((BallWorld) getWorld()).getScore().getScore() - 1000);
			((BallWorld) getWorld()).updateLives();
		}
		
		if (getY() < 0) {
			dy = -dy;
		}
		
		if (getOneIntersectingObject(Paddle.class) != null) {
			dy = -dy;
		}
		
		if (getOneIntersectingObject(Brick.class) != null) {
			Brick brick = getOneIntersectingObject(Brick.class);
			if (getX() < brick.getX() + brick.getWidth() && getX() > brick.getX()) {
				dy = -dy;
			} else if (getY() < brick.getY() + brick.getHeight() && getY() > brick.getY()) {
				dx = -dx;
			} else {
				dy = -dy;
				dx = -dx;
			}
			((BallWorld) getWorld()).getScore().setScore(((BallWorld) getWorld()).getScore().getScore() + 100);
			getWorld().remove(brick);
		}

	}

}
