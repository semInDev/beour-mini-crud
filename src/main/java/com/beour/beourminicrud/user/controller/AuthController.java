package com.beour.beourminicrud.user.controller;

import com.beour.beourminicrud.user.dto.LoginRequestDto;
import com.beour.beourminicrud.user.dto.TokenResponseDto;
import com.beour.beourminicrud.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody LoginRequestDto request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public TokenResponseDto refresh(@RequestHeader("Authorization") String refreshToken) {
        return authService.refreshToken(refreshToken);
    }
}
