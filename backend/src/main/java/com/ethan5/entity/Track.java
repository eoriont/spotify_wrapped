package com.ethan5.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Track {
    @Id
    private String id;
    private String name; // Matches the "name" field in the JSON

    @ManyToMany(mappedBy = "tracks")
    @JsonIgnore
    private Set<Playlist> playlists = new HashSet<>();
}
