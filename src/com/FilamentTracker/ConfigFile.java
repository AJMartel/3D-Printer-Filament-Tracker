package com.FilamentTracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ConfigFile
{
    public static ConfigFile configFileInstance   = null;

    //General
    private int              minimizeToTrayOnExit = 0; //0 - Yes : 1 - No
    private boolean          promptOnExit         = true;
    private int              autoSaveInterval     = 5;
    private String           saveFileLocation     = OSSpecificVariables.windowsMainDirectory;

    //Reports

    //Logging

    private ConfigFile()
    {
        if (!new File(OSSpecificVariables.windowsConfigFileLocation).isFile())
        {
            saveConfigFile();
        }
        else
        {
            try
            {
                Scanner scan = new Scanner(new File(OSSpecificVariables.windowsConfigFileLocation));
                StringTokenizer token = null;

                //Gets the minimize to tray property
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                minimizeToTrayOnExit = Integer.parseInt(token.nextToken());

                //Gets the prompt on exit property
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                promptOnExit = Boolean.parseBoolean(token.nextToken());

                //Gets the auto save interval property
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                autoSaveInterval = Integer.parseInt(token.nextToken());

                //Gets the save file location property
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                saveFileLocation = token.nextToken();

                scan.close();

            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * FUNCTION:    getInstance<P>
     * PURPOSE:     Allows for only one instance of this object top be made.
     *              This function is the only way to get this object
     * 
     * @return The instance of this object
     * @throws FileNotFoundException 
     */
    public static ConfigFile getInstance()
    {
        if (configFileInstance == null)
        {
            configFileInstance = new ConfigFile();
        }
        return configFileInstance;
    }

    public void saveConfigFile()
    {
        try
        {
            FileWriter fw = new FileWriter(OSSpecificVariables.windowsConfigFileLocation);
            fw.write("[Minimize to tray options]=" + minimizeToTrayOnExit + "\n");
            fw.write("[Prompt on exit]=" + promptOnExit + "\n");
            fw.write("[Auto save interval]=" + autoSaveInterval + "\n");
            fw.write("[Save file location]=" + saveFileLocation + "\n");
            fw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public int getMinimizeToTrayOnExit(){return minimizeToTrayOnExit;}
    public boolean getPromptOnExit(){return promptOnExit;}
    public int getAutoSaveInterval(){return autoSaveInterval;}
    public String getSaveFileLocation(){return saveFileLocation;}

    public void setMinimizeToTrayOnExit(int minimizeToTrayOnExit){this.minimizeToTrayOnExit = minimizeToTrayOnExit;}
    public void setPromptOnExit(boolean promptOnExit){this.promptOnExit = promptOnExit;}
    public void setAutoSaveInterval(int autoSaveInterval){this.autoSaveInterval = autoSaveInterval;}
    public void setSaveFileLocation(String saveFileLocation){this.saveFileLocation = saveFileLocation;}
}
