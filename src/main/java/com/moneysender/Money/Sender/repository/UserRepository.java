package com.moneysender.Money.Sender.repository;

import com.moneysender.Money.Sender.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}