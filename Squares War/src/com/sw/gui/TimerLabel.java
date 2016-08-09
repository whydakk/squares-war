package com.sw.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
/**
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class TimerLabel extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4050930688677716273L;
	private String title = "Timer";
	private Font font = new Font("arial", Font.PLAIN, 20);
	private Color back = Color.DARK_GRAY;
	private Color front = Color.WHITE;
	
	/**
	 * Presents timer in beautiful coloured clock.
	 */
	public TimerLabel() {
		setText(title);
		setFont(font);
		setBackground(back);
		setForeground(front);
		setHorizontalAlignment(SwingConstants.CENTER);
	}

}
