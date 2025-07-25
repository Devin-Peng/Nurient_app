package service;

import classes.Account;
import classes.Profile;
import dao.UserDAO;

public class ProfileService {
	private UserDAO userDAO = new UserDAO();

    public void createProfile(Profile user) {
        userDAO.saveUser(user);
    }

    public void createProfileWithAuth(Profile profile, Account account) {
        // Check if username already exists
        if (userDAO.usernameExists(account.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        userDAO.saveUserWithAuth(profile, account);
    }
}	
