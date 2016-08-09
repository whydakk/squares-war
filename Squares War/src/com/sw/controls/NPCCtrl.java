package com.sw.controls;

import java.util.Random;

import com.sw.entitys.Bullet;
import com.sw.entitys.Vehicle;
/**
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class NPCCtrl {
	// NPCs are from [1].
	private Vehicle[]	vehicles;
	private Bullet[]	bullets;
	private Random		r			= new Random();
	private int[]		shootBreak;
	private Collision	collision	= Collision.getInstance();
	// [i]-NPC's ID, [][0]-for how long, [][1]-move direction.
	private int[][]		movemem;
	private boolean[][]	direction;
	private boolean[]	isBulletMoving;
	private int[][]		reload;

	/**
	 * Controls NPCs moves and shots.
	 * 
	 * @param npcBullets
	 *            NPCs bullets.
	 * @param vehicles
	 *            all players vehicles.
	 */
	public NPCCtrl(Bullet[] npcBullets, Vehicle[] vehicles) {
		this.vehicles = vehicles;
		bullets = npcBullets;
		movemem = new int[vehicles.length][2];
		direction = new boolean[vehicles.length][4];
		isBulletMoving = new boolean[vehicles.length];
		reload = new int[vehicles.length][1];
		shootBreak = new int[vehicles.length];
	}

	public void prepare() {
		for (int i = 0; i < vehicles.length; i++)
			shootBreak[i] = vehicles[i].getShootingInterval();
	}

	/**
	 * Moves the bullet in proper direction.
	 * 
	 * @param i
	 *            index of bullet.
	 */
	private void bulletMove(int i) {
		if (isBulletMoving[i]) {
			double bulletSpeed = vehicles[i].getBulletSpeed();
			if (direction[i][2] == true && direction[i][0] == true)
				isBulletMoving[i] = bullets[i].move(-(bulletSpeed), -(bulletSpeed), vehicles, i);
			else if (direction[i][2] == true && direction[i][1] == true)
				isBulletMoving[i] = bullets[i].move(-(bulletSpeed), bulletSpeed, vehicles, i);
			else if (direction[i][3] == true && direction[i][0] == true)
				isBulletMoving[i] = bullets[i].move(bulletSpeed, -(bulletSpeed), vehicles, i);
			else if (direction[i][3] == true && direction[i][1] == true)
				isBulletMoving[i] = bullets[i].move(bulletSpeed, bulletSpeed, vehicles, i);
			else if (direction[i][0] == true)
				isBulletMoving[i] = bullets[i].move(0, -(bulletSpeed), vehicles, i);
			else if (direction[i][1] == true)
				isBulletMoving[i] = bullets[i].move(0, bulletSpeed, vehicles, i);
			else if (direction[i][2] == true)
				isBulletMoving[i] = bullets[i].move(-(bulletSpeed), 0, vehicles, i);
			else if (direction[i][3] == true)
				isBulletMoving[i] = bullets[i].move(bulletSpeed, 0, vehicles, i);
		} else {
			for (int j = 0; j < direction[i].length; j++)
				direction[i][j] = false;
		}
	}

	/**
	 * Try to shoot.
	 */
	public void shoot() {
		for (int i = 1; i < vehicles.length; i++) {
			if (vehicles[i].getHealth() > 0 && !isBulletMoving[i] && reload(i)) {
				isBulletMoving[i] = true;
				vehicles[i].playFireSound();

				// Central point of the vehicle.
				int x = vehicles[i].getX() + (int) (vehicles[i].getSize().getWidth() / 2);
				int y = vehicles[i].getY() + (int) (vehicles[i].getSize().getHeight() / 2);
				// Vehicle's cannon point.
				int gx = vehicles[i].getCannonX();
				int gy = vehicles[i].getCannonY();

				bullets[i].setX(gx);
				bullets[i].setY(gy);

				// Up
				if (y > gy) direction[i][0] = true;
				// Down
				if (y < gy) direction[i][1] = true;
				// Left
				if (x > gx) direction[i][2] = true;
				// Right
				if (x < gx) direction[i][3] = true;
			}
			bulletMove(i);
		}
	}

	/**
	 * Delay between shooting and reloading the cannon.
	 * 
	 * @param i
	 *            vehicle's index.
	 * @return true if vehicle can shoot.
	 */
	private boolean reload(int i) {
		if (reload[i][0] < vehicles[i].getShootingInterval()) {
			++reload[i][0];
		}
		if (reload[i][0] >= vehicles[i].getShootingInterval()) {
			reload[i][0] = 0;
			return true;
		}
		return false;
	}

	/**
	 * Move vehicles in random direction.
	 */
	public void move() {
		for (int i = 1; i < vehicles.length; i++) {
			if (vehicles[i].getHealth() > 0) {
				if (movemem[i][0] < 1) {
					int howLong = r.nextInt(120);
					int moveDirection = r.nextInt(8);
					movemem[i][0] = howLong;
					movemem[i][1] = moveDirection;
				}

				switch (movemem[i][1]) {
					case 0:
						if (movemem[i][0] > 0) {
							vehicles[i].moveLeft();
							vehicles[i].moveUp();
							vehicles[i].moveCannon(0);
							if (collision.checkVehicles(vehicles, i)) {
								vehicles[i].incX();
								vehicles[i].incY();
							}
						}
						movemem[i][0] -= vehicles[i].getSpeed();
						break;

					case 1:
						if (movemem[i][0] > 0) {
							vehicles[i].moveUp();
							vehicles[i].moveCannon(1);
							if (collision.checkVehicles(vehicles, i)) {
								vehicles[i].incY();
							}
						}
						movemem[i][0] -= vehicles[i].getSpeed();
						break;

					case 2:
						if (movemem[i][0] > 0) {
							vehicles[i].moveRight();
							vehicles[i].moveUp();
							vehicles[i].moveCannon(2);
							if (collision.checkVehicles(vehicles, i)) {
								vehicles[i].decX();
								vehicles[i].incY();
							}
						}
						movemem[i][0] -= vehicles[i].getSpeed();
						break;

					case 3:
						if (movemem[i][0] > 0) {
							vehicles[i].moveLeft();
							vehicles[i].moveCannon(3);
							if (collision.checkVehicles(vehicles, i)) {
								vehicles[i].incX();
							}
						}
						movemem[i][0] -= vehicles[i].getSpeed();
						break;

					case 4:
						if (movemem[i][0] > 0) {
							vehicles[i].moveRight();
							vehicles[i].moveCannon(4);
							if (collision.checkVehicles(vehicles, i)) {
								vehicles[i].decX();
							}
						}
						movemem[i][0] -= vehicles[i].getSpeed();
						break;

					case 5:
						if (movemem[i][0] > 0) {
							vehicles[i].moveLeft();
							vehicles[i].moveDown();
							vehicles[i].moveCannon(5);
							if (collision.checkVehicles(vehicles, i)) {
								vehicles[i].incX();
								vehicles[i].decY();
							}
						}
						movemem[i][0] -= vehicles[i].getSpeed();
						break;

					case 6:
						if (movemem[i][0] > 0) {
							vehicles[i].moveDown();
							vehicles[i].moveCannon(6);
							if (collision.checkVehicles(vehicles, i)) {
								vehicles[i].decY();
							}
						}
						movemem[i][0] -= vehicles[i].getSpeed();
						break;

					case 7:
						if (movemem[i][0] > 0) {
							vehicles[i].moveRight();
							vehicles[i].moveDown();
							vehicles[i].moveCannon(7);
							if (collision.checkVehicles(vehicles, i)) {
								vehicles[i].decX();
								vehicles[i].decY();
							}
						}
						movemem[i][0] -= vehicles[i].getSpeed();
						break;

					default:
						if (movemem[i][0] > 0) movemem[i][0] -= vehicles[i].getSpeed();
						break;
				}
			}
		}
	}
}
