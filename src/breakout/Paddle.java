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
import javafx.scene.input.KeyCode;

public class Paddle extends Actor {
	public Paddle() {
		String path = getClass().getClassLoader().getResource("breakoutresources/paddle.png").toString();
		Image paddleImg = new Image(path);
		setImage(paddleImg);
	}

	@Override
	public void act(long now) {
		//if (paddle is moving to the right AND the paddle's x-coord is greater than midpoint of the Scene)
		//scroll
		//if (paddle is moving to the left AND the paddle's x-coord is less than the midpoint of the Scene)
			//scroll
		
			if (getWorld().isKeyPressed(KeyCode.LEFT)) {
				move(-5, 0);
				if (getX() < getWorld().getWidth() / 2) {
					((BallWorld) getWorld()).scroll(-5);
				}
			}
			
			if (getWorld().isKeyPressed(KeyCode.RIGHT)) {
				move(5, 0);
				if (getX() > getWorld().getWidth() / 2) {
					((BallWorld) getWorld()).scroll(5);
				}
			}
		
	}
}
