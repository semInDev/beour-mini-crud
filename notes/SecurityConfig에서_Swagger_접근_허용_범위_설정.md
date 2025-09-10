# Swagger UI 접근 불가 오류 보고서

### 1. 문제 개요

- **오류 상황**:
    
    `http://localhost:8080/swagger-ui/index.html`에 접속 시 Swagger UI 페이지가 열리지 않고 `403 Forbidden` 에러가 발생함.
    
    이는 Swagger 자체의 오류가 아니라 Spring Security가 Swagger 관련 리소스 요청을 차단하여 Swagger UI가 정상적으로 로딩되지 못한 상황임.
    
- **발생 배경**:
    
    프로젝트(`beour-mini-crud`)에 Swagger(Springdoc OpenAPI)를 적용한 뒤 API 문서 페이지에 접속을 시도하면서 발생.
    
    초기 SecurityConfig에서 `/swagger-ui/index.html` 경로만 허용하였으나, Swagger UI는 이외에도 여러 정적 리소스와 API 문서 엔드포인트를 사용하기 때문에 발생한 문제임.
    

---

### 2. 오류 코드 및 오류 메시지

- **오류 코드**: `403 Forbidden`
- **오류 메시지** (로그 일부):
    
    ```
    2025-09-10T11:27:13.865+09:00 DEBUG ... Securing GET /error
    2025-09-10T11:27:13.866+09:00 DEBUG ... Set SecurityContextHolder to anonymous SecurityContext
    2025-09-10T11:27:13.867+09:00 DEBUG ... Saved request http://localhost:8080/error?continue to session
    2025-09-10T11:27:13.867+09:00 DEBUG ... Pre-authenticated entry point called. Rejecting access
    
    ```
    
- **관련 로그 해석**:
    
    Spring Security가 `/swagger-ui/index.html` 외의 Swagger 리소스 요청을 차단 → 익명 사용자로 처리 → 인증이 필요하다고 판단 → 접근 거부(403).
    

---

### 3. 원인 분석

- **오류 원인**:
    
    SecurityConfig에서 Swagger 접근 허용 범위를 너무 제한적으로 설정했기 때문.
    
    Swagger UI는 단일 HTML(`index.html`)만 불러오는 것이 아니라 다음과 같은 리소스를 동시에 요청함:
    
    - `/swagger-ui/**` (JS, CSS, 정적 리소스)
    - `/v3/api-docs/**` (API 문서 JSON 엔드포인트)
    - `/swagger-resources/**` (Swagger 리소스)
    - `/webjars/**` (라이브러리 리소스)
- **에러 로그 해석**:
    
    `Securing GET /error` → Security FilterChain이 `/error` 요청까지 가로채며, 인증이 필요하다고 판단
    
    `Rejecting access` → 익명 사용자라서 거부
    
- **구조적 이해**:
    - **Spring Boot**: REST API 및 보안 관리
    - **Spring Security**: 요청 경로별 권한 제어
    - **Springdoc OpenAPI (Swagger)**: API 문서 자동화 및 UI 제공
    - 문제는 Swagger 리소스가 Spring Security의 필터링을 거치며 인증 요구 조건에 걸린 구조에서 발생함.

---

### 4. 해결책

- **단계별 조치 방법**:
    - **Step 1**: SecurityConfig에서 Swagger 관련 URL을 전부 `permitAll()`로 추가
    - **Step 2**: `/signup`, `/login` 등 인증이 필요 없는 URL도 함께 허용
    - **Step 3**: 서버 재시작 후 Swagger UI 접속 확인
- **코드 예시**:
    
    ```java
    @Configuration
    @EnableWebSecurity
    @RequiredArgsConstructor
    public class SecurityConfig {
    
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                            // Swagger 관련 URL 허용
                            .requestMatchers(
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**",
                                    "/swagger-resources/**",
                                    "/webjars/**"
                            ).permitAll()
                            // 회원가입/로그인 허용
                            .requestMatchers("/signup", "/login").permitAll()
                            // 비즈니스 API 권한
                            .requestMatchers("/spaces/**").hasAuthority("HOST")
                            // 나머지는 인증 필요
                            .anyRequest().authenticated()
                    )
                    .formLogin(form -> form.disable());
    
            return http.build();
        }
    
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }
    }
    
    ```
    
- **추가 자료**:
    - [Springdoc OpenAPI 공식 문서](https://springdoc.org/)
    - [Spring Security 공식 문서](https://docs.spring.io/spring-security/reference/)
