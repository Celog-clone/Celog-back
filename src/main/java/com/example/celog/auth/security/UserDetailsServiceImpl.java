package com.example.celog.auth.security;

import com.example.celog.exception.CustomException;
import com.example.celog.member.entity.Member;
import com.example.celog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.celog.exception.enumclass.Error.NOT_EXIST_USER;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByEmail(useremail).orElseThrow(
                () -> new CustomException(NOT_EXIST_USER));
        return new UserDetailsImpl(findMember, findMember.getEmail());
    }

}