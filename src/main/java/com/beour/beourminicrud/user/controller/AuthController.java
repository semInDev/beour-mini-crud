package com.beour.beourminicrud.user.controller;

import com.beour.beourminicrud.user.dto.LoginRequestDto;
import com.beour.beourminicrud.user.dto.UserResponseDto;
import com.beour.beourminicrud.user.entity.CustomUserDetails;
import com.beour.beourminicrud.user.entity.User;
import com.beour.beourminicrud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        // 요청을 받아서 UsernamePasswordAuthenticationToken 생성
        // 이 토큰을 AuthenticationManager에 전달 → 실제 인증 프로세스 시작
        // AuthenticationManager → CustomUserDetailsService 호출
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        // Authentication 객체에 CustomUserDetails가 principal로 저장됨
        // AuthController에서 authentication.getPrincipal() 꺼내 UserResponseDto로 변환 후 반환
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        return ResponseEntity.ok(UserResponseDto.fromEntity(user));
    }
}
