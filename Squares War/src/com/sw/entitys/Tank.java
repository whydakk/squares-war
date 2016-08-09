package com.sw.entitys;

import java.awt.Color;

import com.sw.resources.Assets;
/**
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class Tank extends Vehicle {
	public Tank(boolean isPlayer, Color mainColor, Color cannonColor) {
		super(32, 32, 0.8, 450, 450, 3.0, (short) 100, (short) 10, mainColor, cannonColor,
				Assets.TANK_CANNON, isPlayer);
	}

	public Tank() {}

}
