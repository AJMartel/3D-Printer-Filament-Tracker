package com.FilamentTracker;

import java.util.Date;

/**
 *	FILENAME:		AutoSave.java
 *	DESCRIPTION:	This class saves the info file if needed every 5 minute.
 *
 * @author Andrew Comer
 */
public class AutoSave extends Thread {
	
	/**
	 * FUNCTION:	run
	 * PURPOSE:		Thread to check if the info file needs to be saved every 5 minutes.
	 */
    public void run() {
        try {
        	if (Main.saveNeeded) {
        		FileIO.save();
    			Main.autoSaveLabel.setText("Auto Save: " + new Date().toString());
        	}
			sleep(300000);
			run();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}