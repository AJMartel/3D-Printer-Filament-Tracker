package com.FilamentTracker.Dialogs;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.FilamentTracker.Main;
import com.FilamentTracker.Print;
import com.toedter.calendar.JDateChooser;

/**
 * FILENAME:    AddPrintDialog.java<P>
 * DESCRIPTION: This class creates and opens the add/edit/delete print dialog.
 * 
 * @author Andrew Comer
 * @email AndrewJComer@yahoo.com
 */
public class AddPrintDialog extends JFrame
{
    private static final long       serialVersionUID      = 1L;
    private final JTextField        amountUsedTextField   = new JTextField();
    private final JDateChooser      date                  = new JDateChooser();
    private final JComboBox<String> filamentUsedComboBox  = new JComboBox<String>();
    private final JScrollPane       descriptionScrollPane = new JScrollPane();
    private final JTextPane         descriptionTextPane   = new JTextPane();
    private final JLabel            filamentUsedLabel     = new JLabel("Filament Used");
    private final JLabel            dateLabel             = new JLabel("Date");
    private final JLabel            descriptionLabel      = new JLabel("Description");
    private final JLabel            amountUsedLabel       = new JLabel("Amount Used");
    private final JButton           addPrintButton        = new JButton("Add New Print");
    private final JButton           deletePrintButton     = new JButton("Delete");
    private final JButton           cancelButton          = new JButton("Cancel");
    private final DateFormat        format                = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.ENGLISH);
    private String                  errorMessage;
    private Boolean                 hasErrors;

    /**
     * FUNCTION:    AddPrintDialog<P>
     * PURPOSE:     Constructor.
     * 
     * @param x X coordinate of the main frame
     * @param y Y coordinate of the main frame
     * @param forEdit Add new or edit existing filament
     * @param index Index of the filament to be edited
     */
    public AddPrintDialog(int x, int y, boolean forEdit, int index)
    {
        setTitle(forEdit == true ? "Edit Prints Dialog" : "Add Print Dialog");
        setBounds((int) ((921 / 2) - (308 / 2)) + x, (int) ((546 / 2) - (301 / 2)) + y, 308, 301);
        if (!forEdit)
        {
            setIconImage(System.getProperty("DEBUG") != null ? new ImageIcon("com/FilamentTracker/Dialogs/Print_Icon.png").getImage() : new ImageIcon(getClass().getResource("Print_Icon.png")).getImage());
        }
        else
        {
            setIconImage(System.getProperty("DEBUG") != null ? new ImageIcon("com/FilamentTracker/Dialogs/Edit_Print_Icon.png").getImage() : new ImageIcon(getClass().getResource("Edit_Print_Icon.png")).getImage());
        }
        setLayout(null);
        setResizable(false);

        // Filament Used
        filamentUsedLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentUsedLabel.setBounds(10, 12, 84, 17);

        filamentUsedComboBox.setBounds(104, 10, 187, 20);

        if (!forEdit)
        {
            for (int i = 0; i <= Main.index; i++)
            {
                filamentUsedComboBox.addItem(Main.filaments.get(i).getName());
            }
            if (index != -1)
            {
                filamentUsedComboBox.setSelectedIndex(index);
            }
        }
        else
        {
            filamentUsedLabel.setText("Print");
            for (Print print : Main.filaments.get(index).getPrint())
            {
                filamentUsedComboBox.addItem(print.getDescription());
            }

            filamentUsedComboBox.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        date.setDate(format.parse(Main.filaments.get(index).getPrint().get(filamentUsedComboBox.getSelectedIndex()).getDate()));
                    }
                    catch (ParseException e1)
                    {
                        e1.printStackTrace();
                    }
                    descriptionTextPane.setText(Main.filaments.get(index).getPrint().get(filamentUsedComboBox.getSelectedIndex()).getDescription());
                    amountUsedTextField.setText(Main.filaments.get(index).getPrint().get(filamentUsedComboBox.getSelectedIndex()).getAmountUsed() + "");
                }
            });
        }

        // Print Date
        dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        dateLabel.setBounds(10, 43, 29, 17);

        date.setBounds(104, 41, 187, 20);
        date.getDateEditor().setEnabled(false);

        // Print Description
        descriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        descriptionLabel.setBounds(10, 126, 68, 17);

        descriptionScrollPane.setBounds(104, 72, 187, 124);
        descriptionScrollPane.setViewportView(descriptionTextPane);

        // Amount of Filament Used
        amountUsedLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        amountUsedLabel.setBounds(10, 209, 83, 17);

        amountUsedTextField.setBounds(104, 207, 187, 20);
        amountUsedTextField.setColumns(10);

        // Add Button
        addPrintButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {

                hasErrors = false;
                errorMessage = "";

                if (!forEdit)
                {
                    if (Main.filaments.get(filamentUsedComboBox.getSelectedIndex()).getPRemaining() == 0.0)
                    {
                        errorMessage(1);
                    }
                }

                if (date.getDate() == null)
                {
                    errorMessage(2);
                }

                if (descriptionTextPane.getText().trim().equals(""))
                {
                    errorMessage(3);
                }

                if (!(amountUsedTextField.getText().trim().equals("")))
                {
                    if (!forEdit)
                    {
                        if (Double.parseDouble(amountUsedTextField.getText().replaceAll("[^\\d.-]", "")) > Main.filaments.get(filamentUsedComboBox.getSelectedIndex()).getLRemaining())
                        {
                            errorMessage(4);
                        }
                    }
                    else
                    {
                        if (Double.parseDouble(amountUsedTextField.getText().replaceAll("[^\\d.-]", "")) > (Main.filaments.get(index).getLRemaining() + Main.filaments.get(index).getPrint().get(filamentUsedComboBox.getSelectedIndex()).getAmountUsed()))
                        {
                            errorMessage(4);
                        }
                    }
                }
                else
                {
                    errorMessage(5);
                }

                if (!hasErrors)
                {
                    if (!forEdit)
                    {
                        Main.addPrint(filamentUsedComboBox.getSelectedIndex(), date, descriptionTextPane.getText(), Double.parseDouble(amountUsedTextField.getText().replaceAll("[^\\d.-]", "")));
                    }
                    else
                    {
                        Main.filaments.get(index).getPrint().get(filamentUsedComboBox.getSelectedIndex()).setDate(date.getDate().toString().substring(0, 3) + "," + date.getDate().toString().substring(3, 10) + "," + date.getDate().toString().substring(23, 28));
                        Main.filaments.get(index).getPrint().get(filamentUsedComboBox.getSelectedIndex()).setDescription(descriptionTextPane.getText());
                        Main.filaments.get(index).getPrint().get(filamentUsedComboBox.getSelectedIndex()).setAmountUsed(Double.parseDouble(amountUsedTextField.getText().replaceAll("[^\\d.-]", "")));
                    }
                    Main.saveNeeded = true;
                    Main.updateTable();
                    Main.updatePrintArea();
                    dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, errorMessage);
                }
            }
        });

        // Delete button
        deletePrintButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Main.filaments.get(index).getPrint().remove(filamentUsedComboBox.getSelectedIndex());
                Main.filamentTable.setRowSelectionInterval((int) Main.filamentTable.getValueAt(Main.filamentTable.getSelectedRow(), 0) - 1, (int) Main.filamentTable.getValueAt(Main.filamentTable.getSelectedRow(), 0) - 1);
                Main.updatePrintArea();
                Main.updateTable();
                Main.saveNeeded = true;
                dispose();
            }
        });

        // Cancel Button
        cancelButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                dispose();
            }
        });

        if (forEdit)
        {
            addPrintButton.setBounds(8, 238, 89, 23);
            cancelButton.setBounds(202, 238, 89, 23);
            deletePrintButton.setBounds(105, 238, 89, 23);
            addPrintButton.setText("Update");
        }
        else
        {
            addPrintButton.setBounds(10, 238, 136, 23);
            cancelButton.setBounds(155, 238, 136, 23);
        }

        // Add everything to content pane
        add(date);
        add(filamentUsedComboBox);
        add(descriptionScrollPane);
        add(amountUsedTextField);
        add(filamentUsedLabel);
        add(dateLabel);
        add(descriptionLabel);
        add(amountUsedLabel);
        add(addPrintButton);
        add(cancelButton);
        add(deletePrintButton);

        if (forEdit)
        {
            try
            {
                date.setDate(format.parse(Main.filaments.get(index).getPrint().get(0).getDate()));
            }
            catch (ParseException e1)
            {
                e1.printStackTrace();
            }
            descriptionTextPane.setText(Main.filaments.get(index).getPrint().get(0).getDescription());
            amountUsedTextField.setText(Main.filaments.get(index).getPrint().get(0).getAmountUsed() + "");
        }
    }

    /**
     * FUNCTION:    errorMessage<P>
     * PURPOSE:     Alert the user of any errors
     * 
     * @param flag Number for specific error message
     */
    private void errorMessage(int flag)
    {
        switch (flag)
        {
            case 1:
                errorMessage += "The filament you selected has no more plastic left.\n";
                hasErrors = true;
                break;
            case 2:
                errorMessage += "No date specified.\n";
                hasErrors = true;
                break;
            case 3:
                errorMessage += "Description not specified.\n";
                hasErrors = true;
                break;
            case 4:
                errorMessage += "Amount used is more that whats is left.\n";
                hasErrors = true;
                break;
            case 5:
                errorMessage += "Amount used not specified.\n";
                hasErrors = true;
                break;
        }
    }
}