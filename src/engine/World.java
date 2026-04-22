/*
    Name:       Rohan Vellamcheti
    Date:       4/18
    Period:     P1 APCS- Ferrante

    Is this lab fully working?  Yes, resubmitting because i didn't test and now 
    i fixed the getObjectsAt to check the parents x,y not the actors x,y. i also 
    fixed the width and height listenres to only call onDimensionsInitialized once
 */

package engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public abstract class World extends Pane {
	boolean timerRunning;
	boolean widthSet;
	boolean heightSet;
	Set<KeyCode> key;
	AnimationTimer timer;
	boolean dimensionsInitialized;
	
	public World() {
		key = new HashSet<KeyCode>();
		
		widthProperty().addListener(e -> {
		    widthSet = getWidth() > 0;
		    if (widthSet && heightSet && !dimensionsInitialized) {
		        dimensionsInitialized = true;
		        onDimensionsInitialized();
		    }
		});

		heightProperty().addListener(e -> {
		    heightSet = getHeight() > 0;
		    if (widthSet && heightSet && !dimensionsInitialized) {
		        dimensionsInitialized = true;
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
			if (cls.isInstance(actor) && actor.getBoundsInParent().contains(x, y)) {
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
		actor.setWorld(null);
	}

	public boolean isKeyPressed(KeyCode code) {
		return key.contains(code);
	}
}
