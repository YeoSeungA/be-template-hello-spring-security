package com.springboot.member;

import com.springboot.auth.utils.HelloAuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DBMemberService implements MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final HelloAuthorityUtils authorityUtils;

    public DBMemberService(PasswordEncoder passwordEncoder, MemberRepository memberRepository, HelloAuthorityUtils authorityUtils) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
        this.authorityUtils = authorityUtils;
    }

    @Override
    public Member createMember(Member member) {
//        verifiyExistEmail(member.getEmail());
//        password는 꼭 암호화 된것으로 관리해야 한다.
        String encyptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encyptedPassword);

        List<String> roles = authorityUtils.creteRoles(member.getEmail());
        member.setRoles(roles);

        Member savedMember = memberRepository.save(member);
        return savedMember;
    }
}
