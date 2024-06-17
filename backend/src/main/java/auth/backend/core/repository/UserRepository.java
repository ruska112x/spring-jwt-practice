package auth.backend.core.repository;

import auth.backend.core.model.User;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private final String ROLE = "role";
    private final String PASSWORD = "password";
    private final JdbcOperations jdbcOperations;

    public UserRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Transactional
    public void addUser(String email, String password) {
        String sqlUsers = "insert into users (email, password, enabled) values (?, ?, ?)";
        jdbcOperations.update(sqlUsers, email, password, true);
        String sqlRoles = "insert into user_roles (user_email, role) values (?, ?)";
        jdbcOperations.update(sqlRoles, email, "USER");
    }

    @Transactional
    public User findByEmail(String email) {
        Map<String, Object> rawUser;
        try {
            String sqlUser = "SELECT email, password FROM users u WHERE u.enabled = true AND u.email = ?";
            rawUser = jdbcOperations.queryForMap(sqlUser, email);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
        List<String> roles = new ArrayList<>();
        String sqlRoles = "SELECT user_email, role FROM user_roles WHERE user_email = ?";
        jdbcOperations.query(sqlRoles, resultSet -> {
            String role = resultSet.getString(ROLE);
            roles.add(role);
        }, email);
        String password = String.valueOf(rawUser.get(PASSWORD));
        return new User(email, roles, password);
    }
}
