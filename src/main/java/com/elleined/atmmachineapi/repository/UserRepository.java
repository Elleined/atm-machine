package com.elleined.atmmachineapi.repository;

import com.elleined.atmmachineapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.uuid = ?1")
    Optional<User> fetchByUUID(String uuid);
}