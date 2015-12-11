package com.FilamentTracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import com.FilamentTracker.Dialogs.AddFilamentDialog;


/**
 *	FILENAME:		FileIO.java
 *	DESCRIPTION:	This class controls all the save file input/output actions.
 *	
 *	@author Andrew Comer
 */
public class FileIO {
	
	private final static File 	fileName = new File("FilamentInfo.txt");

	/**
	 * FUNTION:	initializeObjects
	 * PURPOSE:	Reads information from the save file into Filament and Print objects.
	 * 
	 * @throws IOException
	 */
	public static void initializeObjects() throws IOException{
		String line;
		boolean updateFile = true;
		try {
			Scanner scan = new Scanner(fileName);
			while (scan.hasNextLine()) {
				String tmp = scan.nextLine();
				StringTokenizer st = new StringTokenizer(tmp, ":");
				while (st.hasMoreTokens()) {
					line = st.nextToken();
					if (line.equalsIgnoreCase("[Filament]")) { //filament information
						Main.index = Integer.parseInt(st.nextToken());
						Main.filaments.add(Main.index, new Filament(Main.index, st.nextToken(), st.nextToken(), st.nextToken(), Double.parseDouble(st.nextToken())));
					} else if (line.equalsIgnoreCase("[Print]")) { //print information
						Main.filaments.get(Main.index).addPrint(st.nextToken(), st.nextToken(), Double.parseDouble(st.nextToken()));
					} else if (line.equalsIgnoreCase("[FilamentType]")) {
						updateFile = false;
						StringTokenizer st2 = new StringTokenizer(tmp, ":");
						st2.nextToken();
						while (st2.hasMoreTokens())
							AddFilamentDialog.filamentType.add(st2.nextToken());
					} else if (line.equalsIgnoreCase("[FilamentWeight]")) {
						updateFile = false;
						StringTokenizer st2 = new StringTokenizer(tmp, ":");
						st2.nextToken();
						while (st2.hasMoreTokens())
							AddFilamentDialog.filamentWeight.add(st2.nextToken());
					} else if (line.equalsIgnoreCase("[FilamentLength]")) {
						updateFile = false;
						StringTokenizer st2 = new StringTokenizer(tmp, ":");
						st2.nextToken();
						while (st2.hasMoreTokens())
							AddFilamentDialog.filamentLength.add(st2.nextToken());
					}
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Info file not found.\nCreating new file.");
			FileWriter fw = new FileWriter(fileName);
			fw.close();
			AddFilamentDialog.filamentType.add("PLA (1.75mm)");
			AddFilamentDialog.filamentWeight.add("1.0kg");
			AddFilamentDialog.filamentLength.add("330000mm");
			save();
		}
		
		if (updateFile)
			updateSaveFile();
	}
	
	/**
	 * FUNCTION:	save
	 * PURPOSE:		Reads information from the Filament and Print objects into a save file.
	 */
	public static void save() {
		try {
			FileWriter fw = new FileWriter(fileName);
			fw.write("[FilamentType]");
			for (String type : AddFilamentDialog.filamentType) {
				fw.write(":" + type);
			}
			fw.write("\n[FilamentWeight]");
			for (String weight : AddFilamentDialog.filamentWeight) {
				fw.write(":" + weight);
			}
			fw.write("\n[FilamentLength]");
			for (String length : AddFilamentDialog.filamentLength) {
				fw.write(":" + length);
			}
			fw.write("\n");
			Iterator<Filament> filamentIterator = Main.filaments.iterator();
			while (filamentIterator.hasNext()){
				Filament filament = filamentIterator.next();
				fw.write("[Filament]:" + filament.getIndex() + ":" + filament.getName() + ":" + filament.getType() + ":" + filament.getWeight() + ":" + filament.getLength() + "\n");
				Iterator<Print> printIterator = filament.getPrint().iterator();
				while (printIterator.hasNext()){
					Print print = printIterator.next();
					fw.write("[Print]:" + print.getDate() + ":" + print.getDescription() + ":" + print.getAmountUsed() + "\n");
				}
			}
			fw.close();
			Main.saveNeeded = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * FUNCTION:	updateSaveFile
	 * PURPOSE:		Updates the save file to be used with a newer version of the program
	 */
	synchronized private static void updateSaveFile() {
		for (Filament filament : Main.filaments) {
			if (!AddFilamentDialog.filamentType.contains(filament.getType()))
				AddFilamentDialog.filamentType.add(filament.getType());
			if (!AddFilamentDialog.filamentWeight.contains(filament.getWeight()))
				AddFilamentDialog.filamentWeight.add(filament.getWeight());
			if (!AddFilamentDialog.filamentLength.contains(filament.getLength().toString().replaceAll("[^\\d.-]", "").concat("mm")))
				AddFilamentDialog.filamentLength.add(filament.getLength().toString().replaceAll("[^\\d.-]", "").concat("mm"));
		}
		save();
	}
}
