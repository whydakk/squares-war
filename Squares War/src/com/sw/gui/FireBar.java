package com.sw.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JProgressBar;

import com.sw.entitys.Vehicle;
import com.sw.resources.Assets;
/**
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class FireBar extends JProgressBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2976167398704974389L;
	private Vehicle	v;
	private int		i;
	
	/**
	 * Creates player's reload bar. 
	 */
	public FireBar() {
		setPreferredSize(new Dimension(32, Assets.HEIGHT));
		setOrientation(JProgressBar.VERTICAL);
	}

	/**
	 * Sets the time between loaded cannon and reloading.
	 * @param v
	 */
	public void setInterval(Vehicle v) {
		this.v = v;
		setForeground(Color.GREEN);
		setMinimum(0);
		setMaximum(v.getShootingInterval());
		setValue(v.getShootingInterval());
	}

	public boolean reload() {
		setForeground(Color.RED);
		if (i < v.getShootingInterval()) {
			setValue(++i);
		}
		if (i >= v.getShootingInterval()) {
			i = 0;
			setForeground(Color.GREEN);
			return true;
		}
		return false;
	}
}
