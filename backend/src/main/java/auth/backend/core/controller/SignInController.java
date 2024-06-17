package auth.backend.core.controller;

import auth.backend.core.model.Token;
import auth.backend.core.model.User;
import auth.backend.core.model.UserSign;
import auth.backend.core.repository.RefreshTokensRepository;
import auth.backend.core.service.JsonWebTokenService;
import auth.backend.core.service.SignInService;
import auth.backend.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class SignInController {
    private final String REFRESH_TOKEN = "refreshToken";
    private final SignInService loginService;
    private final JsonWebTokenService tokenService;
    private final UserService userService;
    private final MailSender mailSender;
    private final RefreshTokensRepository refreshTokensRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public SignInController(final SignInService loginService, JsonWebTokenService tokenService, UserService userService, MailSender mailSender, RefreshTokensRepository refreshTokensRepository) {
        this.loginService = loginService;
        this.tokenService = tokenService;
        this.userService = userService;
        this.mailSender = mailSender;
        this.refreshTokensRepository = refreshTokensRepository;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Token> register(@RequestBody UserSign login) {
        User user = loginService.signUp(login);
        Token accessToken = new Token(tokenService.createAccessToken(user));
        String refreshToken = tokenService.createRefreshToken(user);
        refreshTokensRepository.addByEmail(login.getEmail(), refreshToken);
        String cookie = REFRESH_TOKEN + "=" + refreshToken + "; HttpOnly";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@example.com");
        message.setTo(login.getEmail());
        message.setSubject("Somebody registered at your web-service");
        message.setText("Email: " + login.getEmail());
        mailSender.send(message);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie).body(accessToken);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Token> login(@RequestBody UserSign login) {
        User user = loginService.signIn(login);
        Token accessToken = new Token(tokenService.createAccessToken(user));
        String refreshToken = tokenService.createRefreshToken(user);
        refreshTokensRepository.addByEmail(login.getEmail(), refreshToken);
        String cookie = REFRESH_TOKEN + "=" + refreshToken + "; HttpOnly";
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie).body(accessToken);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Token> refresh(@CookieValue(REFRESH_TOKEN) String token) {
        if (token == null || tokenService.isExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String email = tokenService.parseToken(token).getEmail();
        List<String> tokens = refreshTokensRepository.getByEmail(email);
        boolean found = false;
        for (String t : tokens) {
            if (t.equals(token)) {
                found = true;
                refreshTokensRepository.deleteByToken(t);
                logger.debug("delete equals token {}", t);
            } else {
                if (tokenService.isExpired(t)) {
                    refreshTokensRepository.deleteByToken(t);
                    logger.debug("delete expired token {}", t);
                }
            }
        }
        if (found) {
            User user = userService.findByEmail(email);
            Token accessToken = new Token(tokenService.createAccessToken(user));
            String refreshToken = tokenService.createRefreshToken(user);
            refreshTokensRepository.addByEmail(email, refreshToken);
            String cookie = REFRESH_TOKEN + "=" + refreshToken + "; HttpOnly";
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie).body(accessToken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(REFRESH_TOKEN) String token) {
        refreshTokensRepository.deleteByToken(token);
        String cookie = REFRESH_TOKEN + "=deleted; expires=Thu, 01 Jan 1970 00:00:00 GMT";
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie).body(null);
    }
}
