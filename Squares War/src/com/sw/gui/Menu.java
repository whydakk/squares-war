package com.sw.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

import com.sw.controls.MouseMenu;
import com.sw.entitys.Destroyer;
import com.sw.entitys.Enforcer;
import com.sw.entitys.Tank;
import com.sw.entitys.Vehicle;
import com.sw.resources.Assets;

/**
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class Menu {
	private final String	title			= Assets.TITLE;
	private final String	text01			= "Choose your vehicle";
	private final String	controls_text	= "Steering: arrows, shooting: ctrl";
	private final Font		titleFont		= new Font("arial", Font.BOLD, 60);
	private final Font		font			= new Font("arial", Font.PLAIN, 32);
	private final Font		description		= new Font("arial", Font.PLAIN, 14);
	private final Font		stars			= new Font("arial", Font.PLAIN, 20);
	private Stroke			stroke			= new BasicStroke(4F);
	private Rectangle		enfoR			= new Rectangle();
	private Rectangle		tankR			= new Rectangle();
	private Rectangle		destroR			= new Rectangle();
	private final Vehicle	enfo			= new Enforcer(false, Assets.PLAYER_MAIN_COLOR,
			Color.WHITE);
	private final Vehicle	tank			= new Tank(false, Assets.PLAYER_MAIN_COLOR,
			Color.WHITE);
	private final Vehicle	destro			= new Destroyer(false, Assets.PLAYER_MAIN_COLOR,
			Color.WHITE);
	private int				starsWidth;
	private int				boundWidth		= 160;
	private int				boundHeight		= 100;

	/**
	 * Creates menu where vehicles are presented and player can choose the one.
	 * 
	 * @param mouse mouse adapter with regional clickable elements.
	 */

	public Menu(MouseMenu mouse) {
		mouse.addRect(enfoR, tankR, destroR);
	}

	/**
	 * @param g
	 *            Graphics2D buffer image.
	 */
	public void paint(Graphics2D g) {
		// Title
		g.setColor(Color.WHITE);
		g.setFont(titleFont);
		int x = (Assets.WIDTH / 2) - (g.getFontMetrics().stringWidth(title) / 2);
		int y = g.getFontMetrics().getHeight();
		g.drawString(title, x, y);

		// Text01
		g.setFont(font);
		x = (Assets.WIDTH / 2) - (g.getFontMetrics().stringWidth(text01) / 2);
		y += g.getFontMetrics().getHeight() + 50;
		g.drawString(text01, x, y);

		// Vehicles
		x = (Assets.WIDTH / 3) - (boundWidth / 2);
		y += 100;
		paintVehicle(g, enfo, enfoR, x, y, 1, 3, 3, 1, 1);

		x = (int) (Assets.WIDTH / 2) - (boundWidth / 2);
		paintVehicle(g, tank, tankR, x, y, 2, 2, 2, 3, 2);

		x = (int) (Assets.WIDTH / 1.5) - (boundWidth / 2);
		paintVehicle(g, destro, destroR, x, y, 3, 1, 1, 2, 3);

		g.setFont(stars);
		x = (Assets.WIDTH / 2) - (g.getFontMetrics(stars).stringWidth(controls_text) / 2);
		y += 320;
		g.drawString(controls_text, x, y);
	}

	private void paintVehicle(Graphics2D g, Vehicle v, Rectangle r, int x, int y, int firepower,
			int reload, int speed, int range, int health) {
		starsWidth = g.getFontMetrics(stars).stringWidth(stars(0));

		g.setStroke(stroke);
		r.setBounds(x, y, boundWidth, boundHeight);
		g.draw(r);
		v.setX((int) (x + (r.getWidth() / 2) - (v.getSize().getWidth() / 2)));
		v.setY((int) (y + (r.getHeight() / 2) - (v.getSize().getHeight() / 2)));
		v.setCannonX((int) (x + (r.getWidth() / 2)));
		v.setCannonY((int) ((y + r.getHeight() / 2) - (v.getSize().getHeight() / 2)));
		v.paint(g);

		y += boundHeight;

		y = paintDescription(g, x, y, "Firepower", stars(firepower));
		y = paintDescription(g, x, y, "Reload", stars(reload));
		y = paintDescription(g, x, y, "Speed", stars(speed));
		y = paintDescription(g, x, y, "Range", stars(range));
		paintDescription(g, x, y, "Health", stars(health));
	}

	private int paintDescription(Graphics2D g, int x, int y, String option, String stars) {
		g.setFont(description);
		g.drawString(option, x, y += 20);
		g.setFont(this.stars);
		g.drawString(stars, x + boundWidth - starsWidth, y);
		return y;
	}

	private String stars(int i) {
		switch (i) {
			case 1:
				return "★☆☆";
			case 2:
				return "★★☆";
			case 3:
				return "★★★";
			default:
				return "☆☆☆";
		}
	}
}
