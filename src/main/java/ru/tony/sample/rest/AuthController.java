package ru.tony.sample.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tony.sample.rest.request.AuthRequest;
import ru.tony.sample.service.AuthService;

@RestController
@RequestMapping("/")
public class AuthController {

    private AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }


    @PostMapping("login")
    public String login(@RequestBody AuthRequest request) {
        return service.authorize(request.getLogin(), request.getPassword());
    }
}
