package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import classes.FoodItem;
import dbmanager.DatabaseManager;

//This class is used to access data in database tables related to food

public class FoodDAO {
	public void fetchFoodId(FoodItem foodItem) {
		String foodName = foodItem.getItemName();
		String sqlToSelectFoodName = "SELECT food_id FROM food_items WHERE food_name = ?";
		try (Connection conn = DatabaseManager.getInstance().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sqlToSelectFoodName)) {
				
			stmt.setString(1, foodName);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			foodItem.setFoodId(rs.getInt(1));
					
			} catch (Exception e) {
				e.printStackTrace();
			}	
	}
	
	public void fetchFoodGroup(FoodItem foodItem) {
		String foodName = foodItem.getItemName();
		String sqlToSelectFoodGroupName = "SELECT food_group_name FROM food_groups, food_items WHERE food_groups.food_group_id = food_items.food_group_id AND food_name = ?";
		try (Connection conn = DatabaseManager.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlToSelectFoodGroupName)) {
					
				stmt.setString(1, foodName);
				ResultSet rs = stmt.executeQuery();
				rs.next();
				foodItem.setFoodGroup(rs.getString(1));
						
				} catch (Exception e) {
					e.printStackTrace();
				}	
	
	}
}
