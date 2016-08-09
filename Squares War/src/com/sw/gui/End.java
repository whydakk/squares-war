package com.sw.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.sw.resources.Assets;

/**
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class End {
	private final String	win_title		= "CONGRATULATIONS!";
	private final String	win_text01		= "You're the winner!";
	private final String	loose_title		= "Oooooohh!";
	private final String	loose_text01	= "You have lost the match!";
	private final Font		titleFont		= new Font("arial", Font.BOLD, 60);
	private final Font		font			= new Font("arial", Font.PLAIN, 20);

	/**
	 * Creates ending menu. If player wins it displays congratulations,
	 * otherwise it displays critique.
	 */
	public End() {}

	/**
	 * Displayed when the player is the winner.
	 * 
	 * @param g
	 *            Graphics2D buffer image.
	 */
	public void paint_Win(Graphics2D g) {
		// Title
		g.setColor(Color.WHITE);
		g.setFont(titleFont);
		int x = (Assets.WIDTH / 2) - (g.getFontMetrics().stringWidth(win_title) / 2);
		int y = g.getFontMetrics().getHeight();
		g.drawString(win_title, x, y);

		// Text01
		g.setFont(font);
		x = (Assets.WIDTH / 2) - (g.getFontMetrics().stringWidth(win_text01) / 2);
		y += g.getFontMetrics().getHeight() + 50;
		g.drawString(win_text01, x, y);
	}

	/**
	 * Displayed when the player is the looser.
	 * 
	 * @param g
	 *            Graphics2D buffer image.
	 */
	public void paint_Loose(Graphics2D g) {
		// Title
		g.setColor(Color.WHITE);
		g.setFont(titleFont);
		int x = (Assets.WIDTH / 2) - (g.getFontMetrics().stringWidth(loose_title) / 2);
		int y = g.getFontMetrics().getHeight();
		g.drawString(loose_title, x, y);

		// Text01
		g.setFont(font);
		x = (Assets.WIDTH / 2) - (g.getFontMetrics().stringWidth(loose_text01) / 2);
		y += g.getFontMetrics().getHeight() + 50;
		g.drawString(loose_text01, x, y);
	}
}
