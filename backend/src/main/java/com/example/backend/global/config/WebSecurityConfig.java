package com.example.backend.global.config;

import com.example.backend.global.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Component
public class WebSecurityConfig {

    // JWT 토큰 인증 필터 주입
    private final JwtTokenFilter jwtTokenFilter;

    // 비밀번호 암호화를 위한 인코더 (BCrypt 사용)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())              // CORS 설정 적용
                .csrf(csrf -> csrf.disable())                // CSRF 비활성화 (JWT 사용 시 불필요)
                .formLogin(form -> form.disable())           // 폼 로그인 비활성화
                .httpBasic(basic -> basic.disable())         // HTTP Basic 비활성화
                .authorizeHttpRequests((request) -> request
                        .requestMatchers(
                                "/api/login",
                                "/api/register",
                                "/api/refresh",
                                "/api/logout"
                        ).permitAll()                        // 인증 없이 허용할 경로
                        .requestMatchers("/reviews/movie/*").permitAll()
                        .anyRequest().authenticated()        // 그 외는 인증 필요
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class); // JWT 필터 적용

        return http.build();
    }

    // CORS 허용 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 프론트엔드 Vite 개발 서버 주소
        config.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://52.79.198.3",
                "http://cinecampus.kro.kr",
                "http://www.cinecampus.kro.kr"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
