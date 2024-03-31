package com.elleined.atmmachineapi.service.user;

import com.elleined.atmmachineapi.exception.resource.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.User;
import com.elleined.atmmachineapi.request.user.UserRequest;

import java.math.BigDecimal;

public interface UserService {

    User save(UserRequest userRequest);

    User getById(int userId) throws ResourceNotFoundException;

    User getByUUID(String uuid) throws ResourceNotFoundException;

    boolean isExists(int id);

    void updateBalance(User user, BigDecimal newBalance);
}
