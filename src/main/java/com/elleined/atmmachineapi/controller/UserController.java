package com.elleined.atmmachineapi.controller;

import com.elleined.atmmachineapi.dto.UserDTO;
import com.elleined.atmmachineapi.mapper.UserMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public UserDTO save(@Valid @RequestBody UserDTO userDTO) {
        User savedUser = userService.save(userDTO);
        return userMapper.toDTO(savedUser);
    }

    @GetMapping("/getById/{userId}")
    public UserDTO getById(@PathVariable("userId") int userId) {
        User user = userService.getById(userId);
        return userMapper.toDTO(user);
    }

    @GetMapping("/getByUUID/{uuid}")
    public UserDTO getByUUID(@PathVariable("uuid") String uuid) {
        User user = userService.getByUUID(uuid);
        return userMapper.toDTO(user);
    }
}
