package com.beour.beourminicrud.space.service;

import com.beour.beourminicrud.global.exception.SpaceNotFoundException;
import com.beour.beourminicrud.space.dto.SpaceRequestDto;
import com.beour.beourminicrud.space.dto.SpaceResponseDto;
import com.beour.beourminicrud.space.entity.Space;
import com.beour.beourminicrud.space.repository.SpaceRepository;
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
                .orElseThrow(()->new SpaceNotFoundException(id));

        return new SpaceResponseDto(space.getId(), space.getName(), space.getAddress(), space.getDescription());
    }
}
