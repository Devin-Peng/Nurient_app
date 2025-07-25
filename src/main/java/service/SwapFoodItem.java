package service;

import classes.FoodItem;
import classes.MealDetails;

public class SwapFoodItem {
	// may alter to return boolean later
		// !!!!!!!!!!quantity to be remove is in the FoodItem object
		// check model.MealDetails class to know swapping mechanism
		public void swap(MealDetails mealDetails, FoodItem itemToBeReplaced, FoodItem newItem) {
			mealDetails.removeItem(itemToBeReplaced);
			mealDetails.addNewItem(newItem);
		}
}
