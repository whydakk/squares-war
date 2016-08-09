package com.sw.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.sw.entitys.Bullet;
import com.sw.entitys.Vehicle;
import com.sw.resources.Assets;

/**
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class Playground {
	private Vehicle[]	vehicles;
	private Bullet		playerBullet;
	private Bullet[]	npcBullets;
	private Clip		backgroundSound;

	/**
	 * This is the component where match happens.
	 */
	public Playground(Bullet playerBullet, Bullet[] npcBullets, Vehicle[] vehicles) {
		this.vehicles = vehicles;
		this.playerBullet = playerBullet;
		this.npcBullets = npcBullets;
		try {
			AudioInputStream ais = AudioSystem
					.getAudioInputStream(getClass().getResource(Assets.BACKGROUND_SOUND));
			backgroundSound = AudioSystem.getClip();
			backgroundSound.open(ais);
			backgroundSound.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startBackgroundSound() {
		if (backgroundSound.getFramePosition() >= backgroundSound.getFrameLength()) {
			backgroundSound.stop();
			backgroundSound.setFramePosition(0);
			backgroundSound.start();
		}
	}

	/**
	 * @param g
	 *            Graphics2D buffer image.
	 */
	public void paint(Graphics2D g) {
		for (int i = 0; i < vehicles.length; i++)
			vehicles[i].paint(g);

		// Player's bullets.
		playerBullet.draw(g, Color.WHITE);

		// NPC's bullets.
		for (Bullet e : npcBullets)
			e.draw(g, Color.ORANGE);
	}

}
