package com.FilamentTracker;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;


/**
 *	FILENAME:		FileIO.java
 *	DESCRIPTION:	This class controls all the save file input/output actions.
 *	
 *	@author Andrew Comer
 */
public class FileIO {

	/**
	 * FUNTION:	initializeObjects
	 * PURPOSE:	Reads information from the save file into Filament and Print objects.
	 * 
	 * @throws IOException
	 */
	public static void initializeObjects() throws IOException{
		String line;
		try {
			Scanner scan = new Scanner(Global.fileName);
			while (scan.hasNextLine()) {
				StringTokenizer st = new StringTokenizer(scan.nextLine(), ":");
				while (st.hasMoreTokens()) {
					line = st.nextToken();
					if (line.equalsIgnoreCase("[Filament]")) { //filament information
						Global.index = Integer.parseInt(st.nextToken());
						Main.filaments.add(Global.index, new Filament(Global.index, st.nextToken(), st.nextToken(), st.nextToken(), Double.parseDouble(st.nextToken())));
					} else { //print information
						Main.filaments.get(Global.index).addPrint(st.nextToken(), st.nextToken(), Double.parseDouble(st.nextToken()));
					}
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Info file not found.\nCreating new file.");
			FileWriter fw = new FileWriter(Global.fileName);
			fw.close();
		}
	}
	
	/**
	 * FUNCTION:	save
	 * PURPOSE:		Reads information from the Filament and Print objects into a save file.
	 */
	public static void save() {
		try {
			FileWriter fw = new FileWriter(Global.fileName);
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
			Global.saveNeeded = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
