package classes;

import java.util.HashMap;
import java.time.LocalDate;

// class represents a single meal

public class Meal {
	private int userId;	// id of the user consuming this meal
	private String mealType; // breakfast, lunch, dinner, or snack
	private LocalDate date;
	private HashMap<String, Integer> items; 
	
	
	
	public Meal(int userId, String mealType, LocalDate date) {
		this.userId = userId;
		this.mealType = mealType;
		this.date = date;
		this.items = new HashMap<String, Integer>();
	}
	
	
	
	public void addItems(String itemName, int quantity) {
		if (this.items.containsKey(itemName)) {
			// if already exist, add quantity to it
			this.items.put(itemName, this.items.get(itemName) + quantity);
		} else {
		this.items.put(itemName, quantity);
		}
	}
	
	public HashMap<String, Integer> getItems() {
		return this.items;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public String getMealType() {
		return this.mealType;
	}
	
	public LocalDate getDate() {
		return this.date;
	}
	
	// this method help with food swapping
	public void deleteItem(String itemName) {
		this.items.remove(itemName);
	}
	
	// this method help with food swapping
	public void removeItem(String itemName, int quantity) {
		int currentQuantity = this.items.get(itemName);
		this.items.put(itemName, currentQuantity - quantity);
	}
}
