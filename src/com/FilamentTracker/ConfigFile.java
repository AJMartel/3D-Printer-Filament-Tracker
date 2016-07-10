package com.FilamentTracker;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ConfigFile
{
    public static ConfigFile configFileInstance   = null;

    //General
    private int              minimizeToTrayOnExit = 0;
    private boolean          promptOnExit         = true;
    private int              autoSaveInterval     = 5;
    private String           saveFileLocation     = OSSpecificVariables.windowsMainDirectory;

    //Reports
    private String           reportFileLocation   = "";
    private boolean          openAfterGeneration  = true;

    //HTML Color
    private int              timestampTextAlign   = 1;
    private int              timestampTextSize    = 10;
    private String           timestampColor       = "#000000";
    private int              titleTextAlign       = 1;
    private int              titleTextSize        = 15;
    private String           titleColor           = "#000000";
    private String           tableHeaderColor     = "#FFFFFF";
    private String           tableDataColor       = "#000000";
    private String           tableEvenColumnColor = "#FFFFFF";
    private String           tableOddColumnColor  = "#FFFFFF";
    private String           filamentBoarderColor = "#000000";
    private String           filamentHeaderColor  = "#000000";
    private String           printBoarderColor    = "#000000";
    private String           printHeaderColor     = "#000000";

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

                File configFile = new File(OSSpecificVariables.windowsConfigFileLocation);

                //Create a document builder.
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(configFile);
                
                Element configElement = (Element) doc.getElementsByTagName("Config").item(0);

                /*
                 * Gets the information from the general section of the config file
                 */
                NodeList generalList = configElement.getElementsByTagName("General");
                Element generalElement = (Element) generalList.item(0);
                minimizeToTrayOnExit = Integer.parseInt(generalElement.getElementsByTagName("MinimizeToTray").item(0).getTextContent());
                promptOnExit = generalElement.getElementsByTagName("PromptOnExit").item(0).getTextContent().equalsIgnoreCase("True") ? true : false;
                autoSaveInterval = Integer.parseInt(generalElement.getElementsByTagName("AutoSaveInterval").item(0).getTextContent());
                
                /*
                 * Gets the information from the Report section of the config file
                 */
                NodeList reportList = configElement.getElementsByTagName("Report");
                
                /*
                 * General section of Report section
                 */
                NodeList reportGeneralList = ((Element) reportList.item(0)).getElementsByTagName("General");
                Element reportGeneralElement = (Element) reportGeneralList.item(0);
                saveFileLocation = reportGeneralElement.getElementsByTagName("SaveLocation").item(0).getTextContent();
                openAfterGeneration = reportGeneralElement.getElementsByTagName("OpenAfterGeneration").item(0).getTextContent().equalsIgnoreCase("True") ? true : false;

                /*
                 * HTML section of Report section
                 */
                NodeList reportHTMLList = ((Element) reportList.item(0)).getElementsByTagName("HTML");
                NodeList reportHTMLTimestampList = ((Element) reportHTMLList.item(0)).getElementsByTagName("Timestamp");
                Element reportHTMLTimestampElement = (Element) reportHTMLTimestampList.item(0);
                timestampTextAlign = Integer.parseInt(reportHTMLTimestampElement.getElementsByTagName("Alignment").item(0).getTextContent());
                timestampTextSize = Integer.parseInt(reportHTMLTimestampElement.getElementsByTagName("Size").item(0).getTextContent());
                timestampColor = reportHTMLTimestampElement.getElementsByTagName("Color").item(0).getTextContent();

                NodeList reportHTMLTitleList = ((Element) reportHTMLList.item(0)).getElementsByTagName("Title");
                Element reportHTMLTitleElement = (Element) reportHTMLTitleList.item(0);
                titleTextAlign = Integer.parseInt(reportHTMLTitleElement.getElementsByTagName("Alignment").item(0).getTextContent());
                titleTextSize = Integer.parseInt(reportHTMLTitleElement.getElementsByTagName("Size").item(0).getTextContent());
                titleColor = reportHTMLTitleElement.getElementsByTagName("Color").item(0).getTextContent();

                NodeList reportHTMLTableList = ((Element) reportHTMLList.item(0)).getElementsByTagName("Table");
                Element reportHTMLTableElement = (Element) reportHTMLTableList.item(0);
                tableHeaderColor = reportHTMLTableElement.getElementsByTagName("HeaderColor").item(0).getTextContent();
                tableDataColor = reportHTMLTableElement.getElementsByTagName("DataColor").item(0).getTextContent();
                tableEvenColumnColor = reportHTMLTableElement.getElementsByTagName("EvenColumnColor").item(0).getTextContent();
                tableOddColumnColor = reportHTMLTableElement.getElementsByTagName("OddColumnColor").item(0).getTextContent();

                NodeList reportHTMLFilamentTableList = ((Element) reportHTMLList.item(0)).getElementsByTagName("FilamentTable");
                Element reportHTMLFilamentTableElement = (Element) reportHTMLFilamentTableList.item(0);
                filamentBoarderColor = reportHTMLFilamentTableElement.getElementsByTagName("BorderColor").item(0).getTextContent();
                filamentHeaderColor = reportHTMLFilamentTableElement.getElementsByTagName("HeaderColor").item(0).getTextContent();

                NodeList reportHTMLPrintTableList = ((Element) reportHTMLList.item(0)).getElementsByTagName("PrintTable");
                Element reportHTMLPrintTableElement = (Element) reportHTMLPrintTableList.item(0);
                printBoarderColor = reportHTMLPrintTableElement.getElementsByTagName("BorderColor").item(0).getTextContent();
                printHeaderColor = reportHTMLPrintTableElement.getElementsByTagName("HeaderColor").item(0).getTextContent();
                
                /*
                 * Logging Section
                 */
                NodeList loggingList = configElement.getElementsByTagName("Logging");
            }
            catch (Exception e)
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
     */
    public static ConfigFile getInstance()
    {
        if (configFileInstance == null)
        {
            configFileInstance = new ConfigFile();
        }
        return configFileInstance;
    }

    /**
     * 
     */
    public void saveConfigFile()
    {
        try
        {
            File configFile = new File(OSSpecificVariables.windowsConfigFileLocation);

            //Create a document builder.
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("Config");
            doc.appendChild(rootElement);

            /*
             * General Section
             */
            Element general = doc.createElement("General");
            rootElement.appendChild(general);

            addChild(doc, general, "MinimizeToTray", minimizeToTrayOnExit);
            addChild(doc, general, "PromptOnExit", promptOnExit);
            addChild(doc, general, "AutoSaveInterval", autoSaveInterval);

            /*
             * Report Section
             */
            Element report = doc.createElement("Report");
            rootElement.appendChild(report);

            Element generalReport = doc.createElement("General");
            report.appendChild(generalReport);

            addChild(doc, report, "SaveLocation", saveFileLocation);
            addChild(doc, report, "OpenAfterGeneration", openAfterGeneration);

            Element htmlReport = doc.createElement("HTML");
            report.appendChild(htmlReport);

            Element timestamp = doc.createElement("Timestamp");
            htmlReport.appendChild(timestamp);

            addChild(doc, timestamp, "Alignment", timestampTextAlign);
            addChild(doc, timestamp, "Size", timestampTextSize);
            addChild(doc, timestamp, "Color", timestampColor);

            Element title = doc.createElement("Title");
            htmlReport.appendChild(title);

            addChild(doc, title, "Alignment", titleTextAlign);
            addChild(doc, title, "Size", titleTextSize);
            addChild(doc, title, "Color", titleColor);

            Element table = doc.createElement("Table");
            htmlReport.appendChild(table);

            addChild(doc, table, "HeaderColor", tableHeaderColor);
            addChild(doc, table, "DataColor", tableDataColor);
            addChild(doc, table, "EvenColumnColor", tableEvenColumnColor);
            addChild(doc, table, "OddColumnColor", tableOddColumnColor);

            Element filamentTable = doc.createElement("FilamentTable");
            htmlReport.appendChild(filamentTable);

            addChild(doc, filamentTable, "BorderColor", filamentBoarderColor);
            addChild(doc, filamentTable, "HeaderColor", filamentHeaderColor);

            Element printTable = doc.createElement("PrintTable");
            htmlReport.appendChild(printTable);

            addChild(doc, printTable, "BorderColor", printBoarderColor);
            addChild(doc, printTable, "HeaderColor", printHeaderColor);

            /*
             * Logging Section
             */
            Element logging = doc.createElement("Logging");
            rootElement.appendChild(logging);

            addChild(doc, logging, "Temp", 1);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            StreamResult result = new StreamResult(configFile);
            transformer.transform(source, result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private static void addChild(Document doc, Element general, String key, Object value)
    {
        Element element = doc.createElement(key);
        element.appendChild(doc.createTextNode(String.valueOf(value)));
        general.appendChild(element);
    }

    public int getMinimizeToTrayOnExit(){return minimizeToTrayOnExit;}
    public boolean getPromptOnExit(){return promptOnExit;}
    public int getAutoSaveInterval(){return autoSaveInterval;}
    public String getSaveFileLocation(){return saveFileLocation;}
    public String getReportFileLocation(){return reportFileLocation;}
    public boolean getOpenAfterGeneration(){return openAfterGeneration;}
    public String getFilamentBoarderColor(){return filamentBoarderColor;}
    public String getFilamentFillColor(){return filamentHeaderColor;}
    public String getPrintBoarderColor(){return printBoarderColor;}
    public String getTimestampColor(){return timestampColor;}
    public String getTitleColor(){return titleColor;}
    public String getTableHeaderColor(){return tableHeaderColor;}
    public String getTableDataColor(){return tableDataColor;}
    public String getTableEvenColumnColor(){return tableEvenColumnColor;}
    public String getTableOddColumnColor(){return tableOddColumnColor;}
    public String getPrintFillColor(){return printHeaderColor;}
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
    public void setReportFileLocation(String reportFileLocation){this.reportFileLocation = reportFileLocation;}
    public void setOpenAfterGeneration(boolean openAfterGeneration){this.openAfterGeneration = openAfterGeneration;}
    public void setFilamentBoarderColor(String filamentBoarderColor){this.filamentBoarderColor = filamentBoarderColor;}
    public void setFilamentFillColor(String filamentFillColor){this.filamentHeaderColor = filamentFillColor;}
    public void setPrintBoarderColor(String printBoarderColor){this.printBoarderColor = printBoarderColor;}
    public void setPrintFillColor(String printFillColor){this.printHeaderColor = printFillColor;}
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
