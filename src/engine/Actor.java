/*
    Name:       Rohan Vellamcheti
    Date:       4/18
    Period:     P1 APCS- Ferrante

    Is this lab fully working?  Yes, resubmitting because i accidently missed adding some methods 
    and added them in
*/

package engine;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

public abstract class Actor extends ImageView implements javafx.css.Styleable, javafx.event.EventTarget {
	World world;
	
	public Actor() {
		
	}
	
	public abstract void act(long now);
	
	public void addedToWorld() {
		
	}
	
	public double getHeight() {
		Bounds bound = getBoundsInParent();
		return bound.getHeight();
	}
	
	public double getWidth() {
		Bounds bound = getBoundsInParent();
		return bound.getWidth();
	}
	
	public void move(double dx, double dy) {
		setX(getX() + dx);
		setY(getY() + dy);
	}
	
	public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls) {
		List<A> list = getIntersectingObjects(cls);
		
		return (list.size() == 0) ? null : list.get(0);
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