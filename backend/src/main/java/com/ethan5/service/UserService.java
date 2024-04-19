package com.ethan5.service;

import com.ethan5.dao.ArtistRepository;
import com.ethan5.dao.FriendRepository;
import com.ethan5.dao.HistoryRepository;
import com.ethan5.dao.TrackRepository;
import com.ethan5.dao.UserRepository;
import com.ethan5.dto.UpdateUserRequest;
import com.ethan5.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final ArtistRepository artistRepository;
    private final FriendRepository friendRepository;
    private final HistoryRepository historyRepository;
    private final TrackRepository trackRepository;
    private BCryptPasswordEncoder encoder;

    public String createUser(User user) {
        repository.save(user);
        return user.getId();
    }

    public User readUser(String id) {
        return repository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    public User readUserByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    public User updateUser(String id, UpdateUserRequest req) {
        User user = repository.findById(id).get();

        if (isValid(req.firstName())) {
            user.setFirstName(req.firstName());
        }

        if (isValid(req.lastName())) {
            user.setLastName(req.lastName());
        }

        if (isValid(req.email())) {
            user.setEmail(req.email());
        }

        if (isValid(req.password())) {
            user.setPassword(encoder.encode(req.password()));
        }

        repository.save(user);
        return user;
    }

    private boolean isValid(String s) {
        return s != null && !s.isEmpty();
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
