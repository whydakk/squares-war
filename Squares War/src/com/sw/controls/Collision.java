package com.sw.controls;

import java.awt.Rectangle;

import com.sw.entitys.Scoreboard;
import com.sw.entitys.Vehicle;
/**
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class Collision {
	private final static Collision	instance	= new Collision();
	private final static Rectangle	r2			= new Rectangle();
	private final static Rectangle	r1			= new Rectangle();
	private static Scoreboard		sb			= Scoreboard.getInstance();

	private Collision() {}

	/**
	 * This object checks collisions between entities like vehicles and/or
	 * bullets.
	 * 
	 * @return instance of the Collision object.
	 */
	public static Collision getInstance() {
		return instance;
	}

	/**
	 * Check collisions between vehicles.
	 * 
	 * @param vehicles array with vehicles.
	 * @param index
	 *            which vehicle is testing intersections.
	 * @return true if vehicle of index 'index' intersects with another.
	 */
	public boolean checkVehicles(Vehicle[] vehicles, int index) {
		r1.setBounds((int) vehicles[index].getX(), (int) vehicles[index].getY(),
				(int) vehicles[index].getSize().getWidth(),
				(int) vehicles[index].getSize().getHeight());

		for (int i = 0; i < vehicles.length; i++) {
			if (i != index) {
				r2.setBounds((int) vehicles[i].getX(), (int) vehicles[i].getY(),
						(int) vehicles[i].getSize().getWidth(),
						(int) vehicles[i].getSize().getHeight());

				if (r1.intersects(r2)) return true;
			}
		}
		return false;
	}

	/**
	 * Checks the bullet's collisions. If bullet intersects with vehicle it
	 * decreases vehicle's health by calling Vehicle.decHealth().
	 * 
	 * @param vehicles array with vehicles.
	 * @param index
	 *            which vehicle has shot.
	 */
	public boolean checkBullets(Vehicle[] vehicles, int index, Rectangle r) {
		for (int i = 0; i < vehicles.length; i++) {
			if (i != index) {
				r2.setBounds((int) vehicles[i].getX(), (int) vehicles[i].getY(),
						(int) vehicles[i].getSize().getWidth(),
						(int) vehicles[i].getSize().getHeight());
				if (r.intersects(r2)) {
					vehicles[i].decHealth(vehicles[index].getPower());
					sb.inflictDMG(index, vehicles[index].getPower());
					return true;
				}
			}
		}
		return false;
	}

}
