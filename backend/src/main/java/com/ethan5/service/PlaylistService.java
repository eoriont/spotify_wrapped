package com.ethan5.service;

import com.ethan5.dao.PlaylistRepository;
import com.ethan5.entity.Playlist;
import com.ethan5.dto.TracksResponse;
import com.ethan5.dto.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistService {
    private PlaylistRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public PlaylistService(PlaylistRepository repository) {
        this.repository = repository;
    }

    public String createPlaylist(String id) {
        Playlist playlist = new Playlist(id);
        repository.saveAndFlush(playlist);
        return playlist.getId();
    }

    public Playlist readPlaylist(String id) {
        return repository.findById(id).orElse(null);
    }

    public void getUserTopTracks(String userId) {
        String accessToken = "";
        try {
            // get/parse tracks into list
            //TODO: GET THIS ACCESS TOKEN!!! possibly from the user entity? if we are gonna store it :O
            List<Track> userTopTracks = spotifyTopTracksRequest(accessToken);
            List<String> tracks = userTopTracks.stream().map(Track::getName).collect(Collectors.toList());

            // put them in the user's top tracks playlist
            Playlist playlist = new Playlist("user"+userId+"-top-tracks");
            playlist.setTracks(tracks);
            repository.save(playlist);
        } catch (Exception e) {
            System.err.println("broke!");
        }
    }

    public List<Track> spotifyTopTracksRequest(String accessToken) throws ParseException {
        // Spotify API endpoint for fetching the current user's top tracks
        String url = "https://api.spotify.com/v1/me/top/tracks";

        // Set up headers with the authorization token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        // Make the GET request
        ResponseEntity<TracksResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, TracksResponse.class);

        // Access the list of tracks from the TracksResponse
        TracksResponse tracksResponse = response.getBody();
        if (tracksResponse != null) {
            for (Track track : tracksResponse.getItems()) {
                System.out.println(track.getName()); // Print each track's name
            }
            return tracksResponse.getItems();
        }

        throw new ParseException("There was an error parsing the response!", 0);
    }
}
