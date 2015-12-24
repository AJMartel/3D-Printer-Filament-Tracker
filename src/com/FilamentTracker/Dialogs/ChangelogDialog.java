package com.FilamentTracker.Dialogs;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 *	FILENAME:		ChangelogDialog.java
 *	DESCRIPTION:	This class creates and opens the changelog for the versions of the program.
 *	
 * @author Andrew Comer
 */
public class ChangelogDialog extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private final JScrollPane 	scrollPane 			= new JScrollPane();
	private final JTextPane 	changelogTextPane 	= new JTextPane();
	
	private final String		version1_0 			= "Version 1.0\n"
													+ "* Initial release of the program.\n";
	
	private final String 		version1_1 			= "Version 1.1\n"
													+ "* Allows user to create filament type, weight, and length that gets saved to the file for later use.\n"
													+ "* The user will be informed if a newer version of the program is available.\n"
													+ "* Small code updates.\n";
	
	private final String 		version1_2 			= "Version 1.2\n"
													+ "* Auto save feature: Will save the data every 5 minutes if necessary.\n"
													+ "* New HTML report format and look.\n"
													+ "* Added \"Add Print\" right click menu item.\n"
													+ "* New icon for edit print and edit filament menu items.\n"
													+ "* Added changelog dialog to help menu.\n"
													+ "* Changed menu item accelerator to use ALT mask instead of CTRL mask.\n"
													+ "* Added thingiverse link to about dialog.\n"
													+ "* Small code updates.\n";
		
	/**
	 * FUNCTION:	Changelog
	 * PURPOSE:		Constructor.
	 * 
	 * @param x X coordinate of the main frame
	 * @param y Y coordinate of the main frame
	 */
	public ChangelogDialog(int x, int y) {
		setTitle("Changelog");
		setBounds((int)((921 / 2) - (550 / 2)) + x, (int)((546 / 2) - (301 / 2)) + y, 550, 301);
		setIconImage(System.getProperty("DEBUG") != null ? new ImageIcon("Changelog_Icon.png").getImage() : new ImageIcon(getClass().getResource("Changelog_Icon.png")).getImage());
		setLayout(null);
		setResizable(false);
		
		scrollPane.setBounds(0, 0, 550, 272);
		scrollPane.setViewportView(changelogTextPane);
		
		changelogTextPane.setText(version1_2 + "\n" + version1_1 + "\n" + version1_0);
		changelogTextPane.setCaretPosition(0);
		changelogTextPane.setEditable(false);
		
		add(scrollPane);
	}
}
