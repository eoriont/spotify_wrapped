package com.ethan5.service;

import com.ethan5.dao.ArtistRepository;
import com.ethan5.dao.FriendRepository;
import com.ethan5.dao.HistoryRepository;
import com.ethan5.dao.TrackRepository;
import com.ethan5.dao.UserRepository;
import com.ethan5.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final ArtistRepository artistRepository;
    private final FriendRepository friendRepository;
    private final HistoryRepository historyRepository;
    private final TrackRepository trackRepository;

    public String createUser(String id) {
        User user = User.builder().id(id).build();
        repository.save(user);
        return user.getId();
    }

    public User readUser(String id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteUser(String id) {
        repository.deleteById(id);
        artistRepository.deleteByUserId(id);
        friendRepository.deleteByUser1Id(id);
        friendRepository.deleteByUser2Id(id);
        historyRepository.deleteByUserId(id);
        trackRepository.deleteByUserId(id);
    }
}
