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
        Space saved = spaceRepository.save(spaceRequestDto.toEntity());
        return SpaceResponseDto.fromEntity(saved);
    }

    public SpaceResponseDto getSpace(Long id) {
        Space space = spaceRepository.findById(id)
                .orElseThrow(()->new SpaceNotFoundException(id));
        return SpaceResponseDto.fromEntity(space);
    }

    public SpaceResponseDto updateSpace(Long id, SpaceRequestDto spaceRequestDto) {
        Space space = spaceRepository.findById(id)
                .orElseThrow(()->new SpaceNotFoundException(id));
        space.update(
                spaceRequestDto.getName(),
                spaceRequestDto.getSpaceCategory(),
                spaceRequestDto.getAddress(),
                spaceRequestDto.getDescription());
        return SpaceResponseDto.fromEntity(spaceRepository.save(space));
    }

    public void deleteSpace(Long id) {
        Space space = spaceRepository.findById(id)
                        .orElseThrow(() -> new SpaceNotFoundException(id));
        spaceRepository.deleteById(id);
    }
}
