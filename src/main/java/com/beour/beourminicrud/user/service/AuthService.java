package com.beour.beourminicrud.user.service;

import com.beour.beourminicrud.global.jwt.JwtTokenProvider;
import com.beour.beourminicrud.user.dto.LoginRequestDto;
import com.beour.beourminicrud.user.dto.TokenResponseDto;
import com.beour.beourminicrud.user.entity.User;
import com.beour.beourminicrud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public TokenResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail(), user.getRole().name());

        return new TokenResponseDto(accessToken, refreshToken);
    }

    public TokenResponseDto refreshToken(String header) {
        String token = header.replace("Bearer ", "");
        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String email = jwtTokenProvider.getEmailFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtTokenProvider.generateAccessToken(user.getEmail(), user.getRole().name());
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail(), user.getRole().name());

        return new TokenResponseDto(newAccessToken, newRefreshToken);
    }
}
