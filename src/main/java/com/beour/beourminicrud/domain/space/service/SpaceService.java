package com.beour.beourminicrud.domain.space.service;

import com.beour.beourminicrud.domain.space.dto.SpaceRequestDto;
import com.beour.beourminicrud.domain.space.dto.SpaceResponseDto;
import com.beour.beourminicrud.domain.space.entity.Space;
import com.beour.beourminicrud.domain.space.repository.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpaceService {
    private final SpaceRepository spaceRepository;

    public SpaceResponseDto createSpace(SpaceRequestDto spaceRequestDto) {
        Space space = Space.builder()
                .name(spaceRequestDto.getName())
                .address(spaceRequestDto.getAddress())
                .description(spaceRequestDto.getDescription())
                .build();

        Space saved = spaceRepository.save(space);

        return new SpaceResponseDto(saved.getId(), saved.getName(), saved.getAddress(), saved.getDescription());
    }

    public SpaceResponseDto getSpace(Long id) {
        Space space = spaceRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 공간이 존재하지 않습니다."));

        return new SpaceResponseDto(space.getId(), space.getName(), space.getAddress(), space.getDescription());
    }
}
