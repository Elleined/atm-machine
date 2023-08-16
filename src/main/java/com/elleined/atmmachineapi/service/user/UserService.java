package com.elleined.atmmachineapi.service.user;

import com.elleined.atmmachineapi.dto.UserDTO;
import com.elleined.atmmachineapi.exception.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.User;

public interface UserService {

    User save(UserDTO userDTO);

    void save(User user);

    User getById(int userId) throws ResourceNotFoundException;

    User getByUUID(String uuid) throws ResourceNotFoundException;

    boolean isUserExists(int id);
}
