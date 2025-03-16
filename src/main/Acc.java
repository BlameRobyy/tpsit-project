public class Acc {
    private String username;
    private String password;
    private User user;

    public Acc(String username, String password, User user) {
        this.username = username;
        this.password = password;
        this.user = user;
    }
    public User getUser() {
        return user;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

}