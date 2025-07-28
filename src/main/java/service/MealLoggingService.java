package service;

import classes.Meal;
import dao.MealDAO;

public class MealLoggingService {
	private MealDAO mealDAO = new MealDAO();
	
	public void logMeal(Meal meal) {
		mealDAO.logMeal(meal);
	}

	public void logMeal_v2(Meal meal) {
		mealDAO.logMeal(meal);
	}
}
