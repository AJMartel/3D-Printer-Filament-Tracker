package com.FilamentTracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import com.FilamentTracker.Dialogs.AddFilamentDialog;

/**
 * FILENAME:    FileIO.java<P>
 * DESCRIPTION: This class controls all the save file input/output actions.
 * 
 * @author Andrew Comer
 * @email AndrewJComer@yahoo.com
 */
public class FileIO
{

    private static File fileName = new File("FilamentInfo.txt");

    /**
     * FUNCTION:    initializeObjects<P>
     * PURPOSE:     Reads information from the save file into Filament and Print objects.
     * 
     * @throws IOException
     */
    public synchronized static void initializeObjects() throws IOException
    {
        String line;
        boolean updateFile = true;

        migrateFile();

        try
        {
            Scanner scan = new Scanner(fileName);
            while (scan.hasNextLine())
            {
                String tmp = scan.nextLine();
                StringTokenizer st = new StringTokenizer(tmp, ":");
                while (st.hasMoreTokens())
                {
                    line = st.nextToken();
                    if (line.equalsIgnoreCase("[Filament]")) // filament information
                    {
                        if (st.countTokens() == 5) //Line does not include cost
                        {
                            Main.index = Integer.parseInt(st.nextToken());
                            Main.filaments.add(Main.index, new Filament(Main.index, st.nextToken(), st.nextToken(), st.nextToken(), Double.parseDouble(st.nextToken())));
                        }
                        else if (st.countTokens() == 6) //Line includes cost
                        {
                            Main.index = Integer.parseInt(st.nextToken());
                            Main.filaments.add(Main.index, new Filament(Main.index, st.nextToken(), st.nextToken(), st.nextToken(), Double.parseDouble(st.nextToken()), st.nextToken()));
                        }
                    }
                    else if (line.equalsIgnoreCase("[Print]")) // print information
                    {
                        Main.filaments.get(Main.index).addPrint(st.nextToken(), st.nextToken(), Double.parseDouble(st.nextToken()));
                    }
                    else if (line.equalsIgnoreCase("[FilamentType]"))
                    {
                        updateFile = false;
                        StringTokenizer st2 = new StringTokenizer(tmp, ":");
                        st2.nextToken();
                        while (st2.hasMoreTokens())
                        {
                            AddFilamentDialog.filamentType.add(st2.nextToken());
                        }
                    }
                    else if (line.equalsIgnoreCase("[FilamentWeight]"))
                    {
                        updateFile = false;
                        StringTokenizer st2 = new StringTokenizer(tmp, ":");
                        st2.nextToken();
                        while (st2.hasMoreTokens())
                        {
                            AddFilamentDialog.filamentWeight.add(st2.nextToken());
                        }
                    }
                    else if (line.equalsIgnoreCase("[FilamentLength]"))
                    {
                        updateFile = false;
                        StringTokenizer st2 = new StringTokenizer(tmp, ":");
                        st2.nextToken();
                        while (st2.hasMoreTokens())
                        {
                            AddFilamentDialog.filamentLength.add(st2.nextToken());
                        }
                    }
                    else if (line.equalsIgnoreCase("[FilamentCost]"))
                    {
                        updateFile = false;
                        StringTokenizer st2 = new StringTokenizer(tmp, ":");
                        st2.nextToken();
                        while (st2.hasMoreTokens())
                        {
                            AddFilamentDialog.filamentCost.add(st2.nextToken());
                        }
                    }
                }
            }
            scan.close();
        }
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Info file not found.\nCreating new file.");
            FileWriter fw = new FileWriter(fileName);
            fw.close();
            AddFilamentDialog.filamentType.add("PLA (1.75mm)");
            AddFilamentDialog.filamentWeight.add("1.0kg");
            AddFilamentDialog.filamentLength.add("330000mm");
            AddFilamentDialog.filamentCost.add("$25.00");
            save();
            Main.autoSaveLabel.setText("Auto Save: " + new Date().toString());
        }

        updateSaveFile();
        if (updateFile)
        {
            save();
            Main.autoSaveLabel.setText("Auto Save: " + new Date().toString());
        }
    }

    /**
     * FUNCTION:    save<P>
     * PURPOSE:     Reads information from the Filament and Print objects into a save file.
     */
    public synchronized static void save()
    {
        try
        {
            FileWriter fw = new FileWriter(fileName);
            fw.write("[FilamentType]");
            for (String type : AddFilamentDialog.filamentType)
            {
                fw.write(":" + type);
            }
            fw.write("\n[FilamentWeight]");
            for (String weight : AddFilamentDialog.filamentWeight)
            {
                fw.write(":" + weight);
            }
            fw.write("\n[FilamentLength]");
            for (String length : AddFilamentDialog.filamentLength)
            {
                fw.write(":" + length);
            }
            fw.write("\n[FilamentCost]");
            for (String cost : AddFilamentDialog.filamentCost)
            {
                fw.write(":" + cost);
            }
            fw.write("\n");
            Iterator<Filament> filamentIterator = Main.filaments.iterator();
            while (filamentIterator.hasNext())
            {
                Filament filament = filamentIterator.next();
                fw.write("[Filament]:" + filament.getIndex() + ":" + filament.getName() + ":" + filament.getType() + ":" + filament.getWeight() + ":" + filament.getLength() + ":" + filament.getCost() + "\n");
                Iterator<Print> printIterator = filament.getPrint().iterator();
                while (printIterator.hasNext())
                {
                    Print print = printIterator.next();
                    fw.write("[Print]:" + print.getDate() + ":" + print.getDescription() + ":" + print.getAmountUsed() + "\n");
                }
            }
            fw.close();
            Main.autoSaveLabel.setText("Manual Save: " + new Date().toString());
            Main.saveNeeded = false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * FUNCTION:    updateSaveFile<P>
     * PURPOSE:     Updates the save file to be used with a newer version of the program.
     */
    public synchronized static void updateSaveFile()
    {
        for (Filament filament : Main.filaments)
        {
            if (!AddFilamentDialog.filamentType.contains(filament.getType()))
            {
                AddFilamentDialog.filamentType.add(filament.getType());
            }
            if (!AddFilamentDialog.filamentWeight.contains(filament.getWeight()))
            {
                AddFilamentDialog.filamentWeight.add(filament.getWeight());
            }
            if (!AddFilamentDialog.filamentLength.contains(filament.getLength().toString().replaceAll("[^\\d.-]", "").concat("mm")))
            {
                AddFilamentDialog.filamentLength.add(filament.getLength().toString().replaceAll("[^\\d.-]", "").concat("mm"));
            }
            if (!AddFilamentDialog.filamentCost.contains(filament.getCost()))
            {
                AddFilamentDialog.filamentCost.add(filament.getCost());
            }
        }
    }

    /**
     * FUNCTION:    migrateFile<P>
     * PURPOSE:     Moves the info file from the current directory to the local directory.
     * 
     * @throws IOException 
     */
    public synchronized static void migrateFile() throws IOException
    {
        if (System.getProperty("os.name").contains("Windows"))
        {
            if (!new File(OSSpecificVariables.windowsInfoFileLocation).isFile())
            {
                if (!new File(OSSpecificVariables.windowsMainDirectory).isDirectory())
                {
                    new File(OSSpecificVariables.windowsMainDirectory).mkdirs();
                }
                if (!new File(ConfigFile.getInstance().getSaveFileLocation()).isDirectory())
                {
                    new File(ConfigFile.getInstance().getSaveFileLocation()).mkdirs();
                }
                if (new File("FilamentInfo.txt").isFile())
                {
                    Files.copy(Paths.get("FilamentInfo.txt"), Paths.get(OSSpecificVariables.windowsInfoFileLocation));
                    if (System.getProperty("DEBUG") == null)
                    {
                        Files.delete(Paths.get("FilamentInfo.txt"));
                    }
                }
            }
            fileName = new File(OSSpecificVariables.windowsInfoFileLocation);
        }
    }
}