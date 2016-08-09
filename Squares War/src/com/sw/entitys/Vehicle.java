package com.sw.entitys;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.sw.resources.Assets;

/**
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public abstract class Vehicle {
	protected double		x, y;
	private final int		cannonDec		= 4;
	protected Dimension		size			= new Dimension(0, 0);
	protected double		speed;
	protected int			cannonX, cannonY;
	protected int			shootingRange;
	protected int			shootingInterval;
	protected double		bulletSpeed;
	protected short			health;
	protected short			power;
	protected boolean		isPlayer;
	protected BasicStroke	perimeter		= Assets.VEHICLE_PERIMETER;
	protected BasicStroke	cannonStroke	= Assets.VEHICLE_CANNON_STROKE;
	protected Color			mainColor;
	protected Color			shieldColor		= Color.GREEN;
	protected Color			cannonColor;
	protected Clip			fireSound;

	/**
	 * @param isPlayer
	 *            mark true if player is real person.
	 */
	protected Vehicle(int width, int height, double speed, int range, int shootingInterval,
			double bulletSpeed, short health, short power, Color mainColor, Color cannonColor,
			String fireSound, boolean isPlayer) {
		super();
		Random r = new Random();
		this.x = r.nextInt(Assets.WIDTH - width);
		this.y = r.nextInt(Assets.HEIGHT - height);
		this.size.setSize(width, height);
		this.speed = speed;
		cannonX = (int) (x + (size.getWidth() / 2));
		cannonY = (int) y;
		this.shootingRange = range;
		this.shootingInterval = shootingInterval;
		this.isPlayer = isPlayer;
		this.bulletSpeed = bulletSpeed;
		this.health = health;
		this.power = power;
		this.mainColor = mainColor;
		this.cannonColor = cannonColor;
		try {
			AudioInputStream ais = AudioSystem
					.getAudioInputStream(Vehicle.class.getResource(fireSound));
			this.fireSound = AudioSystem.getClip();
			this.fireSound.open(ais);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected Vehicle() {}

	public void paint(Graphics2D g) {
		g.setStroke(perimeter);
		g.setColor(mainColor);
		g.fillRect(getX(), getY(), getSize().width, getSize().height);
		g.setColor(shieldColor);
		g.drawRect(getX(), getY(), getSize().width, getSize().height);

		g.setStroke(cannonStroke);
		g.setColor(cannonColor);
		g.drawLine((int) (getX() + (getSize().getWidth() / 2)),
				(int) (getY() + (getSize().getHeight() / 2)), getCannonX(), getCannonY());
	}

	public void playFireSound() {
		fireSound.stop();
		fireSound.setFramePosition(0);
		fireSound.start();
	}

	/**
	 * Moves cannon in proper direction.
	 * 
	 * @param direction
	 *            0 Up-left, 1 Up, 2 Up-right, 3 Left, 4 Right, 5 Down-left, 6
	 *            Down, 7 Down-right.
	 */
	public void moveCannon(int direction) {
		switch (direction) {
			case 0:
				setCannonX(getX() + cannonDec);
				setCannonY(getY() + cannonDec);
				break;
			case 1:
				setCannonX(getX() + (int) (getSize().getWidth() / 2));
				setCannonY(getY() + cannonDec);
				break;
			case 2:
				setCannonX(getX() + (int) getSize().getWidth() - cannonDec);
				setCannonY(getY() + cannonDec);
				break;
			case 3:
				setCannonX(getX() + cannonDec);
				setCannonY(getY() + (int) (getSize().getHeight() / 2));
				break;
			case 4:
				setCannonX(getX() + (int) getSize().getWidth() - cannonDec);
				setCannonY(getY() + (int) (getSize().getHeight() / 2));
				break;
			case 5:
				setCannonX(getX() + cannonDec);
				setCannonY(getY() + (int) getSize().getHeight() - cannonDec);
				break;
			case 6:
				setCannonX(getX() + (int) (getSize().getWidth() / 2));
				setCannonY(getY() + (int) (getSize().getHeight()) - cannonDec);
				break;
			case 7:
				setCannonX(getX() + (int) getSize().getWidth() - cannonDec);
				setCannonY(getY() + (int) getSize().getHeight() - cannonDec);
				break;

			default:
				break;
		}
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void incX() {
		x += speed;
	}

	public void decX() {
		x -= speed;
	}

	public void incY() {
		y += speed;
	}

	public void decY() {
		y -= speed;
	}

	public void moveLeft() {
		if (x - perimeter.getLineWidth() > 0) decX();
	}

	public void moveRight() {
		if (x + perimeter.getLineWidth() + size.getWidth() < Assets.WIDTH) incX();
	}

	public void moveUp() {
		if (y - perimeter.getLineWidth() > 0) decY();
	}

	public void moveDown() {
		if (y + perimeter.getLineWidth() + size.getHeight() < Assets.HEIGHT) incY();
	}

	public Dimension getSize() {
		return this.size;
	}

	public void setSize(int width, int height) {
		this.size.setSize(width, height);
	}

	public double getSpeed() {
		return this.speed;
	}

	public void setSpeed(byte speed) {
		this.speed = speed;
	}

	public int getCannonX() {
		return cannonX;
	}

	public void setCannonX(int cannonX) {
		this.cannonX = cannonX;
	}

	public int getCannonY() {
		return cannonY;
	}

	public void setCannonY(int cannonY) {
		this.cannonY = cannonY;
	}

	public int getRange() {
		return shootingRange;
	}

	public void setRange(int range) {
		this.shootingRange = range;
	}

	public int getShootingInterval() {
		return shootingInterval;
	}

	public void setShootingInterval(int shootingInterval) {
		this.shootingInterval = shootingInterval;
	}

	public int getShootingRange() {
		return shootingRange;
	}

	public void setShootingRange(int shootingRange) {
		this.shootingRange = shootingRange;
	}

	public boolean isPlayer() {
		return isPlayer;
	}

	public void setPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}

	public double getBulletSpeed() {
		return bulletSpeed;
	}

	public void setBulletSpeed(double bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}

	/**
	 * When player is taking damage it decreases vehicle's health and changes
	 * color of the shield.
	 * 
	 * @param x how much damage was dealt.
	 */
	public void decHealth(short x) {
		health -= x;
		if (health <= 80 && health > 60) shieldColor = Color.BLUE;
		else if (health <= 60 && health > 40) shieldColor = Color.ORANGE;
		else if (health <= 40 && health > 20) shieldColor = Color.RED;
		else if (health <= 20 && health > 0) shieldColor = Color.GRAY;
		else if (health <= 0) {
			mainColor = Color.BLACK;
			shieldColor = Color.BLACK;
		}
	}

	public short getHealth() {
		return health;
	}

	public short getPower() {
		return power;
	}

	public Color getColor() {
		return mainColor;
	}

	public void setColor(Color color) {
		this.mainColor = color;
	}

}
