package com.beour.beourminicrud.space.dto;

import com.beour.beourminicrud.space.entity.Space;
import com.beour.beourminicrud.space.enums.SpaceCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "공간 유형 입력은 필수입니다.") // NotEmpty는 String에 씀. enum은 NotNull
    private SpaceCategory spaceCategory;

    @NotEmpty(message = "주소 입력은 필수입니다.")
    private String address;

    @NotEmpty(message = "내용 입력은 필수입니다.")
    private String description;

    public Space toEntity(){ // Service 코드가 간결해짐!
        return Space.builder()
                .name(this.name)
                .spaceCategory(this.spaceCategory)
                .address(this.address)
                .description(this.description)
                .build();
    }
}
