package com.beour.beourminicrud.domain.space.repository;

import com.beour.beourminicrud.domain.space.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<Space, Long> {
}
