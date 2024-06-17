package auth.backend.core.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RefreshTokensRepository {
    private final String TOKEN = "token";
    private final JdbcOperations jdbcOperations;

    public RefreshTokensRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public void addByEmail(String email, String token) {
        String sql = "insert into user_refresh_tokens (user_email, token) values (?, ?)";
        jdbcOperations.update(sql, email, token);
    }

    public List<String> getByEmail(String email) {
        List<String> tokens = new ArrayList<>();
        String sqlRoles = "SELECT token FROM user_refresh_tokens WHERE user_email = ?";
        jdbcOperations.query(sqlRoles, resultSet -> {
            String token = resultSet.getString(TOKEN);
            tokens.add(token);
        }, email);
        return tokens;
    }

    public void deleteByToken(String token) {
        String sql = "delete from user_refresh_tokens where token = ?";
        jdbcOperations.update(sql, token);
    }
}
