package com.elleined.atmmachineapi.service.user;

import com.elleined.atmmachineapi.exception.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.User;

public interface UserService {

    User getById(int userId) throws ResourceNotFoundException;

    User getByUUID(String uuid) throws ResourceNotFoundException;

    int getIdByUUID(String uuid) throws ResourceNotFoundException;

    String getUUIDById(int id) throws ResourceNotFoundException;
}
