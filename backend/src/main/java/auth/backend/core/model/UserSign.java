package auth.backend.core.model;

public class UserSign {
    private final String email;
    private final String password;

    public UserSign(
            String email,
            String password
    ) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
