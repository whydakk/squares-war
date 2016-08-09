package com.sw.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sw.controls.MouseMenu;
import com.sw.controls.NPCCtrl;
import com.sw.controls.PlayerCtrl;
import com.sw.entitys.Bullet;
import com.sw.entitys.Destroyer;
import com.sw.entitys.Enforcer;
import com.sw.entitys.Scoreboard;
import com.sw.entitys.Tank;
import com.sw.entitys.Vehicle;
import com.sw.resources.Assets;
import com.sw.states.GameStates;

/**
 * The main screen, where all happens.
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class Display extends JFrame implements KeyEventDispatcher, Runnable {
	/**
	 * 
	 */
	private static final long		serialVersionUID	= -7201967928687454877L;
	private final int				vehicleTypeCount	= 3;
	private final Color				backgroundColor		= new Color(30, 90, 0);
	private static final Vehicle[]	vehicles			= new Vehicle[Assets.PLAYERS_COUNT];
	private Bullet					playerBullet		= new Bullet(-10, -10);
	private Bullet[]				npcBullets			= new Bullet[Assets.PLAYERS_COUNT * 2];
	private PlayerCtrl				playerCtlr;
	private NPCCtrl					npcCtrl;
	private Playground				playground;
	private FireBar					fireBar;
	private MouseMenu				mouse;
	private Menu					menu;
	private Canvas					canva;
	private End						end;
	private BufferStrategy			bs;
	private Graphics				g;
	private Thread					thread;
	private Scoreboard				scoreboard;
	private Timer					timer;
	private GameStates				state;

	public Display() {
		super(Assets.TITLE);
	}

	/**
	 * Initialize all components.
	 */
	public void init() {
		state = new GameStates();
		canva = new Canvas();
		canva.setPreferredSize(new Dimension(Assets.WIDTH, Assets.HEIGHT));
		canva.setFocusable(false);
		mouse = new MouseMenu(this);
		menu = new Menu(mouse);
		end = new End();
		playground = new Playground(playerBullet, npcBullets, vehicles);
		fireBar = new FireBar();
		fireBar.setOrientation(SwingUtilities.VERTICAL);
		thread = new Thread(this);
		timer = new Timer(state, vehicles);
		scoreboard = Scoreboard.getInstance();
		scoreboard.setEnabled(false);
		scoreboard.setFocusable(false);
		playerCtlr = new PlayerCtrl(playerBullet, fireBar, vehicles);
		npcCtrl = new NPCCtrl(npcBullets, vehicles);
	}

	/**
	 * Arrange all components and start game thread.
	 */
	public void start() {
		canva.addMouseListener(mouse);

		GridBagConstraints c = new GridBagConstraints();
		JPanel main = new JPanel(new GridBagLayout());
		main.setBackground(Assets.MENU_BACKGROUD_COLOR);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 3;
		c.fill = GridBagConstraints.BOTH;
		main.add(canva, c);
		c.gridx = 1;
		main.add(fireBar, c);
		JLabel timerLab = new TimerLabel();
		c.gridx = 2;
		c.gridheight = 1;
		main.add(timerLab, c);
		c.gridy = 1;
		main.add(timer, c);
		c.gridy = 2;
		main.add(scoreboard, c);
		add(main);

		canva.createBufferStrategy(3);
		thread.start();
	}

	@Override
	public void run() {
		// Compute proper timing.
		int fps = 120;
		int nanos = 1000000000;
		double tpt = nanos / fps;
		long now;
		long prev = System.nanoTime();
		double delta = 0;
		long timer = 0;
		int ticks = 0;

		while (true) {
			now = System.nanoTime();
			delta += (now - prev) / tpt;
			timer += now - prev;
			prev = now;

			if (delta >= 1) {
				render();
				delta--;
				ticks++;
			}

			if (timer >= nanos) {
				System.out.println("FPS: " + ticks);
				ticks = 0;
				timer = 0;
			}

			try {
				Thread.sleep((long) ((prev - System.nanoTime() + tpt) / 1000000));
			} catch (Exception e) {
				System.out.println("Sleep not needed.");
			}
		}
	}

	/**
	 * Compute only.
	 */
	private void compute() {
		playerCtlr.start();
		npcCtrl.move();
		npcCtrl.shoot();
	}

	/**
	 * Render only.
	 */
	private void render() {
		bs = canva.getBufferStrategy();
		g = bs.getDrawGraphics();

		playground.startBackgroundSound();
		g.setColor(backgroundColor);
		g.fillRect(0, 0, Assets.WIDTH, Assets.HEIGHT);
		if (state.getState() == GameStates.STATE.game) {
			compute();
			playground.paint((Graphics2D) g);
		} else if (state.getState() == GameStates.STATE.menu) menu.paint((Graphics2D) g);
		else if (state.getState() == GameStates.STATE.end) {
			if (scoreboard.isPlayerWinner()) end.paint_Win((Graphics2D) g);
			else end.paint_Loose((Graphics2D) g);
		}

		bs.show();
		g.dispose();

	}

	/**
	 * Generate NPCs's, arrange them on the playground and start the game.
	 * 
	 * @param player
	 */
	public void startGame(Vehicle player) {
		Random r = new Random();
		vehicles[0] = player;
		Rectangle rj = new Rectangle();
		Rectangle ri = new Rectangle();

		for (int i = 1; i < vehicles.length; i++) {
			vehicles[i] = drawVehicle();
			ri.setBounds(vehicles[i].getX(), vehicles[i].getY(),
					(int) vehicles[i].getSize().getWidth(),
					(int) vehicles[i].getSize().getHeight());

			for (int j = 0; j < i; j++) {
				rj.setBounds(vehicles[j].getX(), vehicles[j].getY(),
						(int) vehicles[j].getSize().getWidth(),
						(int) vehicles[j].getSize().getHeight());
				while (ri.intersects(rj)) {
					vehicles[i].setX(r.nextInt(Assets.WIDTH));
					vehicles[i].setY(r.nextInt(Assets.HEIGHT));
					vehicles[i].setCannonX(
							vehicles[i].getX() + (int) (vehicles[i].getSize().getWidth() / 2));
					vehicles[i].setCannonY(vehicles[i].getY());
					ri.setBounds(vehicles[i].getX(), vehicles[i].getY(),
							(int) vehicles[i].getSize().getWidth(),
							(int) vehicles[i].getSize().getHeight());
				}
			}
		}

		for (int i = 0; i < npcBullets.length; i++)
			npcBullets[i] = new Bullet(-10, -10);

		npcCtrl.prepare();

		fireBar.setInterval(vehicles[0]);

		canva.removeMouseListener(mouse);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
		timer.start();
	}

	private Vehicle drawVehicle() {
		int i = new Random().nextInt(vehicleTypeCount);
		switch (i) {
			case 0:
				return new Tank(false, Assets.NPC_MAIN_COLOR, Color.BLACK);

			case 1:
				return new Enforcer(false, Assets.NPC_MAIN_COLOR, Color.BLACK);

			case 2:
				return new Destroyer(false, Assets.NPC_MAIN_COLOR, Color.BLACK);

			default:
				new Tank(false, Assets.NPC_MAIN_COLOR, Color.BLACK);
		}
		return new Tank(false, Assets.NPC_MAIN_COLOR, Color.BLACK);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == 401) playerCtlr.keyPressed(e);
		else playerCtlr.keyReleased(e);
		return false;
	}

	public GameStates getGameState() {
		return state;
	}

}
