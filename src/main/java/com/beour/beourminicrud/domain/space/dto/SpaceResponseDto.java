package com.beour.beourminicrud.domain.space.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpaceResponseDto {
    private Long id;
    private String name;
    private String address;
    private String description;
}
