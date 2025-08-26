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
    @Column(nullable = false) // DTO에서도 체크하긴 하지만, Entity에도 설정해주면 안정성 있고, DB 무결성 보장 가능!
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // 이거 없으면 DB에 ordinal(숫자) 값으로 저장됨. enum 순서 바꾸면 데이터 꼬임
    private SpaceCategory spaceCategory;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String description;

    public void update(String name, SpaceCategory spaceCategory, String address, String description) {
        this.name = name;
        this.spaceCategory = spaceCategory;
        this.address = address;
        this.description = description;
    }
}
