package com.beour.beourminicrud.space.entity;

import com.beour.beourminicrud.space.enums.SpaceCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // private Long hostId;
    private String name;

    @Enumerated(EnumType.STRING) // 이거 없으면 DB에 ordinal(숫자) 값으로 저장됨. enum 순서 바꾸면 데이터 꼬임
    private SpaceCategory spaceCategory;
    private String address;
    private String description;
}
