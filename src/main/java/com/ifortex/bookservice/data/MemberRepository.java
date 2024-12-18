package com.ifortex.bookservice.data;

import com.ifortex.bookservice.model.Member;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberRepository {
    List<Member> findOldestRomanceReader(String genre);
    List<Member> findMembersNotReadBooks(LocalDateTime startDate, LocalDateTime endDate);
}
