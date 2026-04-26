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
	private boolean dying = false;

	public Brick() {
		String path = getClass().getClassLoader().getResource("breakoutresources/brick.png").toString();
		Image brickImg = new Image(path);
		setImage(brickImg);
	}

	public boolean isDying() {
		return dying; 
	}
	public void setDying(boolean dying) { 
		this.dying = dying; 
	}

	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		if (((BallWorld) getWorld()).isPaused()) {

		} else {

		}

	}

}
