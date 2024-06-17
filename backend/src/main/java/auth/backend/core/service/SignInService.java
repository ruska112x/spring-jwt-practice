package auth.backend.core.service;

import auth.backend.core.exception.SignInFailedException;
import auth.backend.core.model.User;
import auth.backend.core.model.UserSign;
import auth.backend.core.repository.UserRepository;
import auth.backend.core.util.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignInService {

    private final UserRepository users;
    private final BCryptPasswordEncoder passwordEncoder;

    public SignInService(UserRepository users, BCryptPasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUp(UserSign userSign) {
        User user = users.findByEmail(userSign.getEmail());

        if (user != null) {
            throw new SignInFailedException("User '" + userSign.getEmail() + "' already exist");
        } else {
            String passwordInBd = passwordEncoder.encode(userSign.getPassword());
            users.addUser(userSign.getEmail(), passwordInBd);
        }
        return new User(userSign.getEmail(), List.of("USER"));
    }

    public User signIn(UserSign userSign) {
        User user = users.findByEmail(userSign.getEmail());
        if (user == null) {
            throw new SignInFailedException("User '" + userSign.getEmail() + "' not found");
        }

        if (!passwordEncoder.matches(userSign.getPassword(), user.getPassword())) {
            throw new SignInFailedException("Wrong password");
        }

        return new User(user.getEmail(), user.getRoles());
    }
}
