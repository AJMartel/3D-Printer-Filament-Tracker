package com.FilamentTracker;

/**
 * 
 * @author Andrew Comer
 *
 */
public class Print {
	private String date, description;
	private double amountUsed;
	
	public Print(String date, String description, Double amountUsed) {
		this.date = date;
		this.description = description;
		this.amountUsed = amountUsed;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmountUsed() {
		return amountUsed;
	}

	public void setAmountUsed(Double amountUsed) {
		this.amountUsed = amountUsed;
	}

}
