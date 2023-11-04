package com.plonit.plonitservice.domain.crewping.repository;

import com.plonit.plonitservice.domain.crewping.CrewpingMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewpingMemberRepository extends JpaRepository<CrewpingMember, Long> {
}
