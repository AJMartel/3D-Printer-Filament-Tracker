package com.FilamentTracker.Dialogs;

import java.awt.Color;
import java.awt.Font;

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

	private final String VERSION 				= "v1.1";
	private final JLabel pictureLabel 			= new JLabel(System.getProperty("DEBUG") != null ? new ImageIcon("About_Window_Icon.jpg") : new ImageIcon(getClass().getResource("About_Window_Icon.jpg")));
	private final JLabel programNameLabel 		= new JLabel("3D Printer Filament Tracker");
	private final JLabel versionLabel 			= new JLabel(VERSION);
	private final JLabel infoLabel 				= new JLabel("<html>Feel free to contact me if you have any questions or comments.</html>", JLabel.CENTER);
	private final JLabel emailLabel 			= new JLabel("AndrewJComer@yahoo.com", JLabel.CENTER);
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
		setIconImage(System.getProperty("DEBUG") != null ? new ImageIcon("About_Icon.png").getImage() : new ImageIcon(getClass().getResource("About_Icon.png")).getImage());
		getContentPane().setLayout(null);
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
		
		getContentPane().add(nameAndVersionPanel);
		getContentPane().add(pictureLabel);
		getContentPane().add(infoLabel);
		getContentPane().add(emailLabel);
	}
}
