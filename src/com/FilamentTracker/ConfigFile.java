package com.FilamentTracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ConfigFile
{
    public static ConfigFile configFileInstance   = null;
    //General
    public int               minimizeToTrayOnExit = -1;  //0 - Yes : 1 - No : 2 - Ask
    public boolean           promptOnExit         = true;
    public int               autoSaveInterval     = -1;
    public String            saveFileLocation     = "";

    //Reports

    //Logging

    private ConfigFile()
    {
        if (!new File(OSSpecificVariables.windowsConfigFileLocation).isFile())
        {
            createConfigFile();
        }
        else
        {
            Scanner scan;
            try
            {
                scan = new Scanner(new File(OSSpecificVariables.windowsConfigFileLocation));
                
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

    private void createConfigFile()
    {
        //create the default config file
        //maybe store it in the jar file and then just copy it to the location
    }

    public void saveConfigFile()
    {
        //save the config file when the user hits apply
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
