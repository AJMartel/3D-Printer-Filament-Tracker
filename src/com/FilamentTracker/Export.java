package com.FilamentTracker;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class Export {

	   static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	   static DateFormat saveFileDateFormat = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
	   static Date date = new Date();
	   static String separator =  "====================================================================================================";
	   
	/**
	 * Exports the data to a HTML file
	 */
	public static void exportToHTML() {
		try {
			FileWriter fw = new FileWriter(new File("Report_" + saveFileDateFormat.format(date) + ".html"));
			fw.write( "<HTML>\n"
					+ "   <BODY>\n"
					+ "      <b>" + dateFormat.format(date) + "</b>\n"
					+ "      <BR>\n"
					+ "      <BR>\n"
					+ "      <CENTER><b>" + System.getProperty("user.name") + "'s 3D Printer Filament Report</b></CENTER>\n"
					+ "      <BR>\n"
					+ "      <BR>\n"
					+ "      <HR>\n"
					+ "      <BR>\n"
					+ "      <table border=0 cellspacing=0 cellpadding=0 style='border-collapse:collapse; mso-padding-alt:0in 5.4pt 0in 5.4pt'>\n"
					+ "         <tr>\n"
					+ "            <td width=165 valign=top style='width:100pt;padding:0in 5.4pt 0in 5.4pt'>\n"
					+ "               <p>Filament Name</p>\n"
					+ "            </td>\n"
					+ "            <td width=510 valign=top style='width:100pt;padding:0in 5.4pt 0in 5.4pt'>\n"
					+ "               <p>Filament Type</p>\n"
					+ "            </td>\n"
					+ "            <td width=510 valign=top style='width:100pt;padding:0in 5.4pt 0in 5.4pt'>\n"
					+ "               <p>Filament Weight</p>\n"
					+ "            </td>\n"
					+ "            <td width=510 valign=top style='width:100pt;padding:0in 5.4pt 0in 5.4pt'>\n"
					+ "               <p>Filament Length</p>\n"
					+ "            </td>\n"
					+ "            <td width=510 valign=top style='width:150pt;padding:0in 5.4pt 0in 5.4pt'>\n"
					+ "               <p>Filament Length Remaining</p>\n"
					+ "            </td>\n"
					+ "            <td width=510 valign=top style='width:150pt;padding:0in 5.4pt 0in 5.4pt'>\n"
					+ "               <p>Filament Percent Remaining</p>\n"
					+ "            </td>\n"
					+ "         </tr>\n"
					+ "      </table>\n");
			Iterator<Filament> filamentIterator = Main.filaments.iterator();
			while (filamentIterator.hasNext()){
				Filament filament = filamentIterator.next();
				fw.write( "      <BR>\n"
						+ "      <HR>\n"
						+ "      <BR>\n"
						+ "      <table border=0 cellspacing=0 cellpadding=0 style='border-collapse:collapse; mso-padding-alt:0in 5.4pt 0in 5.4pt'>\n"
						+ "         <tr>\n"
						+ "            <td width=165 valign=top style='width:100pt;padding:0in 5.4pt 0in 5.4pt'>\n"
						+ "               <p>" + filament.getName() + "</p>\n"
						+ "            </td>\n"
						+ "            <td width=510 valign=top style='width:100pt;padding:0in 5.4pt 0in 5.4pt'>\n"
						+ "               <p>" + filament.getType() + "</p>\n"
						+ "            </td>\n"
						+ "            <td width=510 valign=top style='width:100pt;padding:0in 5.4pt 0in 5.4pt'>\n"
						+ "               <p>" + filament.getWeight() + "</p>\n"
						+ "            </td>\n"
						+ "            <td width=510 valign=top style='width:100pt;padding:0in 5.4pt 0in 5.4pt'>\n"
						+ "               <p>" + Main.numberFormat.format(filament.getLength()) + "mm</p>\n"
						+ "            </td>\n"
						+ "            <td width=510 valign=top style='width:150pt;padding:0in 5.4pt 0in 5.4pt'>\n"
						+ "               <p>" + Main.numberFormat.format(filament.getLRemaining()) + "mm</p>\n"
						+ "            </td>\n"
						+ "            <td width=510 valign=top style='width:150pt;padding:0in 5.4pt 0in 5.4pt'>\n"
						+ "               <p>" + Main.percentFormat.format(filament.getPRemaining()) + "</p>\n"
						+ "            </td>\n"
						+ "         </tr>\n"
						+ "      </table>\n"
						+ "      <BR>\n"
						+ "      <table border=0 cellspacing=0 cellpadding=0 style='border-collapse:collapse; mso-padding-alt:0in 5.4pt 0in 5.4pt'>\n"
						+ "         <tr>\n"
						+ "            <td width=165 valign=top style='width:100pt;padding:0in 5.4pt 0in 5.4pt'>\n"
						+ "               <p><u>Print Date</u></p>\n"
						+ "            </td>\n"
						+ "            <td width=510 valign=top style='width:100pt;padding:0in 5.4pt 0in 5.4pt'>\n"
						+ "               <p><u>Filament Used</u></p>\n"
						+ "            </td>\n"
						+ "            <td width=510 valign=top style='width:100pt;padding:0in 5.4pt 0in 5.4pt'>\n"
						+ "               <p><u>Print Description</u></p>\n"
						+ "            </td>\n"
						+ "         </tr>\n"
						+ "      </table>\n");
				Iterator<Print> printIterator = filament.getPrint().iterator();
				while (printIterator.hasNext()){
					Print print = printIterator.next();
					fw.write( "      <table border=0 cellspacing=0 cellpadding=0 style='border-collapse:collapse; mso-padding-alt:0in 5.4pt 0in 5.4pt'>\n"
							+ "         <tr>\n"
							+ "            <td width=165 valign=top style='width:100pt;padding:0in 5.4pt 0in 5.4pt'>\n"
							+ "               <p>" + print.getDate() + "</p>\n"
							+ "            </td>\n"
							+ "            <td width=510 valign=top style='width:100pt;padding:0in 5.4pt 0in 5.4pt'>\n"
							+ "               <p>" + Main.numberFormat.format(print.getAmountUsed()) + "mm</p>\n"
							+ "            </td>\n"
							+ "            <td width=510 valign=top style='width:500pt;padding:0in 5.4pt 0in 5.4pt'>\n"
							+ "               <p>" + print.getDescription() + "</p>\n"
							+ "            </td>\n"
							+ "         </tr>\n"
							+ "      </table>\n");
				}
			}
			fw.write( "   </BODY>\n"
					+ "</HTML>");
			fw.close();
			Desktop.getDesktop().browse(new File("Report_" + saveFileDateFormat.format(date) + ".html").toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Exports the data to a text file
	 */
	public static void exportToText() {
		try {
			FileWriter fw = new FileWriter(new File("Report_" + saveFileDateFormat.format(date) + ".txt"));
			fw.write(String.format("%-19s%-15s%-17s%-17s%-17s", "Filament Name", "Filament Type", "Filament Weight", "Filament Length", "Filament Length Remaining", "Filament Percent Remaining"));
			Iterator<Filament> filamentIterator = Main.filaments.iterator();
			while (filamentIterator.hasNext()){
				Filament filament = filamentIterator.next();
				fw.write(String.format("\n%s\n%-19s%-15s%-17s%-17s%-17s", separator, filament.getName(), filament.getType(), filament.getWeight(), Main.numberFormat.format(filament.getLength()) + "mm", Main.numberFormat.format(filament.getLRemaining()) + "mm", Main.percentFormat.format(filament.getPRemaining())));
				fw.write(String.format("\n\t%-19s%-13s%s", "Print Date", "Amount Used", "Print Description"));
				Iterator<Print> printIterator = filament.getPrint().iterator();
				while (printIterator.hasNext()){
					Print print = printIterator.next();
					fw.write(String.format("\n\t%-19s%-13s%s", print.getDate(), Main.numberFormat.format(print.getAmountUsed()), print.getDescription()));
				}
			}
			fw.write("");
			fw.close();
			Desktop.getDesktop().browse(new File("Report_" + saveFileDateFormat.format(date) + ".txt").toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
