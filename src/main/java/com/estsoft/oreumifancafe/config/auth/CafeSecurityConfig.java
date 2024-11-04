package com.estsoft.oreumifancafe.config.auth;

import com.estsoft.oreumifancafe.service.auth.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class CafeSecurityConfig {
    private final CustomUserDetailsService userDetailsService;

    // 스프링 시큐리티 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers("/resources/**");
    }

    // 패스워드 인코더
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").hasRole("GUEST")      // 준회원이 접근할 수 있는 페이지 설정
                        .requestMatchers("/").hasRole("USER")       // 정회원
                        .requestMatchers("/").hasRole("ELITE")      // 우수회원
                        .requestMatchers("/").hasRole("CELEBRITY")  // 연예인
                        .requestMatchers("/").hasRole("ADMIN")      // 관리자
                        .requestMatchers("/").hasAnyRole("CELEBRITY", "ADMIN") // 연예인과 관리자가 접근할 수 있는 페이지 설정
                        .anyRequest().permitAll()
                )
                .formLogin(auth -> auth
                        .loginPage("/login") // 로그인 페이지 URL
                        .defaultSuccessUrl("/main") // 로그인 성공시 자동으로 이동할 페이지 URL
                )
                .logout(auth -> auth
                        .logoutSuccessUrl("/logout") // 로그아웃 성공시 자동으로 이동할 페이지 URL
                        .invalidateHttpSession(true)
                )
                .csrf(auth -> auth.disable()) // CSRF 비활성화
                .build();
    }
}
