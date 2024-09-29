package com.microservice.stateless_auth_api.core;

import com.microservice.stateless_auth_api.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
