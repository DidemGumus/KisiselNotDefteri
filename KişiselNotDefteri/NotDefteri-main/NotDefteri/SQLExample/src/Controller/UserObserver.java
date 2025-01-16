package Controller;

public class UserObserver implements Observer {
    private String username;
    private int userId;

    public UserObserver(String username, int userId) {
        this.username = username;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public void update(String message) {
        System.out.println(username + " (ID: " + userId + "): " + message);
    }
}
