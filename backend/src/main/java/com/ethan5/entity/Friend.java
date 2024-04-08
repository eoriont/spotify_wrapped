package com.ethan5.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Friend {
    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String user1Id;

    @Column(nullable = false)
    private String user2Id;
}
