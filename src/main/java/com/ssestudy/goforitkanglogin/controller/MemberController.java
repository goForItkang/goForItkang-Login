package com.ssestudy.goforitkanglogin.controller;

import com.ssestudy.goforitkanglogin.dto.MemberDTO;
import com.ssestudy.goforitkanglogin.props.JwtUtil;
import com.ssestudy.goforitkanglogin.service.CustomUserDetailsService;
import com.ssestudy.goforitkanglogin.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/api/signup")
    public ResponseEntity<String> signup(@RequestBody MemberDTO memberDTO) {
        memberService.join(memberDTO);
        return ResponseEntity.ok().body("success");
    }
    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody MemberDTO memberDTO) {
        UserDetails result = customUserDetailsService.loadUserByUsername(memberDTO.getEmail());
        if (!passwordEncoder.matches(memberDTO.getPassword(), result.getPassword())) {
            return ResponseEntity.status(401).body("사용자 정보가 없습니다");
        }
        HttpHeaders headers = new HttpHeaders();
        String token = jwtUtil.createToken(result.getUsername());
        headers.set("Authorization","Bearer "+token);
        return ResponseEntity.ok().headers(headers).body(token);
    }
    @GetMapping("/api/user/test")
    public ResponseEntity<String> userTest() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok().body("success");
    }
    @GetMapping("/api/admin/test")
    public ResponseEntity<String> AdminTest() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok().body("success");
    }


}
