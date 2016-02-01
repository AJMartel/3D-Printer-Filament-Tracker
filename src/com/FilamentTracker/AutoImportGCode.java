package com.FilamentTracker;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.commons.io.input.ReversedLinesFileReader;

/**
 *	FILENAME:		AutoSave.java
 *	DESCRIPTION:	This class saves the info file if needed every 5 minute.
 *
 * @author Andrew Comer
 */
public class AutoImportGCode extends Thread {

	boolean hasSimplify3D = false;
	boolean hasRepetier = false;
	static String simplify3DDate;
	static String repetierDate;
	File simplify3DFile = null;
	File repetierFile = null;
	public static  DateFormat printDateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
	public static ArrayList<Print> printStream	= new ArrayList<Print>();
	
	/**
	 * FUNCTION:	run
	 * PURPOSE:		
	 */
    public void run() {
        try {
        	if (hasSimplify3D) {
	        	if (!(simplify3DDate.equalsIgnoreCase(Export.dateFormat.format(simplify3DFile.lastModified())))) {
	        		simplify3DDate = Export.dateFormat.format(simplify3DFile.lastModified());
	        		addToStream("Simplify3D", simplify3DFile);
	        		if (System.getProperty("DEBUG") != null)
	        			System.out.println("Simplify3D gcode file has been updated.");
	        	}
        	}
        	if (hasRepetier) {
	        	if (!(repetierDate.equalsIgnoreCase(Export.dateFormat.format(repetierFile.lastModified())))) {
	        		repetierDate = Export.dateFormat.format(repetierFile.lastModified());
	        		addToStream("Repetier", repetierFile);
	        		if (System.getProperty("DEBUG") != null)
	        			System.out.println("Repetier gcode file has been updated.");
	        	}
        	}
        	
//        	for (Print print : printStream) {
//				System.out.println(print.getDate() + " " + print.getAmountUsed() + " " + print.getDescription());
//			}
        	
			sleep(1000);
			run();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
    }
    
    public void checkIfFilesExist() {
		if (new File(System.getenv("APPDATA") + "/../Local/Simplify3D/S3D-Software/temp.gcode").isFile()) {
			hasSimplify3D = true;
			simplify3DFile = new File(System.getenv("APPDATA") + "/../Local/Simplify3D/S3D-Software/temp.gcode");
			simplify3DDate = Export.dateFormat.format(simplify3DFile.lastModified());
		}
		if (new File(System.getenv("APPDATA") + "/../Local/RepetierHost/composition.gcode").isFile()) {
			hasRepetier = true;
			repetierFile = new File(System.getenv("APPDATA") + "/../Local/RepetierHost/composition.gcode");
			repetierDate = Export.dateFormat.format(repetierFile.lastModified());
		}
		this.start();
    }
    
    /**
     * FUNCTION:	getFilamentUsed
     * PURPOSE:		
     * 
     * @param line
     * @return
     */
    private double getFilamentUsed(String line) {
		return Double.parseDouble(line.replaceAll("\\(.*\\)", "").replaceAll("[^\\d.-]", ""));
	}

    /**
     * FUNCTION:	readLastFewLines
     * PURPOSE:		
     * 
     * @param file
     * @param program
     * @return
     * @throws IOException
     */
	public String readLastFewLines(File file, String program) throws IOException {
		String compareString = "";
		int counter = 1; 
		
		switch (program) {
		case "Simplify3D":
			compareString = "Filament length";
			break;
		case "Repetier":
			compareString = "filament used";
			break;
		default:
			break;
		}
		
		ReversedLinesFileReader object = new ReversedLinesFileReader(file);
		String line = object.readLine();
		while(!line.isEmpty())
		{
			if(line.contains(compareString))
				return line;
			
			if (counter++ < 10) {
		    	line = object.readLine();
			}
			else
		    	break;
		}
		return null;
    }
	
	/**
	 * 
	 * @param slicer
	 * @param file
	 * @throws IOException
	 */
	public void addToStream(String slicer, File file) throws IOException {
		printStream.add(new Print(printDateFormat.format(simplify3DFile.lastModified()), slicer, getFilamentUsed(readLastFewLines(file, slicer))));
	}
}