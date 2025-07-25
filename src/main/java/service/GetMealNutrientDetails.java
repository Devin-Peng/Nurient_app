package service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import classes.FoodItem;
import classes.Meal;
import classes.MealDetails;
import classes.Nutrient;
import dao.FoodDAO;
import dao.MealDAO;
import dao.NutrientDAO;

// This class is used to fetch data from database to create and return MealDetails objects 
// !!!!!!!!! This class works quiet slowly
// consider static
public class GetMealNutrientDetails {
	private MealDAO mealDAO = new MealDAO();
	private FoodDAO foodDAO = new FoodDAO();
	private NutrientDAO nutrientDAO = new NutrientDAO();
	
	
	// This method return a FoodItem object that contain corresponding nutrient list
	public FoodItem fetchFoodItem(String item, int quantity) {
		FoodItem foodItem = new FoodItem(item, quantity);
		foodDAO.fetchFoodId(foodItem); // fetch food_id
		foodDAO.fetchFoodGroup(foodItem);
		// base on food_id, add nutrient to foodItem
		List<Integer> nutrientIds = nutrientDAO.fetchNutrientIds(foodItem.getFoodId());
		Nutrient nutrient = null;
		for (Integer nutrientId : nutrientIds) {
			nutrient = new Nutrient(nutrientId);
			nutrientDAO.fetchNutrientValue(nutrient, foodItem);
			nutrientDAO.fetchNutrientUnit(nutrient);
			nutrientDAO.fetchNutrientName(nutrient);
			foodItem.addToNutritionFacts(nutrient);
		}		
		return foodItem;
	}
	
	public MealDetails fetchMealDetails(int userId, LocalDate date, String mealType) {
		Meal meal = mealDAO.fetchMeal(userId, date, mealType);
		MealDetails mealDetails = new MealDetails(meal); 
		for (Map.Entry<String, Integer> entry : meal.getItems().entrySet()) {
			String item = entry.getKey();
			Integer quantity = entry.getValue();
			FoodItem foodItem = fetchFoodItem(item, quantity);
			mealDetails.addFoodItem(foodItem);
		}
		return mealDetails;
	}
	
	public MealDetails fetchMealDetails(Meal meal) {
		MealDetails mealDetails = new MealDetails(meal); 
		for (Map.Entry<String, Integer> entry : meal.getItems().entrySet()) {
			String item = entry.getKey();
			Integer quantity = entry.getValue();
			FoodItem foodItem = fetchFoodItem(item, quantity);
			mealDetails.addFoodItem(foodItem);
		}
		return mealDetails;
	}
}
