package com.sw.entitys;
/**
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class Player {
	private String	name;
	private int		dmg;

	/**
	 * Used in scoreboard.
	 * @param name name of the player.
	 */
	public Player(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}
	
	public void incDmg(int dmg) {
		this.dmg += dmg;
	}

	@Override
	public String toString() {
		return name + "\t" + dmg;
	}
	
}
