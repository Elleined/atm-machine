package com.elleined.atmmachineapi.controller;

import com.elleined.atmmachineapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @PostMapping
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password) {

        return userService.login(email, password);
    }
}
