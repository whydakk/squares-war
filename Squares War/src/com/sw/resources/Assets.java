package com.sw.resources;

import java.awt.BasicStroke;
import java.awt.Color;

/**
 * Stores all global options.
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class Assets {
	public static final String		TITLE					= "Squares War";
	public static final int			PLAYERS_COUNT			= 10;
	public static final Color		NPC_MAIN_COLOR			= Color.ORANGE;
	public static final Color		PLAYER_MAIN_COLOR		= Color.RED;
	public static final Color		MENU_BACKGROUD_COLOR	= Color.DARK_GRAY;
	public static final int			WIDTH					= 1100;
	public static final int			HEIGHT					= 650;
	public static final BasicStroke	VEHICLE_PERIMETER		= new BasicStroke(3F);
	public static final BasicStroke	VEHICLE_CANNON_STROKE	= new BasicStroke(6F);
	public static final String		ENFO_CANNON				= "/audio/enfo-cannon.wav";
	public static final String		TANK_CANNON				= "/audio/tank-cannon.wav";
	public static final String		DESTRO_CANNON			= "/audio/destro-cannon.wav";
	public static final String		BACKGROUND_SOUND		= "/audio/background.wav";
}
