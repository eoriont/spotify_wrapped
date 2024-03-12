package com.ethan5.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ethan5.dto.UserRequest;
import com.ethan5.entity.User;
import com.ethan5.service.UserService;

@RestController
@RequestMapping("v1/user")
public class UserController {
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping()
    public String createUser(@RequestBody UserRequest req) {
        return service.createUser(req.id());
    }
    

    @GetMapping("{id}")
    public User readUser(@PathVariable("id") String id) {
        return service.readUser(id);
    }
}
