package dao;

import java.util.List;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import classes.Account;
import classes.Profile;
import dbmanager.DatabaseManager;

public class UserDAO {

    public void saveUser(Profile user) {
        String query = "INSERT INTO users (name, gender, date_of_birth, height_cm, weight_kg) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getGender());
            stmt.setDate(3, Date.valueOf(user.getDateOfBirth()));
            stmt.setDouble(4, user.getHeightCm());
            stmt.setDouble(5, user.getWeightKg());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Profile> loadProfilesFromDatabase() {
        List<Profile> profiles = new ArrayList<>();
        String query = "SELECT user_id, name, gender, date_of_birth, height_cm, weight_kg FROM users ORDER BY name";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                LocalDate dateOfBirth = resultSet.getDate("date_of_birth").toLocalDate();
                double heightCm = resultSet.getDouble("height_cm");
                double weightKg = resultSet.getDouble("weight_kg");

                Profile profile = new Profile(name, gender, dateOfBirth, heightCm, weightKg);
                profile.setId(id);
                profiles.add(profile);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging in real applications
        }
        return profiles;
    }

    public boolean validateLogin(String username, String password) {
        String query = "SELECT u.user_id FROM users u " +
                "INNER JOIN auth a ON u.auth_id = a.auth_id " +
                "WHERE a.username = ? AND a.password = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.next(); // Returns true if a record is found
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveUserWithAuth(Profile user, Account account) {
        String authQuery = "INSERT INTO auth (username, password) VALUES (?, ?)";
        String userQuery = "INSERT INTO users (name, gender, date_of_birth, height_cm, weight_kg, auth_id) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = DatabaseManager.getInstance().getConnection();
            conn.setAutoCommit(false);

            // First, insert into auth table
            int authId;
            try (PreparedStatement authStmt = conn.prepareStatement(authQuery, Statement.RETURN_GENERATED_KEYS)) {
                authStmt.setString(1, account.getUsername());
                authStmt.setString(2, account.getPassword());
                authStmt.executeUpdate();

                // Get the generated auth id
                try (ResultSet generatedKeys = authStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        authId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to get auth id");
                    }
                }
            }

            // Then, insert into users table with the auth_id foreign key
            try (PreparedStatement userStmt = conn.prepareStatement(userQuery)) {
                userStmt.setString(1, user.getName());
                userStmt.setString(2, user.getGender());
                userStmt.setDate(3, Date.valueOf(user.getDateOfBirth()));
                userStmt.setDouble(4, user.getHeightCm());
                userStmt.setDouble(5, user.getWeightKg());
                userStmt.setInt(6, authId); // Foreign key reference
                userStmt.executeUpdate();
            }

            conn.commit(); // Commit transaction

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Failed to save user and auth data", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset auto-commit
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean usernameExists(String username) {
        String query = "SELECT id FROM auth WHERE username = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProfile(int userId, String name, LocalDate dob, double height, double weight) {
        
        String query = "UPDATE users SET name = ?, date_of_birth = ?, height_cm = ?, weight_kg = ? WHERE user_id = ?";
        
        try (Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);){
            
            stmt.setString(1, name);
            stmt.setDate(2, dob != null ? java.sql.Date.valueOf(dob) : null);
            stmt.setDouble(3, height);
            stmt.setDouble(4, weight);
            stmt.setInt(5, userId);
            
            int rowsAffected = stmt.executeUpdate();
            
            stmt.close();
            conn.close();
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
