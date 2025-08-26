package com.beour.beourminicrud.user.dto;

import com.beour.beourminicrud.user.enums.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserRequestDto {
    @NotEmpty(message = "이메일 입력은 필수입니다.")
    private String email;

    @NotEmpty(message = "비밀번호 입력은 필수입니다.")
    private String password;

    @NotNull(message = "역할 선택은 필수입니다.")
    private Role role;
}
