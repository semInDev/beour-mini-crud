package com.beour.beourminicrud.space.repository;

import com.beour.beourminicrud.space.entity.Space;
import com.beour.beourminicrud.space.enums.SpaceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpaceRepository extends JpaRepository<Space, Long> {
    Optional<Space> findByName(String name);
    Optional<Space> findByNameAndSpaceCategory(String name, SpaceCategory spaceCategory);
}
