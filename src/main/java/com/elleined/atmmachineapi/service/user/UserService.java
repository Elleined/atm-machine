package com.elleined.atmmachineapi.service.user;

import com.elleined.atmmachineapi.exception.resource.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.User;

public interface UserService {

    User save(String name);

    User getById(int userId) throws ResourceNotFoundException;

    User getByUUID(String uuid) throws ResourceNotFoundException;

    boolean isExists(int id);
}
