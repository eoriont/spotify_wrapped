package com.ethan5.dao;

import com.ethan5.entity.Track;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface TrackRepository extends JpaRepository<Track, String> {
    void deleteByUserId(String userId);
}
