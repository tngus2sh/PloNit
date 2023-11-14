package com.plonit.plonitservice.domain.crew.repository;

import com.plonit.plonitservice.domain.crew.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {
    Optional<CrewMember> findById(Long id);

    @Query("SELECT cm.crew.id FROM CrewMember cm WHERE cm.member.id = :memberId")
    List<Long> findCrewMemberByMemberId(Long memberId);

    @Query("SELECT cm FROM CrewMember cm JOIN FETCH cm.crew JOIN FETCH cm.member WHERE cm.member.id = :memberId AND cm.crew.id = :crewId")
    Optional<CrewMember> findCrewMemberByJoinFetch(Long memberId, Long crewId);
}