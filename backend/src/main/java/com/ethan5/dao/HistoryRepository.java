package com.ethan5.dao;

import com.ethan5.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUserId(String userId);
    History findTopByUserIdOrderByDateDesc(String userId);

}
