package me.forklift.userservice.controller;

import me.forklift.userservice.dto.UserDto;
import me.forklift.userservice.service.UserService;
import me.forklift.userservice.vo.Greeting;
import me.forklift.userservice.vo.RequestUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private Greeting greeting;

    private UserService userService;

    private Environment env;

    @Autowired
    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/health_check")
    public String status() {
        return "it's working";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.message")+ "\n" + greeting.getMessage();
    }


    @PostMapping("/users")
    public String createUser(@RequestBody RequestUser requestUser) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(requestUser, UserDto.class);
        userService.createUser(userDto);

        return "created user";
    }
}
