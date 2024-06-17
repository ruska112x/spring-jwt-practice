package auth.backend.core.service;

import auth.backend.core.model.User;
import auth.backend.core.model.UserCredentials;
import auth.backend.core.model.interfaces.IUserCredentials;
import auth.backend.core.util.JwtSettings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class JsonWebTokenService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JwtSettings settings;
    private final String ROLES = "roles";

    public JsonWebTokenService(final JwtSettings settings) {
        this.settings = settings;
    }

    private String createToken(User user, TemporalAmount timeBeforeExpiration) {
        logger.debug("Generating token for {}", user.getEmail());
        Instant now = Instant.now();

        Claims claims = Jwts.claims()
                .setIssuer(settings.getTokenIssuer())
                .setIssuedAt(Date.from(now))
                .setSubject(user.getEmail())
                .setExpiration(Date.from(now.plus(timeBeforeExpiration)));
        claims.put(ROLES, user.getRoles());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();
    }

    public String createAccessToken(User user) {
        return createToken(user, settings.getAccessTokenExpiredIn());
    }

    public String createRefreshToken(User user) {
        return createToken(user, settings.getRefreshTokenExpiredIn());
    }

    public IUserCredentials parseToken(String token) {
        logger.debug("Parsing token {}", token);

        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(settings.getTokenSigningKey()).parseClaimsJws(token);

        String subject = claims.getBody().getSubject();
        List<String> roles = claims.getBody().get(ROLES, List.class);

        return new UserCredentials(subject, Collections.unmodifiableList(roles));
    }

    public boolean isExpired(String token) {
        logger.debug("Checking token expiration {}", token);
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(settings.getTokenSigningKey()).parseClaimsJws(token);
        return Date.from(Instant.now()).after(claims.getBody().getExpiration());
    }
}
