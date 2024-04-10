package com.ethan5.dao;

import com.ethan5.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByUser1Id(String user1Id);
    Optional<Friend> findByUser1IdAndUser2Id(String user1Id, String user2Id);
}
