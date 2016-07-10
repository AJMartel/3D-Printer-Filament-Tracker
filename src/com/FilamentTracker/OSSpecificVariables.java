package com.FilamentTracker;

public class OSSpecificVariables
{
    public static String windowsMainDirectory      = System.getenv("APPDATA") + "/../Local/3DPrinterFilamentTracker";
    public static String windowsConfigFileLocation = windowsMainDirectory + "/config.xml";
    public static String windowsInfoFileLocation   = ConfigFile.getInstance().getSaveFileLocation() + "/FilamentInfo.txt";
    public static String windowsReportsLocation    = windowsMainDirectory + "/Reports";
    public static String windowsS3DFileLocation    = System.getenv("APPDATA") + "/../Local/Simplify3D/S3D-Software/temp.gcode";
    public static String windowsRHFileLocation     = System.getenv("APPDATA") + "/../Local/RepetierHost/composition.gcode";
    
    public static String linuxS3DFileLocation      = "/root/.local/share/Simplify3D/S3D-Software/temp.gcode";
    public static String linuxRHFileLocation       = "/root/.Local/RepetierHost/composition.gcode";
}