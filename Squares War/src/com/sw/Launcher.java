package com.sw;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.sw.gui.Display;

/**
 * Created: 2016.08.09
 * Simple game about riding and shooting to opponents.
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class Launcher {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Display f = new Display();
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);
				f.init();
				f.start();
				f.pack();
			}
		});

	}

}
