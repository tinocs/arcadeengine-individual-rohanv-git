/*
    Name:       Rohan Vellamcheti
    Date:       (submission date)
    Period:     P1 APCS- Ferrante

    Is this lab fully working?  (Yes/No)  If not, explain:
    If resubmitting, explain what was wrong and what you fixed.
 */

package engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public abstract class World extends Pane {
	boolean timerRunning;
	boolean widthSet;
	boolean heightSet;
	Set<KeyCode> key;
	AnimationTimer timer;

	public World() {
		key = new HashSet<KeyCode>();

		widthProperty().addListener((obs, oldVal, newVal) -> {
			widthSet = (newVal.doubleValue() > 0) ? true : false;

			if (widthSet == true && heightSet == true) {
				onDimensionsInitialized();
			}
		});
		
		heightProperty().addListener((obs, oldVal, newVal) -> {
			heightSet = (newVal.doubleValue() > 0) ? true : false;

			if (widthSet == true && heightSet == true) {
				onDimensionsInitialized();
			}
		});
		
		sceneProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal != null) {
				requestFocus();
			}
		});
		
		setOnKeyPressed(e -> {
			   key.add(e.getCode());
		});
		
		setOnKeyReleased(e -> {
			key.remove(e.getCode());
		});
		
		timer = new AnimationTimer() {
		    public void handle(long now) {
		    	act(now);
		    	ArrayList<Actor> arr = (ArrayList<Actor>) getObjects(Actor.class);
		    	for (Actor actor : arr) {
		    		if (getChildren().contains(actor)) {
		    			actor.act(now);
		    		}
		    	}
		    }
		};
	}

	public abstract void act(long now);

	public void add(Actor actor) {
		this.getChildren().add(actor);
		actor.setWorld(this);
		actor.addedToWorld();
	}

	public <A extends Actor> java.util.List<A> getObjects(java.lang.Class<A> cls) {
		ArrayList<A> arr = new ArrayList<>();

		for (Node actor : this.getChildren()) {
			if (cls.isInstance(actor)) {
				arr.add((A) actor);
			}
		} 
		return arr;
	}

	public <A extends Actor> java.util.List<A> getObjectsAt(double x, double y, java.lang.Class<A> cls) {
		ArrayList<A> arr = new ArrayList<>();

		for (Node actor : this.getChildren()) {
			if (cls.isInstance(actor) && actor.contains(x, y)) {
				arr.add((A) actor);
			}
		} 
		return arr;

	}

	public abstract void onDimensionsInitialized();
	
	public void start() {
	    timer.start();
	    timerRunning = true;
	}

	public void stop() {
	    timer.stop();
	    timerRunning = false;
	}

	public boolean isStopped() {
	    return !timerRunning;
	}

	public void remove(Actor actor) {
	    getChildren().remove(actor);
	}

	public boolean isKeyPressed(KeyCode code) {
	    return key.contains(code);
	}
}
