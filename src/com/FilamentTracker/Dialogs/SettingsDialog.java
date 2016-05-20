package com.FilamentTracker.Dialogs;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.tree.DefaultMutableTreeNode;

import com.FilamentTracker.ConfigFile;

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

    private final JPanel            cardsPanel             = new JPanel();
    private final JPanel            generalPanel           = new JPanel();
    private final JPanel            reportsPanel           = new JPanel();
    private final JPanel            HTMLColorsPanel        = new JPanel();
    private final JPanel            loggingPanel           = new JPanel();

    private final HTMLEditorKit     HTMLKit                = new HTMLEditorKit();
    private final StyleSheet        styleSheet             = HTMLKit.getStyleSheet();
    private final JEditorPane       sampleEditorPane       = new JEditorPane();
    private final JScrollPane       sampleScrollPane       = new JScrollPane(sampleEditorPane);
    private final Document          doc                    = HTMLKit.createDefaultDocument();

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
        DefaultMutableTreeNode root      = new DefaultMutableTreeNode("ROOT");
        DefaultMutableTreeNode general   = new DefaultMutableTreeNode("General");
        DefaultMutableTreeNode reports   = new DefaultMutableTreeNode("Reports");
        DefaultMutableTreeNode HTMLColor = new DefaultMutableTreeNode("HTML Colors");
        DefaultMutableTreeNode logging   = new DefaultMutableTreeNode("Logging");

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
                ConfigFile.getInstance().setMinimizeToTrayOnExit(minimizeToTrayComboBox.getSelectedIndex());
                ConfigFile.getInstance().setPromptOnExit(promptOnExit.isSelected());
                ConfigFile.getInstance().setAutoSaveInterval((Integer) autoSaveSpinner.getValue());
                ConfigFile.getInstance().setSaveFileLocation(saveLocationTextField.getText());

                ConfigFile.getInstance().saveConfigFile();
                applyButton.setEnabled(false);
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
        minimizeToTrayComboBox.setSelectedIndex(ConfigFile.getInstance().getMinimizeToTrayOnExit());
        minimizeToTrayComboBox.setEnabled(!ConfigFile.getInstance().getPromptOnExit());
        minimizeToTrayComboBox.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                applyButton.setEnabled(true);
            }
        });
        minimizeToTrayComboBox.setBounds(187, 9, 95, 20);

        promptOnExit.setFont(new Font("Tahoma", Font.PLAIN, 14));
        promptOnExit.setSelected(ConfigFile.getInstance().getPromptOnExit());
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
        autoSaveSpinner.setValue(ConfigFile.getInstance().getAutoSaveInterval());
        autoSaveSpinner.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                if ((Integer) autoSaveSpinner.getValue() <= 0)
                {
                    autoSaveSpinner.setValue(1);
                }

                if ((Integer) autoSaveSpinner.getValue() == ConfigFile.getInstance().getAutoSaveInterval())
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
        saveLocationTextField.setText(ConfigFile.getInstance().getSaveFileLocation());

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
        
        drawSample();

        JSeparator separator_2 = new JSeparator();
        separator_2.setBounds(0, 475, 748, 8);
        getContentPane().add(separator_2);

        JLabel sampleOutputLabel = new JLabel("Sample Output");
        sampleOutputLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sampleOutputLabel.setBounds(0, 494, 748, 14);
        getContentPane().add(sampleOutputLabel);

        /*
         * Timestamp
         */
        JLabel timestampTextAlignLabel = new JLabel("Timestamp Align");
        timestampTextAlignLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        timestampTextAlignLabel.setBounds(10, 11, 131, 17);
        
        JComboBox<String> timestampComboBox = new JComboBox<String>();
        timestampComboBox.setBounds(151, 11, 100, 17);
        timestampComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Left", "Center", "Right" }));
        timestampComboBox.setSelectedIndex(ConfigFile.getInstance().getTimestampTextAlign());
        timestampComboBox.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                switch (timestampComboBox.getSelectedIndex())
                {
                    case 0: //Left
                        ConfigFile.getInstance().setTimestampTextAlign(0);
                        break;
                    case 1: //Center
                        ConfigFile.getInstance().setTimestampTextAlign(1);
                        break;
                    case 2: //Right
                        ConfigFile.getInstance().setTimestampTextAlign(2);
                        break;
                    default:
                        break;
                }
                applyButton.setEnabled(true);
                drawSample();
            }
        });
        

        JLabel timestampTextSizeLabel = new JLabel("Timestamp Size");
        timestampTextSizeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        timestampTextSizeLabel.setBounds(10, 39, 131, 17);
        
        JSpinner timestampTextSizeSpinner = new JSpinner();
        timestampTextSizeSpinner.setBounds(151, 37, 100, 20);
        timestampTextSizeSpinner.setValue(ConfigFile.getInstance().getTimestampTextSize());
        timestampTextSizeSpinner.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                if ((Integer) timestampTextSizeSpinner.getValue() <= 0)
                {
                    timestampTextSizeSpinner.setValue(1);
                }
                ConfigFile.getInstance().setTimestampTextSize((Integer) timestampTextSizeSpinner.getValue());
                applyButton.setEnabled(true);
                drawSample();
            }
        });

        JLabel timestampColorLabel = new JLabel("Timestamp Color");
        timestampColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        timestampColorLabel.setBounds(261, 39, 103, 17);

        JPanel timestampColorPanel = new JPanel();
        timestampColorPanel.setBounds(374, 39, 100, 17);
        timestampColorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        timestampColorPanel.setBackground(new Color(Integer.parseInt(ConfigFile.getInstance().getTimestampColor().replace("#", ""), 16)));
        timestampColorPanel.addMouseListener(new MouseListener()
        {
            public void mouseReleased(MouseEvent e){}
            public void mousePressed(MouseEvent e){}
            public void mouseExited(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mouseClicked(MouseEvent e)
            {
                openColorChooser(timestampColorPanel, "timestamp");
            }
        });

        /*
         * Title Header
         */
        JLabel titleTextAlignLabel = new JLabel("Title Align");
        titleTextAlignLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        titleTextAlignLabel.setBounds(10, 65, 131, 17);
        
        JComboBox<String> titleComboBox = new JComboBox<String>();
        titleComboBox.setBounds(151, 65, 100, 17);
        titleComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Left", "Center", "Right" }));
        titleComboBox.setSelectedIndex(ConfigFile.getInstance().getTitleTextAlign());
        titleComboBox.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                switch (titleComboBox.getSelectedIndex())
                {
                    case 0: //Left
                        ConfigFile.getInstance().setTitleTextAlign(0);
                        break;
                    case 1: //Center
                        ConfigFile.getInstance().setTitleTextAlign(1);
                        break;
                    case 2: //Right
                        ConfigFile.getInstance().setTitleTextAlign(2);
                        break;
                    default:
                        break;
                }
                applyButton.setEnabled(true);
                drawSample();
            }
        });
        

        JLabel titleTextSizeLabel = new JLabel("Title Size");
        titleTextSizeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        titleTextSizeLabel.setBounds(10, 93, 131, 17);
        
        JSpinner titleTextSizeSpinner = new JSpinner();
        titleTextSizeSpinner.setBounds(151, 91, 100, 20);
        titleTextSizeSpinner.setValue(ConfigFile.getInstance().getTitleTextSize());
        titleTextSizeSpinner.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                if ((Integer) titleTextSizeSpinner.getValue() <= 0)
                {
                    titleTextSizeSpinner.setValue(1);
                }
                ConfigFile.getInstance().setTitleTextSize((Integer) titleTextSizeSpinner.getValue());
                applyButton.setEnabled(true);
                drawSample();
            }
        });

        JLabel titleColorLabel = new JLabel("Title Color");
        titleColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        titleColorLabel.setBounds(261, 93, 103, 17);

        JPanel titleColorPanel = new JPanel();
        titleColorPanel.setBounds(374, 93, 100, 17);
        titleColorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        titleColorPanel.setBackground(new Color(Integer.parseInt(ConfigFile.getInstance().getTitleColor().replace("#", ""), 16)));
        titleColorPanel.addMouseListener(new MouseListener()
        {
            public void mouseReleased(MouseEvent e){}
            public void mousePressed(MouseEvent e){}
            public void mouseExited(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mouseClicked(MouseEvent e)
            {
                openColorChooser(titleColorPanel, "title");
            }
        });

        /*
         * Filament Border
         */
        JLabel filamentBorderColorLabel = new JLabel("Filament Border Color");
        filamentBorderColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentBorderColorLabel.setBounds(10, 121, 131, 17);

        JPanel filamentBorderColorPanel = new JPanel();
        filamentBorderColorPanel.setBounds(151, 121, 100, 17);
        filamentBorderColorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filamentBorderColorPanel.setBackground(new Color(Integer.parseInt(ConfigFile.getInstance().getFilamentBoarderColor().replace("#", ""), 16)));
        filamentBorderColorPanel.addMouseListener(new MouseListener()
        {
            public void mouseReleased(MouseEvent e){}
            public void mousePressed(MouseEvent e){}
            public void mouseExited(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mouseClicked(MouseEvent e)
            {
                openColorChooser(filamentBorderColorPanel, "filamentBoarder");
            }
        });

        /*
         * Filament Fill
         */
        JLabel filamentFillColorLabel = new JLabel("Filament Fill Color");
        filamentFillColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentFillColorLabel.setBounds(261, 121, 103, 17);

        JPanel filamentFillColorPanel = new JPanel();
        filamentFillColorPanel.setBounds(374, 121, 100, 17);
        filamentFillColorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filamentFillColorPanel.setBackground(new Color(Integer.parseInt(ConfigFile.getInstance().getFilamentFillColor().replace("#", ""), 16)));
        filamentFillColorPanel.addMouseListener(new MouseListener()
        {
            public void mouseReleased(MouseEvent e){}
            public void mousePressed(MouseEvent e){}
            public void mouseExited(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mouseClicked(MouseEvent e)
            {
                openColorChooser(filamentFillColorPanel, "filamentFill");
            }
        });
        
        /*
         * Print Border
         */
        JLabel printBorderColorLabel = new JLabel("Print Border Color");
        printBorderColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        printBorderColorLabel.setBounds(10, 149, 131, 17);

        JPanel printBorderColorPanel = new JPanel();
        printBorderColorPanel.setBounds(151, 149, 100, 17);
        printBorderColorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        printBorderColorPanel.setBackground(new Color(Integer.parseInt(ConfigFile.getInstance().getPrintBoarderColor().replace("#", ""), 16)));
        printBorderColorPanel.addMouseListener(new MouseListener()
        {
            public void mouseReleased(MouseEvent e){}
            public void mousePressed(MouseEvent e){}
            public void mouseExited(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mouseClicked(MouseEvent e)
            {
                openColorChooser(printBorderColorPanel, "printBoarder");
            }
        });

        /*
         * Print Fill
         */
        JLabel printFillColorLabel = new JLabel("Print Fill Color");
        printFillColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        printFillColorLabel.setBounds(261, 149, 103, 17);

        JPanel printFillColorPanel = new JPanel();
        printFillColorPanel.setBounds(374, 149, 100, 17);
        printFillColorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        printFillColorPanel.setBackground(new Color(Integer.parseInt(ConfigFile.getInstance().getPrintFillColor().replace("#", ""), 16)));
        printFillColorPanel.addMouseListener(new MouseListener()
        {
            public void mouseReleased(MouseEvent e){}
            public void mousePressed(MouseEvent e){}
            public void mouseExited(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mouseClicked(MouseEvent e)
            {
                openColorChooser(printFillColorPanel, "printFill");
            }
        });


        /*
         * Table Header
         */
        JLabel tableHeaderColorLabel = new JLabel("Table header Color");
        tableHeaderColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tableHeaderColorLabel.setBounds(10, 177, 131, 17);

        JPanel tableHeaderColorPanel = new JPanel();
        tableHeaderColorPanel.setBounds(151, 177, 100, 17);
        tableHeaderColorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tableHeaderColorPanel.setBackground(new Color(Integer.parseInt(ConfigFile.getInstance().getTableHeaderColor().replace("#", ""), 16)));
        tableHeaderColorPanel.addMouseListener(new MouseListener()
        {
            public void mouseReleased(MouseEvent e){}
            public void mousePressed(MouseEvent e){}
            public void mouseExited(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mouseClicked(MouseEvent e)
            {
                openColorChooser(tableHeaderColorPanel, "tableHeader");
            }
        });
        
        /*
         * Table Header
         */
        JLabel tableDataColorLabel = new JLabel("Table data Color");
        tableDataColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tableDataColorLabel.setBounds(10, 205, 131, 17);

        JPanel tableDataColorPanel = new JPanel();
        tableDataColorPanel.setBounds(151, 205, 100, 17);
        tableDataColorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tableDataColorPanel.setBackground(new Color(Integer.parseInt(ConfigFile.getInstance().getTableDataColor().replace("#", ""), 16)));
        tableDataColorPanel.addMouseListener(new MouseListener()
        {
            public void mouseReleased(MouseEvent e){}
            public void mousePressed(MouseEvent e){}
            public void mouseExited(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mouseClicked(MouseEvent e)
            {
                openColorChooser(tableDataColorPanel, "tableData");
            }
        });

        HTMLColorsPanel.add(timestampTextAlignLabel);
        HTMLColorsPanel.add(timestampComboBox);
        HTMLColorsPanel.add(timestampTextSizeLabel);
        HTMLColorsPanel.add(timestampTextSizeSpinner);
        HTMLColorsPanel.add(timestampColorLabel);
        HTMLColorsPanel.add(timestampColorPanel);
        HTMLColorsPanel.add(titleTextAlignLabel);
        HTMLColorsPanel.add(titleComboBox);
        HTMLColorsPanel.add(titleTextSizeLabel);
        HTMLColorsPanel.add(titleTextSizeSpinner);
        HTMLColorsPanel.add(titleColorLabel);
        HTMLColorsPanel.add(titleColorPanel);
        HTMLColorsPanel.add(filamentBorderColorLabel);
        HTMLColorsPanel.add(filamentBorderColorPanel);
        HTMLColorsPanel.add(filamentFillColorPanel);
        HTMLColorsPanel.add(filamentFillColorLabel);
        HTMLColorsPanel.add(printBorderColorLabel);
        HTMLColorsPanel.add(printBorderColorPanel);
        HTMLColorsPanel.add(printFillColorLabel);
        HTMLColorsPanel.add(printFillColorPanel);
        HTMLColorsPanel.add(tableHeaderColorLabel);
        HTMLColorsPanel.add(tableHeaderColorPanel);
        HTMLColorsPanel.add(tableDataColorLabel);
        HTMLColorsPanel.add(tableDataColorPanel);
        cardsPanel.add(HTMLColorsPanel, "HTML Colors");
    }

    /**
     * FUNCTION:    openColorChooser<P>
     * PURPOSE:     Opens a color chooser and sets the selected color
     * 
     * @param panel
     * @param string
     */
    private void openColorChooser(JPanel panel, String string)
    {
        Color c = JColorChooser.showDialog(null, "Choose a Color", panel.getBackground());
        if (c != null)
        {
            panel.setBackground(c);
            switch (string)
            {
                case "filamentBoarder":
                    ConfigFile.getInstance().setFilamentBoarderColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
                    break;
                case "filamentFill":
                    ConfigFile.getInstance().setFilamentFillColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
                    break;
                case "printBoarder":
                    ConfigFile.getInstance().setPrintBoarderColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
                    break;
                case "printFill":
                    ConfigFile.getInstance().setPrintFillColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
                    break;
                case "timestamp":
                    ConfigFile.getInstance().setTimestampColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
                    break;
                case "title":
                    ConfigFile.getInstance().setTitleColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
                    break;
                case "tableHeader":
                    ConfigFile.getInstance().setTableHeaderColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
                    break;
                case "tableData":
                    ConfigFile.getInstance().setTableDataColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
                    break;
                default:
                    break;
            }
            applyButton.setEnabled(true);
            drawSample();
        }
    }

    /**
     * FUNCTION:    drawSample<P>
     * PURPOSE:     Draws the sample HTML file when the user changes it
     */
    private void drawSample()
    {
        //Create styles
        styleSheet.addRule("h1{text-align:" + ConfigFile.getInstance().getTitleTextAlignString() + ";text-transform:uppercase;letter-spacing:2px;font-size:" + ConfigFile.getInstance().getTitleTextSize() / 10.0  + "em;margin:20px 0;color:" + ConfigFile.getInstance().getTitleColor() + ";}");
        styleSheet.addRule(".date{text-align:" + ConfigFile.getInstance().getTimestampTextAlignString() + ";letter-spacing:1px;font-size:" + ConfigFile.getInstance().getTimestampTextSize() / 10.0  + "em;margin:0px 0;color:" + ConfigFile.getInstance().getTimestampColor() + ";}");
        styleSheet.addRule("table{border-collapse:collapse;width:95%;margin:auto;color:" + ConfigFile.getInstance().getTableHeaderColor() + ";}");
        styleSheet.addRule(".filament{border:2px solid " + ConfigFile.getInstance().getFilamentBoarderColor() + ";}");
        styleSheet.addRule(".filamentthead{background:" + ConfigFile.getInstance().getFilamentFillColor() + ";}");
        styleSheet.addRule(".print{border:2px solid " + ConfigFile.getInstance().getPrintBoarderColor() + ";}");
        styleSheet.addRule(".printthead{background:" + ConfigFile.getInstance().getPrintFillColor() + ";}");
        styleSheet.addRule(".test{color:" + ConfigFile.getInstance().getTableDataColor() + ";}");
//        styleSheet.addRule("thead{color:" + ConfigFile.getInstance().getTableHeaderColor() + ";}");
        styleSheet.addRule("th,td{text-align:center;padding:5px 0;}");
        styleSheet.addRule("tbody tr:nth-child(even){background:#ECF0F1;}");
        styleSheet.addRule("tbody td:nth-child(even){background:#ECF0F1;}");
        
        sampleScrollPane.setBounds(0, 527, 748, 245);
        
        sampleEditorPane.setLocation(-22, 0);
        sampleEditorPane.setEditable(false);
        sampleEditorPane.setEditorKit(HTMLKit);
        sampleEditorPane.setDocument(doc);
        sampleEditorPane.setText( "<!DOCTYPE html>\r\n"
                                + "<html>\r\n"
                                + "    <head>\r\n"
                                + "        <meta charset=\"UTF-8\">\r\n"
                                + "        <meta name=\"viewport\" content=\"initial-scale=1.0\">\r\n"
                                + "        <link rel=\"stylesheet\" href=\"reset.css\">\r\n"
                                + "        <link rel=\"stylesheet\" href=\"style.css\">\r\n"
                                + "    </head>\r\n"
                                + "    <h1 class=\"date\">05/17/2016 14:59:28</h1>\r\n"
                                + "    <h1>Andrew.Comer's 3D Printer Filament Report</h1>\r\n"
                                + "    <table class=\"filament\">\r\n"
                                + "        <thead class=\"filamentthead\">\r\n"
                                + "            <tr>\r\n"
                                + "                <th style=\"width: 100%\">Actual report will have more data</th>\r\n"
                                + "            </tr>\r\n"
                                + "        </thead>\r\n"
                                + "        <tr class=\"test\">\r\n"
                                + "            <th>Test filament here</th>\r\n"
                                + "        </tr>\r\n"
                                + "        <td colspan = 7>\r\n"
                                + "            <table class=\"print\">\r\n"
                                + "                <thead class=\"printthead\">\r\n"
                                + "                    <tr>\r\n"
                                + "                        <th style=\"width: 50%\">Print Date</th>\r\n"
                                + "                        <th style=\"width: 50%\">Description</th>\r\n"
                                + "                    </tr>\r\n"
                                + "                </thead>\r\n"
                                + "                <tbody>\r\n"
                                + "                    <tr class=\"test\">\r\n"
                                + "                        <td>Mon, Dec 14, 2015</td>\r\n"
                                + "                        <td>Test description here</td>\r\n"
                                + "                    </tr>\r\n"
                                + "                </tbody>\r\n"
                                + "            </table>\r\n"
                                + "        </td>\r\n"
                                + "    </table>\r\n"
                                + "</html>");

        sampleEditorPane.setCaretPosition(0);
        getContentPane().add(sampleScrollPane);
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
