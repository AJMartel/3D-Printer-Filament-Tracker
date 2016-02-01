package com.FilamentTracker.Dialogs;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *	FILENAME:		AboutDialog.java
 *	DESCRIPTION:	This class creates and opens the about dialog.
 *	
 * @author Andrew Comer
 */
public class AboutDialog extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public static final String VERSION			= "v1.3";
	private final JLabel pictureLabel 			= new JLabel(System.getProperty("DEBUG") != null ? new ImageIcon("com/FilamentTracker/Dialogs/About_Window_Icon.png") : new ImageIcon(getClass().getResource("About_Window_Icon.png")));
	private final JLabel programNameLabel 		= new JLabel("3D Printer Filament Tracker");
	private final JLabel versionLabel 			= new JLabel(VERSION);
	private final JLabel infoLabel 				= new JLabel("<html>Feel free to contact me if you have any questions or comments.</html>", JLabel.CENTER);
	private final JLabel emailLabel 			= new JLabel("AndrewJComer@yahoo.com", JLabel.CENTER);
	private final JLabel thingiverseLabel		= new JLabel("http://www.thingiverse.com/thing:1190757", JLabel.CENTER);
	private final JPanel nameAndVersionPanel 	= new JPanel();
		
	/**
	 * FUNCTION:	aboutDialog
	 * PURPOSE:		Constructor.
	 * 
	 * @param x X coordinate of the main frame
	 * @param y Y coordinate of the main frame
	 */
	public AboutDialog(int x, int y) {
		setTitle("About 3D Printer Filament Tracker");
		setBounds((int)((921 / 2) - (308 / 2)) + x, (int)((546 / 2) - (301 / 2)) + y, 308, 301);
		setIconImage(System.getProperty("DEBUG") != null ? new ImageIcon("com/FilamentTracker/Dialogs/About_Icon.png").getImage() : new ImageIcon(getClass().getResource("About_Icon.png")).getImage());
		setLayout(null);
		setResizable(false);
		
		pictureLabel.setBounds(12, 11, 100, 100);
		
		programNameLabel.setBounds(0, 0, 166, 17);
		programNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		versionLabel.setBounds(69, 28, 28, 17);
		versionLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		nameAndVersionPanel.setBounds(122, 39, 166, 45);
		nameAndVersionPanel.setLayout(null);
		nameAndVersionPanel.add(programNameLabel);
		nameAndVersionPanel.add(versionLabel);
		
		infoLabel.setBounds(12, 144, 276, 41);
		infoLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		emailLabel.setBounds(12, 212, 276, 17);
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		emailLabel.setForeground(Color.BLUE);
		
		thingiverseLabel.setBounds(12, 244, 276, 17);
		thingiverseLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		thingiverseLabel.setForeground(Color.BLUE);
		thingiverseLabel.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				try {
					Desktop.getDesktop().browse(URI.create("http://www.thingiverse.com/thing:1190757"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		add(nameAndVersionPanel);
		add(pictureLabel);
		add(infoLabel);
		add(emailLabel);
		add(thingiverseLabel);
	}
}