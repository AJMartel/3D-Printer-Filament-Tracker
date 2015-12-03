/*
 * ***STUFF TO DO****
 * remove print/filament - using popup menu.
 * about dialog
 * more menu items
 * program icon
 * 
 * Not urgent
 * grey out combo box filaments that have no filament left
 * fix table sort to sort by numeric values opposed to the string
 * sort prints by date
 * add cost field when creating a new filament
 * 		show cost of each print on table
 * add some sort of stats feature that shows the total number of filaments, prints, cost, ...
 * 		print at the bottom of the report
 * 		maybe add to info file
 * 
 * DONE *** error checking on the dialog boxes
 * DONE *** make it so the length remaining is determined by subtracting all the prints from the length. will do each table update.rather then storing the % left
 * DONE *** Export stuff
 * DONE *** title over the print test area show which filaments selected since the row color messes with the selection color 
 * DOME *** Donation - https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=CKF9ND26CTW34&lc=US&item_name=3D%20Printer%20Filament%20Tracker&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted
 */

package com.FilamentTracker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.URI;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.FilamentTracker.Dialogs.AboutDialog;
import com.FilamentTracker.Dialogs.AddFilamentDialog;
import com.FilamentTracker.Dialogs.AddPrintDialog;
import com.toedter.calendar.JDateChooser;


/**
 * Main class for the program.
 * 
 * @author Andrew Comer
 *
 */
public class Main extends JFrame {
	
	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final Main frame = new Main();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static final long			serialVersionUID	= 1L;
	
	//Menu Bar
	final JMenuBar 						mainMenuBar 		= new JMenuBar();
	
	//Menu
	final JMenu 						fileMenuBar 		= new JMenu("File");
	final JMenu 						exportMenuBar 		= new JMenu("Export");
	final JMenu 						editMenuBar 		= new JMenu("Edit");
	final JMenu 						helpMenuBar 		= new JMenu("Help");
	
	final JPopupMenu					popupMenu			= new JPopupMenu();
	
	//Menu Item
	final JMenuItem 					saveMenuItem 		= new JMenuItem("Save");
	final JMenuItem 					exportHTMLMenuItem 	= new JMenuItem("HTML File");
	final JMenuItem 					exportTextMenuItem 	= new JMenuItem("Text File");
	final JMenuItem 					exitMenuItem 		= new JMenuItem("Exit");
	final JMenuItem 					addFilamentMenuItem = new JMenuItem("Add New Filament");
	final JMenuItem 					addPrintMenuItem 	= new JMenuItem("Add New Print");
	final JMenuItem 					aboutMenuItem 		= new JMenuItem("About");
	final JMenuItem 					editPopupMenuItem 	= new JMenuItem("Edit");
	final JMenuItem 					deletePopupMenuItem = new JMenuItem("Delete");
	final JMenuItem 					printsPopupMenuItem = new JMenuItem("Edit Prints");

	final JSeparator 					separator1 			= new JSeparator();
	final JSeparator 					separator2 			= new JSeparator();

	private static JLabel 				printInfoLabel 		= new JLabel("");
	private final JLabel 				donationLabel 		= new JLabel("Donations");
	
	//Scroll Panes
	final JScrollPane 					tableScrollPane 	= new JScrollPane();
	final JScrollPane 					printScrollPane 	= new JScrollPane();
	
	//Table
	final static JTable 				filamentTable 		= new JTable();
	static DefaultTableModel 			tableModel;
	
	//Text Areas
	final JTextArea 					printHeaderTextArea = new JTextArea();
	final static JTextArea 				printsTextArea 		= new JTextArea();
	
	//Other
	public static ArrayList<Filament> 	filaments 			= new ArrayList<Filament>();
	public static NumberFormat			percentFormat 		= NumberFormat.getPercentInstance();
	public static NumberFormat 			numberFormat		= new DecimalFormat("#0.00"); 


	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public Main() throws IOException {
		setTitle("3D Printer Filament Tracker");
		setIconImage(new ImageIcon(getClass().getResource("icon.jpg")).getImage());
		setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int)((screenSize.getWidth() / 2) - (921 / 2)), (int)((screenSize.getHeight() / 2) - (546 / 2)), 921, 546);
		getContentPane().setLayout(null);
		
		addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowActivated(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowClosing(WindowEvent arg0) {
				closingSave();
			}
		});
		
		//Menu Bar
		mainMenuBar.setBounds(0, 0, 915, 21);
		mainMenuBar.add(fileMenuBar);
		fileMenuBar.add(saveMenuItem);
		fileMenuBar.add(exportMenuBar);
		exportMenuBar.add(exportHTMLMenuItem);
		exportMenuBar.add(exportTextMenuItem);
		fileMenuBar.add(exitMenuItem);
		mainMenuBar.add(editMenuBar);
		editMenuBar.add(addFilamentMenuItem);
		editMenuBar.add(addPrintMenuItem);
		mainMenuBar.add(helpMenuBar);
		helpMenuBar.add(aboutMenuItem);

		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				FileIO.save();
			}
		});
		
		exportHTMLMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Export.exportToHTML();
			}
		});
		
		exportTextMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Export.exportToText();
			}
		});

		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				closingSave();
			}
		});

		addFilamentMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				final AddFilamentDialog addFilamentDialog = new AddFilamentDialog(getX(), getY());
				addFilamentDialog.setVisible(true);
			}
		});

		addPrintMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				final AddPrintDialog addPrintDialog = new AddPrintDialog(getX(), getY());
				addPrintDialog.setVisible(true);
			}
		});

		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				final AboutDialog AboutDialog = new AboutDialog(getX(), getY());
				AboutDialog.setVisible(true);
			}
		});

		//Filament Table
		filamentTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Index", "Filament Name", "Filament Type", "Filament Weight", "Filament Length", "Length Remaining", "% Remaining" }) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		
		tableModel = (DefaultTableModel) filamentTable.getModel();
		filamentTable.setAutoCreateRowSorter(true);
		tableScrollPane.setBounds(10, 32, 895, 199);
		tableScrollPane.setViewportView(filamentTable);
		
		//Set widths of each column in the table
		filamentTable.getColumnModel().getColumn(0).setPreferredWidth(40);	//Index
		filamentTable.getColumnModel().getColumn(1).setPreferredWidth(145);	//Name
		filamentTable.getColumnModel().getColumn(2).setPreferredWidth(90);	//Type
		filamentTable.getColumnModel().getColumn(3).setPreferredWidth(100);	//Weight
		filamentTable.getColumnModel().getColumn(4).setPreferredWidth(100);	//Length
		filamentTable.getColumnModel().getColumn(5).setPreferredWidth(110);	//Length Remaining
		filamentTable.getColumnModel().getColumn(6).setPreferredWidth(85);	//Percent Remaining

		filamentTable.addMouseListener(new MouseListener() {
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {
				filamentTable.setRowSelectionInterval(filamentTable.rowAtPoint(arg0.getPoint()), filamentTable.rowAtPoint(arg0.getPoint()));
				updatePrintArea();				
			}
		});

		//Popup Menu
		popupMenu.add(editPopupMenuItem);
		popupMenu.add(deletePopupMenuItem);
		popupMenu.addSeparator();
		popupMenu.add(printsPopupMenuItem);
		filamentTable.setComponentPopupMenu(popupMenu);
		
		editPopupMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Edit " + filaments.get(filamentTable.getSelectedRow()).getName());		
			}
		});
		
		deletePopupMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Delete " + filaments.get(filamentTable.getSelectedRow()).getName());
			}
		});
		
		printsPopupMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		//Print Information
		printScrollPane.setBounds(10, 283, 895, 199);
		printScrollPane.setViewportView(printsTextArea);
		printScrollPane.setColumnHeaderView(printHeaderTextArea);
		printsTextArea.setEditable(false);
		printsTextArea.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		printHeaderTextArea.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		printHeaderTextArea.setText(String.format("%-19s%-13s%s", "Date", "Amount Used", "Description"));
		printHeaderTextArea.setEditable(false);
		printHeaderTextArea.setBackground(Color.CYAN);
		

		separator1.setBounds(0, 242, 915, 2);
		separator2.setBounds(-10, 493, 915, 2);
		
		printInfoLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		printInfoLabel.setBounds(10, 253, 400, 19);
		
		donationLabel.setForeground(Color.BLUE);
		donationLabel.setBounds(845, 493, 60, 14);
		donationLabel.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				try {
					Desktop.getDesktop().browse(URI.create("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=CKF9ND26CTW34&lc=US&item_name=3D%20Printer%20Filament%20Tracker&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		percentFormat.setMinimumFractionDigits(2);
		
		//Add everything to content pane
		getContentPane().add(mainMenuBar);
		getContentPane().add(tableScrollPane);
		getContentPane().add(separator1);
		getContentPane().add(printInfoLabel);
		getContentPane().add(printScrollPane);
		getContentPane().add(separator2);
		getContentPane().add(donationLabel);

		//Create filament objects from file and create table from objects
		FileIO.initializeObjects();
		updateTable();
	}
	
	/**
	 * Generates the table form the filaments object.
	 * Only function to modify the table.
	 */
	public static void updateTable(){
		if (Global.tableMade){
			for (int i = tableModel.getRowCount() - 1; i >= 0 ; i--)
				tableModel.removeRow(i);
		}
		Iterator<Filament> filamentIterator = filaments.iterator();
			while (filamentIterator.hasNext()){
				Filament filament = filamentIterator.next();
				setRemainingFilament(filament);
				tableModel.addRow(new Object[] {filament.getIndex() + 1, filament.getName(), filament.getType(), filament.getWeight(), numberFormat.format(filament.getLength()) + "mm", numberFormat.format(filament.getLRemaining()) + "mm", percentFormat.format(filament.getPRemaining())});
				Global.tableMade = true;
			}
		updateTableColors(filamentTable);
	}
	
	/**
	 * Determines the length and percent remaining for all filament objects.
	 * 
	 * @param filament Filament Object.
	 */
	private static void setRemainingFilament(Filament filament) {
		Double totalAmountUsed = 0.0;
		filament.setLRemaining(filament.getLength());
		Iterator<Print> printIterator = filament.getPrint().iterator();
		while (printIterator.hasNext()) {
			Print print = printIterator.next();
			totalAmountUsed += print.getAmountUsed();
		}		
		filament.setLRemaining(filament.getLength() - totalAmountUsed);
		filament.setPRemaining(filament.getLRemaining() / filament.getLength());
	}

	/**
	 * Creates a new filament object and adds it to the table.
	 * 
	 * @param name Name of the filament.
	 * @param type Type of the filament.
	 * @param weight Weight of the filament.
	 * @param length Length of the filament.
	 */
	public static void addFilament(String name, String type, String weight, Double length) {
		filaments.add(Global.index += 1, new Filament(Global.index, name, type, weight, length));
		Global.saveNeeded = true;
		updateTable();
	}
	
	/**
	 * Creates a new print for a selected filament. The print is added to the selected filament object.
	 * 
	 * @param filamentIndex Index of the filament used.
	 * @param date Date the print was started.
	 * @param description Short description of the print.
	 * @param amountUsed Amount of filament used during print.
	 */
	public static void addPrint(int filamentIndex, JDateChooser date, String description, double amountUsed) {
		filaments.get(filamentIndex).addPrint(date.getDate().toString().substring(0, 3) + "," + date.getDate().toString().substring(3, 10) + "," + date.getDate().toString().substring(23, 28), description, amountUsed);
		if (filamentTable.getSelectedRow() != -1){
			if (filamentIndex == Integer.parseInt(filamentTable.getValueAt(filamentTable.getSelectedRow(), 0).toString()) - 1)
				updatePrintArea();
		}
		Global.saveNeeded = true;
		updateTable();
	}
	
	/**
	 * Updates the print information area with prints done by the selected filament. 
	 */
	public static void updatePrintArea(){
		printInfoLabel.setText("Prints for " + filaments.get(Integer.parseInt(filamentTable.getValueAt(filamentTable.getSelectedRow(), 0).toString()) - 1).getName() + " Filament.");
		printsTextArea.setText(filaments.get(Integer.parseInt(filamentTable.getValueAt(filamentTable.getSelectedRow(), 0).toString()) - 1).getPrints());
		printsTextArea.setCaretPosition(0);
	}
	
	/**
	 * Updates the each row color to indicate the filament level.
	 * 
	 * WIll probably need to be modified to fix the sort issues
	 * 
	 * @param table Filament table.
	 */
    private static void updateTableColors(final JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
			private static final long serialVersionUID = 1L;
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);                
        		double percentLeft = Double.parseDouble(filamentTable.getValueAt(row, 6).toString().replaceAll("[^\\d.-]", ""));
				if (percentLeft > 50.0) {
					setBackground(Color.GREEN);
				} else if (percentLeft > 20.0) {
					setBackground(Color.YELLOW);
				} else if (percentLeft > 0.0) {
					setBackground(Color.RED);
				} else
					setBackground(Color.GRAY);
				return this;
			} 
        });
    }
    
    /**
     * Prompts the user to save the file when exiting the program if necessary.
     */
    private void closingSave(){
		if (Global.saveNeeded) {
			Object[] options = { "Yes", "No", "Cancel" };
			switch (JOptionPane.showOptionDialog(null, "Would you like to save?", "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0])) {
			case 0:
				FileIO.save();
				System.exit(0);
				break;
			case 1:
				System.exit(0);
				break;
			case 2:
				setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				break;
			}
		} else
			System.exit(0);
    }
}