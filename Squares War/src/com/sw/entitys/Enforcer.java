package com.sw.entitys;

import java.awt.Color;

import com.sw.resources.Assets;
/**
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class Enforcer extends Vehicle {
	public Enforcer(boolean isPlayer, Color mainColor, Color cannonColor) {
		super(26, 26, 1.2, 200, 300, 2.5, (short) 60, (short) 6, mainColor, cannonColor,
				Assets.ENFO_CANNON, isPlayer);
	}

	public Enforcer() {}

}
