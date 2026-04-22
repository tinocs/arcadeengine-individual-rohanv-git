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

public class Brick extends Actor {
	public Brick() {
		String path = getClass().getClassLoader().getResource("breakoutresources/brick.png").toString();
		Image brickImg = new Image(path);
		setImage(brickImg);
	}

	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		
	}

}
