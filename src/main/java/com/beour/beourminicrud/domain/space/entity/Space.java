package com.beour.beourminicrud.domain.space.entity;

import com.beour.beourminicrud.domain.space.enums.SpaceCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // private Long hostId;
    private String name;
    private SpaceCategory spaceCategory;
    private String address;
    private String description;
}
