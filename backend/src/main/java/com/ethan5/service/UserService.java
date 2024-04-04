package com.ethan5.service;

import com.ethan5.dao.PlaylistRepository;
import com.ethan5.dao.TrackRepository;
import com.ethan5.dto.TrackInfo;
import com.ethan5.dto.TracksWrapper;
import com.ethan5.entity.Playlist;
import com.ethan5.entity.Track;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ethan5.dao.UserRepository;
import com.ethan5.entity.User;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private UserRepository repository;
    private PlaylistRepository playlistRepository;
    private TrackRepository trackRepository;

    @Autowired
    private RestTemplate restTemplate;


    public UserService(UserRepository repository, PlaylistRepository playlistRepository, TrackRepository trackRepository) {
        this.repository = repository;
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
    }

    public String createUser(String id) {
        User user = new User(id);
        repository.saveAndFlush(user);
        return user.getId();
    }

    public User readUser(String id) {
        return repository.findById(id).orElse(null);
    }

    public Playlist getUserTopTracks(String userId, String authHeader) {
        try {
            Set<Track> userTopTracks = fetchTopUserSongs(authHeader);

            Playlist playlist = new Playlist();
            for (Track t : userTopTracks) {
                Track persistentTrack = trackRepository.findById(t.getId()).orElse(t);
                saveTrackIfNotExists(persistentTrack);
                System.out.println(t.getName());
                playlist.addTrack(persistentTrack);
            }
            playlist.setUser(repository.getReferenceById(userId));
            playlistRepository.saveAndFlush(playlist);
            return playlist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Set<Track> fetchTopUserSongs(String authHeader) {
        // TODO make some config for endpoints
        String url = "https://api.spotify.com/v1/me/top/tracks";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authHeader.substring(7));
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> rawJson = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            TracksWrapper wrapper = mapper.readValue(rawJson.getBody(), TracksWrapper.class);
            Set<Track> tracks = new HashSet<>();
            for (TrackInfo track : wrapper.getTracks()) {
                tracks.add(new Track(track.getName(), track.getId()));
            }
            return tracks;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }

    public Track saveTrackIfNotExists(Track track) {
        Optional<Track> existingTrack = trackRepository.findById(track.getId());
        if (existingTrack.isPresent()) {
            // The track already exists. Update it or simply return the existing one
            // depending on your specific requirements.
            return existingTrack.get(); // Or perform an update if necessary
        } else {
            // The track doesn't exist, save the new one.
            return trackRepository.save(track);
        }
    }

}
