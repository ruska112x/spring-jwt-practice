package auth.backend.core.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.TemporalAmount;

@Component
public class JwtSettings {
    private final String tokenIssuer;
    private final byte[] tokenSigningKey;
    private final int aTokenDuration;
    private final int rTokenDuration;

    public JwtSettings(@Value("${jwt.issuer}") final String tokenIssuer,
                       @Value("${jwt.signingKey}") final String tokenSigningKey,
                       @Value("${jwt.aTokenDuration}") final int aTokenDuration,
                       @Value("${jwt.rTokenDuration}") final int rTokenDuration) {
        this.tokenIssuer = tokenIssuer;
        this.tokenSigningKey = tokenSigningKey.getBytes();
        this.aTokenDuration = aTokenDuration;
        this.rTokenDuration = rTokenDuration;
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }

    public byte[] getTokenSigningKey() {
        return tokenSigningKey;
    }

    public TemporalAmount getAccessTokenExpiredIn() {
        return Duration.ofMinutes(aTokenDuration);
    }

    public TemporalAmount getRefreshTokenExpiredIn() {
        return Duration.ofDays(rTokenDuration);
    }
}
