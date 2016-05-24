package com.FilamentTracker.Dialogs;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
    private final HTMLEditorKit          HTMLKit                   = new HTMLEditorKit();
    private final Document               doc                       = HTMLKit.createDefaultDocument();
    private final JButton                applyButton               = new JButton("Apply");
    private final JButton                cancelButton              = new JButton("Cancel");
    private final JButton                saveLocationButton        = new JButton("...");
    private final JCheckBox              promptOnExit              = new JCheckBox("Prompt on exit.");
    private final JComboBox<String>      minimizeToTrayComboBox    = new JComboBox<String>();
    private final JComboBox<String>      timestampComboBox         = new JComboBox<String>();
    private final JComboBox<String>      titleComboBox             = new JComboBox<String>();
    private final JEditorPane            sampleEditorPane          = new JEditorPane();
    private final JLabel                 autoSaveLabel             = new JLabel("Auto save interval (minutes)");
    private final JLabel                 filamentBorderColorLabel  = new JLabel("Filament Border Color");
    private final JLabel                 filamentFillColorLabel    = new JLabel("Filament Fill Color");
    private final JLabel                 minizeToTrayLabel         = new JLabel("Minize to tray on exit");
    private final JLabel                 printBorderColorLabel     = new JLabel("Print Border Color");
    private final JLabel                 printFillColorLabel       = new JLabel("Print Fill Color");
    private final JLabel                 sampleOutputLabel         = new JLabel("Sample Output");
    private final JLabel                 saveLocationLabel         = new JLabel("Save File Location");
    private final JLabel                 settingsHeaderLabel       = new JLabel("General");
    private final JLabel                 tableDataColorLabel       = new JLabel("Table data Color");
    private final JLabel                 tableEvenColumnColorLabel = new JLabel("Table even column colors");
    private final JLabel                 tableHeaderColorLabel     = new JLabel("Table header Color");
    private final JLabel                 tableOddColumnColorLabel  = new JLabel("Table odd column colors");
    private final JLabel                 timestampColorLabel       = new JLabel("Timestamp Color");
    private final JLabel                 timestampTextAlignLabel   = new JLabel("Timestamp Align");
    private final JLabel                 timestampTextSizeLabel    = new JLabel("Timestamp Size");
    private final JLabel                 titleColorLabel           = new JLabel("Title Color");
    private final JLabel                 titleTextAlignLabel       = new JLabel("Title Align");
    private final JLabel                 titleTextSizeLabel        = new JLabel("Title Size");
    private final JPanel                 HTMLColorsPanel           = new JPanel();
    private final JPanel                 cardsPanel                = new JPanel();
    private final JPanel                 filamentBorderColorPanel  = new JPanel();
    private final JPanel                 filamentFillColorPanel    = new JPanel();
    private final JPanel                 generalPanel              = new JPanel();
    private final JPanel                 loggingPanel              = new JPanel();
    private final JPanel                 printBorderColorPanel     = new JPanel();
    private final JPanel                 printFillColorPanel       = new JPanel();
    private final JPanel                 reportsPanel              = new JPanel();
    private final JPanel                 tableDataColorPanel       = new JPanel();
    private final JPanel                 tableEvenColumnColorPanel = new JPanel();
    private final JPanel                 tableHeaderColorPanel     = new JPanel();
    private final JPanel                 tableOddColumnColorPanel  = new JPanel();
    private final JPanel                 timestampColorPanel       = new JPanel();
    private final JPanel                 titleColorPanel           = new JPanel();
    private final JScrollPane            sampleScrollPane          = new JScrollPane(sampleEditorPane);
    private final JSeparator             separator                 = new JSeparator();
    private final JSeparator             separator_1               = new JSeparator();
    private final JSeparator             separator_2               = new JSeparator();
    private final JSpinner               autoSaveSpinner           = new JSpinner();
    private final JSpinner               timestampTextSizeSpinner  = new JSpinner();
    private final JSpinner               titleTextSizeSpinner      = new JSpinner();
    private final JTextField             saveLocationTextField     = new JTextField();
    private final StyleSheet             styleSheet                = HTMLKit.getStyleSheet();

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
        setBounds((int) ((921 / 2) - (702 / 2)) + x, (int) ((546 / 2) - (402 / 2)) + y, 702, 402);
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        setIconImage(System.getProperty("DEBUG") != null ? new ImageIcon("com/FilamentTracker/Dialogs/About_Icon.png").getImage() : new ImageIcon(getClass().getResource("About_Icon.png")).getImage());
        setResizable(false);
        getContentPane().setLayout(null);
        
        addWindowListener(new WindowListener()
        {
            public void windowOpened(WindowEvent arg0){}
            public void windowClosed(WindowEvent arg0){}
            public void windowIconified(WindowEvent arg0){}
            public void windowActivated(WindowEvent arg0){}
            public void windowDeiconified(WindowEvent arg0){}
            public void windowDeactivated(WindowEvent arg0){}
            public void windowClosing(WindowEvent arg0)
            {
                
            }
        });

        cardsPanel.setBounds(212, 39, 487, 289);
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
        tree.setBounds(0, 0, 213, 328);
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
                        setBounds(getX(), getY(), frameWidth, frameHeight);
                        break;
                    case "Reports":
                        cardLayout.show(cardsPanel, "Reports");
                        settingsHeaderLabel.setText("Reports");
                        setBounds(getX(), getY(), frameWidth, frameHeight);
                        break;
                    case "HTML Colors":
                        cardLayout.show(cardsPanel, "HTML Colors");
                        settingsHeaderLabel.setText("HTML Colors");
                        setBounds(getX(), getY(), frameWidth, 684);
                        break;
                    case "Logging":
                        cardLayout.show(cardsPanel, "Logging");
                        settingsHeaderLabel.setText("Logging");
                        setBounds(getX(), getY(), frameWidth, frameHeight);
                        break;
                    default:
                        break;
                }
            }
        });

        settingsHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        settingsHeaderLabel.setBounds(223, 11, 89, 17);

        applyButton.setBounds(498, 339, 89, 23);
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
                ConfigFile.getInstance().setTimestampTextAlign(timestampComboBox.getSelectedIndex());
                ConfigFile.getInstance().setTimestampTextSize((Integer) timestampTextSizeSpinner.getValue()); 
                ConfigFile.getInstance().setTitleTextAlign(titleComboBox.getSelectedIndex());
                ConfigFile.getInstance().setTitleTextSize((Integer) titleTextSizeSpinner.getValue()); 
                ConfigFile.getInstance().setFilamentBoarderColor(RGBToHex(filamentBorderColorPanel));
                ConfigFile.getInstance().setFilamentFillColor(RGBToHex(filamentFillColorPanel));
                ConfigFile.getInstance().setPrintBoarderColor(RGBToHex(printBorderColorPanel));
                ConfigFile.getInstance().setPrintFillColor(RGBToHex(printFillColorPanel));
                ConfigFile.getInstance().setTimestampColor(RGBToHex(timestampColorPanel));
                ConfigFile.getInstance().setTitleColor(RGBToHex(titleColorPanel));
                ConfigFile.getInstance().setTableHeaderColor(RGBToHex(tableHeaderColorPanel));
                ConfigFile.getInstance().setTableDataColor(RGBToHex(tableDataColorPanel));
                ConfigFile.getInstance().setTableEvenColumnColor(RGBToHex(tableEvenColumnColorPanel));
                ConfigFile.getInstance().setTableOddColumnColor(RGBToHex(tableOddColumnColorPanel));

                ConfigFile.getInstance().saveConfigFile();
                applyButton.setEnabled(false);
            }
        });

        cancelButton.setBounds(597, 339, 89, 23);
        cancelButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                
                dispose();
            }
        });

        separator.setBounds(212, 37, 536, 2);
        separator_1.setBounds(0, 333, 696, 8);

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

        saveLocationTextField.setBounds(187, 65, 235, 17);
        saveLocationTextField.setEditable(false);
        saveLocationTextField.setText(ConfigFile.getInstance().getSaveFileLocation());

        saveLocationButton.setBounds(432, 62, 45, 23);
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

        separator_2.setBounds(0, 373, 699, 8);
        getContentPane().add(separator_2);

        sampleOutputLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        sampleOutputLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sampleOutputLabel.setBounds(0, 383, 699, 17);
        getContentPane().add(sampleOutputLabel);

        /*
         * Timestamp
         */
        timestampTextAlignLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        timestampTextAlignLabel.setBounds(10, 10, 155, 17);
        
        timestampComboBox.setBounds(175, 10, 76, 17);
        timestampComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Left", "Center", "Right" }));
        timestampComboBox.setSelectedIndex(ConfigFile.getInstance().getTimestampTextAlign());
        timestampComboBox.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
//                switch (timestampComboBox.getSelectedIndex())
//                {
//                    case 0: //Left
//                        ConfigFile.getInstance().setTimestampTextAlign(0);
//                        break;
//                    case 1: //Center
//                        ConfigFile.getInstance().setTimestampTextAlign(1);
//                        break;
//                    case 2: //Right
//                        ConfigFile.getInstance().setTimestampTextAlign(2);
//                        break;
//                    default:
//                        break;
//                }
                applyButton.setEnabled(true);
                drawSample();
            }
        });
        

        timestampTextSizeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        timestampTextSizeLabel.setBounds(10, 39, 155, 17);
        
        timestampTextSizeSpinner.setBounds(175, 37, 76, 20);
        timestampTextSizeSpinner.setValue(ConfigFile.getInstance().getTimestampTextSize());
        timestampTextSizeSpinner.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                if ((Integer) timestampTextSizeSpinner.getValue() <= 0)
                {
                    timestampTextSizeSpinner.setValue(1);
                }
//                ConfigFile.getInstance().setTimestampTextSize((Integer) timestampTextSizeSpinner.getValue());
                applyButton.setEnabled(true);
                drawSample();
            }
        });

        timestampColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        timestampColorLabel.setBounds(288, 39, 103, 17);

        timestampColorPanel.setBounds(401, 39, 76, 17);
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
        titleTextAlignLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        titleTextAlignLabel.setBounds(10, 67, 155, 17);
        
        titleComboBox.setBounds(175, 67, 76, 17);
        titleComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Left", "Center", "Right" }));
        titleComboBox.setSelectedIndex(ConfigFile.getInstance().getTitleTextAlign());
        titleComboBox.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
//                switch (titleComboBox.getSelectedIndex())
//                {
//                    case 0: //Left
//                        ConfigFile.getInstance().setTitleTextAlign(0);
//                        break;
//                    case 1: //Center
//                        ConfigFile.getInstance().setTitleTextAlign(1);
//                        break;
//                    case 2: //Right
//                        ConfigFile.getInstance().setTitleTextAlign(2);
//                        break;
//                    default:
//                        break;
//                }
                applyButton.setEnabled(true);
                drawSample();
            }
        });
        

        titleTextSizeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        titleTextSizeLabel.setBounds(10, 96, 155, 17);
        
        titleTextSizeSpinner.setBounds(175, 94, 76, 20);
        titleTextSizeSpinner.setValue(ConfigFile.getInstance().getTitleTextSize());
        titleTextSizeSpinner.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                if ((Integer) titleTextSizeSpinner.getValue() <= 0)
                {
                    titleTextSizeSpinner.setValue(1);
                }
//                ConfigFile.getInstance().setTitleTextSize((Integer) titleTextSizeSpinner.getValue());
                applyButton.setEnabled(true);
                drawSample();
            }
        });

        titleColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        titleColorLabel.setBounds(288, 96, 103, 17);

        titleColorPanel.setBounds(401, 96, 76, 17);
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
        filamentBorderColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentBorderColorLabel.setBounds(10, 124, 155, 17);

        filamentBorderColorPanel.setBounds(175, 124, 76, 17);
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
        filamentFillColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filamentFillColorLabel.setBounds(288, 124, 103, 17);

        filamentFillColorPanel.setBounds(401, 124, 76, 17);
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
        printBorderColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        printBorderColorLabel.setBounds(10, 151, 155, 17);

        printBorderColorPanel.setBounds(175, 151, 76, 17);
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
        printFillColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        printFillColorLabel.setBounds(288, 151, 103, 17);

        printFillColorPanel.setBounds(401, 151, 76, 17);
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
        tableHeaderColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tableHeaderColorLabel.setBounds(10, 178, 155, 17);

        tableHeaderColorPanel.setBounds(175, 178, 76, 17);
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
        tableDataColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tableDataColorLabel.setBounds(10, 205, 155, 17);

        tableDataColorPanel.setBounds(175, 205, 76, 17);
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
        
        /*
         * Table Even Column Colors
         */
        tableEvenColumnColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tableEvenColumnColorLabel.setBounds(10, 232, 155, 17);

        tableEvenColumnColorPanel.setBounds(175, 232, 76, 17);
        tableEvenColumnColorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tableEvenColumnColorPanel.setBackground(new Color(Integer.parseInt(ConfigFile.getInstance().getTableEvenColumnColor().replace("#", ""), 16)));
        tableEvenColumnColorPanel.addMouseListener(new MouseListener()
        {
            public void mouseReleased(MouseEvent e){}
            public void mousePressed(MouseEvent e){}
            public void mouseExited(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mouseClicked(MouseEvent e)
            {
                openColorChooser(tableEvenColumnColorPanel, "tableEvenColumn");
            }
        });
        
        /*
         * Table Odd Column Colors
         */
        tableOddColumnColorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tableOddColumnColorLabel.setBounds(10, 259, 155, 17);

        tableOddColumnColorPanel.setBounds(175, 259, 76, 17);
        tableOddColumnColorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tableOddColumnColorPanel.setBackground(new Color(Integer.parseInt(ConfigFile.getInstance().getTableOddColumnColor().replace("#", ""), 16)));
        tableOddColumnColorPanel.addMouseListener(new MouseListener()
        {
            public void mouseReleased(MouseEvent e){}
            public void mousePressed(MouseEvent e){}
            public void mouseExited(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mouseClicked(MouseEvent e)
            {
                openColorChooser(tableOddColumnColorPanel, "tableOddColumn");
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
        HTMLColorsPanel.add(tableEvenColumnColorLabel);
        HTMLColorsPanel.add(tableEvenColumnColorPanel);
        HTMLColorsPanel.add(tableOddColumnColorLabel);
        HTMLColorsPanel.add(tableOddColumnColorPanel);
        cardsPanel.add(HTMLColorsPanel, "HTML Colors");
        
        drawSample();
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
//            switch (string)
//            {
//                case "filamentBoarder":
//                    ConfigFile.getInstance().setFilamentBoarderColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
//                    break;
//                case "filamentFill":
//                    ConfigFile.getInstance().setFilamentFillColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
//                    break;
//                case "printBoarder":
//                    ConfigFile.getInstance().setPrintBoarderColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
//                    break;
//                case "printFill":
//                    ConfigFile.getInstance().setPrintFillColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
//                    break;
//                case "timestamp":
//                    ConfigFile.getInstance().setTimestampColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
//                    break;
//                case "title":
//                    ConfigFile.getInstance().setTitleColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
//                    break;
//                case "tableHeader":
//                    ConfigFile.getInstance().setTableHeaderColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
//                    break;
//                case "tableData":
//                    ConfigFile.getInstance().setTableDataColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
//                    break;
//                case "tableEvenColumn":
//                    ConfigFile.getInstance().setTableEvenColumnColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
//                    break;
//                case "tableOddColumn":
//                    ConfigFile.getInstance().setTableOddColumnColor(String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue()));
//                    break;
//                default:
//                    break;
//            }
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
        styleSheet.addRule("h1{"
                         + "text-align:" + ConfigFile.getInstance().getTextAlignString(titleComboBox.getSelectedIndex()) + ";"
                         + "text-transform:uppercase;"
                         + "letter-spacing:2px;"
                         + "font-size:" + ((Integer) titleTextSizeSpinner.getValue()) / 10.0  + "em;"
                         + "margin:20px 0;"
                         + "color:" + RGBToHex(titleColorPanel) + ";"
                         + "}");
        styleSheet.addRule(".date{"
                         + "text-align:" + ConfigFile.getInstance().getTextAlignString(timestampComboBox.getSelectedIndex()) + ";"
                         + "letter-spacing:1px;"
                         + "font-size:" + ((Integer) timestampTextSizeSpinner.getValue()) / 10.0  + "em;"
                         + "margin:0px 0;"
                         + "color:" + RGBToHex(timestampColorPanel) + ";"
                         + "}");
        styleSheet.addRule("table{"
                         + "border-collapse:collapse;"
                         + "width:95%;"
                         + "margin:auto;"
                         + "color:" + RGBToHex(tableHeaderColorPanel) + ";"
                         + "}");
        styleSheet.addRule(".filament{"
                         + "border:2px solid " + RGBToHex(filamentBorderColorPanel) + ";"
                         + "}");
        styleSheet.addRule(".filamentthead{"
                         + "background:" + RGBToHex(filamentFillColorPanel) + ";"
                         + "}");
        styleSheet.addRule(".print{"
                         + "border:2px solid " + RGBToHex(printBorderColorPanel) + ";"
                         + "}");
        styleSheet.addRule(".printthead{"
                         + "background:" + RGBToHex(printFillColorPanel) + ";"
                         + "}");
        styleSheet.addRule(".test{"
                         + "color:" + RGBToHex(tableDataColorPanel) + ";"
                         + "}");
//        styleSheet.addRule("thead{color:" + ConfigFile.getInstance().getTableHeaderColor() + ";}");
        styleSheet.addRule("th,td{"
                         + "text-align:center;"
                         + "padding:5px 0;"
                         + "}");
        styleSheet.addRule(".test2{"
                         + "background:" + RGBToHex(tableEvenColumnColorPanel) + ";"
                         + "}");
        styleSheet.addRule(".test3{"
                         + "background:" + RGBToHex(tableOddColumnColorPanel) + ";"
                         + "}");
        
        sampleScrollPane.setBounds(0, 411, 699, 245);
        
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
                                + "                <th style=\"width: 12.6%\">Filament Name</th>\r\n"
                                + "                <th style=\"width: 12.6%\">Filament Type</th>\r\n"
                                + "                <th style=\"width: 12.6%\">Filament Weight</th>\r\n"
                                + "                <th style=\"width: 16.6%\">Filament Length</th>\r\n"
                                + "                <th style=\"width: 16.6%\">Filament Length Remaining</th>\r\n"
                                + "                <th style=\"width: 17.6%\">Filament Percent Remaining</th>\r\n"
                                + "                <th style=\"width: 11%\">Filament Cost</th>\r\n"
                                + "            </tr>\r\n"
                                + "        </thead>\r\n"
                                + "        <tr class=\"test\">\r\n"
                                + "            <th>Hatchbox Green</th>\r\n"
                                + "            <th>PLA (1.75mm)</th>\r\n"
                                + "            <th>1.0kg</th>\r\n"
                                + "            <th>330000.00mm</th>\r\n"
                                + "            <th>273110.90mm</th>\r\n"
                                + "            <th>82.76%</th>\r\n"
                                + "            <th>$22.00</th>\r\n"
                                + "        </tr>\r\n"
                                + "        <td  class=\"test2\" colspan = 7>\r\n"
                                + "            <table class=\"print\">\r\n"
                                + "                <thead class=\"printthead\">\r\n"
                                + "                    <tr>\r\n"
                                + "                        <th style=\"width: 20%\">Print Date</th>\r\n"
                                + "                        <th style=\"width: 20%\">Amount Used</th>\r\n"
                                + "                        <th style=\"width: 10%\">% Used</th>\r\n"
                                + "                        <th style=\"width: 10%\">Cost</th>\r\n"
                                + "                        <th style=\"width: 40%\">Description</th>\r\n"
                                + "                    </tr>\r\n"
                                + "                </thead>\r\n"
                                + "                <tbody>\r\n"
                                + "                    <tr class=\"test\">\r\n"
                                + "                        <td class=\"test2\">Mon, Dec 14, 2015</td>\r\n"
                                + "                        <td class=\"test3\">19098.10mm</td>\r\n"
                                + "                        <td class=\"test2\">5.79%</td>\r\n"
                                + "                        <td class=\"test3\">$1.27</td>\r\n"
                                + "                        <td class=\"test2\">This is a print description area</td>\r\n"
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
     * @param panel
     * @return
     */
    private String RGBToHex(JPanel panel)
    {
        return String.format("#%02X%02X%02X", panel.getBackground().getRed(), panel.getBackground().getGreen(), panel.getBackground().getBlue());
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
