package com.sw.entitys;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JTextArea;

import com.sw.resources.Assets;

/**
 * Presents all players score in the right menu.
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class Scoreboard extends JTextArea {
	/**
	 * 
	 */
	private static final long		serialVersionUID	= -8780288856610423205L;
	private final static Scoreboard	instance			= new Scoreboard();
	private final Font				font				= new Font("arial", Font.PLAIN, 18);
	private ArrayList<Player>		t					= new ArrayList<>();
	private ArrayList<Player>		t_copy				= new ArrayList<>();
	private Comp					comp				= new Comp();
	private StringBuilder			sb					= new StringBuilder();

	private Scoreboard() {
		setBackground(Color.DARK_GRAY);
		setForeground(Color.WHITE);
		setFont(font);

		t.add(new Player("Player"));
		for (int i = 1; i < Assets.PLAYERS_COUNT; i++)
			t.add(new Player("Bot " + i));

		sb.append("[ID] NAME\tDMG\n");
		for (int i = 0; i < t.size(); i++) {
			sb.append("[" + (i + 1) + "] " + t.get(i) + "\n");
		}
		setText(sb.toString());
	}

	public static Scoreboard getInstance() {
		return instance;
	}

	/**
	 * Adds dealt damage to player's score.
	 * 
	 * @param index
	 *            vehicle's index.
	 * @param dmg
	 *            how much damage was dealt.
	 */
	public void inflictDMG(int index, int dmg) {
		t.get(index).incDmg(dmg);
		t_copy.clear();
		t_copy.addAll(t);
		t_copy.sort(comp);
		sb.delete(0, sb.length());
		sb.append("[LP] NAME\tDMG\n");
		for (int i = 0; i < t_copy.size(); i++) {
			sb.append("[" + (i + 1) + "] " + t_copy.get(i) + "\n");
		}
		setText(sb.toString());
	}

	/**
	 * @return true if player is the winner.
	 */
	public boolean isPlayerWinner() {
		t_copy.clear();
		t_copy.addAll(t);
		t_copy.sort(comp);
		if (t_copy.get(0).getName().equalsIgnoreCase("player")) return true;
		return false;
	}

	/**
	 * Compares dealt damage.
	 * 
	 * @author Łukasz 's4bba7' Gąsiorowski
	 *
	 */
	private class Comp implements Comparator<Player> {

		@Override
		public int compare(Player o1, Player o2) {
			return o2.getDmg() - o1.getDmg();
		}
	}

}
