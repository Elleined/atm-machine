package com.elleined.atmmachineapi.service.user;

import com.elleined.atmmachineapi.exception.resource.ResourceNotFoundException;
import com.elleined.atmmachineapi.model.User;

public interface UserService {

    User save(String name,
              String email,
              String password);

    User getById(int userId) throws ResourceNotFoundException;
    User getByUUID(String uuid) throws ResourceNotFoundException;
    User getByJWT(String jwt) throws ResourceNotFoundException;


    boolean isExists(int id);

    String login(String email,
                 String password);
}
