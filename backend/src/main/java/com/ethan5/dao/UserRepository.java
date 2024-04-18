package com.ethan5.dao;

import com.ethan5.entity.User;
import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
