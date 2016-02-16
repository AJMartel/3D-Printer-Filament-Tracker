package com.FilamentTracker.Dialogs;

import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import com.FilamentTracker.AutoImportGCode;
import com.FilamentTracker.Print;

/**
 * FILENAME:    AboutDialog.java 
 * DESCRIPTION: This class creates and opens the about dialog.
 * 
 * @author Andrew Comer
 */
public class PrintStreamDialog extends JFrame {

    private static final long       serialVersionUID    = 1L;
    JLabel                          lblSomething        = new JLabel(String.format("%-19s%-13s%s", "Date", "Amount Used", "Description"));
    JScrollPane                     scrollPane          = new JScrollPane();
    static JList                    list                = new JList();
    static DefaultListModel<String> model               = new DefaultListModel<String>();

    /**
     * FUNCTION:    aboutDialog 
     * PURPOSE:     Constructor.
     * 
     * @param x X coordinate of the main frame
     * @param y Y coordinate of the main frame
     */
    public PrintStreamDialog(int x, int y) {
        setTitle("Print Stream");
        setBounds((int) ((921 / 2) - (533 / 2)) + x, (int) ((546 / 2) - (242 / 2)) + y, 533, 242);
        setIconImage(System.getProperty("DEBUG") != null ? new ImageIcon("com/FilamentTracker/Dialogs/Print_Stream_Icon.png").getImage() : new ImageIcon(getClass().getResource("Print_Stream_Icon.png")).getImage());
        getContentPane().setLayout(null);
        setResizable(false);

        populateList();

        scrollPane.setBounds(0, 0, 527, 151);
        getContentPane().add(scrollPane);
        scrollPane.setViewportView(list);
        list.setFont(new Font("Lucida Console", Font.PLAIN, 11));
        scrollPane.setColumnHeaderView(lblSomething);
        lblSomething.setFont(new Font("Lucida Console", Font.PLAIN, 11));
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
        PrintStreamDialog.list.setModel(model);
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
}