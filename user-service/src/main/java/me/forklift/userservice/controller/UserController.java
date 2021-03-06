package me.forklift.userservice.controller;

import me.forklift.userservice.vo.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private Greeting greeting;

    private Environment env;

    @Autowired
    public UserController(Environment env) {
        this.env = env;
    }

    @GetMapping("/health_check")
    public String status() {
        return "it's working";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.message")+ "\n" + greeting.getMessage();
    }
}
