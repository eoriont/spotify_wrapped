package com.ethan5.service;

import com.ethan5.dao.FriendRepository;
import com.ethan5.dto.FriendRequest;
import com.ethan5.entity.Friend;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FriendService {
    private final FriendRepository repository;

    public void addFriend(FriendRequest req) {
        Friend friendship = Friend
                .builder()
                .user1Id(req.user1Id())
                .user2Id(req.user2Id())
                .build();
        repository.save(friendship);
    }
}
