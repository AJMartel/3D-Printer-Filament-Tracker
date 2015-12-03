package com.FilamentTracker.Dialogs;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.FilamentTracker.Global;
import com.FilamentTracker.Main;
import com.toedter.calendar.JDateChooser;

/**
 * 
 * @author Andrew Comer
 *
 */
public class AddPrintDialog extends JFrame {
	
	private static final long 	serialVersionUID 		= 1L;
	
	private JTextField 			amountUsedTextField 	= new JTextField();
	
	private JDateChooser 		date 					= new JDateChooser();
	
	private JComboBox 			filamentUsedComboBox 	= new JComboBox();
	
	private JScrollPane 		descriptionScrollPane 	= new JScrollPane();
	
	private JTextPane 			descriptionTextPane 	= new JTextPane();
	
	private JLabel 				filamentUsedLabel 		= new JLabel("Filament Used");
	private JLabel 				dateLabel 				= new JLabel("Date");
	private JLabel 				descriptionLabel 		= new JLabel("Description");
	private JLabel 				amountUsedLabel 		= new JLabel("Amount Used");
	
	private JButton 			addPrintButton 			= new JButton("Add New Print");
	private JButton 			cancelButton 			= new JButton("Cancel");
	
	private String				errorMessage;
	private Boolean				hasErrors;

	public AddPrintDialog(int x, int y) {
		setTitle("Add Print Dialog");
		setBounds(x, y, 308, 301);
		getContentPane().setLayout(null);
		setResizable(false);
		
		//Filament Used
		filamentUsedLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		filamentUsedLabel.setBounds(10, 12, 84, 17);
		
		filamentUsedComboBox.setBounds(104, 10, 187, 20);
		for (int i = 0; i <= Global.index; i++) {
			filamentUsedComboBox.addItem(Main.filaments.get(i).getName());
		}
		
		//Print Date
		dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dateLabel.setBounds(10, 43, 29, 17);
		
		date.setBounds(104, 41, 187, 20);
		date.getDateEditor().setEnabled(false);
		
		//Print Description
		descriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		descriptionLabel.setBounds(10, 126, 68, 17);
		
		descriptionScrollPane.setBounds(104, 72, 187, 124);
		descriptionScrollPane.setViewportView(descriptionTextPane);
		
		//Amount of Filament Used
		amountUsedLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		amountUsedLabel.setBounds(10, 209, 83, 17);
		
		amountUsedTextField.setBounds(104, 207, 187, 20);
		amountUsedTextField.setColumns(10);
		
		//Add Button
		addPrintButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				hasErrors = false;
				errorMessage = "";
				
				if (Main.filaments.get(filamentUsedComboBox.getSelectedIndex()).getPRemaining() == 0)
					errorMessage(1);
				
				if (date.getDate() == null)
					errorMessage(2);
				
				if (descriptionTextPane.getText().trim().equals(""))
					errorMessage(3);
				
				if (!(amountUsedTextField.getText().trim().equals(""))) {
					if (Double.parseDouble(amountUsedTextField.getText().replaceAll("[^\\d.-]", "")) > Main.filaments.get(filamentUsedComboBox.getSelectedIndex()).getLRemaining())
						errorMessage(4);
				} else
					errorMessage(5);
				
				if (!hasErrors) {
					Main.addPrint(filamentUsedComboBox.getSelectedIndex(), date, descriptionTextPane.getText(), Double.parseDouble(amountUsedTextField.getText().replaceAll("[^\\d.-]", "")));
					dispose();
				} else
					JOptionPane.showMessageDialog(null, errorMessage);
			}
		});
		addPrintButton.setBounds(10, 238, 136, 23);
		
		//Cancel Button
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		cancelButton.setBounds(155, 238, 136, 23);
		
		//Add everything to content pane
		getContentPane().add(date);
		getContentPane().add(filamentUsedComboBox);
		getContentPane().add(descriptionScrollPane);
		getContentPane().add(amountUsedTextField);
		getContentPane().add(filamentUsedLabel);
		getContentPane().add(dateLabel);
		getContentPane().add(descriptionLabel);
		getContentPane().add(amountUsedLabel);
		getContentPane().add(addPrintButton);
		getContentPane().add(cancelButton);
	}
	
	private void errorMessage(int flag){
		switch (flag) {
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

