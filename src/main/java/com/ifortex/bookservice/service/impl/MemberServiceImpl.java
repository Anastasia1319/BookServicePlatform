package com.ifortex.bookservice.service.impl;

import com.ifortex.bookservice.data.MemberRepository;
import com.ifortex.bookservice.model.Member;
import com.ifortex.bookservice.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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
        return null;
    }
}
