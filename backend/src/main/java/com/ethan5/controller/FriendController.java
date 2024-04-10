package com.ethan5.controller;

import com.ethan5.dto.FriendRequest;
import com.ethan5.entity.Friend;
import com.ethan5.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/friend")
public class FriendController {
    private final FriendService service;

    @PostMapping("{id}/{id2}")
    public void addFriend(@PathVariable("id") String id1,
                          @PathVariable("id2") String id2) {
        service.addFriend(new FriendRequest(id1, id2));
    }

    @DeleteMapping("{id}/{id2}")
    public void deleteFriend(@PathVariable("id") String id1,
                             @PathVariable("id2") String id2) {
        service.deleteFriend(id1, id2);
    }

    @GetMapping("{id}")
    public List<Friend> getFriendsList(@PathVariable("id") String id) {
        return service.getFriendsList(id);
    }
}
