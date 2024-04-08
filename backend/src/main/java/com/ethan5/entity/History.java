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
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {
    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private Long id;

    @CreationTimestamp
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String userId;

    private String track1Id;
    private String track2Id;
    private String track3Id;
    private String artist1Id;
    private String artist2Id;
    private String artist3Id;
}
