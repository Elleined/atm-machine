package com.elleined.atmmachineapi.controller;

import com.elleined.atmmachineapi.dto.UserDTO;
import com.elleined.atmmachineapi.mapper.UserMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public UserDTO save(@RequestParam("name") String name,
                        @RequestParam(defaultValue = "false", name = "includeRelatedLinks") boolean includeRelatedLinks) {

        User savedUser = userService.save(name);
        return userMapper.toDTO(savedUser);
    }

    @GetMapping("/id/{userId}")
    public UserDTO getById(@PathVariable("userId") int userId,
                           @RequestParam(defaultValue = "false", name = "includeRelatedLinks") boolean includeRelatedLinks) {
        User user = userService.getById(userId);
        return userMapper.toDTO(user);
    }

    @GetMapping("/uuid/{uuid}")
    public UserDTO getByUUID(@PathVariable("uuid") String uuid,
                             @RequestParam(defaultValue = "false", name = "includeRelatedLinks") boolean includeRelatedLinks) {
        User user = userService.getByUUID(uuid);
        return userMapper.toDTO(user);
    }
}
