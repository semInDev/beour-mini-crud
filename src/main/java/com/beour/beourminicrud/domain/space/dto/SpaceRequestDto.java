package com.beour.beourminicrud.domain.space.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpaceRequestDto {
    private String name;
    private String address;
    private String description;
}
