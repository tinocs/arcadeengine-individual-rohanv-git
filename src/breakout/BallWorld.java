/*
    Name:       Rohan Vellamcheti
    Date:       (submission date)
    Period:     P1 APCS- Ferrante

    Is this lab fully working?  (Yes/No)  If not, explain:
    If resubmitting, explain what was wrong and what you fixed.
*/
package breakout;

import engine.World;

public class BallWorld extends World {
	
	public BallWorld(int w, int h) {
		setPrefSize(w,h);
	}

	@Override
	public void act(long now) {
		
	}

	@Override
	public void onDimensionsInitialized() {
		Ball ball = new Ball();
		add(ball);
		ball.setX(getWidth() / 2 - ball.getWidth() / 2);
		ball.setY(getHeight() / 2 - ball.getHeight() /2);
		
		Paddle paddle = new Paddle();
		add(paddle);
		paddle.setX(getWidth() / 2 - paddle.getWidth() / 2);
		paddle.setY(getHeight() * 0.8 - paddle.getHeight() / 2);
		
		start();
	}
	
}
