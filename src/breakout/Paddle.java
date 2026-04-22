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

public class Paddle extends Actor {
	public Paddle() {
		String path = getClass().getClassLoader().getResource("breakoutresources/paddle.png").toString();
		Image paddleImg = new Image(path);
		setImage(paddleImg);
	}

	@Override
	public void act(long now) {
		
		
	}

}
