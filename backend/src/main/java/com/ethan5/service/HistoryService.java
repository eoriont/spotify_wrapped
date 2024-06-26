package com.ethan5.service;

import com.ethan5.dao.HistoryRepository;
import com.ethan5.entity.Artist;
import com.ethan5.entity.History;
import com.ethan5.entity.Track;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HistoryService {
    private final HistoryRepository repository;
    private TrackService trackService;
    private ArtistService artistService;

    public History createHistory(
            String userId,
            String index,
            Optional<List<Track>> tracks,
            Optional<List<Artist>> artists
    ) {
        History latestHistory = repository
                .findTopByUserIdOrderByDateDesc(userId);
        History history = History
                .builder()
                .userId(userId)
                .idx(index)
                .build();

        if (tracks.isPresent()) {
            history.setTrack1Id(tracks.get().get(0).getId());
            history.setTrack2Id(tracks.get().get(1).getId());
            history.setTrack3Id(tracks.get().get(2).getId());

            if (!artists.isPresent() && latestHistory != null) {
                history.setArtist1Id(latestHistory.getArtist1Id());
                history.setArtist2Id(latestHistory.getArtist2Id());
                history.setArtist3Id(latestHistory.getArtist3Id());
            }
        }

        if (artists.isPresent()) {
            if (!tracks.isPresent() && latestHistory != null) {
                history.setTrack1Id(latestHistory.getTrack1Id());
                history.setTrack2Id(latestHistory.getTrack2Id());
                history.setTrack3Id(latestHistory.getTrack3Id());
            }

            history.setArtist1Id(artists.get().get(0).getId());
            history.setArtist2Id(artists.get().get(1).getId());
            history.setArtist3Id(artists.get().get(2).getId());
        }

        repository.save(history);
        return history;
    }

    public List<History> readHistory(String userId, String bearerToken) {
        // Ensure we have enough histories
        List<History> histories = repository.findByUserId(userId);
        if (histories.size() < 10) {
            generateHistory(userId, bearerToken);
        }
        return repository.findByUserId(userId);
    }

    public History getLatest(String userId) {
        return repository.findByUserId(userId).get(0);
    }

    public List<History> generateHistory(String userId, String bearerToken) {
        // generate 10 histories
        System.out.println(bearerToken);
        List<History> histories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String idx = userId + "'s History #" + i;
            History h = genOneHistory(userId, bearerToken, i, idx);
            histories.add(h);
        }

        return histories;
    }

    public History genOneHistory(String userId, String bearerToken,
                                 int i, String idx) {

        List<Track> tracks = trackService.readTopTracks(
                userId, "Bearer " + bearerToken,
                3, 3 * i);
        List<Artist> artists = artistService.readTopArtists(
                userId, "Bearer " + bearerToken,
                3, 3 * i);
        History h = this.createHistory(userId, idx,
                Optional.of(tracks), Optional.of(artists));
        return h;
    }

    public History newHistory(String userId, String bearerToken) {
        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        return genOneHistory(userId, bearerToken, 0, date);
    }
}
