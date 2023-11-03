package com.plonit.plonitservice.domain.crew.repository;

import com.plonit.plonitservice.domain.crew.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {
    Optional<CrewMember> findById(Long id);

    @Query("SELECT cm FROM CrewMember cm JOIN FETCH cm.crew WHERE cm.member.id = :memberId AND cm.crew.id = :crewId")
    Optional<CrewMember> findByFetch(Long memberId, Long crewId);

}