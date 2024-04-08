package com.ethan5.service;

import com.ethan5.dao.HistoryRepository;
import com.ethan5.entity.Artist;
import com.ethan5.entity.History;
import com.ethan5.entity.Track;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HistoryService {
    private final HistoryRepository repository;

    public void createHistory(
            String userId,
            Optional<List<Track>> tracks,
            Optional<List<Artist>> artists
    ) {
        History latestHistory = repository.findTopByUserIdOrderByDateDesc(userId);
        History history = History
                .builder()
                .userId(userId)
                .build();

        if (tracks.isPresent()) {
            history.setTrack1Id(tracks.get().get(0).getId());
            history.setTrack2Id(tracks.get().get(1).getId());
            history.setTrack3Id(tracks.get().get(2).getId());

            if (latestHistory != null) {
                history.setArtist1Id(latestHistory.getArtist1Id());
                history.setArtist2Id(latestHistory.getArtist2Id());
                history.setArtist3Id(latestHistory.getArtist3Id());
            }
        } else if (artists.isPresent()) {
            if (latestHistory != null) {
                history.setTrack1Id(latestHistory.getTrack1Id());
                history.setTrack2Id(latestHistory.getTrack2Id());
                history.setTrack3Id(latestHistory.getTrack3Id());
            }

            history.setArtist1Id(artists.get().get(0).getId());
            history.setArtist2Id(artists.get().get(1).getId());
            history.setArtist3Id(artists.get().get(2).getId());
        }

        repository.save(history);
    }

    public List<History> readHistory(String userId) {
        return repository.findByUserId(userId);
    }
}
