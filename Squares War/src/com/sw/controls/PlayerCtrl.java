package com.sw.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.sw.entitys.Bullet;
import com.sw.entitys.Vehicle;
import com.sw.gui.FireBar;
/**
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class PlayerCtrl implements KeyListener {
	private Bullet		bullet;
	private Vehicle[]	vehicles;
	private boolean		shootBreak;
	private Collision	collision	= Collision.getInstance();
	private FireBar		fireBar;
	private boolean[]	keys		= new boolean[256];
	private boolean[]	direction	= new boolean[4];
	private boolean		isBulletMoving;

	/**
	 * Enables player moves by pressing arrow keys and shoots by pressing ctrl.
	 * 
	 * @param playerBullet
	 *            player bullet.
	 * @param firebar
	 *            graphical reloading item.
	 * @param vehicles
	 *            all players vehicles.
	 */
	public PlayerCtrl(Bullet playerBullet, FireBar firebar, Vehicle[] vehicles) {
		this.vehicles = vehicles;
		this.bullet = playerBullet;
		this.fireBar = firebar;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	/**
	 * Enables proper action when player is pressing some keys. Up, down, left,
	 * right arrow - move the player. Ctrl - shoot.
	 */
	public void start() {
		if (keys[KeyEvent.VK_UP] && !keys[KeyEvent.VK_DOWN]) moveUp();
		if (keys[KeyEvent.VK_DOWN] && !keys[KeyEvent.VK_UP]) moveDown();
		if (keys[KeyEvent.VK_LEFT] && !keys[KeyEvent.VK_RIGHT]) moveLeft();
		if (keys[KeyEvent.VK_RIGHT] && !keys[KeyEvent.VK_LEFT]) moveRight();
		if (keys[KeyEvent.VK_CONTROL] && !shootBreak) shoot();
		if (shootBreak) bulletMove();
	}

	/**
	 * Moves the bullet in proper direction.
	 */
	private void bulletMove() {
		if (fireBar.reload()) {
			shootBreak = false;
			for (int i = 0; i < direction.length; i++)
				direction[i] = false;
		}

		if (isBulletMoving) {
			double bulletSpeed = vehicles[0].getBulletSpeed();
			// Because of firerange we need to separate moving sidelong from
			// moving directly.
			if (direction[2] == true && direction[0] == true)
				isBulletMoving = bullet.move(-(bulletSpeed), -(bulletSpeed), vehicles, 0);
			else if (direction[2] == true && direction[1] == true)
				isBulletMoving = bullet.move(-(bulletSpeed), bulletSpeed, vehicles, 0);
			else if (direction[3] == true && direction[0] == true)
				isBulletMoving = bullet.move(bulletSpeed, -(bulletSpeed), vehicles, 0);
			else if (direction[3] == true && direction[1] == true)
				isBulletMoving = bullet.move(bulletSpeed, bulletSpeed, vehicles, 0);
			else if (direction[0] == true)
				isBulletMoving = bullet.move(0, -(bulletSpeed), vehicles, 0);
			else if (direction[1] == true)
				isBulletMoving = bullet.move(0, bulletSpeed, vehicles, 0);
			else if (direction[2] == true)
				isBulletMoving = bullet.move(-(bulletSpeed), 0, vehicles, 0);
			else if (direction[3] == true)
				isBulletMoving = bullet.move(bulletSpeed, 0, vehicles, 0);
		}
	}

	/**
	 * Try to shoot.
	 */
	private void shoot() {
		shootBreak = true;
		isBulletMoving = true;
		vehicles[0].playFireSound();

		// Central point of the vehicle.
		int x = vehicles[0].getX() + (int) (vehicles[0].getSize().getWidth() / 2);
		int y = vehicles[0].getY() + (int) (vehicles[0].getSize().getHeight() / 2);
		// Vehicle's gun point.
		int gx = vehicles[0].getCannonX();
		int gy = vehicles[0].getCannonY();

		bullet.setX(gx);
		bullet.setY(gy);

		// Up
		if (y > gy) direction[0] = true;
		// Down
		if (y < gy) direction[1] = true;
		// Left
		if (x > gx) direction[2] = true;
		// Right
		if (x < gx) direction[3] = true;
	}

	/**
	 * Flags proper direction of the vehicles cannon.
	 */
	private void moveCannon() {
		if (keys[KeyEvent.VK_UP] && keys[KeyEvent.VK_LEFT]) {
			vehicles[0].moveCannon(0);
		} else if (keys[KeyEvent.VK_UP] && keys[KeyEvent.VK_RIGHT]) {
			vehicles[0].moveCannon(2);
		} else if (keys[KeyEvent.VK_DOWN] && keys[KeyEvent.VK_LEFT]) {
			vehicles[0].moveCannon(5);
		} else if (keys[KeyEvent.VK_DOWN] && keys[KeyEvent.VK_RIGHT]) {
			vehicles[0].moveCannon(7);
		} else if (keys[KeyEvent.VK_UP]) {
			vehicles[0].moveCannon(1);
		} else if (keys[KeyEvent.VK_LEFT]) {
			vehicles[0].moveCannon(3);
		} else if (keys[KeyEvent.VK_RIGHT]) {
			vehicles[0].moveCannon(4);
		} else if (keys[KeyEvent.VK_DOWN]) {
			vehicles[0].moveCannon(6);
		}
	}

	private void moveLeft() {
		vehicles[0].moveLeft();
		if (collision.checkVehicles(vehicles, 0)) vehicles[0].incX();
		moveCannon();
	}

	private void moveRight() {
		vehicles[0].moveRight();
		if (collision.checkVehicles(vehicles, 0)) vehicles[0].decX();
		moveCannon();
	}

	private void moveUp() {
		vehicles[0].moveUp();
		if (collision.checkVehicles(vehicles, 0)) vehicles[0].incY();
		moveCannon();
	}

	private void moveDown() {
		vehicles[0].moveDown();
		if (collision.checkVehicles(vehicles, 0)) vehicles[0].decY();
		moveCannon();
	}
}
