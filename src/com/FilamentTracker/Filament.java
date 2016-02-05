package com.FilamentTracker;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * FILENAME: 	Filament.java 
 * DESCRIPTION: This class contains the Filament object that holds all information about the filaments.
 * 
 * @author Andrew Comer
 */
public class Filament {

	private String 				name;
	private String 				type;
	private String 				weight;
	private String 				cost;
	private int 				index;
	private double 				length;
	private double 				lRemaining;
	private double 				pRemaining;
	private ArrayList<Print> 	print = new ArrayList<Print>();

	/**
	 * FUNCTION: 	Filament 
	 * PURPOSE: 	Constructor.
	 * 
	 * @param index The index of the new filament
	 * @param name The name of the new filament
	 * @param type The type of the new filament
	 * @param weight The weight of the new filament
	 * @param length The length of the new filament
	 * @param cost The cost of the new filament
	 */
	public Filament(int index, String name, String type, String weight, Double length, String cost) {
		this.index 	= index;
		this.name 	= name;
		this.type 	= type;
		this.weight = weight;
		this.length = length;
		this.cost 	= cost;
	}

	/**
	 * FUNCTION: 	Filament 
	 * PURPOSE: 	Constructor.
	 * 
	 * @param index The index of the new filament
	 * @param name The name of the new filament
	 * @param type The type of the new filament
	 * @param weight The weight of the new filament
	 * @param length The length of the new filament
	 */
	public Filament(int index, String name, String type, String weight, Double length) {
		this.index 	= index;
		this.name 	= name;
		this.type 	= type;
		this.weight = weight;
		this.length = length;
		this.cost 	= "$0.00";
	}

	/*
	 * Getters
	 */
	public int getIndex() { return this.index; }
	public String getName() { return this.name; }
	public String getType() { return this.type; }
	public String getWeight() { return this.weight; }
	public Double getLength() { return this.length; }
	public String getCost() { return this.cost; }
	public Double getLRemaining() { return this.lRemaining; }
	public Double getPRemaining() { return this.pRemaining; }
	public ArrayList<Print> getPrint() { return this.print; }

	/*
	 * Setters
	 */
	public void setIndex(int index) { this.index = index; }
	public void setName(String name) { this.name = name; }
	public void setType(String type) { this.type = type; }
	public void setWeight(String weight) { this.weight = weight; }
	public void setLength(Double length) { this.length = length; }
	public void setCost(String cost) { this.cost = cost; }
	public void setLRemaining(Double lRemaining) { this.lRemaining = lRemaining; }
	public void setPRemaining(Double pRemaining) { this.pRemaining = pRemaining; }

	/**
	 * FUNCTION: 	addPrint 
	 * PURPOSE: 	Adds a new print to the filament.
	 * 
	 * @param date The date of the print
	 * @param description The description of the print
	 * @param used The amount of filament used
	 */
	public void addPrint(String date, String description, double used) {
		print.add(new Print(date, description, used));
	}

	/**
	 * FUNCTION: 	getPrints 
	 * PURPOSE: 	Returns a string with print information to be shown to the user.
	 * 
	 * @return String A string of all the prints
	 */
	public String getPrints() {
		String output = "";
		Iterator<Print> printIterator = print.iterator();
		while (printIterator.hasNext()) {
			Print print = printIterator.next();
			output += String.format("%-19s%-13s%-8s%-7s%s", print.getDate(), Main.numberFormat.format(print.getAmountUsed()) + "mm", Main.percentFormat.format(print.getAmountUsed() / this.getLength()), Main.costFormat.format((print.getAmountUsed() / this.getLength()) * Double.parseDouble(this.getCost().replaceAll("[^\\d.-]", ""))), print.getDescription() + "\n");
		}
		return output;
	}
}