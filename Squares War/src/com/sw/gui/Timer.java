package com.sw.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.sw.entitys.Vehicle;
import com.sw.states.GameStates;
/**
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class Timer extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3529123869083889865L;
	private int				time	= 5;								// in
																		// minutes.
	private java.util.Timer	t		= new java.util.Timer();
	private GameStates		state;
	private Vehicle[]		vehs;
	private Dimension		size	= new Dimension(100, 64);
	private Font			font	= new Font("arial", Font.PLAIN, 40);
	private Color			back	= Color.DARK_GRAY;
	private Color			front	= Color.RED;

	/**
	 * Creates timer which countdown 5 minutes to finish the match.
	 * 
	 * @param state game state.
	 * @param vehs all players vehicles.
	 */
	public Timer(GameStates state, Vehicle[] vehs) {
		this.state = state;
		this.vehs = vehs;
		time *= 60;
		setFocusable(false);
		setHorizontalAlignment(SwingConstants.CENTER);
		setPreferredSize(size);
		setFont(font);
		setBackground(back);
		setForeground(front);
		setText("" + time);
	}

	public void start() {
		t.schedule(new TimerT(), 0, 1000);
	}

	public int getTime() {
		return time;
	}

	/**
	 * @return false if there are one or less players alive.
	 * 
	 */
	private boolean checkVehsHealth() {
		int alivePlayers = 0;
		for (Vehicle e : vehs)
			if (e.getHealth() > 0) alivePlayers++;
			else if (e.isPlayer() == true) if (e.getHealth() <= 0) return false;

		if (alivePlayers >= 2) return true;
		return false;
	}

	private class TimerT extends TimerTask {

		@Override
		public void run() {
			if (!checkVehsHealth()) {
				state.setState("end");
				t.cancel();
			} else if (time >= 0) setText("" + time--);
			else {
				state.setState("end");
				t.cancel();
			}
		}
	}
}
