package Coursework_35155752;

public class User {
    private final String user_email;
    private final String user_name;
    private final String user_password;
    private final String user_privilege;

    public User(String email, String name, String password, String privilege) {
        this.user_email = email;
        this.user_name = name;
        this.user_password = password;
        this.user_privilege = privilege;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_privilege() {
        return user_privilege;
    }
}
