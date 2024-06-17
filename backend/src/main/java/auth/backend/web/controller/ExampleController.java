package auth.backend.web.controller;

import auth.backend.core.annotation.AuthRoleRequired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("example")
public class ExampleController {
    @GetMapping
    @AuthRoleRequired("USER")
    private String helloWorld() {
        return "Hello, World!";
    }
}
