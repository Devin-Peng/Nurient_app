package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dbmanager.DatabaseManager;

public class FoodSwapDAO {
	/**
     * Returns all items in the same food group as the original.
     * Assumes your loader populated a table `food_items(food_id, food_group_id, food_name)`.
     */
    public List<String> findCandidates(String originalItem) {
        List<String> list = new ArrayList<>();
        String sql =
            "SELECT fi2.food_name                          \n" +
            "  FROM food_items fi1                         \n" +
            "  JOIN food_items fi2                         \n" +
            "    ON fi1.food_group_id = fi2.food_group_id  \n" +
            " WHERE fi1.food_name = ?                      \n" +
            "   AND fi2.food_name <> ?                     ";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, originalItem);
            st.setString(2, originalItem);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("food_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Fetches the amount of a given nutrient in `quantity` grams
     * of a food item, by looking up `nutrient_data(food_id, nutrient_id, nutrient_value)`
     * and `nutrients(nutrient_id, nutrient_name, unit)`.
     */
    public double getNutrientValue(String item, String nutrient, double quantity) {
        String sql =
            "SELECT nd.nutrient_value                      \n" +
            "  FROM nutrient_data nd                        \n" +
            "  JOIN nutrients      n ON nd.nutrient_id = n.nutrient_id  \n" +
            "  JOIN food_items     fi ON nd.food_id     = fi.food_id     \n" +
            " WHERE fi.food_name = ?                        \n" +
            "   AND n.nutrient_name = ?                     ";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, item);
            st.setString(2, nutrient);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    double per100g = rs.getDouble("nutrient_value");
                    // scale to the requested quantity
                    return per100g * (quantity / 100.0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
