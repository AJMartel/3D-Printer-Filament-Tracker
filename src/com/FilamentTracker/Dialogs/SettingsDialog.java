package com.FilamentTracker.Dialogs;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.tree.DefaultMutableTreeNode;

import com.FilamentTracker.ConfigFile;
import com.sun.javafx.css.Stylesheet;
import com.sun.xml.internal.txw2.Document;
import javax.swing.SwingConstants;

/**
 * FILENAME:    SettingsDialog.java<P>
 * DESCRIPTION: This class creates and opens the settings dialog.
 * 
 * @author Andrew Comer
 * @email AndrewJComer@yahoo.com
 */
public class SettingsDialog extends JFrame
{
    private static final long       serialVersionUID       = 1L;
    private final JLabel            settingsHeaderLabel    = new JLabel("General");
    private final JLabel            minizeToTrayLabel      = new JLabel("Minize to tray on exit");
    private final JComboBox<String> minimizeToTrayComboBox = new JComboBox<String>();
    private final JCheckBox         promptOnExit           = new JCheckBox("Prompt on exit.");
    private final JLabel            autoSaveLabel          = new JLabel("Auto save interval (minutes)");
    private final JLabel            saveLocationLabel      = new JLabel("Save File Location");
    private final JTextField        saveLocationTextField  = new JTextField();
    private final JSpinner          autoSaveSpinner        = new JSpinner();
    private final JButton           applyButton            = new JButton("Apply");
    private final JButton           cancelButton           = new JButton("Cancel");
    private final JButton           saveLocationButton     = new JButton("...");
    private final JSeparator        separator              = new JSeparator();
    private final JSeparator        separator_1            = new JSeparator();

    private final ConfigFile        configFileInstance     = ConfigFile.getInstance();
    private final JPanel            cardsPanel             = new JPanel();
    private final JPanel            generalPanel           = new JPanel();
    private final JPanel            reportsPanel           = new JPanel();
    private final JPanel            HTMLColorsPanel        = new JPanel();
    private final JPanel            loggingPanel           = new JPanel();

    /**
     * FUNCTION:    SettingsDialog<P>
     * PURPOSE:     Constructor.
     * 
     * @param x X coordinate of the main frame
     * @param y Y coordinate of the main frame
     * @throws FileNotFoundException 
     */
    public SettingsDialog(int x, int y) throws FileNotFoundException
    {
        setTitle("Settings");
        setBounds((int) ((921 / 2) - (564 / 2)) + x, (int) ((546 / 2) - (503 / 2)) + y, 754, 503);
        setIconImage(System.getProperty("DEBUG") != null ? new ImageIcon("com/FilamentTracker/Dialogs/About_Icon.png").getImage() : new ImageIcon(getClass().getResource("About_Icon.png")).getImage());
        setResizable(false);
        getContentPane().setLayout(null);

        cardsPanel.setBounds(212, 39, 536, 391);
        cardsPanel.setLayout(new CardLayout(0, 0));
        CardLayout cardLayout = (CardLayout) cardsPanel.getLayout();

        //Set up main cards
        setUpGeneralCard();
        setUpReportsCard();
        setUpHTMLColorsCard();
        setUpLoggingCard();

        //Settings Tree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("ROOT");
        DefaultMutableTreeNode general = new DefaultMutableTreeNode("General");
        DefaultMutableTreeNode reports = new DefaultMutableTreeNode("Reports");
        DefaultMutableTreeNode HTMLColor = new DefaultMutableTreeNode("HTML Colors");
        DefaultMutableTreeNode logging = new DefaultMutableTreeNode("Logging");

        root.add(general);
        root.add(reports);
        reports.add(HTMLColor);
        root.add(logging);

        JTree tree = new JTree(root);
        tree.setBounds(0, 0, 213, 430);
        tree.setRootVisible(false);
        tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent arg0)
            {
                switch (tree.getLastSelectedPathComponent().toString())
                {
                    case "General":
                        cardLayout.show(cardsPanel, "General");
                        settingsHeaderLabel.setText("General");
                        setBounds(getX(), getY(), 754, 503);
                        break;
                    case "Reports":
                        cardLayout.show(cardsPanel, "Reports");
                        settingsHeaderLabel.setText("Reports");
                        setBounds(getX(), getY(), 754, 503);
                        break;
                    case "HTML Colors":
                        cardLayout.show(cardsPanel, "HTML Colors");
                        settingsHeaderLabel.setText("HTML Colors");
                        setBounds(getX(), getY(), 754, 800);
                        break;
                    case "Logging":
                        cardLayout.show(cardsPanel, "Logging");
                        settingsHeaderLabel.setText("Logging");
                        setBounds(getX(), getY(), 754, 503);
                        break;
                    default:
                        break;
                }
            }
        });

        settingsHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        settingsHeaderLabel.setBounds(223, 11, 89, 17);

        applyButton.setBounds(550, 441, 89, 23);
        applyButton.setEnabled(false);
        applyButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                // sets the config file instance variables
                configFileInstance.setMinimizeToTrayOnExit(minimizeToTrayComboBox.getSelectedIndex());
                configFileInstance.setPromptOnExit(promptOnExit.isSelected());
                configFileInstance.setAutoSaveInterval((Integer) autoSaveSpinner.getValue());
                configFileInstance.setSaveFileLocation(saveLocationTextField.getText());

                configFileInstance.saveConfigFile();
                dispose();
            }
        });

        cancelButton.setBounds(649, 441, 89, 23);
        cancelButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                dispose();
            }
        });

        separator.setBounds(212, 37, 536, 2);
        separator_1.setBounds(0, 431, 748, 8);

        getContentPane().add(tree);
        getContentPane().add(cardsPanel);
        getContentPane().add(settingsHeaderLabel);
        getContentPane().add(cancelButton);
        getContentPane().add(applyButton);
        getContentPane().add(separator);
        getContentPane().add(separator_1);

    }

    /**
     * FUNCTION:    setUpGeneralCard<P>
     * PURPOSE:     Sets up and places all the components on the general settings card
     */
    private void setUpGeneralCard()
    {
        generalPanel.setLayout(null);

        minizeToTrayLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        minizeToTrayLabel.setBounds(6, 9, 127, 17);

        minimizeToTrayComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Yes", "No" }));
        minimizeToTrayComboBox.setSelectedIndex(configFileInstance.getMinimizeToTrayOnExit());
        minimizeToTrayComboBox.setEnabled(!configFileInstance.getPromptOnExit());
        minimizeToTrayComboBox.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                applyButton.setEnabled(true);
            }
        });
        minimizeToTrayComboBox.setBounds(187, 9, 95, 20);

        promptOnExit.setFont(new Font("Tahoma", Font.PLAIN, 14));
        promptOnExit.setSelected(configFileInstance.getPromptOnExit());
        promptOnExit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                minimizeToTrayComboBox.setEnabled(!minimizeToTrayComboBox.isEnabled());
                applyButton.setEnabled(true);
            }
        });
        promptOnExit.setBounds(288, 7, 121, 25);

        autoSaveLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        autoSaveLabel.setBounds(6, 37, 171, 17);

        autoSaveSpinner.setBounds(187, 35, 95, 20);
        autoSaveSpinner.setValue(configFileInstance.getAutoSaveInterval());
        autoSaveSpinner.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                if ((Integer) autoSaveSpinner.getValue() <= 0)
                {
                    autoSaveSpinner.setValue(1);
                }

                if ((Integer) autoSaveSpinner.getValue() == configFileInstance.getAutoSaveInterval())
                {
                    applyButton.setEnabled(false);
                }
                else
                {
                    applyButton.setEnabled(true);
                }
            }
        });

        saveLocationLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        saveLocationLabel.setBounds(6, 65, 108, 17);

        saveLocationTextField.setBounds(187, 65, 284, 17);
        saveLocationTextField.setEditable(false);
        saveLocationTextField.setText(configFileInstance.getSaveFileLocation());

        saveLocationButton.setBounds(481, 62, 45, 23);
        saveLocationButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                JFileChooser saveLocationFileChooser = new JFileChooser();
                saveLocationFileChooser.setCurrentDirectory(new File(saveLocationTextField.getText()));
                saveLocationFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (saveLocationFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                {
                    saveLocationTextField.setText(saveLocationFileChooser.getSelectedFile().toString());
                    System.out.println(saveLocationFileChooser.getSelectedFile().toString());
                    applyButton.setEnabled(true);
                }
                else
                {
                    System.out.println("No Selection ");
                }
            }
        });

        generalPanel.add(minizeToTrayLabel);
        generalPanel.add(minimizeToTrayComboBox);
        generalPanel.add(promptOnExit);
        generalPanel.add(autoSaveLabel);
        generalPanel.add(autoSaveSpinner);
        generalPanel.add(saveLocationLabel);
        generalPanel.add(saveLocationTextField);
        generalPanel.add(saveLocationButton);
        cardsPanel.add(generalPanel, "General");
    }

    /**
     * FUNCTION:    setUpReportsCard<P>
     * PURPOSE:     Sets up and places all the components on the reports settings card
     */
    private void setUpReportsCard()
    {
        reportsPanel.setLayout(null);
        cardsPanel.add(reportsPanel, "Reports");
    }

    /**
     * FUNCTION:    setUpHTMLColorsCard<P>
     * PURPOSE:     Sets up and places all the components on the HTML colors settings card
     */
    private void setUpHTMLColorsCard()
    {
        HTMLColorsPanel.setLayout(null);
        HTMLEditorKit kit = new HTMLEditorKit();
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule("<body{font:1.0em normal Arial,sans-serif;color:#34495E;}");
        styleSheet.addRule("h1{text-align:center;text-transform:uppercase;letter-spacing:2px;font-size:1.5em;margin:20px 0;}");
        styleSheet.addRule("date{text-align:left;letter-spacing:1px;font-size:1em;margin:20px 0;}");
        styleSheet.addRule("table{border-collapse:collapse;width:95%;margin:auto;}");
        styleSheet.addRule(".blue{border:2px solid #433242;}");
        styleSheet.addRule(".bluethead{background:#433242;}");
        styleSheet.addRule(".purple{border:2px solid #9B59B6;}");
        styleSheet.addRule(".purplethead{background:#9B59B6;}");
        styleSheet.addRule("thead{color:white;}");
        styleSheet.addRule("th,td{text-align:center;padding:5px 0;}");
        styleSheet.addRule("tbody tr:nth-child(even){background:#ECF0F1;}");
        styleSheet.addRule("tbody td:nth-child(even){background:#ECF0F1;}");
        javax.swing.text.Document doc = kit.createDefaultDocument();
        cardsPanel.add(HTMLColorsPanel, "HTML Colors");
        //Create pane to show HTML sample output
        JEditorPane jEditorPane = new JEditorPane();
        jEditorPane.setLocation(-22, 0);
        jEditorPane.setEditable(false);
        jEditorPane.setEditorKit(kit);
        JScrollPane scrollpane = new JScrollPane(jEditorPane);
        scrollpane.setBounds(0, 527, 748, 245);
        getContentPane().add(scrollpane);
        jEditorPane.setDocument(doc);
        jEditorPane.setText("<!DOCTYPE html>\r\n<html>\r\n    <head>\r\n        <meta charset=\"UTF-8\">\r\n        <meta name=\"viewport\" content=\"initial-scale=1.0\">\r\n        <link rel=\"stylesheet\" href=\"reset.css\">\r\n        <link rel=\"stylesheet\" href=\"style.css\">\r\n    </head>\r\n    <date>05/17/2016 14:59:28</date>\r\n    <h1>Andrew.Comer's 3D Printer Filament Report</h1>\r\n    <table class=\"blue\">\r\n        <thead class=\"bluethead\">\r\n            <tr>\r\n                <th style=\"width: 100%\">Actual report will have more data</th>\r\n            </tr>\r\n        </thead>\r\n        <tr>\r\n            <th>Test filament here</th>\r\n        </tr>\r\n        <td colspan = 7>\r\n            <table class=\"purple\">\r\n                <thead class=\"purplethead\">\r\n                    <tr>\r\n                        <th style=\"width: 50%\">Print Date</th>\r\n                        <th style=\"width: 50%\">Description</th>\r\n                    </tr>\r\n                </thead>\r\n                <tbody>\r\n                    <tr>\r\n                        <td>Mon, Dec 14, 2015</td>\r\n                        <td>Test description here</td>\r\n                    </tr>\r\n                </tbody>\r\n            </table>\r\n        </td>\r\n    </table>\r\n</html>");

        JSeparator separator_2 = new JSeparator();
        separator_2.setBounds(0, 475, 748, 8);
        getContentPane().add(separator_2);

        JLabel lblExampleOutput = new JLabel("Example output");
        lblExampleOutput.setHorizontalAlignment(SwingConstants.CENTER);
        lblExampleOutput.setBounds(0, 494, 748, 14);
        getContentPane().add(lblExampleOutput);

        //Labels and such
        JLabel label = new JLabel("Minize to tray on exit");
        label.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label.setBounds(10, 11, 127, 17);

        HTMLColorsPanel.add(label);
    }

    /**
     * FUNCTION:    setUpLoggingCard<P>
     * PURPOSE:     Sets up and places all the components on the logging settings card
     */
    private void setUpLoggingCard()
    {
        loggingPanel.setLayout(null);
        cardsPanel.add(loggingPanel, "Logging");
    }
}
