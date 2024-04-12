package com.ethan5.dao;

import com.ethan5.entity.Friend;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Transactional
public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByUser1Id(String user1Id);

    Optional<Friend> findByUser1IdAndUser2Id(String user1Id, String user2Id);

    void deleteByUser1Id(String userId);

    void deleteByUser2Id(String userId);
}
