package com.beour.beourminicrud.user.dto;


import com.beour.beourminicrud.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter // Jackson이 직렬화하여 JSON을 변환함.
@Builder
public class UserResponseDto {
    private Long id;
    private String email;
    // private String password; // 보안상 비밀번호 응답은 하지 말 것.
    private String role;

    public static UserResponseDto fromEntity(User user) {
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().getText())
                .build();
        return userResponseDto;
    }
}
