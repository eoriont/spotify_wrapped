package com.example.spotifywrappedapp.models;

public class Friendship {

    private String id;
    private String user1Id;
    private String user2Id;

    public String getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(String user1Id) {
        this.user1Id = user1Id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(String user2Id) {
        this.user2Id = user2Id;
    }
}
