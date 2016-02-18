package com.FilamentTracker;

/**
 * FILENAME:    Print.java 
 * DESCRIPTION: This class contains the Print object that holds all information about the prints of each filament.
 * 
 * @author Andrew Comer
 * @email AndrewJComer@yahoo.com
 */
public class Print {

    private String date;
    private String description;
    private double amountUsed;

    /**
     * FUNCTION:    Print 
     * PURPOSE:     Constructor.
     *
     * @param date The date the print occurred
     * @param description The description of the print
     * @param amountUsed The amount of filament used in the print
     */
    public Print(String date, String description, Double amountUsed) {
        this.date = date;
        this.description = description;
        this.amountUsed = amountUsed;
    }

    /*
     * Getters
     */
    public String getDate() { return date; }
    public String getDescription() { return description; }
    public double getAmountUsed() { return amountUsed; }

    /*
     * Setters
     */
    public void setDate(String date) { this.date = date; }
    public void setDescription(String description) { this.description = description; }
    public void setAmountUsed(Double amountUsed) { this.amountUsed = amountUsed; }
}