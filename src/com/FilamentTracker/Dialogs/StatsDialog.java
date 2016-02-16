package com.FilamentTracker.Dialogs;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.FilamentTracker.Filament;
import com.FilamentTracker.Main;
import com.FilamentTracker.Print;

/**
 * FILENAME:    StatsDialog.java 
 * DESCRIPTION: This class creates and opens the stats dialog.
 * 
 * @author Andrew Comer
 */
public class StatsDialog extends JFrame {
    private static final long       serialVersionUID        = 1L;
    private final ArrayList<Double> printsPerYear           = new ArrayList<Double>();
    private final String[]          months                  = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
    private final JButton           decrementYearButton     = new JButton("<<");
    private final JButton           incrementYearButton     = new JButton(">>");
    private final JLabel            yearLabel               = new JLabel();
    private final JPanel            totalStatsPanel         = new JPanel();
    private final JPanel            yearStatsPanel          = new JPanel();
    private final JPanel            graphPanel              = new JPanel();
    private final JToggleButton     graphTogButton          = new JToggleButton("Number of Prints");
    private final JToggleButton     yearTotalTogButton      = new JToggleButton("Yearly Data");
    private final JScrollPane       yearStatsScrollPane     = new JScrollPane();
    private final JScrollPane       totalStatsScrollPane    = new JScrollPane();
    private final JTextPane         totalStatsTextPane      = new JTextPane();
    private final JTextPane         yearStatsTextPane       = new JTextPane();
    private int                     costQuantityToggle      = 0;
    private int                     yearTotalToggle         = 0;
    private int                     titleState              = 0;
    private int                     titleState2             = 0;

    /**
     * FUNCTION:    StatsDialog 
     * PURPOSE:     Constructor.
     * 
     * @param x X coordinate of the main frame
     * @param y Y coordinate of the main frame
     */
    public StatsDialog(int x, int y) {
        setTitle("Print Stats");
        setBounds((int) ((921 / 2) - (700 / 2)) + x, (int) ((546 / 2) - (700 / 2)) + y, 700, 700);
        setIconImage(System.getProperty("DEBUG") != null ? new ImageIcon("com/FilamentTracker/Dialogs/Stats_Icon.png").getImage() : new ImageIcon(getClass().getResource("Stats_Icon.png")).getImage());
        getContentPane().setLayout(null);

        initializeArrayList();

        ChartFrame frame = new ChartFrame("Stats", resetChart(Calendar.getInstance().get(Calendar.YEAR)));
        graphPanel.setBounds(0, 0, 694, 434);
        graphPanel.add(frame.getContentPane());
        setResizable(false);

        decrementYearButton.setBounds(262, 467, 49, 23);
        decrementYearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                yearLabel.setText(Integer.parseInt(yearLabel.getText()) - 1 + "");
                refreshChart(graphPanel, frame);
                populateStats();
            }
        });

        incrementYearButton.setBounds(384, 467, 49, 23);
        incrementYearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                yearLabel.setText(Integer.parseInt(yearLabel.getText()) + 1 + "");
                refreshChart(graphPanel, frame);
                populateStats();
            }
        });

        yearLabel.setText(Calendar.getInstance().get(Calendar.YEAR) + "");
        yearLabel.setBounds(321, 467, 53, 19);
        yearLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        yearLabel.setHorizontalAlignment(SwingConstants.CENTER);

        yearStatsPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), yearLabel.getText() + " Stats", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
        yearStatsPanel.setBounds(10, 501, 301, 159);
        yearStatsPanel.setLayout(null);

        totalStatsPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Total Stats", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
        totalStatsPanel.setBounds(383, 501, 301, 159);
        totalStatsPanel.setLayout(null);

        graphTogButton.setBounds(405, 433, 171, 23);
        graphTogButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    graphTogButton.setText("Cost of Prints");
                    costQuantityToggle = 12;
                    titleState = 1;
                    refreshChart(graphPanel, frame);
                }
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    graphTogButton.setText("Number of Prints");
                    costQuantityToggle = 0;
                    titleState = 0;
                    refreshChart(graphPanel, frame);
                }
            }
        });

        yearTotalTogButton.setBounds(117, 433, 171, 23);
        yearTotalTogButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    yearTotalTogButton.setText("Total Data");
                    yearTotalToggle = 24; // Used data 24 - 47 : Total Data
                    titleState2 = 1;
                    incrementYearButton.setEnabled(false);
                    decrementYearButton.setEnabled(false);
                    yearLabel.setEnabled(false);
                    refreshChart(graphPanel, frame);
                }
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    yearTotalTogButton.setText("Yearly Data");
                    yearTotalToggle = 0; // Use data 0 - 23 : Year Data
                    titleState2 = 0;
                    incrementYearButton.setEnabled(true);
                    decrementYearButton.setEnabled(true);
                    yearLabel.setEnabled(true);
                    refreshChart(graphPanel, frame);
                }
            }
        });

        yearStatsScrollPane.setBounds(10, 24, 281, 124);
        yearStatsScrollPane.setViewportView(yearStatsTextPane);
        yearStatsTextPane.setCaretPosition(0);
        yearStatsTextPane.setEditable(false);
        yearStatsTextPane.setFont(new Font("Lucida Console", Font.PLAIN, 11));
        yearStatsPanel.add(yearStatsScrollPane);

        totalStatsScrollPane.setBounds(10, 24, 281, 124);
        totalStatsScrollPane.setViewportView(totalStatsTextPane);
        totalStatsTextPane.setCaretPosition(0);
        totalStatsTextPane.setEditable(false);
        totalStatsTextPane.setFont(new Font("Lucida Console", Font.PLAIN, 11));
        totalStatsPanel.add(totalStatsScrollPane);

        getContentPane().add(graphPanel);
        getContentPane().add(decrementYearButton);
        getContentPane().add(incrementYearButton);
        getContentPane().add(yearLabel);
        getContentPane().add(yearStatsPanel);
        getContentPane().add(graphTogButton);
        getContentPane().add(yearTotalTogButton);
        getContentPane().add(totalStatsPanel);

        populateStats();
    }

    /**
     * FUNCTION:    populateStats 
     * PURPOSE:     Populates the stats test areas.
     */
    public void populateStats() {
        String yearStats = "", totalStats = "";

        yearStats   = "Total Number of Prints: \t" + (int) getNumberCostOfPrints(0) 
                    + "\nTotal Cost of Prints: \t" + Main.costFormat.format(getNumberCostOfPrints(12)) 
                    + "\nMost Prints (Month): \t" + getHighestPrintCostCount(0) 
                    + "\nHighest Cost (Month): \t" + getHighestPrintCostCount(12);

        totalStats  = "Total Number of Prints: \t" + (int) getNumberCostOfPrints(24) 
                    + "\nTotal Cost of Prints: \t" + Main.costFormat.format(getNumberCostOfPrints(36)) 
                    + "\nMost Prints (Month): \t" + getHighestPrintCostCount(24) 
                    + "\nHighest Cost (Month): \t" + getHighestPrintCostCount(36);

        yearStatsTextPane.setText(yearStats);
        totalStatsTextPane.setText(totalStats);
        yearStatsPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), yearLabel.getText() + " Stats", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
    }

    /**
     * FUNCTION:    getNumberCostOfPrints 
     * PURPOSE:     Gets the number of prints and the cost of prints.
     * 
     * @param index Index of the printsPerYear array list to start at
     * @return The number of prints or the cost of prints depending on the input index
     */
    public double getNumberCostOfPrints(int index) {
        double num = 0;
        for (int i = index; i < index + 12; i++)
            num += printsPerYear.get(i);
        return num;
    }

    /**
     * FUNCTION:    getHighestPrintCostCount 
     * PURPOSE:     Gets the highest print cost and total prints.
     * 
     * @param index Index of the printsPerYear array list to start at
     * @return The the highest print cost or total prints depending on the input index
     */
    public String getHighestPrintCostCount(int index) {
        double num = -1, amt = 0;
        for (int i = index; i < index + 12; i++) {
            if (printsPerYear.get(i) > amt) {
                amt = printsPerYear.get(i);
                num = i;
            }
        }
        if (num == -1)
            return "N/A";
        return months[(int) (num % 12)];
    }

    /**
     * FUNCTION:    initializeArrayList 
     * PURPOSE:     Add the 0 values to the array list.
     */
    public void initializeArrayList() {
        for (int i = 0; i < 48; i++) {
            printsPerYear.add(0.0);
        }
    }

    /**
     * FUNCTION:    getNewData 
     * PURPOSE:     Loops through all the prints and sets the print count and cost value for each month.
     * 
     * @param year The year to find prints for
     */
    public void getNewData(int year) {
        for (Filament filament : Main.filaments) {
            for (Print print : filament.getPrint()) {
                if (Integer.parseInt(print.getDate().substring(13, 17)) == year) {
                    switch (print.getDate().substring(5, 8)) {
                    case "Jan":
                        incrementMonthCount(0.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                        break;
                    case "Feb":
                        incrementMonthCount(1.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                        break;
                    case "Mar":
                        incrementMonthCount(2.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                        break;
                    case "Apr":
                        incrementMonthCount(3.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                        break;
                    case "May":
                        incrementMonthCount(4.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                        break;
                    case "Jun":
                        incrementMonthCount(5.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                        break;
                    case "Jul":
                        incrementMonthCount(6.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                        break;
                    case "Aug":
                        incrementMonthCount(7.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                        break;
                    case "Sep":
                        incrementMonthCount(8.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                        break;
                    case "Oct":
                        incrementMonthCount(9.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                        break;
                    case "Nov":
                        incrementMonthCount(10.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                        break;
                    case "Dec":
                        incrementMonthCount(11.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                        break;
                    default:
                        break;
                    }
                }
                switch (print.getDate().substring(5, 8)) {
                case "Jan":
                    incrementMonthCount(24.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                    break;
                case "Feb":
                    incrementMonthCount(25.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                    break;
                case "Mar":
                    incrementMonthCount(26.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                    break;
                case "Apr":
                    incrementMonthCount(27.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                    break;
                case "May":
                    incrementMonthCount(28.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                    break;
                case "Jun":
                    incrementMonthCount(29.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                    break;
                case "Jul":
                    incrementMonthCount(30.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                    break;
                case "Aug":
                    incrementMonthCount(31.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                    break;
                case "Sep":
                    incrementMonthCount(32.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                    break;
                case "Oct":
                    incrementMonthCount(33.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                    break;
                case "Nov":
                    incrementMonthCount(34.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                    break;
                case "Dec":
                    incrementMonthCount(35.0, (print.getAmountUsed() / filament.getLength()) * Double.parseDouble(filament.getCost().replaceAll("[^\\d.-]", "")));
                    break;
                default:
                    break;
                }
            }
        }
    }

    /**
     * FUNCTION:    incrementMonthCount 
     * PURPOSE:     Adds each print and cost to its month totals.
     * 
     * @param month The month to add to
     * @param cost The cost to add to the month
     */
    public void incrementMonthCount(Double month, double cost) {
        printsPerYear.set(month.intValue(), printsPerYear.get(month.intValue()) + 1);
        printsPerYear.set(month.intValue() + 12, printsPerYear.get(month.intValue() + 12) + cost);
    }

    /**
     * FUNCTION:    resetData 
     * PURPOSE:     Resets the data and sets the new dataset for the graph.
     * 
     * @return The dataset of the graph
     */
    public DefaultCategoryDataset resetData(int year) {
        for (int i = 0; i < 48; i++) {
            printsPerYear.set(i, 0.0);
        }

        getNewData(year);

        DefaultCategoryDataset objDataset = new DefaultCategoryDataset();
        objDataset.setValue(printsPerYear.get(0 + costQuantityToggle + yearTotalToggle), "", "Jan");
        objDataset.setValue(printsPerYear.get(1 + costQuantityToggle + yearTotalToggle), "", "Feb");
        objDataset.setValue(printsPerYear.get(2 + costQuantityToggle + yearTotalToggle), "", "Mar");
        objDataset.setValue(printsPerYear.get(3 + costQuantityToggle + yearTotalToggle), "", "Apr");
        objDataset.setValue(printsPerYear.get(4 + costQuantityToggle + yearTotalToggle), "", "May");
        objDataset.setValue(printsPerYear.get(5 + costQuantityToggle + yearTotalToggle), "", "Jun");
        objDataset.setValue(printsPerYear.get(6 + costQuantityToggle + yearTotalToggle), "", "Jul");
        objDataset.setValue(printsPerYear.get(7 + costQuantityToggle + yearTotalToggle), "", "Aug");
        objDataset.setValue(printsPerYear.get(8 + costQuantityToggle + yearTotalToggle), "", "Sep");
        objDataset.setValue(printsPerYear.get(9 + costQuantityToggle + yearTotalToggle), "", "Oct");
        objDataset.setValue(printsPerYear.get(10 + costQuantityToggle + yearTotalToggle), "", "Nov");
        objDataset.setValue(printsPerYear.get(11 + costQuantityToggle + yearTotalToggle), "", "Dec");
        return objDataset;
    }

    /**
     * FUNCTION:    resetChart 
     * PURPOSE:     Creates a new graph with new data
     * 
     * @param year The year to get data for
     * @return The chart
     */
    public JFreeChart resetChart(int year) {
        String title = "", yAxis = "";

        if (titleState == 0) {
            if (titleState2 == 0) { // Yearly Number of Prints
                title = "Print Count for Year " + year;
                yAxis = "Number of Prints";
            } else { // Total Number of prints
                title = "Total Print Count";
                yAxis = "Number of Prints";
            }
        } else {
            if (titleState2 == 0) { // Yearly Cost
                title = "Print Cost for Year " + year;
                yAxis = "Cost of Prints ($)";
            } else { // Total Cost
                title = "Total Print Cost";
                yAxis = "Cost of Prints ($)";
            }
        }

        JFreeChart objChart = ChartFactory.createBarChart(title, // Chart title
                "Months", // X-axis label
                yAxis, // Y-axis label
                resetData(year), // Chart Data
                PlotOrientation.VERTICAL, // Orientation
                false, // Legend
                false, // Tooltips
                false // URL
        );
        objChart.getPlot();
        yearStatsPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), yearLabel.getText() + " Stats", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
        return objChart;
    }

    /**
     * FUNCTION:    refreshChart 
     * PURPOSE:     Refreshes the graph to show the new one with new data.
     * 
     * @param graphPanel The panel the graph is on
     * @param frame The frame the graph is on
     */
    public void refreshChart(JPanel graphPanel, ChartFrame frame) {
        graphPanel.removeAll();
        frame = new ChartFrame("Stats", resetChart(Integer.parseInt(yearLabel.getText())));
        graphPanel.add(frame.getContentPane());
    }
}