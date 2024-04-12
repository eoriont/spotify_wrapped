package com.ethan5.controller;

import com.ethan5.entity.User;
import com.ethan5.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("v1/user")
public class UserController {
    private UserService userService;

    @GetMapping("{id}")
    public User getUser(@PathVariable("id") String id) {
        return userService.readUser(id);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
    }
}
