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
		String path = getClass().getClassLoader().getResource("breakresources/ball.png").toString();
		Image ballImg = new Image(path);
		setImage(ballImg);
		dx = 5;
		dy = 5;
	}

	@Override
	public void act(long now) {
		move(dx, dy);
		if (getX() > getWorld().getWidth() - getHeight()|| getX() < 0) {
			dx = -dx;
		}
		
		if (getY() > getWorld().getHeight() - getHeight() || getY() < 0) {
			dy = -dy;
		}

	}

}
