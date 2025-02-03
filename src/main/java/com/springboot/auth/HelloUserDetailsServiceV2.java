package com.springboot.auth;

import com.springboot.auth.utils.HelloAuthorityUtils;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.Member;
import com.springboot.member.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

    @Component
    public class HelloUserDetailsServiceV2 implements UserDetailsService {
        private final MemberRepository memberRepository;
        private final HelloAuthorityUtils authorityUtils;

        public HelloUserDetailsServiceV2(MemberRepository memberRepository, HelloAuthorityUtils authorityUtils) {
            this.memberRepository = memberRepository;
            this.authorityUtils = authorityUtils;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        DB에서 불러와 userName으로(우리의 경우 아이디는 이메일)
            Optional<Member> optionalMember = memberRepository.findByEmail(username);
            Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

            return new HelloUserDetails(findMember);

////            하는일이 너무 많아서 캡슐화 해서 아래에 만들어 놓은다.
////        권한 부여하고
////        List<GrantedAuthority> authorities = authorityUtils.createAuthorities(findMember.getEmail());
//            Collection<? extends GrantedAuthority> authorities = authorityUtils.createAuthorities(findMember.getEmail());
////        UserDetails 형태의 객체로 만들어 반환
////        UserDetails는 인터페이스(추상화), 구현체로 User 객체 만든다.
////         findMember 가 DB에서 가져옴. 애초에 암호화 된 비밀번호가 들어온다.
//            return new User(findMember.getEmail(), findMember.getPassword(), authorities);
        }

        private final class HelloUserDetails extends Member implements UserDetails {

            public HelloUserDetails(Member member) {
                setMemberId(member.getMemberId());
                setFullName(member.getFullName());
                setEmail(member.getEmail());
                setPassword(member.getPassword());
                setRoles(member.getRoles());
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return authorityUtils.createAuthorities(this.getRoles());
            }

            @Override
            public String getUsername() {
                return this.getEmail();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        }
    }

