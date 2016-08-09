package com.sw.controls;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.sw.entitys.Destroyer;
import com.sw.entitys.Enforcer;
import com.sw.entitys.Tank;
import com.sw.gui.Display;
import com.sw.resources.Assets;
import com.sw.states.GameStates;
/**
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class MouseMenu extends MouseAdapter {
	private ArrayList<Rectangle>	list	= new ArrayList<>();
	private GameStates				state;
	private Display					display;

	/**
	 * Used in menu, where player can choose between vehicles. When chosen
	 * region is clicked then the game starts.
	 * 
	 * @param d
	 *            display.
	 */
	public MouseMenu(Display d) {
		state = d.getGameState();
		display = d;
	}

	/**
	 * Adds clickable rectangle regions.
	 * 
	 * @param rlist
	 */
	public void addRect(Rectangle... rlist) {
		for (Rectangle e : rlist)
			list.add(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		int mx = e.getX();
		int my = e.getY();
		int i = 0;

		for (; i < list.size(); i++) {
			Rectangle r = list.get(i);
			if ((mx >= r.getX() && mx <= (r.getX() + r.getWidth()))
					&& (my >= r.getY() && my <= (r.getY() + r.getHeight()))) {
				break;
			}
		}

		if (i < list.size()) switch (i) {
			case 0:
				display.startGame(new Enforcer(true, Assets.PLAYER_MAIN_COLOR, Color.WHITE));
				state.setState("game");
				break;
			case 1:
				display.startGame(new Tank(true, Assets.PLAYER_MAIN_COLOR, Color.WHITE));
				state.setState("game");
				break;
			case 2:
				display.startGame(new Destroyer(true, Assets.PLAYER_MAIN_COLOR, Color.WHITE));
				state.setState("game");
				break;

			default:
				display.startGame(new Tank(true, Assets.PLAYER_MAIN_COLOR, Color.WHITE));
				state.setState("game");
				break;
		}
	}

}
