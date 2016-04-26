package com.FilamentTracker.Dialogs;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * FILENAME:    ChangelogDialog.java<P>
 * DESCRIPTION: This class creates and opens the changelog for the versions of the program.
 * 
 * @author Andrew Comer
 * @email AndrewJComer@yahoo.com
 */
public class ChangelogDialog extends JFrame {

    private static final long   serialVersionUID    = 1L;
    private final JScrollPane   scrollPane          = new JScrollPane();
    private final JTextPane     changelogTextPane   = new JTextPane();
    private final String        version1_0          = "Version 1.0\n" 
                                                    + "* Initial release of the program.\n";

    private final String         version1_1         = "Version 1.1\n" 
                                                    + "* Allows user to create filament type, weight, and length that gets saved to the file for later use.\n" 
                                                    + "* The user will be informed if a newer version of the program is available.\n" 
                                                    + "* Small code updates.\n";

    private final String         version1_2         = "Version 1.2\n" 
                                                    + "* Auto save feature: Will save the data every 5 minutes if necessary.\n" 
                                                    + "* New HTML report format and look.\n" 
                                                    + "* Added \"Add Print\" right click menu item.\n" 
                                                    + "* New icon for edit print and edit filament menu items.\n" 
                                                    + "* Added changelog dialog to help menu.\n" 
                                                    + "* Changed menu item accelerator to use ALT mask instead of CTRL mask.\n" 
                                                    + "* Added thingiverse link to about dialog.\n" 
                                                    + "* Small code updates.\n";

    private final String         version1_3         = "Version 1.3\n" 
                                                    + "* Add % used to each print.\n" 
                                                    + "* Add a cost field to the filament and for each print.\n" 
                                                    + "* Stats feature showing total number of prints, filament used, cost, etc.\n";

    private final String         version1_4         = "Version 1.4\n"
                                                    + "* Allow program to mimize to tray. Allowing program to run in background for print stream purposes.\n"
                                                    + "* Will automatically import prints from gcode files. (Repetier Host slic3r & Simplify 3D). \n"
                                                    + "* Imported prints from gcode will show in new print stream dialog.\n"
                                                    + "* Print stream dialog shows the user prints that have occured while the program is running.\n"
                                                    + "* Added tool tips to various components.\n"
                                                    + "* Save file and reports will now be stored in the local folder. \"C:\\Users\\<user name>\\AppData\\Local\\3DPrinterFilamentTracker\\FilamentInfo.txt\".\n"
                                                    + "* Various code improvements.\n";

    /**
     * FUNCTION:    Changelog<P>
     * PURPOSE:     Constructor.
     * 
     * @param x X coordinate of the main frame
     * @param y Y coordinate of the main frame
     */
    public ChangelogDialog(int x, int y) {
        setTitle("Changelog");
        setBounds((int) ((921 / 2) - (700 / 2)) + x, (int) ((546 / 2) - (301 / 2)) + y, 700, 301);
        setIconImage(System.getProperty("DEBUG") != null ? new ImageIcon("com/FilamentTracker/Dialogs/Changelog_Icon.png").getImage() : new ImageIcon(getClass().getResource("Changelog_Icon.png")).getImage());
        setLayout(null);
        setResizable(false);

        scrollPane.setBounds(0, 0, 700, 272);
        scrollPane.setViewportView(changelogTextPane);

        changelogTextPane.setText(version1_4 + "\n" + version1_3 + "\n" + version1_2 + "\n" + version1_1 + "\n" + version1_0);
        changelogTextPane.setCaretPosition(0);
        changelogTextPane.setEditable(false);

        add(scrollPane);
    }
}
