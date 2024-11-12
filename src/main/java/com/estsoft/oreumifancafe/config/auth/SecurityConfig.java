package com.estsoft.oreumifancafe.config.auth;

import com.estsoft.oreumifancafe.aop.auth.*;
import com.estsoft.oreumifancafe.service.auth.CustomUserDetailsService;
import com.estsoft.oreumifancafe.service.board.BoardService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
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
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, BoardService boardService) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                                // 회원가입 페이지, 회원가입 누구나 가능
                                // 로그아웃은 session이 없어도 그냥 통과되게
                                .requestMatchers("/user/signup", "/user", "/user/logout").permitAll()
                                // 게스트만
                                .requestMatchers("/user/updateInfo", "/board/new-article").hasRole("GUEST")
                                .requestMatchers(HttpMethod.GET, "/help").hasRole("GUEST")
                                .requestMatchers(HttpMethod.POST, "/board", "/help/question").hasRole("GUEST")
                                // 회원정보에대한 수정, 조회, 삭제는 자기 자신인지 검사하는 access에 걸림
                                .requestMatchers("/user/{userId}")
                                .access(new CustomAuthorizationManager())
                                .requestMatchers(HttpMethod.DELETE,"/board/{id}")
                                .access(new CustomAuthorizationBoardManager(boardService))
                                .requestMatchers(HttpMethod.GET, "/board/article/edit/{id}")
                                .access(new CustomAuthorizationBoardManager(boardService))
                                .requestMatchers(HttpMethod.PUT, "/board/article/edit/{id}")
                                .access(new CustomAuthorizationBoardManager(boardService))
                                .requestMatchers("/admin/**").hasRole("ADMIN")      // 관리자
//                        .requestMatchers("/").hasAnyRole("CELEBRITY", "ADMIN") // 연예인과 관리자가 접근할 수 있는 페이지 설정
                                .anyRequest().permitAll()
                )
                .formLogin(auth -> auth
                        .loginProcessingUrl("/user/login")
                        .usernameParameter("userId")
                        .passwordParameter("userPw")
                        .successHandler(new CustomAuthenticationSuccessHandler())
                        .failureHandler(new CustomAuthenticationFailureHandler())
                )
                .logout(auth -> auth
                        .logoutSuccessUrl("/logout") // 로그아웃 성공시 자동으로 이동할 페이지 URL
                        .invalidateHttpSession(true)
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            if ("DELETE".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) {
                                // DELETE와 PUT 요청은 리다이렉트 대신 상태 코드 반환
//                                response.sendRedirect("/accessDenied");
//                            } else {
                                // GET 요청 등은 리다이렉트 처리
                                response.sendRedirect("/accessDenied");
//                            }
                        })
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )
                .csrf(auth -> auth.disable()) // CSRF 비활성화
                .cors(Customizer.withDefaults()) // CORS 활성화
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, CustomUserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }
}
