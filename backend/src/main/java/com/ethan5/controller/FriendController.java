package com.ethan5.controller;

import com.ethan5.dto.FriendRequest;
import com.ethan5.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("v1/friend")
public class FriendController {
    private final FriendService service;

    @PostMapping
    public void addFriend(@RequestBody FriendRequest req) {
        service.addFriend(req);
    }
}
