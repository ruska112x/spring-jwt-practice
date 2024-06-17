package auth.backend.core.model;

import auth.backend.core.model.interfaces.IUserCredentials;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserCredentials implements IUserCredentials {

    private final String email;
    private final List<String> roles;

    public UserCredentials(String email, List<String> roles) {
        this.email = email;
        this.roles = roles;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public Set<String> getRoles() {
        return new HashSet<>(roles);
    }
}
