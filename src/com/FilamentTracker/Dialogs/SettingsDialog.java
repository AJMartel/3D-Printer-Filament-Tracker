package com.FilamentTracker.Dialogs;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * FILENAME:    SettingsDialog.java 
 * DESCRIPTION: This class creates and opens the settings dialog.
 * 
 * @author Andrew Comer
 * @email AndrewJComer@yahoo.com
 */
public class SettingsDialog extends JFrame {

    private static final long serialVersionUID = 1L;
    JLabel settingsHeaderLabel = new JLabel("General");
    JLabel minizeToTrayLabel = new JLabel("Minize to tray on exit");
    JComboBox minimizeToTrayComboBox = new JComboBox();
    JLabel autoSaveLabel = new JLabel("Auto save interval (minutes)");
    JLabel saveLocationLabel = new JLabel("Save File Location");
    JTextField saveLocationTextField = new JTextField();
    JSpinner autoSaveSpinner = new JSpinner();
    JButton applyButton = new JButton("Apply");
    JButton cancelButton = new JButton("Cancel");
    JButton saveLocationButton = new JButton("...");
    JSeparator separator = new JSeparator();
    JSeparator separator_1 = new JSeparator();

    /**
     * FUNCTION:    SettingsDialog 
     * PURPOSE:     Constructor.
     * 
     * @param x X coordinate of the main frame
     * @param y Y coordinate of the main frame
     */
    public SettingsDialog(int x, int y) {
        setTitle("Settings");
        setBounds((int) ((921 / 2) - (564 / 2)) + x, (int) ((546 / 2) - (503 / 2)) + y, 754, 503);
        setIconImage(System.getProperty("DEBUG") != null ? new ImageIcon("com/FilamentTracker/Dialogs/About_Icon.png").getImage() : new ImageIcon(getClass().getResource("About_Icon.png")).getImage());
        setResizable(false);
        getContentPane().setLayout(null);
        
        
        JPanel cardsPanel = new JPanel();
        JPanel generalPanel = new JPanel();
        JPanel reportsPanel = new JPanel();
        JPanel loggingPanel = new JPanel();
        
        cardsPanel.setBounds(212, 39, 469, 391);
        cardsPanel.setLayout(new CardLayout(0, 0));
        
        //General Card
        
        generalPanel.setLayout(null);
        
        minizeToTrayLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        minizeToTrayLabel.setBounds(6, 9, 127, 17);
        
        minimizeToTrayComboBox.setModel(new DefaultComboBoxModel(new String[] {"Yes", "No", "Ask"}));
        minimizeToTrayComboBox.setBounds(187, 9, 95, 20);
        
        autoSaveLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        autoSaveLabel.setBounds(6, 37, 171, 17);
        
        autoSaveSpinner.setBounds(187, 35, 95, 20);
        
        saveLocationLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        saveLocationLabel.setBounds(6, 65, 108, 17);
        
        saveLocationTextField.setBounds(187, 65, 171, 17);
        saveLocationTextField.setEditable(false);
        //temp - will be set to whats in the config file
        saveLocationTextField.setText("C:\\Users\\andrew.comer\\Documents\\workspace\\3D Printer Filament Tracker");
        //temp
        
        saveLocationButton.setBounds(368, 62, 45, 23);
        saveLocationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser saveLocationFileChooser = new JFileChooser();
                saveLocationFileChooser.setCurrentDirectory(new File(saveLocationTextField.getText()));
                saveLocationFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (saveLocationFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    saveLocationTextField.setText(saveLocationFileChooser.getSelectedFile().toString());
                    System.out.println(saveLocationFileChooser.getSelectedFile().toString());
                  } else {
                    System.out.println("No Selection ");
                  }
            }
        });
        
        generalPanel.add(minizeToTrayLabel);
        generalPanel.add(minimizeToTrayComboBox);
        generalPanel.add(autoSaveLabel);
        generalPanel.add(autoSaveSpinner);
        generalPanel.add(saveLocationLabel);
        generalPanel.add(saveLocationTextField);
        generalPanel.add(saveLocationButton);
        cardsPanel.add(generalPanel, "General");
        
        //Reports Cards

        reportsPanel.setLayout(null);
        loggingPanel.setLayout(null);
        
        
        cardsPanel.add(reportsPanel, "Reports");
        cardsPanel.add(loggingPanel, "Logging");
        
        getContentPane().add(cardsPanel);
        
        CardLayout cl = (CardLayout) cardsPanel.getLayout();
        
        //Settings Tree

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("ROOT");
        DefaultMutableTreeNode general = new DefaultMutableTreeNode("General");
        DefaultMutableTreeNode reports = new DefaultMutableTreeNode("Reports");
        DefaultMutableTreeNode logging = new DefaultMutableTreeNode("Logging");
        
        root.add(general);
        root.add(reports);
        root.add(logging);
        
        JTree tree = new JTree(root);
        tree.setBounds(0, 0, 213, 430);
        tree.setRootVisible(false);
        
        
        settingsHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        settingsHeaderLabel.setBounds(223, 11, 89, 17);
        
        applyButton.setBounds(360, 441, 89, 23);
        applyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //save changes to config file
                //call read to changes the variables
                dispose();
            }
        });
        
        cancelButton.setBounds(459, 441, 89, 23);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //call read file as if the dialog was just opening
                dispose();
            }
        });
        
        
        separator.setBounds(212, 37, 346, 2);
        
        separator_1.setBounds(0, 431, 558, 8);
        
        getContentPane().add(tree);
        getContentPane().add(settingsHeaderLabel);
        getContentPane().add(cancelButton);
        getContentPane().add(applyButton);
        getContentPane().add(separator);
        getContentPane().add(separator_1);
        
        tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent arg0) {                
                switch (tree.getLastSelectedPathComponent().toString()) {
                case "General":
                    cl.show(cardsPanel, "General");
                    settingsHeaderLabel.setText("General");
                    break;
                case "Reports":
                    cl.show(cardsPanel, "Reports");
                    settingsHeaderLabel.setText("Reports");
                    break;
                case "Logging":
                    cl.show(cardsPanel, "Logging");
                    settingsHeaderLabel.setText("Logging");
                    break;
                default:
                    break;
                }
            }
        });
    }
}
