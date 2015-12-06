package com.FilamentTracker.Dialogs;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.FilamentTracker.Global;

public class AboutDialog extends JFrame {
	private static final long serialVersionUID = 1L;
		JLabel pictureLabel = new JLabel(new ImageIcon("About_Window_Icon.jpg"));
		JLabel programNameLabel = new JLabel("3D Printer Filament Tracker");
		JLabel versionLabel = new JLabel(Global.VERSION);
		JLabel emailLabel = new JLabel("AndrewJComer@yahoo.com", JLabel.CENTER);
		private final JPanel nameAndVersionPanel = new JPanel();
		
	/**
	 * Create the frame.
	 */
	public AboutDialog(int x, int y) {
		setTitle("About 3D Printer Filament Tracker");
		setBounds((int)((921 / 2) - (308 / 2)) + x, (int)((546 / 2) - (301 / 2)) + y, 308, 301);
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
		
		emailLabel.setBounds(12, 172, 276, 17);
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		emailLabel.setForeground(Color.BLUE);
		
		getContentPane().add(nameAndVersionPanel);
		getContentPane().add(pictureLabel);
		getContentPane().add(emailLabel);
	}
}
