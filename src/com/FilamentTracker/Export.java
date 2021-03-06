package com.FilamentTracker;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * FILENAME:    Export.java<P>
 * DESCRIPTION: This class exports all the data in the table to a HTML or text file.
 *
 * @author Andrew Comer
 * @email AndrewJComer@yahoo.com
 */
public class Export
{
    public static DateFormat  dateFormat         = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private static DateFormat saveFileDateFormat = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
    private static String     separator          = "====================================================================================================";

    /**
     * FUNCTION:    exportToHTML<P>
     * PURPOSE:     Exports the data to a HTML file.
     */
    public static void exportToHTML()
    {
        if (!new File(OSSpecificVariables.windowsReportsLocation).isDirectory())
        {
            new File(OSSpecificVariables.windowsReportsLocation).mkdirs();
        }

        createCSSFiles();
        try
        {
            Date date = new Date();
            FileWriter fw = new FileWriter(new File(OSSpecificVariables.windowsReportsLocation + "/Report_" + saveFileDateFormat.format(date) + ".html"));
            fw.write( "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <meta charset=\"UTF-8\">\n"
                    + "        <meta name=\"viewport\" content=\"initial-scale=1.0\">\n"
                    + "        <link rel=\"stylesheet\" href=\"reset.css\">\n"
                    + "        <link rel=\"stylesheet\" href=\"style.css\">\n"
                    + "    </head>\n"
                    + "    <h1 class=\"date\">" + dateFormat.format(date) + "</h1>\n"
                    + "    <h1>" + System.getProperty("user.name") + "'s 3D Printer Filament Report</h1>\n"
                    + "    <table class=\"filament\">\n"
                    + "        <thead class=\"filamentthead\">\n"
                    + "            <tr>\n"
                    + "                <th style=\"width: 12.6%\">Filament Name</th>\n"
                    + "                <th style=\"width: 12.6%\">Filament Type</th>\n"
                    + "                <th style=\"width: 12.6%\">Filament Weight</th>\n"
                    + "                <th style=\"width: 16.6%\">Filament Length</th>\n"
                    + "                <th style=\"width: 16.6%\">Filament Length Remaining</th>\n"
                    + "                <th style=\"width: 17.6%\">Filament Percent Remaining</th>\n"
                    + "                <th style=\"width: 11%\">Filament Cost</th>\n"
                    + "            </tr>\n"
                    + "        </thead>\n");
            Iterator<Filament> filamentIterator = Main.filaments.iterator();
            while (filamentIterator.hasNext())
            {
                Filament filament = filamentIterator.next();
                fw.write( "        <tr>\n"
                        + "            <th>" + filament.getName() + "</th>\n"
                        + "            <th>" + filament.getType() + "</th>\n"
                        + "            <th>" + filament.getWeight() + "</th>\n"
                        + "            <th>" + Main.numberFormat.format(filament.getLength()) + "mm</th>\n"
                        + "            <th>" + Main.numberFormat.format(filament.getLRemaining()) + "mm</th>\n"
                        + "            <th>" + Main.percentFormat.format(filament.getPRemaining()) + "</th>\n"
                        + "            <th>" + filament.getCost() + "</th>\n"
                        + "        </tr>\n"
                        + "        <td colspan = 7>\n"
                        + "            <table class=\"print\">\n"
                        + "                <thead class=\"printthead\">\n"
                        + "                    <tr>\n"
                        + "                        <th style=\"width: 20%\">Print Date</th>\n"
                        + "                        <th style=\"width: 20%\">Amount Used</th>\n"
                        + "                        <th style=\"width: 10%\">% Used</th>\n"
                        + "                        <th style=\"width: 10%\">Cost</th>\n"
                        + "                        <th style=\"width: 40%\">Description</th>\n"
                        + "                    </tr>\n"
                        + "                </thead>\n"
                        + "                <tbody>\n");
                Iterator<Print> printIterator = filament.getPrint().iterator();
                while (printIterator.hasNext())
                {
                    Print print = printIterator.next();
                    fw.write( "                    <tr>\n"
                            + "                        <td>" + print.getDate() + "</td>\n"
                            + "                        <td>" + Main.numberFormat.format(print.getAmountUsed()) + "mm</td>\n"
                            + "                        <td>" + Main.percentFormat.format(print.getAmountUsed() / filament.getLength()) + "</td>\n"
                            + "                        <td>" + Main.costFormat.format((print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", ""))) + "</td>\n"
                            + "                        <td>" + print.getDescription() + "</td>\n"
                            + "                    </tr>\n");
                }
                fw.write( "                </tbody>\n"
                        + "            </table>\n"
                        + "        </td>\n");
            }
            fw.write( "    </table>\n"
                    + "</html>\n");
            fw.close();
            Desktop.getDesktop().browse(new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/3DPrinterFilamentTracker/Reports/Report_" + saveFileDateFormat.format(date) + ".html").toURI());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
            
            
    /**
     * FUNCTION:    createCSSFiles<P>
     * PURPOSE:     Creates the CSS files to format the HTML file.
     */
    public static void createCSSFiles()
    {
        try
        {
            FileWriter fw = new FileWriter(new File(OSSpecificVariables.windowsReportsLocation + "/style.css"));
            fw.write( "h1{\n"
                    + "    text-align:" + ConfigFile.getInstance().getTextAlignString(ConfigFile.getInstance().getTitleTextAlign()) + ";\n"
                    + "    text-transform:uppercase;\n"
                    + "    letter-spacing:2px;\n"
                    + "    font-size:" + ConfigFile.getInstance().getTitleTextSize() / 10.0  + "em;\n"
                    + "    margin:20px 0;\n"
                    + "    color:" + ConfigFile.getInstance().getTitleColor() + ";\n"
                    + "}\n"
                    
                    + ".date{\n"
                    + "    text-align:" + ConfigFile.getInstance().getTextAlignString(ConfigFile.getInstance().getTimestampTextAlign()) + ";\n"
                    + "    letter-spacing:1px;\n"
                    + "    font-size:" + ConfigFile.getInstance().getTimestampTextSize() / 10.0  + "em;\n"
                    + "    margin:0px 0;\n"
                    + "    color:" + ConfigFile.getInstance().getTimestampColor() + ";\n"
                    + "}\n"
                    
                    + "table{\n"
                    + "    border-collapse:collapse;\n"
                    + "    width:95%;\n"
                    + "    margin:auto;\n"
                    + "    color:" + ConfigFile.getInstance().getTableDataColor() + ";\n"
                    + "}\n"
                    
                    + ".filament{\n"
                    + "    border:2px solid " + ConfigFile.getInstance().getFilamentBoarderColor() + ";\n"
                    + "}\n"
                    
                    + ".filamentthead{\n"
                    + "    background:" + ConfigFile.getInstance().getFilamentFillColor() + ";\n"
                    + "}\n"
                    
                    + ".print{\n"
                    + "    border:2px solid " + ConfigFile.getInstance().getPrintBoarderColor() + ";\n"
                    + "}\n"
                    
                    + ".printthead{\n"
                    + "    background:" + ConfigFile.getInstance().getPrintFillColor() + ";\n"
                    + "}\n"
                    
                    + "thead{\n"
                    + "    color:" + ConfigFile.getInstance().getTableHeaderColor() + ";\n"
                    + "}\n"
                    
                    + "th,td{\n"
                    + "    text-align:center;\n"
                    + "    padding:5px 0;\n"
                    + "}\n"
                    
                    + "tbody tr:nth-child(even){\n" //other table color
                    + "    background:" + ConfigFile.getInstance().getTableEvenColumnColor() + ";\n"
                    + "}\n"
                    
                    + "tbody td:nth-child(even){\n" //mid print column color
                    + "    background:" + ConfigFile.getInstance().getTableOddColumnColor() + ";\n"
                    + "}");
            fw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            FileWriter fw = new FileWriter(new File(OSSpecificVariables.windowsReportsLocation + "/reset.css"));            
            fw.write( "html,body,div,span,applet,object,iframe,h1,h2,h3,h4,h5,h6,p,blockquote,pre,a,abbr,acronym,address,big,cite,code,del,"
                    + "dfn,em,img,ins,kbd,q,s,samp,small,strike,strong,sub,sup,tt,var,b,u,i,center,dl,dt,dd,ol,ul,li,fieldset,form,label,"
                    + "legend,table,caption,tbody,tfoot,thead,tr,th,td,article,aside,canvas,details,embed,figure,figcaption,footer,header,"
                    + "hgroup,menu,nav,output,ruby,section,summary,time,mark,audio,video{margin:0;padding:0;border:0;font-size:100%;font:"
                    + "inherit;vertical-align:baseline}article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section{display:"
                    + "block}body{line-height:1}ol,ul{list-style:none}blockquote,q{quotes:none}blockquote:before,blockquote:after,q:before,q:"
                    + "after{content:'';content:none}table{border-collapse:collapse;border-spacing:0}");
            fw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * FUNCTION:    exportToHTML<P>
     * PURPOSE:     Exports the data to a text file.
     */
    public static void exportToText()
    {
        if (!new File(OSSpecificVariables.windowsReportsLocation).isDirectory())
        {
            new File(OSSpecificVariables.windowsReportsLocation).mkdirs();
        }

        try
        {
            Date date = new Date();
            FileWriter fw = new FileWriter(new File(OSSpecificVariables.windowsReportsLocation + "/Report_" + saveFileDateFormat.format(date) + ".txt"));
            fw.write(dateFormat.format(date) + "\n");
            fw.write(System.getProperty("user.name") + "'s 3D Printer Filament Report\n\n");
            fw.write(String.format("%-19s%-15s%-17s%-17s%-17s", "Filament Name", "Filament Type", "Filament Weight", "Filament Length", "Filament Length Remaining", "Filament Percent Remaining"));
            Iterator<Filament> filamentIterator = Main.filaments.iterator();
            while (filamentIterator.hasNext())
            {
                Filament filament = filamentIterator.next();
                fw.write(String.format("\n%s\n%-19s%-15s%-17s%-17s%-17s", separator, filament.getName(), filament.getType(), filament.getWeight(), Main.numberFormat.format(filament.getLength()) + "mm", Main.numberFormat.format(filament.getLRemaining()) + "mm", Main.percentFormat.format(filament.getPRemaining())));
                fw.write(String.format("\n\t%-19s%-13s%-8s%-7s%s", "Print Date", "Amount Used", "% Used", "Cost", "Print Description"));
                Iterator<Print> printIterator = filament.getPrint().iterator();
                while (printIterator.hasNext())
                {
                    Print print = printIterator.next();
                    fw.write(String.format("\n\t%-19s%-13s%-8s%-7s%s", print.getDate(), Main.numberFormat.format(print.getAmountUsed()), Main.percentFormat.format(print.getAmountUsed() / filament.getLength()), Main.costFormat.format((print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", ""))), print.getDescription()));
                }
            }
            fw.close();
            Desktop.getDesktop().browse(new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/3DPrinterFilamentTracker/Reports/Report_" + saveFileDateFormat.format(date) + ".txt").toURI());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}