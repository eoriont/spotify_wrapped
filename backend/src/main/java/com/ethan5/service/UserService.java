package com.ethan5.service;

import org.springframework.stereotype.Service;
import com.ethan5.dao.UserRepository;
import com.ethan5.entity.User;

@Service
public class UserService {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public String createUser(String id) {
        User user = new User(id);
        repository.saveAndFlush(user);
        return user.getId();
    }

    public User readUser(String id) {
        return repository.findById(id).orElse(null);
    }
}
