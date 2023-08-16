package com.elleined.atmmachineapi.controller;

import com.elleined.atmmachineapi.dto.UserDTO;
import com.elleined.atmmachineapi.mapper.UserMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

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

    @GetMapping("/getIdByUUID/{uuid}")
    public int getIdByUUID(@PathVariable("uuid") String uuid) {
        return userService.getIdByUUID(uuid);
    }

    @GetMapping("/getUUIDById/{userId}")
    public String getUUIDById(@PathVariable("userId") int userId) {
        return userService.getUUIDById(userId);
    }

    @GetMapping("getBalanceById/{userId}")
    public BigDecimal getBalance(@PathVariable("userId") int userId) {
        return getById(userId).getBalance();
    }

    @GetMapping("getBalanceByUUID/{uuid}")
    public BigDecimal getBalance(@PathVariable("uuid") String uuid) {
        return getByUUID(uuid).getBalance();
    }
}
