package de.aittr.g_31_2_shop.controllers;

import de.aittr.g_31_2_shop.domain.jpa.User;
import de.aittr.g_31_2_shop.services.jpa.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);
    }

    @PostMapping("/register/admin")
    public User registerAdmin(@RequestBody User user) {
        return service.registerAdmin(user);
    }

}
