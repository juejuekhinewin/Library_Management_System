package controller;

import model.User;
import view.UserProfileView;

public class UserProfileController {
    private final UserProfileView view;

    public UserProfileController(UserProfileView view) {
        this.view = view;
    }

    public void showProfile(User user) {
        if (user != null) 
        {
            view.setProfileData(
            		user.getUsername(), 
            		user.getEmail(), 
            		user.getRole()
            		);
            view.setVisible(true);
        } else 
        {
           
        }
    }
}