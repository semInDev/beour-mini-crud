package com.beour.beourminicrud.domain.space.dto;

import com.beour.beourminicrud.domain.space.enums.SpaceCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpaceRequestDto {
    @NotEmpty(message = "공간명 입력은 필수입니다.")
    @Size(max = 100)
    private String name;

    @NotEmpty(message = "공간 유형 입력은 필수입니다.")
    private SpaceCategory spaceCategory;

    @NotEmpty(message = "주소 입력은 필수입니다.")
    private String address;

    @NotEmpty(message = "내용 입력은 필수입니다.")
    private String description;

}
