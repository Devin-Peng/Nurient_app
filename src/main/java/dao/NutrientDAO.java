package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import classes.FoodItem;
import classes.Meal;
import classes.MealDetails;
import classes.Nutrient;
import dbmanager.DatabaseManager;

//This class is used to access data in database tables related to nutrient 

public class NutrientDAO {
	// this method's function has been moved to GetMealNutrientDetails class
//		public MealDetails fetchMealDetails(Meal meal) {
//			MealDetails mealDetails = new MealDetails(meal); 
//			// iterate through meal.items, fetch nutrient name, nutrient unit and nutrient value
//			for (Map.Entry<String, Integer> entry : meal.getItems().entrySet()) {
//				String item = entry.getKey();
//				Integer quantity = entry.getValue();
//			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//			}
//			return mealDetails;
//		}
		
		public void fetchNutrientValue(Nutrient nutrient, FoodItem foodItem) {
			int nutrientId = nutrient.getNutrientId();
			int foodId = foodItem.getFoodId();
			String sqlToSelectNutrientValue = "SELECT nutrient_value FROM nutrient_data WHERE  food_id = ? AND nutrient_id = ?";
			try (Connection conn = DatabaseManager.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlToSelectNutrientValue)) {
						
					stmt.setInt(1, foodId);
					stmt.setInt(2, nutrientId);
					ResultSet rs = stmt.executeQuery();
					rs.next();
					nutrient.setQuantity(rs.getDouble(1));
					
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
			
		public void fetchNutrientUnit(Nutrient nutrient) {
			int nutrientId = nutrient.getNutrientId();
			String sqlToSelectNutrientValue = "SELECT unit FROM nutrients WHERE nutrient_id = ?";
			try (Connection conn = DatabaseManager.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlToSelectNutrientValue)) {
						
					stmt.setInt(1, nutrientId);
					ResultSet rs = stmt.executeQuery();
					rs.next();
					nutrient.setUnit(rs.getString(1));
							
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		public void fetchNutrientName(Nutrient nutrient) {
			int nutrientId = nutrient.getNutrientId();
			String sqlToSelectNutrientValue = "SELECT nutrient_name FROM nutrients WHERE nutrient_id = ?";
			try (Connection conn = DatabaseManager.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlToSelectNutrientValue)) {
						
					stmt.setInt(1, nutrientId);
					ResultSet rs = stmt.executeQuery();
					rs.next();
					nutrient.setNutrientName(rs.getString(1));
							
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// get nutrient_ids associate with a food_id
		public List<Integer> fetchNutrientIds(int foodId) {
			String sqlToSelectNutrientIds = "SELECT nutrient_id FROM nutrient_data WHERE food_id = ? AND nutrient_value != 0";
			List<Integer> list = new ArrayList<Integer>();
			try (Connection conn = DatabaseManager.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlToSelectNutrientIds)) {
							
				stmt.setInt(1, foodId);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					list.add(rs.getInt(1));
				}
								
			} catch (Exception e) {
					e.printStackTrace();
			}		
			return list;
		}
}
