package classes;

import java.util.ArrayList;
import java.util.List;

public class MealDetails {
	private Meal meal;
	private List<FoodItem> foodItems; // this field gets data from this.meal and database
	
	
	
	public MealDetails(Meal meal) {
		this.meal = meal;
		this.foodItems = new ArrayList<FoodItem>();
	}
	
	
	// shallow copy here to allow food swapping
	public List<FoodItem> getFoodItems() {
		return this.foodItems;
	}
	
	public void addFoodItem(FoodItem foodItem) {
		this.foodItems.add(foodItem);
	}
	
	public Meal getMeal() {
		return this.meal;
	}
	
	// this method help with food swapping
	public int getIndexOfItem(FoodItem foodItem) {
		return this.foodItems.indexOf(foodItem);
	}
	
	// this method help with food swapping
	/*
	 * !!!!!! may need to add exception later in case the FoodItem 
	 * looked for doesn't exist
	 */	
	public FoodItem getCertianFoodItem (String itemName) {
		for (FoodItem foodItem : this.foodItems) {
			if (foodItem.getItemName().equals(itemName)) {
				return foodItem;
			}
		}
		return null;
	}
	
	// this method help with food swapping, quantity to delete is inside foodItem
	/*
	 * !!!!!! may need to add exception later in case user deletes more
	 *  quantity of a foodItem than there are in this.meal and this.foodItems
	 */	
	public void removeItem(FoodItem foodItem) {
		FoodItem foodItemToBeDeleted = getCertianFoodItem(foodItem.getItemName());
			// more this FoodItem than needed to be deleted
		if (foodItem.getQuantity() < foodItemToBeDeleted.getQuantity()) {
			foodItemToBeDeleted.setQuantity(foodItemToBeDeleted.getQuantity() - foodItem.getQuantity());
			// update this.meal
			this.meal.removeItem(foodItem.getItemName(), foodItem.getQuantity());
		} else {
			// less this FoodItem than needed to be deleted
			this.foodItems.remove(getIndexOfItem(foodItem));
			this.meal.deleteItem(foodItem.getItemName());	// it also deletes item in this.meal.items
		}
		
	}
	
	// this method help with food swapping
	public void addNewItem(FoodItem foodItem) {
		addFoodItem(foodItem);
		// it also adds item in this.meal.items
		this.meal.addItems(foodItem.getItemName(), foodItem.getQuantity());
	}
}
