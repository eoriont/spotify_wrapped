package com.ethan5.service;

import com.ethan5.dao.FriendRepository;
import com.ethan5.dto.FriendRequest;
import com.ethan5.entity.Friend;
import com.ethan5.entity.Track;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecommendationService {
    private final FriendRepository repository;
    private final TrackService trackService;

    public void addFriend(FriendRequest req) {
        Friend friendship = Friend
                .builder()
                .user1Id(req.user1Id())
                .user2Id(req.user2Id())
                .build();
        repository.save(friendship);

        Friend friendship2 = Friend
                .builder()
                .user1Id(req.user2Id())
                .user2Id(req.user1Id())
                .build();
        repository.save(friendship2);
    }

    // TODO: make this return a list of tracks instead of a string??
    public String getRecommendations(String id, String bearerToken) throws Exception {
        List<Track> topTracks = trackService.readTopTracks(id, bearerToken);

        List<String> dataList = new ArrayList<>();

        for (Track t : topTracks) {
            dataList.add(t.getName());
        }

        // This is Colby's code, don't touch for now
        // In the future, we need to refactor this to do clean request and
        // Return the actual songs
        String baseUrl = "https://colbyb1123.pythonanywhere.com/";
        String queryString = dataList.stream()
                .map(s -> "data=" + s)
                .collect(Collectors.joining("&"));
        String urlStr = baseUrl + "?" + queryString;
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection)
                url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public void deleteFriend(String id1, String id2) {
        Optional<Friend> f = repository.findByUser1IdAndUser2Id(id1, id2);
        f.ifPresent(repository::delete);

        Optional<Friend> f2 = repository.findByUser1IdAndUser2Id(id2, id1);
        f2.ifPresent(repository::delete);
    }

    public List<Friend> getFriendsList(String id) {
        return repository.findByUser1Id(id);
    }
}
