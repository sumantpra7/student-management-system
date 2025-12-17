package com.sms.service;

import com.sms.dao.UserDAO;
import com.sms.model.User;

public class AuthService {
    private final UserDAO userDAO;
    private static User currentUser;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public boolean login(String email, String password) {
        User user = userDAO.authenticate(email, password);
        if (user != null) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isAdmin() {
        return currentUser != null && currentUser.getRole() == User.Role.ADMIN;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
