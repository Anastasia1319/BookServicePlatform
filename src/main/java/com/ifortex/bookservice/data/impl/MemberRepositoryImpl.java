package com.ifortex.bookservice.data.impl;

import com.ifortex.bookservice.data.MemberRepository;
import com.ifortex.bookservice.model.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private static final String FIND_OLDEST_ROMANCE_READER_QUERY =
            "SELECT m.id, m.name, m.membership_date " +
                    "FROM members m " +
                    "JOIN member_books mb ON m.id = mb.member_id " +
                    "JOIN books b ON mb.book_id = b.id " +
                    "AND book_id = (SELECT b.id FROM books b " +
                    "WHERE array_position(b.genre, :genre) IS NOT NULL ORDER BY b.publication_date LIMIT 1) " +
                    "ORDER BY m.membership_date DESC LIMIT 1";

    private static final String FIND_MEMBERS_NOT_READ_BOOKS_QUERY =
            "SELECT m FROM Member m " +
                    "LEFT JOIN m.borrowedBooks mb " +
                    "WHERE m.membershipDate BETWEEN :startDate AND :endDate " +
                    "AND mb IS NULL";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Member> findOldestRomanceReader(String genre) {
        return entityManager.createNativeQuery(FIND_OLDEST_ROMANCE_READER_QUERY, Member.class)
                .setParameter("genre", genre)
                .getResultList();
    }

    @Override
    public List<Member> findMembersNotReadBooks(LocalDateTime startDate, LocalDateTime endDate) {
        return entityManager.createQuery(FIND_MEMBERS_NOT_READ_BOOKS_QUERY, Member.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}
