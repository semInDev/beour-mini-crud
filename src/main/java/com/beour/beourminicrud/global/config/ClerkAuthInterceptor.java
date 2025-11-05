package com.beour.beourminicrud.global.config;

import com.beour.beourminicrud.user.service.ClerkAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class ClerkAuthInterceptor implements HandlerInterceptor {

    private final ClerkAuthService clerkAuthService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String token = header.substring(7);

        // Clerk JWT 검증
        var claims = clerkAuthService.verifyClerkToken(token);
        if (claims == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 요청 속성에 clerkId 저장
        request.setAttribute("clerkUserId", claims.getSubject());
        return true;
    }
}
