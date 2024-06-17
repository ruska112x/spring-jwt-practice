package auth.backend.core.annotation;

import java.lang.annotation.*;

/**
 * Аннотация для уточнения требуемой роли
 * для вполнения запроса в контроллере
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthRoleRequired {
    String value();
}
