package com.ifortex.bookservice.service.impl;

import com.ifortex.bookservice.data.MemberRepository;
import com.ifortex.bookservice.model.Member;
import com.ifortex.bookservice.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Primary
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private MemberRepository memberRepository;

    @Override
    public Member findMember() {
        String genre = "Romance";
        List<Member> result = memberRepository.findOldestRomanceReader(genre);
        return result.isEmpty() ? new Member() : result.get(0);
    }

    @Override
    public List<Member> findMembers() {
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999999);
        return memberRepository.findMembersNotReadBooks(startDate, endDate);
    }
}
