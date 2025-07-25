package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import classes.Meal;
import dbmanager.DatabaseManager;

// may be changed to utility class later, access database table meals
// !!!!!!!!!!!!!can try to refactor this to use TEMPLETE METHOD later
public class MealDAO {
	
	// load Meal data into database table meals
	// public void logMeal(Meal meal) {
	// 	String sqlToInsert = "INSERT INTO meals (user_id, meal_type, date, ingredient, quantity) VALUES (?, ?, ?, ?, ?)";
	// 	try (Connection conn = DatabaseManager.getInstance().getConnection();
	// 		 PreparedStatement stmt = conn.prepareStatement(sqlToInsert)) {
			
	// 		stmt.setInt(1, meal.getUserId());
	// 		stmt.setString(2, meal.getMealType());
	// 		stmt.setDate(3, Date.valueOf(meal.getDate()));
	// 		for (Map.Entry<String, Integer> entry : meal.getItems().entrySet()) {
	// 			String ingredient = entry.getKey();
	// 			Integer quantity = entry.getValue();
	// 			stmt.setString(4, ingredient);
	// 			stmt.setInt(5, quantity);
	// 			stmt.executeUpdate();
	// 		}
			
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}
	// }
	
	public Meal fetchMeal(int id, LocalDate date, String mealType) {
		String sqlToSelectItems = "SELECT ingredient, quantity FROM meals WHERE user_id = ? AND meal_type = ? AND date = ?";
		Meal meal = new Meal(id, mealType, date);
		try (Connection conn = DatabaseManager.getInstance().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sqlToSelectItems)) {
				
			stmt.setInt(1, id);
			stmt.setString(2, mealType);
			stmt.setDate(3, Date.valueOf(date));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				meal.addItems(rs.getString(1), rs.getInt(2));
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return meal;
	}

	    public List<Integer> getAllUserIds() {
        List<Integer> userIds = new ArrayList<>();
        String sql = "SELECT DISTINCT user_id FROM meal_table";
        try (
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next()) {
                userIds.add(rs.getInt("user_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userIds;
    }

    public List<String> getDatesForUser(int userId) {
        List<String> dates = new ArrayList<>();
        String sql = "SELECT DISTINCT date FROM meal_table WHERE user_id = ? ORDER BY date DESC";
        try (
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dates.add(rs.getString("date"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dates;
    }

    public List<String> getMealTypesForUserDate(int userId, String date) {
        List<String> mealTypes = new ArrayList<>();
        String sql = "SELECT meal_type FROM meal_table WHERE user_id = ? AND date = ?";
        try (
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, userId);
            stmt.setString(2, date);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    mealTypes.add(rs.getString("meal_type"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mealTypes;
    }

    public String getFoodForMeal(int userId, String date, String mealType) {
        String sql = "SELECT ingredient FROM meal_table WHERE user_id = ? AND date = ? AND meal_type = ?";
        try (
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, userId);
            stmt.setString(2, date);
            stmt.setString(3, mealType);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getString("ingredient");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getQuantityForMeal(int userId, String date, String mealType) {
        String sql = "SELECT quantity FROM meal_table WHERE user_id = ? AND date = ? AND meal_type = ?";
        try (
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, userId);
            stmt.setString(2, date);
            stmt.setString(3, mealType);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getString("quantity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // log meal to meal_table 
    public boolean logMeal(Meal meal) {
        String sqlCheck = "SELECT COUNT(*) FROM meal_table WHERE user_id = ? AND meal_type = ? AND date = ?";
        String sqlInsert = "INSERT INTO meal_table (user_id, meal_type, date, ingredient, quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(sqlCheck)) {

            checkStmt.setInt(1, meal.getUserId());
            checkStmt.setString(2, meal.getMealType());
            checkStmt.setDate(3, java.sql.Date.valueOf(meal.getDate()));

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // duplicate
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(sqlInsert)) {
                insertStmt.setInt(1, meal.getUserId());
                insertStmt.setString(2, meal.getMealType());
                insertStmt.setDate(3, java.sql.Date.valueOf(meal.getDate()));
                insertStmt.setString(4, meal.getIngredient());
                insertStmt.setString(5, meal.getQuantity());
                insertStmt.executeUpdate();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    // this helps with getting meal_id for logging swap meal
    public int getMealId(int userId, String date, String mealType) {
        String sql = "SELECT meal_id FROM meal_table WHERE user_id = ? AND date = ? AND meal_type = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, date);
            stmt.setString(3, mealType);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("meal_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // public List<Meal> getRecentMeals(int userId, int limit) {
    //     List<Meal> meals = new ArrayList<>();
        
    //     // SQL query to get recent meals 
    //     String query = "SELECT id, meal_name, category, calories, date_logged " +
    //                 "FROM meals " +
    //                 "WHERE user_id = ? " +
    //                 "ORDER BY date_logged DESC " +
    //                 "LIMIT ?";
        
    //     try (Connection conn = DatabaseManager.getInstance().getConnection();
    //             PreparedStatement statement = conn.prepareStatement(query); {
    //         stmt.setInt(1, userId);
    //         stmt.setInt(2, limit);
            
    //         ResultSet rs = stmt.executeQuery();
            
    //         while (rs.next()) {
    //             Meal meal = new Meal(
    //                 rs.getInt("id"),
    //                 rs.getString("meal_name"),
    //                 rs.getString("category"),
    //                 rs.getInt("calories"),
    //                 rs.getTimestamp("date_logged")
    //             );
    //             meals.add(meal);
    //         }
            
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         throw new RuntimeException("Error retrieving recent meals", e);
    //     }
        
    //     return meals;
    // }

}
