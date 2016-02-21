package com.FilamentTracker.Dialogs;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.FilamentTracker.AutoImportGCode;
import com.FilamentTracker.Main;
import com.FilamentTracker.Print;

/**
 * FILENAME:    AboutDialog.java 
 * DESCRIPTION: This class creates and opens the about dialog.
 * 
 * @author Andrew Comer
 * @email AndrewJComer@yahoo.com
 */
public class PrintStreamDialog extends JFrame {

    private static final long               serialVersionUID    = 1L;
    private final JTextArea                 listHeader          = new JTextArea(String.format("%-19s%-13s%s", "Date", "Amount Used", "Description"));
    private final JScrollPane               scrollPane          = new JScrollPane();
    public static JButton                   addPrintButton      = new JButton("Add Print");
    public static JButton                   removePrintButton   = new JButton("Remove from stream");
    private final JComboBox                 filamentComboBox    = new JComboBox();
    private static JList                    list                = new JList();
    private static DefaultListModel<String> model               = new DefaultListModel<String>();

    /**
     * FUNCTION:    aboutDialog 
     * PURPOSE:     Constructor.
     * 
     * @param x X coordinate of the main frame
     * @param y Y coordinate of the main frame
     */
    public PrintStreamDialog(int x, int y) {
        setTitle("Print Stream");
        setBounds((int) ((921 / 2) - (533 / 2)) + x, (int) ((546 / 2) - (242 / 2)) + y, 533, 223);
        setIconImage(System.getProperty("DEBUG") != null ? new ImageIcon("com/FilamentTracker/Dialogs/Print_Stream_Icon.png").getImage() : new ImageIcon(getClass().getResource("Print_Stream_Icon.png")).getImage());
        setLayout(null);
        setResizable(false);

        populateList();

        list.setFont(new Font("Lucida Console", Font.PLAIN, 11));
        scrollPane.setBounds(0, 0, 527, 151);
        scrollPane.setViewportView(list);
        listHeader.setFont(new Font("Lucida Console", Font.PLAIN, 11));
        listHeader.setBackground(Color.CYAN);
        scrollPane.setColumnHeaderView(listHeader);
        
        
        removePrintButton.setBounds(374, 161, 138, 23);
        removePrintButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                removeSelected();
            }
        });
        
        filamentComboBox.setBounds(14, 162, 194, 20);
        for (int i = 0; i <= Main.index; i++) {
            filamentComboBox.addItem(Main.filaments.get(i).getName());
            filamentComboBox.setSelectedIndex(0);
        }
        
        if (filamentComboBox.getItemAt(0) == null) {
            addPrintButton.setEnabled(false);
            removePrintButton.setEnabled(false);
        } else {
            addPrintButton.setEnabled(true);
            removePrintButton.setEnabled(true);
        }
        
        addPrintButton.setBounds(222, 161, 138, 23);
        addPrintButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                for (int index : list.getSelectedIndices()) { // add checks if filament is over the amount left
                    Print print = AutoImportGCode.printStream.get(index);
                    Main.filaments.get(filamentComboBox.getSelectedIndex()).addPrint(print.getDate(), print.getDescription(), print.getAmountUsed());
                    Main.saveNeeded = true;
                    Main.updateTable();
                    Main.updatePrintArea();
                }
                removeSelected();
            }
        });
        
        add(addPrintButton);
        add(removePrintButton);
        add(filamentComboBox);
        add(scrollPane);
    }
    
    /**
     * FUNCTION:    populateList 
     * PURPOSE:     Populates the list with print stream entries.
     */
    public void populateList() {
        model.clear();
        for (Print print : AutoImportGCode.printStream) {
            model.addElement(String.format("%-19s%-13s%s", print.getDate(), print.getAmountUsed() + "mm", print.getDescription()));
        }
        list.setModel(model);
    }

    /**
     * FUNCTION:    updateList 
     * PURPOSE:     Updates the list with new entries.
     */
    public static void updateList() {
        int index = AutoImportGCode.printStream.size() - 1;
        if (index != -1)
            model.addElement(String.format("%-19s%-13s%s", AutoImportGCode.printStream.get(index).getDate(), AutoImportGCode.printStream.get(index).getAmountUsed() + "mm", AutoImportGCode.printStream.get(index).getDescription()));
    }
    
    /**
     * FUNCTION:    removeSelected 
     * PURPOSE:     Removes selected items from the list.
     */
    public void removeSelected() {
        for (int index = list.getSelectedIndices().length - 1; index >= 0; index--) {
            AutoImportGCode.printStream.remove(list.getSelectedIndices()[index]);
        }
        populateList();
    }
}