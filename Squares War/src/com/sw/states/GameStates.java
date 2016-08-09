package com.sw.states;

/**
 * Stores actual state of the game. Possible state are: MENU, GAME and END.
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class GameStates {
	public static enum STATE {
		menu, game, end
	};

	private STATE state = STATE.menu;

	public STATE getState() {
		return state;
	}

	public void setState(String s) {
		switch (s) {
			case "menu":
				state = STATE.menu;
				break;
			case "game":
				state = STATE.game;
				break;
			case "end":
				state = STATE.end;
				break;

			default:
				state = STATE.menu;
				break;
		}
	}

}