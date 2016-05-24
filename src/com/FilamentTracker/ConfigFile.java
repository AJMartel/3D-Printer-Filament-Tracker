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
    private int              minimizeToTrayOnExit = 0;
    private boolean          promptOnExit         = true;
    private int              autoSaveInterval     = 5;
    private String           saveFileLocation     = OSSpecificVariables.windowsMainDirectory;

    //Reports

    //HTML Color
    private int              timestampTextAlign   = 1;
    private int              timestampTextSize    = 10;
    private String           timestampColor       = "#FF0033";
    private int              titleTextAlign       = 1;
    private int              titleTextSize        = 15;
    private String           titleColor           = "#33FF66";
    private String           filamentBoarderColor = "#000000";
    private String           filamentFillColor    = "#006666";
    private String           printBoarderColor    = "#FF6666";
    private String           printFillColor       = "#6600CC";
    private String           tableHeaderColor     = "#00CC99";
    private String           tableDataColor       = "#FF0000";
    private String           tableEvenColumnColor = "#333333";
    private String           tableOddColumnColor  = "#000000";

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

                /*  General  */
                
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                minimizeToTrayOnExit = Integer.parseInt(token.nextToken());

                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                promptOnExit = Boolean.parseBoolean(token.nextToken());

                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                autoSaveInterval = Integer.parseInt(token.nextToken());

                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                saveFileLocation = token.nextToken();

                /*  Reports  */
                
                
                /*  HTML Color  */
                
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                timestampTextAlign = Integer.parseInt(token.nextToken());
                
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                timestampTextSize = Integer.parseInt(token.nextToken());
                
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                timestampColor = token.nextToken();
                
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                titleTextAlign = Integer.parseInt(token.nextToken());
                
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                titleTextSize = Integer.parseInt(token.nextToken());
                
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                titleColor = token.nextToken();
                
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                filamentBoarderColor = token.nextToken();
                
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                filamentFillColor = token.nextToken();
                
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                printBoarderColor = token.nextToken();
                
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                printFillColor = token.nextToken();
                
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                tableHeaderColor = token.nextToken();
                
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                tableDataColor = token.nextToken();
                
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                tableEvenColumnColor = token.nextToken();
                
                token = new StringTokenizer(scan.nextLine(), "=");
                token.nextToken();
                tableOddColumnColor = token.nextToken();
                
                
                /*  Logging  */

                
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
            fw.write("[Timestamp text alignment]=" + timestampTextAlign + "\n");
            fw.write("[Timestamp text size]=" + timestampTextSize + "\n");
            fw.write("[Timestamp text color]=" + timestampColor + "\n");
            fw.write("[Title text alignment]=" + titleTextAlign + "\n");
            fw.write("[Title text size]=" + titleTextSize + "\n");
            fw.write("[Title text color]=" + titleColor + "\n");
            fw.write("[Filament table boarder color]=" + filamentBoarderColor + "\n");
            fw.write("[Filament table header color]=" + filamentFillColor + "\n");
            fw.write("[Print table boarder color]=" + printBoarderColor + "\n");
            fw.write("[Print table header color]=" + printFillColor + "\n");
            fw.write("[Table header color]=" + tableHeaderColor + "\n");
            fw.write("[Table data color]=" + tableDataColor + "\n");
            fw.write("[Table even column color]=" + tableEvenColumnColor + "\n");
            fw.write("[Table odd column color]=" + tableOddColumnColor + "\n");
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
    public String getFilamentBoarderColor(){return filamentBoarderColor;}
    public String getFilamentFillColor(){return filamentFillColor;}
    public String getPrintBoarderColor(){return printBoarderColor;}
    public String getTimestampColor(){return timestampColor;}
    public String getTitleColor(){return titleColor;}
    public String getTableHeaderColor(){return tableHeaderColor;}
    public String getTableDataColor(){return tableDataColor;}
    public String getTableEvenColumnColor(){return tableEvenColumnColor;}
    public String getTableOddColumnColor(){return tableOddColumnColor;}
    public String getPrintFillColor(){return printFillColor;}
    public int getTimestampTextAlign(){return timestampTextAlign;}
    public String getTextAlignString(int textAlign)
    {
        switch (textAlign)
        {
            case 0: //Left
                return "left";
            case 1: //Center
                return "center";
            case 2: //Right
                return "right";
            default:
                return "left";
        }
    }
    public int getTimestampTextSize(){return timestampTextSize;}
    public int getTitleTextAlign(){return titleTextAlign;}
    public int getTitleTextSize(){return titleTextSize;}
    

    public void setMinimizeToTrayOnExit(int minimizeToTrayOnExit){this.minimizeToTrayOnExit = minimizeToTrayOnExit;}
    public void setPromptOnExit(boolean promptOnExit){this.promptOnExit = promptOnExit;}
    public void setAutoSaveInterval(int autoSaveInterval){this.autoSaveInterval = autoSaveInterval;}
    public void setSaveFileLocation(String saveFileLocation){this.saveFileLocation = saveFileLocation;}
    public void setFilamentBoarderColor(String filamentBoarderColor){this.filamentBoarderColor = filamentBoarderColor;}
    public void setFilamentFillColor(String filamentFillColor){this.filamentFillColor = filamentFillColor;}
    public void setPrintBoarderColor(String printBoarderColor){this.printBoarderColor = printBoarderColor;}
    public void setPrintFillColor(String printFillColor){this.printFillColor = printFillColor;}
    public void setTimestampColor(String timestampColor){this.timestampColor = timestampColor;}
    public void setTitleColor(String titleColor){this.titleColor = titleColor;}
    public void setTableHeaderColor(String tableHeaderColor){this.tableHeaderColor = tableHeaderColor;}
    public void setTableDataColor(String tableDataColor){this.tableDataColor = tableDataColor;}
    public void setTableEvenColumnColor(String tableEvenColumnColor){this.tableEvenColumnColor = tableEvenColumnColor;}
    public void setTableOddColumnColor(String tableOddColumnColor){this.tableOddColumnColor = tableOddColumnColor;}
    public void setTimestampTextAlign(int timestampTextAlign){this.timestampTextAlign = timestampTextAlign;}
    public void setTimestampTextSize(int timestampTextSize){this.timestampTextSize = timestampTextSize;}
    public void setTitleTextAlign(int titleTextAlign){this.titleTextAlign = titleTextAlign;}
    public void setTitleTextSize(int titleTextSize){this.titleTextSize = titleTextSize;}
}
