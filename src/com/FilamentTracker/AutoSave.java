package com.FilamentTracker;

import java.util.Date;

/**
 * FILENAME:    AutoSave.java<P>
 * DESCRIPTION: This class saves the info file if needed every X minute.
 *
 * @author Andrew Comer
 * @email AndrewJComer@yahoo.com
 */
public class AutoSave extends Thread
{

    /**
     * FUNCTION:    run<P>
     * PURPOSE:     Thread to check if the info file needs to be saved every X minutes.
     */
    public void run()
    {
        while (true)
        {
            try
            {
                if (Main.saveNeeded)
                {
                    FileIO.save();
                    Main.autoSaveLabel.setText("Auto Save: " + new Date().toString());
                }
                Thread.sleep(ConfigFile.getInstance().getAutoSaveInterval() * 1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}