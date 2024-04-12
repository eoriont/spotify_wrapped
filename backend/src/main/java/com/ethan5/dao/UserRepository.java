package com.ethan5.dao;

import com.ethan5.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
public interface UserRepository extends JpaRepository<User, String> {
}
