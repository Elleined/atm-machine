package com.elleined.atmmachineapi.repository;

import com.elleined.atmmachineapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}