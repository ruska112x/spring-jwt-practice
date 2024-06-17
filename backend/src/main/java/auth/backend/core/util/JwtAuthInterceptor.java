package auth.backend.core.util;

import auth.backend.core.annotation.AuthRoleRequired;
import auth.backend.core.model.interfaces.IUserCredentials;
import auth.backend.core.service.JsonWebTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class JwtAuthInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static String USER_CREDENTIALS = "userCredentialsAttr";

    private final JsonWebTokenService jwtService;

    public JwtAuthInterceptor(
            final JsonWebTokenService jwtService
    ) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            return checkAuthorization(method, request, response);
        }
        return true;
    }


    private IUserCredentials getUserCredentials(final HttpServletRequest request) {
        try {
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (header == null || !header.startsWith("Bearer ")) {
                return null;
            }

            String token = header.substring(7);
            IUserCredentials credentials = jwtService.parseToken(token);

            logger.debug("Found credentials in Authorization header: {}", credentials.getEmail());
            request.setAttribute(USER_CREDENTIALS, credentials);
            return credentials;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean checkAuthorization(
            final Method method,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {
        try {
            IUserCredentials IUserCredentials = getUserCredentials(request);
            if (method.isAnnotationPresent(AuthRoleRequired.class)) {
                if (IUserCredentials == null) {
                    response.sendError(
                            HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                    return false;
                }
                AuthRoleRequired annotation = method
                        .getAnnotation(AuthRoleRequired.class);
                String requiredRole = annotation.value();
                Set<String> userRoles = new HashSet<>();
                if (IUserCredentials.getRoles() != null) {
                    userRoles.addAll(IUserCredentials.getRoles());
                }
                boolean isAuthorized = userRoles.contains(requiredRole);
                if (!isAuthorized) {
                    response.sendError(
                            HttpServletResponse.SC_FORBIDDEN, "Not enough permissions");
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
