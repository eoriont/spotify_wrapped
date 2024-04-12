package com.ethan5.dao;

import com.ethan5.entity.Artist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface ArtistRepository extends JpaRepository<Artist, String> {
    void deleteByUserId(String userId);
}
