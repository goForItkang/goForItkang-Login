package com.ssestudy.goforitkanglogin.service;

import com.ssestudy.goforitkanglogin.dao.MemberRepository;
import com.ssestudy.goforitkanglogin.dto.MemberDTO;
import com.ssestudy.goforitkanglogin.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(MemberDTO memberDTO) {
        Member member = new Member();
        member.setEmail(memberDTO.getEmail());
        // password 암호화
        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        member.setMemberName(memberDTO.getMemberName());
        member.setRole(memberDTO.getRole());
        memberRepository.save(member);
    }

}
