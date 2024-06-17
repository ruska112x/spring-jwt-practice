package auth.backend.core.model.interfaces;


import java.util.Set;

public interface IUserCredentials {
    String getEmail();

    Set<String> getRoles();
}
