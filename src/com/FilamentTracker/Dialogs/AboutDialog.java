package com.FilamentTracker.Dialogs;

import javax.swing.JFrame;

public class AboutDialog extends JFrame {
	private static final long serialVersionUID = 1L;
	/**
	 * Create the frame.
	 */
	public AboutDialog(int x, int y) {
		setTitle("About 3D Printer Filament Tracker");
		setBounds(x, y, 308, 301);
		getContentPane().setLayout(null);
		setResizable(false);
	}

}
