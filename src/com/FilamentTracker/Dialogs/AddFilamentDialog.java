package com.FilamentTracker.Dialogs;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.FilamentTracker.Filament;
import com.FilamentTracker.FileIO;
import com.FilamentTracker.Main;

/**
 * FILENAME:    AddFilamentDialog.java 
 * DESCRIPTION: This class creates and opens the add/edit filament dialog.
 * 
 * @author Andrew Comer
 */
public class AddFilamentDialog extends JFrame {

    private static final long serialVersionUID = 1L;

    private final JLabel            filamentNameLabel           = new JLabel("Filament Name");
    private final JLabel            filamentTypeLabel           = new JLabel("Filament Type");
    private final JLabel            filamentWeightLabel         = new JLabel("Filament Weight");
    private final JLabel            filamentLengthLabel         = new JLabel("Filament Length");
    private final JLabel            filamentCostLabel           = new JLabel("Filament Cost");
    private final JTextField        filamentNameField           = new JTextField();
    private final JTextField        filamentTypeCustomField     = new JTextField();
    private final JTextField        filamentWeightCustomField   = new JTextField();
    private final JTextField        filamentLengthCustomField   = new JTextField();
    private final JTextField        filamentCostCustomField     = new JTextField();
    private final JComboBox         filamentTypeComboBox        = new JComboBox();
    private final JComboBox         filamentWeightComboBox      = new JComboBox();
    private final JComboBox         filamentLengthComboBox      = new JComboBox();
    private final JComboBox         filamentCostComboBox        = new JComboBox();
    private final JButton           addFilamentButton           = new JButton("Add New Filament");
    private final JButton           cancelButton                = new JButton("Cancel");
    public static ArrayList<String> filamentType                = new ArrayList<String>();
    public static ArrayList<String> filamentWeight              = new ArrayList<String>();
    public static ArrayList<String> filamentLength              = new ArrayList<String>();
    public static ArrayList<String> filamentCost                = new ArrayList<String>();
    private String                  errorMessage;
    private Boolean                 hasErrors;
    private Boolean                 customField;

    /**
     * FUNCTION:    AddFilamentDialog 
     * PURPOSE:     Constructor.
     * 
     * @param x X coordinate of the main frame
     * @param y Y coordinate of the main frame
     * @param forEdit Add new or edit existing filament
     * @param index Index of the filament to be edited
     */
    public AddFilamentDialog(int x, int y, boolean forEdit, int index) {
        setTitle(forEdit == true ? "Edit Filament Dialog" : "Add Filament Dialog");
        setBounds((int) ((921 / 2) - (308 / 2)) + x, (int) ((546 / 2) - (301 / 2)) + y, 308, 365);
        if (!forEdit)
            setIconImage(System.getProperty("DEBUG") != null ? new ImageIcon("com/FilamentTracker/Dialogs/Filament_Icon.png").getImage() : new ImageIcon(getClass().getResource("Filament_Icon.png")).getImage());
        else
            setIconImage(System.getProperty("DEBUG") != null ? new ImageIcon("com/FilamentTracker/Dialogs/Edit_Filament_Icon.png").getImage() : new ImageIcon(getClass().getResource("Edit_Filament_Icon.png")).getImage());
        setLayout(null);
        setResizable(false);

        // Name
        filamentNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentNameLabel.setBounds(10, 12, 98, 17);

        filamentNameField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentNameField.setColumns(10);
        filamentNameField.setBounds(118, 9, 174, 22);

        // Type
        filamentTypeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentTypeLabel.setBounds(10, 45, 98, 17);

        for (String type : filamentType) {
            filamentTypeComboBox.addItem(type);
        }
        filamentTypeComboBox.addItem("Add new");
        filamentTypeComboBox.setSelectedIndex(0);
        filamentTypeComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentTypeComboBox.setBounds(118, 42, 174, 23);
        filamentTypeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (filamentTypeComboBox.getSelectedIndex() == filamentType.size())
                    filamentTypeCustomField.setEditable(true);
                else
                    filamentTypeCustomField.setEditable(false);
            }
        });

        filamentTypeCustomField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentTypeCustomField.setColumns(10);
        filamentTypeCustomField.setBounds(118, 76, 174, 20);
        filamentTypeCustomField.setEditable(false);

        // Weight
        filamentWeightLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentWeightLabel.setBounds(10, 110, 98, 17);

        for (String type : filamentWeight) {
            filamentWeightComboBox.addItem(type);
        }
        filamentWeightComboBox.addItem("Add new");
        filamentWeightComboBox.setSelectedIndex(0);
        filamentWeightComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentWeightComboBox.setBounds(118, 107, 174, 23);
        filamentWeightComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (filamentWeightComboBox.getSelectedIndex() == filamentWeight.size())
                    filamentWeightCustomField.setEditable(true);
                else
                    filamentWeightCustomField.setEditable(false);
            }
        });

        filamentWeightCustomField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentWeightCustomField.setColumns(10);
        filamentWeightCustomField.setBounds(118, 141, 174, 20);
        filamentWeightCustomField.setEditable(false);

        // Length
        filamentLengthLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentLengthLabel.setBounds(10, 175, 98, 17);

        for (String type : filamentLength) {
            filamentLengthComboBox.addItem(type);
        }
        filamentLengthComboBox.addItem("Add new");
        filamentLengthComboBox.setSelectedIndex(0);
        filamentLengthComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentLengthComboBox.setBounds(118, 172, 174, 23);
        filamentLengthComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (filamentLengthComboBox.getSelectedIndex() == filamentLength.size())
                    filamentLengthCustomField.setEditable(true);
                else
                    filamentLengthCustomField.setEditable(false);
            }
        });

        filamentLengthCustomField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentLengthCustomField.setColumns(10);
        filamentLengthCustomField.setBounds(118, 206, 174, 20);
        filamentLengthCustomField.setEditable(false);

        // Cost
        filamentCostLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentCostLabel.setBounds(10, 240, 98, 17);

        for (String type : filamentCost) {
            filamentCostComboBox.addItem(type);
        }
        filamentCostComboBox.addItem("Add new");
        filamentCostComboBox.setSelectedIndex(0);
        filamentCostComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentCostComboBox.setBounds(118, 237, 174, 23);
        filamentCostComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (filamentCostComboBox.getSelectedIndex() == filamentCost.size())
                    filamentCostCustomField.setEditable(true);
                else
                    filamentCostCustomField.setEditable(false);
            }
        });

        filamentCostCustomField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentCostCustomField.setEditable(false);
        filamentCostCustomField.setColumns(10);
        filamentCostCustomField.setBounds(118, 271, 174, 20);

        // Add Button
        addFilamentButton.setBounds(10, 302, 136, 23);
        addFilamentButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent arg0) {
                String filamentName, filamentType, filamentWeight, filamentLength, filamentCost;

                hasErrors = false;
                errorMessage = "";

                filamentName = filamentNameField.getText().trim();
                if (filamentName.equals(""))
                    errorMessage(0);

                Iterator<Filament> filamentIterator = Main.filaments.iterator();
                while (filamentIterator.hasNext()) {
                    String name = filamentIterator.next().getName();
                    if (filamentName.equals(name)) {
                        if (!forEdit)
                            errorMessage(1);
                    }
                }

                if (filamentTypeCustomField.isEditable()) { // Custom type
                    filamentType = filamentTypeCustomField.getText().trim();
                    if (filamentType.equals(""))
                        errorMessage(2);
                } else
                    filamentType = filamentTypeComboBox.getSelectedItem().toString();

                if (filamentWeightCustomField.isEditable()) { // Custom weight
                    filamentWeight = filamentWeightCustomField.getText().trim();
                    if (filamentWeight.equals(""))
                        errorMessage(3);
                } else
                    filamentWeight = filamentWeightComboBox.getSelectedItem().toString();

                if (filamentLengthCustomField.isEditable()) { // Custom Length
                    filamentLength = filamentLengthCustomField.getText().trim();
                    if (filamentLength.equals(""))
                        errorMessage(4);
                    if (forEdit) {
                        if (Double.parseDouble(
                                filamentLength.replaceAll("[^\\d.-]", "")) < Main.filaments.get(index).getLength() - Main.filaments.get(index).getLRemaining())
                            errorMessage(5);
                    }
                } else {
                    filamentLength = filamentLengthComboBox.getSelectedItem().toString();
                    if (forEdit) {
                        if (Double.parseDouble(
                                filamentLength.replaceAll("[^\\d.-]", "")) < Main.filaments.get(index).getLength() - Main.filaments.get(index).getLRemaining())
                            errorMessage(5);
                    }
                }

                if (filamentCostCustomField.isEditable()) { // Custom cost
                    filamentCost = filamentCostCustomField.getText().trim();
                    if (filamentCost.equals(""))
                        errorMessage(6);
                } else
                    filamentCost = filamentCostComboBox.getSelectedItem().toString();

                if (!hasErrors) {
                    if (!forEdit) {
                        if (filamentTypeCustomField.isEditable())
                            AddFilamentDialog.filamentType.add(filamentType);
                        if (filamentWeightCustomField.isEditable())
                            AddFilamentDialog.filamentWeight.add(filamentWeight);
                        if (filamentLengthCustomField.isEditable())
                            AddFilamentDialog.filamentLength.add(filamentLength.replaceAll("[^\\d.]", "").concat("mm"));
                        if (filamentCostCustomField.isEditable())
                            AddFilamentDialog.filamentCost.add("$" + Main.numberFormat.format(Double.parseDouble(filamentCost.replaceAll("[^\\d.-]", ""))));

                        Main.filaments.add(Main.index += 1,
                                new Filament(Main.index, filamentName, filamentType, filamentWeight, Double.parseDouble(filamentLength.replaceAll("[^\\d.-]", "")), "$" + Main.numberFormat.format(Double.parseDouble(filamentCost.replaceAll("[^\\d.-]", "")))));
                    } else {
                        Main.filaments.get(index).setName(filamentName);
                        Main.filaments.get(index).setType(filamentType);
                        Main.filaments.get(index).setWeight(filamentWeight);
                        Main.filaments.get(index).setLength(Double.parseDouble(filamentLength.replaceAll("[^\\d.-]", "")));
                        Main.filaments.get(index).setCost("$" + Main.numberFormat.format(Double.parseDouble(filamentCost.replaceAll("[^\\d.-]", ""))));
                    }
                    Main.updateTable();
                    Main.updatePrintArea();
                    Main.saveNeeded = true;
                    dispose();
                } else
                    JOptionPane.showMessageDialog(null, errorMessage);
                FileIO.updateSaveFile();
            }
        });

        // Cancel Button
        cancelButton.setBounds(156, 302, 136, 23);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent arg0) {
                dispose();
            }
        });

        // Add everything to content pane
        add(filamentNameLabel);
        add(filamentNameField);
        add(filamentTypeLabel);
        add(filamentTypeComboBox);
        add(filamentTypeCustomField);
        add(filamentWeightLabel);
        add(filamentWeightComboBox);
        add(filamentWeightCustomField);
        add(filamentLengthLabel);
        add(filamentLengthComboBox);
        add(filamentLengthCustomField);
        add(filamentCostLabel);
        add(filamentCostComboBox);
        add(filamentCostCustomField);
        add(addFilamentButton);
        add(cancelButton);

        // If the user selects to edit the filament
        if (forEdit) {
            customField = true;
            filamentNameField.setText(Main.filaments.get(index).getName());

            for (int i = 0; i < filamentTypeComboBox.getItemCount() - 1; i++) {
                if (Main.filaments.get(index).getType().equals(filamentTypeComboBox.getItemAt(i))) {
                    filamentTypeComboBox.setSelectedIndex(i);
                    customField = false;
                }
            }
            if (customField) {
                filamentTypeComboBox.setSelectedIndex(filamentTypeComboBox.getItemCount() - 1);
                filamentTypeCustomField.setText(Main.filaments.get(index).getType());
            }

            customField = true;

            for (int i = 0; i < filamentWeightComboBox.getItemCount() - 1; i++) {
                if (Main.filaments.get(index).getWeight().equals(filamentWeightComboBox.getItemAt(i))) {
                    filamentWeightComboBox.setSelectedIndex(i);
                    customField = false;
                }
            }
            if (customField) {
                filamentWeightComboBox.setSelectedIndex(filamentWeightComboBox.getItemCount() - 1);
                filamentWeightCustomField.setText(Main.filaments.get(index).getWeight());
            }

            customField = true;

            for (int i = 0; i < filamentLengthComboBox.getItemCount() - 1; i++) {
                if (Main.filaments.get(index).getLength().toString().concat("mm")
                        .equals(filamentLengthComboBox.getItemAt(i))) {
                    filamentLengthComboBox.setSelectedIndex(i);
                    customField = false;
                }
            }
            if (customField) {
                filamentLengthComboBox.setSelectedIndex(filamentLengthComboBox.getItemCount() - 1);
                filamentLengthCustomField.setText(Main.filaments.get(index).getLength().toString());
            }

            customField = true;

            for (int i = 0; i < filamentCostComboBox.getItemCount() - 1; i++) {
                if (Main.filaments.get(index).getCost().equals(filamentCostComboBox.getItemAt(i))) {
                    filamentCostComboBox.setSelectedIndex(i);
                    customField = false;
                }
            }
            if (customField) {
                filamentCostComboBox.setSelectedIndex(filamentCostComboBox.getItemCount() - 1);
                filamentCostCustomField.setText(Main.filaments.get(index).getCost());
            }

            addFilamentButton.setText("Edit Filament");
        }
    }

    /**
     * FUNCTION:    errorMessage 
     * PURPOSE:     Alert the user of any errors
     * 
     * @param flag Number for specific error message
     */
    private void errorMessage(int flag) {
        switch (flag) {
        case 0:
            errorMessage += "Please enter a name for the filament.\n";
            hasErrors = true;
            break;
        case 1:
            errorMessage += "The filament name matches an existing name. Please choose a new one.\n";
            hasErrors = true;
            break;
        case 2:
            errorMessage += "Type not specified.\n";
            hasErrors = true;
            break;
        case 3:
            errorMessage += "Weight not specified.\n";
            hasErrors = true;
            break;
        case 4:
            errorMessage += "Length not specified.\n";
            hasErrors = true;
            break;
        case 5:
            errorMessage += "Length shorter than what has been used.\n";
            hasErrors = true;
            break;
        case 6:
            errorMessage += "Cost not specified.\n";
            hasErrors = true;
            break;
        }
    }
}