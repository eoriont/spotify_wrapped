package com.ethan5.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ethan5.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}