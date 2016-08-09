package com.sw.entitys;

import java.awt.Color;

import com.sw.resources.Assets;
/**
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class Destroyer extends Vehicle {
	public Destroyer(boolean isPlayer, Color mainColor, Color cannonColor) {
		super(40, 40, 0.5, 400, 600, 5.0, (short) 150, (short) 18, mainColor, cannonColor,
				Assets.DESTRO_CANNON, isPlayer);
	}

	public Destroyer() {}

}
