package com.ethan5.service;

import com.ethan5.dao.UserRepository;
import com.ethan5.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository repository;

    public String createUser(String id) {
        User user = User.builder().id(id).build();
        repository.save(user);
        return user.getId();
    }

    public User readUser(String id) {
        return repository.findById(id).orElse(null);
    }
}
