package com.ethan5.dao;

import com.ethan5.entity.History;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUserId(String userId);
    History findTopByUserIdOrderByDateDesc(String userId);
    void deleteByUserId(String userId);
}
