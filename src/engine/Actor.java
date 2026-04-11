/*
    Name:       Rohan Vellamcheti
    Date:       (submission date)
    Period:     P1 APCS- Ferrante

    Is this lab fully working?  (Yes/No)  If not, explain:
    If resubmitting, explain what was wrong and what you fixed.
*/

package engine;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

public abstract class Actor extends ImageView implements javafx.css.Styleable, javafx.event.EventTarget {
	World world;
	
	public Actor() {
		
	}
	
	public abstract void act(long now);
	
	public void addedToWorld() {
		
	}
	
	public double getHeight() {
		return getFitHeight();
	}
	
	public void setWorld(World world) {
		this.world = world; 
	}
	
	public World getWorld() {
		return world;
	}
	
	public <A extends Actor> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls) {
		ArrayList<A> arr = (ArrayList<A>) getWorld().getObjects(cls);
		ArrayList<A> matches = new ArrayList<>();
		
		for (A obj : arr) {
			if (getBoundsInParent().intersects(obj.getBoundsInParent())) {
				matches.add(obj);
			}
		}
		matches.remove(this);
		
		return matches;
	}	
}