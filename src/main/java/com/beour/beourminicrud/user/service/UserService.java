package com.beour.beourminicrud.user.service;

import com.beour.beourminicrud.global.exception.EmailAlreadyExistsException;
import com.beour.beourminicrud.user.dto.UserRequestDto;
import com.beour.beourminicrud.user.dto.UserResponseDto;
import com.beour.beourminicrud.user.entity.User;
import com.beour.beourminicrud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto register(UserRequestDto userRequestDto) {
        // 이메일 중복 확인 + 커스텀 예외 생성
        if(userRepository.findByEmail(userRequestDto.getEmail()).isPresent())
            throw new EmailAlreadyExistsException(userRequestDto.getEmail());

        // 유저 생성(+비밀번호 인코딩)
        User newUser = User.builder()
                .email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .role(userRequestDto.getRole())
                .build();

        // 유저 저장 및 리턴
        return UserResponseDto.fromEntity(userRepository.save(newUser));
    }
}
