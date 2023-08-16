package com.elleined.atmmachineapi.controller;

import com.elleined.atmmachineapi.dto.UserDTO;
import com.elleined.atmmachineapi.mapper.UserMapper;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
    public UserDTO getById(int userId) {
        User user = userService.getById(userId);
        return userMapper.toDTO(user);
    }

    @GetMapping("/getByUUID/{uuid}")
    public UserDTO getByUUID(String uuid) {
        User user = userService.getByUUID(uuid);
        return userMapper.toDTO(user);
    }

    @GetMapping("/getIdByUUID/{uuid}")
    public int getIdByUUID(String uuid) {
        return userService.getIdByUUID(uuid);
    }

    @GetMapping("/getUUIDById/{userId}")
    public String getUUIDById(int userId) {
        return userService.getUUIDById(userId);
    }

    @GetMapping("getBalanceById/{userId}")
    public BigDecimal getBalance(int userId) {
        return getById(userId).getBalance();
    }

    @GetMapping("getBalanceByUUID/{uuid}")
    public BigDecimal getBalance(String uuid) {
        return getByUUID(uuid).getBalance();
    }
}
