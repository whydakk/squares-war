package com.sw.entitys;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.sw.controls.Collision;
import com.sw.resources.Assets;
/**
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class Bullet {
	private double		x, y;
	private Dimension	size		= new Dimension(4, 4);
	private Rectangle	ri			= new Rectangle();
	private Collision	collision	= Collision.getInstance();
	private int			shootingRange;

	/**
	 * Just bullet.
	 * 
	 * @param x
	 *            x coordinate.
	 * @param y
	 *            y coordinate.
	 */
	public Bullet(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics2D g, Color color) {
		int w = (int) size.getWidth();
		int h = (int) size.getHeight();
		g.setColor(color);
		g.fillRect((int) (x - (w / 2)), (int) (y - (h / 2)), w, h);
	}

	/**
	 * Moves bullet to the proper direction and checks the range of shooting.
	 * 
	 * @param nextX
	 *            "-int" if you want to move the bullet to the left, "+int" to
	 *            the right.
	 * @param nextY
	 *            "-int" if you want to move the bullet to the top, "+int to the
	 *            bottom.
	 * @param vehicles
	 *            all player vehicles.
	 * @param index
	 *            vehicle's index.
	 */
	public boolean move(double nextX, double nextY, Vehicle[] vehicles, int index) {
		if (shootingRange <= 0) shootingRange = vehicles[index].getShootingRange();
		ri.setBounds((int) x, (int) y, (int) size.getWidth(), (int) size.getHeight());

		if (!collision.checkBullets(vehicles, index, ri) && (x >= 0 && y >= 0)
				&& (x <= Assets.WIDTH - size.getWidth() && y <= Assets.HEIGHT - size.getHeight())) {
			x += nextX;
			y += nextY;
			shootingRange -= vehicles[index].getBulletSpeed();

			if (shootingRange <= 0) {
				x = -10;
				y = -10;
				shootingRange = 0;
				return false;
			}
		} else {
			x = -10;
			y = -10;
			shootingRange = 0;
			return false;
		}
		return true;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
}
