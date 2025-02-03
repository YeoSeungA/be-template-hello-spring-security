package com.springboot.auth.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HelloAuthorityUtils {
//    환경변수 불러옴 yml 파일
    @Value("${mail.address.admin}")
    private String adminMailAddress;
//GrantedAuthority의 특징 ROLE_ 가 있어야한다. DB에는 ADMIN, USER 로 저장된다.
//    ROLE_ 붙을시 DB는 SPRING에 종속된다.
    private final List<GrantedAuthority> ADMIN_ROLES =
            AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");

    private final List<GrantedAuthority> USER_ROLES =
            AuthorityUtils.createAuthorityList("ROLE_USER");
//    String을 GrantedAuthority로 바꾸자!
    private final List<String> ADMIN_ROLES_STRING = List.of("ADMIN", "USER");
    private final List<String> USER_ROLES_STRING = List.of("USER");

    public List<String> creteRoles(String email) {
        if(email.equals(adminMailAddress)) {
            return ADMIN_ROLES_STRING;
        } else {
            return USER_ROLES_STRING;
        }
    }

//    public List<GrantedAuthority> createAuthorities(String email) {
//        if(email.equals(adminMailAddress)) {
//            return ADMIN_ROLES;
//        } else {
//            return USER_ROLES;
//        }
//    }

    public List<GrantedAuthority> createAuthorities(List<String> roles) {
//        List를 순회하며 String을 GrantedAuthority로 바꿔서 모두 다시 List로 패키징 후 반환
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}
