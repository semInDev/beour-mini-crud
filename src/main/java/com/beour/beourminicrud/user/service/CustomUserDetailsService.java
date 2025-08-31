package com.beour.beourminicrud.user.service;

import com.beour.beourminicrud.user.entity.CustomUserDetails;
import com.beour.beourminicrud.user.entity.User;
import com.beour.beourminicrud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Spring Security는 UserDetailsService를 사용해 email로 DB 사용자 조회
        // UserRepository에서 해당 이메일을 가진 User 엔티티를 가져옴
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // DB에서 조회한 User를 CustomUserDetails로 감싸서 반환
        // 이 객체는 Spring Security가 인증 정보를 다룰 수 있는 형태
        return new CustomUserDetails(user);
    }
}
