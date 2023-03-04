package com.example.celog.post.service;

import com.example.celog.post.entity.Member;
import com.example.celog.post.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    @Transactional
    public void addMember() {
        Member member = Member.builder().email("aaa@naver.com").password("1234").nickname("aaa").build();
        Member member2 = Member.builder().email("bbb@naver.com").password("1234").nickname("bbb").build();
        Member member3 = Member.builder().email("ccc@naver.com").password("1234").nickname("ccc").build();
        Member member4 = Member.builder().email("ddd@naver.com").password("1234").nickname("ddd").build();

        memberRepository.save(member);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
    }
}
