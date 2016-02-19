package com.FilamentTracker.Dialogs;

import java.awt.CardLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

    /**
     * FUNCTION:    SettingsDialog 
     * PURPOSE:     Constructor.
     * 
     * @param x X coordinate of the main frame
     * @param y Y coordinate of the main frame
     */
    public SettingsDialog(int x, int y) {
        setTitle("Settings");
        setBounds((int) ((921 / 2) - (564 / 2)) + x, (int) ((546 / 2) - (563 / 2)) + y, 564, 563);
        setIconImage(System.getProperty("DEBUG") != null ? new ImageIcon("com/FilamentTracker/Dialogs/About_Icon.png").getImage() : new ImageIcon(getClass().getResource("About_Icon.png")).getImage());
        setResizable(false);
        getContentPane().setLayout(null);
        
        
        JPanel cards = new JPanel();
        JPanel card1 = new JPanel();
        JPanel card2 = new JPanel();
        
        //Card 1
        
        card1.setLayout(null);
        JLabel label = new JLabel("asd");
        label.setBounds(75, 76, 56, 36);
        card1.add(label);
        
        //Card 2
        
        card2.setLayout(null);
        JLabel label_1 = new JLabel("dsadad");
        label_1.setBounds(117, 5, 73, 42);
        card2.add(label_1);
        
        
        
        cards.setBounds(212, 0, 346, 430);
        getContentPane().add(cards);
        cards.setLayout(new CardLayout(0, 0));
        cards.add(card1, "1");
        cards.add(card2, "2");
        
        CardLayout cl = (CardLayout) cards.getLayout();
                
        //Settings Tree
        

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("ROOT");
        DefaultMutableTreeNode general = new DefaultMutableTreeNode("General");
        DefaultMutableTreeNode subGeneral = new DefaultMutableTreeNode("Inside General");
        DefaultMutableTreeNode reports = new DefaultMutableTreeNode("Reports");
        
        root.add(general);
        root.add(reports);
        general.add(subGeneral);
        
        JTree tree = new JTree(root);
        tree.setBounds(0, 0, 213, 430);
        tree.setRootVisible(false);
        
        getContentPane().add(tree);
        
        tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent arg0) {
                System.out.println(tree.getLastSelectedPathComponent());
                
                switch (tree.getLastSelectedPathComponent().toString()) {
                case "Inside General":
                    cl.show(cards, "1");
                    break;
                case "Reports":
                    cl.show(cards, "2");
                    break;
                default:
                    break;
                }
            }
        });
        //read line from config. strip all content in [] to get value. set labels/text areas based on value
    }
}
