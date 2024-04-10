package com.ethan5.service;

import com.ethan5.dao.FriendRepository;
import com.ethan5.dto.FriendRequest;
import com.ethan5.entity.Friend;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FriendService {
    private final FriendRepository repository;

    public Friend addFriend(FriendRequest req) {
        Friend friendship = Friend
                .builder()
                .user1Id(req.user1Id())
                .user2Id(req.user2Id())
                .build();
        repository.save(friendship);

        Friend friendship2 = Friend
                .builder()
                .user1Id(req.user2Id())
                .user2Id(req.user1Id())
                .build();
        repository.save(friendship2);

        return friendship;
    }

    public void deleteFriend(String id1, String id2) {
        Optional<Friend> f = repository.findByUser1IdAndUser2Id(id1, id2);
        f.ifPresent(repository::delete);

        Optional<Friend> f2 = repository.findByUser1IdAndUser2Id(id2, id1);
        f2.ifPresent(repository::delete);
    }

    public List<Friend> getFriendsList(String id) {
        return repository.findByUser1Id(id);
    }
}
