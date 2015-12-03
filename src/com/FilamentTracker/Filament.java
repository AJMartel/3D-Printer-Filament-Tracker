package com.FilamentTracker;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * 
 * @author Andrew Comer
 *
 */
public class Filament {
	private String name, type, weight;
	private int index;
	private Double length, lRemaining, pRemaining;
	private ArrayList<Print> print = new ArrayList<Print>();
	

	public Filament(int index, String name, String type, String weight, Double length) {
		this.index = index;
		this.name = name;
		this.type = type;
		this.weight = weight;
		this.length = length;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public void setIndex(int index){
		this.index = index;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType(){
		return this.type;
	}

	public void setType(String type){
		this.type= type;
	}
	
	public String getWeight(){
		return this.weight;
	}
	
	public void setWeight(String weight){
		this.weight = weight;
	}
	
	public Double getLength(){
		return this.length;
	}
	
	public void setLength(Double length){
		this.length = length;
	}
	
	public Double getLRemaining(){
		return this.lRemaining;
	}
	
	public void setLRemaining(Double lRemaining){
		this.lRemaining = lRemaining;
	}
	
	public Double getPRemaining(){
		return this.pRemaining;
	}
	
	public void setPRemaining(Double pRemaining){
		this.pRemaining = pRemaining;
	}
	
	public ArrayList<Print> getPrint(){
		return this.print;
	}
	
	public void addPrint(String date, String description, Double used){
		print.add(new Print(date, description, used));
	}
	
	public boolean isEmpty(){
		if (Global.index == -1)
			return true;
		else
			return false;
	}
	
	public String getPrints(){
		String output = "";
		Iterator<Print> printIterator = print.iterator();
		while (printIterator.hasNext()) {
			Print print = printIterator.next();
			output += String.format("%-19s%-13s%s", print.getDate(), Main.numberFormat.format(print.getAmountUsed()) + "mm", print.getDescription() + "\n");
		}
		return output;
	}
}
