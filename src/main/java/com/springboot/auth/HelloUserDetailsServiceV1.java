//package com.springboot.auth;
//
//import com.springboot.auth.utils.HelloAuthorityUtils;
//import com.springboot.exception.BusinessLogicException;
//import com.springboot.exception.ExceptionCode;
//import com.springboot.member.Member;
//import com.springboot.member.MemberRepository;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//
////@Component
//public class HelloUserDetailsServiceV1 implements UserDetailsService {
//    private final MemberRepository memberRepository;
//    private final HelloAuthorityUtils authorityUtils;
//
//    public HelloUserDetailsServiceV1(MemberRepository memberRepository, HelloAuthorityUtils authorityUtils) {
//        this.memberRepository = memberRepository;
//        this.authorityUtils = authorityUtils;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        DB에서 불러와 userName으로(우리의 경우 아이디는 이메일)
//        Optional<Member> optionalMember = memberRepository.findByEmail(username);
//        Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
//
////        권한 부여하고
////        List<GrantedAuthority> authorities = authorityUtils.createAuthorities(findMember.getEmail());
//        Collection<? extends GrantedAuthority> authorities = authorityUtils.createAuthorities(findMember.getEmail());
////        UserDetails 형태의 객체로 만들어 반환
////        UserDetails는 인터페이스(추상화), 구현체로 User 객체 만든다.
////         findMember 가 DB에서 가져옴. 애초에 암호화 된 비밀번호가 들어온다.
//        return new User(findMember.getEmail(), findMember.getPassword(), authorities);
//    }
//}
