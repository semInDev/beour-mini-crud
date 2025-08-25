package com.beour.beourminicrud.space.dto;

import com.beour.beourminicrud.space.entity.Space;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpaceResponseDto {
    private Long id;
    private String name;
    private String spaceCategory;
    private String address;
    private String description;

    public static SpaceResponseDto fromEntity(Space space) {
        return new SpaceResponseDto(
                space.getId(),
                space.getName(),
                space.getSpaceCategory().getText(),
                space.getAddress(),
                space.getDescription()
        );
    }
}
