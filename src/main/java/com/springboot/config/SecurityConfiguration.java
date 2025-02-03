package com.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfiguration {
//    보안 구성 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
//                csrg 보안 설정을 끔. local에서 사용시 끔
                .csrf().disable()
//                form 로그인 방식으로 인증을 처리한다.
                .formLogin()
//                login 페이지 설정한다. 너가 만든거 말고 우리가 만든 페이지를 사용할것입니다.
                .loginPage("/auths/login-form")
//                로그인을 수행할 페이지
                .loginProcessingUrl("/process_login")
//                로그인 실패시 요 위치로 보내주세요
                .failureUrl("/auths/login-form?error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
//                권한이 없는 페이지로 접근시 "/auths/access-denied" 여기로 접근해주세요.
                .exceptionHandling().accessDeniedPage("/auths/access-denied")
                .and()
//                다음은 인가 부분이다.
//                요청에 따라 인가를 나눌것이다.
//                순서를 지켜야 한다. 위 -> 아래 먼저 거를거를 위로 먼저 check 할 것을 맨 위에 둬야한다.
                .authorizeHttpRequests(authorize->authorize
//                        **  : 하위 어떤게 오더라도 ADMIN 권한만 접근할 수 있습니다.
                        .antMatchers("/orders/**").hasRole("ADMIN")
                        .antMatchers("/members/my-page").hasRole("USER")
                        .antMatchers("/**").permitAll()
                );

        return http.build();
    }


////    회원 생성 코드
//    @Bean
//    public UserDetailsManager userDetailsManager() {
//        UserDetails userDetails = User.withDefaultPasswordEncoder()
//                .username("bana@rabbit.com")
//                .password("1234")
//                .roles("USER")
//                .build();
//// 관리자는 my-page가 나오면 안된다.
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin@google.com")
//                .password("qwer")
//                .roles("ADMIN")
//                .build();
//
////        UserDetailsManager의 구현체
//        return new InMemoryUserDetailsManager(userDetails, admin);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        createDelegatingPasswordEncoder() 얘가 구현체
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
