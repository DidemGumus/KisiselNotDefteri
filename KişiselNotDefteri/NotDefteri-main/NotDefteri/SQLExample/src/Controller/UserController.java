package Controller;

import Model.UserModel;

public class UserController {
    private UserModel userModel;

    public UserController() {
        this.userModel = new UserModel();
    }

    public boolean register(String username, String password, String email, String userType) {
        return userModel.registerUser(username, password, email, userType);
    }

    public int login(String username, String password) {
        return userModel.loginUser(username, password);
    }

}
