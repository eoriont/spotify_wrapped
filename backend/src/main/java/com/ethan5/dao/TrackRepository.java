package com.ethan5.dao;

import com.ethan5.entity.Track;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
public interface TrackRepository extends JpaRepository<Track, String> {
    void deleteByUserId(String userId);
}
