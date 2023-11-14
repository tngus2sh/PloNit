package com.plonit.plonitservice.api.badge.service.impl;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.plonit.plonitservice.api.badge.service.BadgeService;
import com.plonit.plonitservice.api.badge.service.dto.*;
import com.plonit.plonitservice.common.AwsS3Uploader;
import com.plonit.plonitservice.common.enums.BadgeCode;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.domain.badge.Badge;
import com.plonit.plonitservice.domain.badge.BadgeCondition;
import com.plonit.plonitservice.domain.badge.CrewBadge;
import com.plonit.plonitservice.domain.badge.MemberBadge;
import com.plonit.plonitservice.domain.badge.repository.BadgeConditionRepository;
import com.plonit.plonitservice.domain.badge.repository.BadgeRepository;
import com.plonit.plonitservice.domain.badge.repository.CrewBadgeRepository;
import com.plonit.plonitservice.domain.badge.repository.MemberBadgeRepository;
import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.crew.repository.CrewRepository;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.plonit.plonitservice.common.exception.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class BadgeServiceImpl implements BadgeService {
    
    private final MemberRepository memberRepository;
    private final CrewRepository crewRepository;
    private final BadgeRepository badgeRepository;
    private final BadgeConditionRepository badgeConditionRepository;
    private final MemberBadgeRepository memberBadgeRepository;
    private final CrewBadgeRepository crewBadgeRepository;
    private final AwsS3Uploader awsS3Uploader;

    @Override
    @Transactional
    public void saveBadge(List<BadgeDto> badgeDtos) {

        List<BadgeCondition> badgeConditions = new ArrayList<>();
        List<Badge> badges = new ArrayList<>();
        
        for (BadgeDto badgeDto : badgeDtos) {
            String badgeImage = null;
            if (badgeDto.getImage() != null) {
                try {
                    badgeImage = awsS3Uploader.uploadFile(badgeDto.getImage(), "badge/badgeImage");
                } catch (IOException e) {
                    throw new CustomException(INVALID_FIELDS_REQUEST);
                }
            }
            // 뱃지 상태 저장
            BadgeCondition badgeCondition = BadgeDto.toEntity(badgeDto);
            badgeConditions.add(badgeCondition);
            
            // 뱃지 저장
            badges.add(BadgeDto.toEntity(badgeDto, badgeCondition, badgeImage));
            
        }
        
        badgeConditionRepository.saveAll(badgeConditions);
        badgeRepository.saveAll(badges);
    }

    @Override
    @Transactional
    public void saveBadgeByIndividual(List<MembersBadgeDto> membersBadgeDtos) {
        
        List<MemberBadge>  memberBadges = new ArrayList<>();
        
        for (MembersBadgeDto membersBadgeDto : membersBadgeDtos) {
            // 뱃지 가져오기
            Badge badge = badgeRepository.findById(membersBadgeDto.getBadgeId())
                    .orElseThrow(() -> new CustomException(INVALID_FIELDS_REQUEST));

            // 멤버 가져오기
            Member member = memberRepository.findById(membersBadgeDto.getMemberId())
                    .orElseThrow(() -> new CustomException(INVALID_FIELDS_REQUEST));

            memberBadges.add(MembersBadgeDto.toEntity(badge, member));
        }

        memberBadgeRepository.saveAll(memberBadges);
    }

    @Override
    @Transactional
    public void saveBadgeByCrew(List<CrewBadgeDto> crewBadgeDtos) {
        
        List<CrewBadge> crewBadges = new ArrayList<>();

        for (CrewBadgeDto crewBadgeDto : crewBadgeDtos) {
            // 뱃지 가져오기
            Badge badge = badgeRepository.findById(crewBadgeDto.getBadgeId())
                    .orElseThrow(() -> new CustomException(INVALID_FIELDS_REQUEST));

            // 크루 가져오기
            Crew crew = crewRepository.findById(crewBadgeDto.getCrewId())
                    .orElseThrow(() -> new CustomException(INVALID_FIELDS_REQUEST));

            crewBadges.add(CrewBadgeDto.toEntity(badge, crew));
        }

        crewBadgeRepository.saveAll(crewBadges);

    }

    @Transactional
    @Override
    public void grantBadgeByIndividual(GrantMemberBadgeDto grantMemberBadgeDto) {
        // 플로깅 횟수  배지 부여
        doGrantCountBadge(grantMemberBadgeDto.getPloggingCount(), grantMemberBadgeDto.getMemberKey());

        // 플로깅 거리 배지 부여
        doGrantDistanceBadge(grantMemberBadgeDto.getDistance(), grantMemberBadgeDto.getMemberKey());

    }

    /* 개인 랭킹 */
    @Transactional
    @Override
    public void grantRankBadge(GrantMemberRankDto grantMemberRankDto) {
        Integer rank = grantMemberRankDto.getRank();

        if (rank == 1) {
            saveMemberBadge(BadgeCode.RANK_1, grantMemberRankDto.getMemberKey());
        } else if (rank == 2) {
            saveMemberBadge(BadgeCode.RANK_2, grantMemberRankDto.getMemberKey());
        } else if (rank == 3) {
            saveMemberBadge(BadgeCode.RANK_3, grantMemberRankDto.getMemberKey());
        }
    }

    /* 크루 랭킹 */
    @Transactional
    @Override
    public void grantCrewRankBadge(GrantCrewRankDto grantCrewRankDto) {
        Integer rank = grantCrewRankDto.getRank();

        if (rank == 1) {
            saveCrewBadge(BadgeCode.RANK_1, grantCrewRankDto.getCrewId());
        } else if (rank == 2) {
            saveCrewBadge(BadgeCode.RANK_2, grantCrewRankDto.getCrewId());
        } else if (rank == 3) {
            saveCrewBadge(BadgeCode.RANK_3, grantCrewRankDto.getCrewId());
        }
    }

    @Transactional
    @Override
    public void doGrantCountBadge(int value, Long memberKey) {
        // 1회, 10회, 50회, 100회
        if (value == 100) {
            saveMemberBadge(BadgeCode.COUNT_100, memberKey);
        } else if (value == 50) {
            saveMemberBadge(BadgeCode.COUNT_50, memberKey);
        } else if (value == 10) {
            saveMemberBadge(BadgeCode.COUNT_10, memberKey);
        } else if (value == 1) {
            saveMemberBadge(BadgeCode.COUNT_1, memberKey);
        }
    }
    /* 플로깅 거리 */

    @Transactional
    @Override
    public void doGrantDistanceBadge(Double distance, Long memberKey) {
        // 1km, 10km, 20km, 30km, 50km, 100km
        if (distance >= 1) {
            saveMemberBadgeByDistance(BadgeCode.DISTANCE_1, memberKey);
        }

        if (distance >= 10) {
            saveMemberBadgeByDistance(BadgeCode.DISTANCE_10, memberKey);
        }

        if (distance >= 20) {
            saveMemberBadgeByDistance(BadgeCode.DISTANCE_20, memberKey);
        }

        if (distance >= 30) {
            saveMemberBadgeByDistance(BadgeCode.DISTANCE_30, memberKey);
        }

        if (distance >= 50) {
            saveMemberBadgeByDistance(BadgeCode.DISTANCE_50, memberKey);
        }

        if (distance >= 100) {
            saveMemberBadgeByDistance(BadgeCode.DISTANCE_100, memberKey);
        }
    }


    /* 횟수 배지 저장 */
    @Transactional
    @Override
    public void saveMemberBadge(BadgeCode code, Long memberKey) {
        Badge badge = badgeRepository.findByCode(BadgeCode.COUNT_1)
                .orElseThrow(() -> new CustomException(BADGE_NOT_FOUND));

        Member member = memberRepository.findById(memberKey)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        memberBadgeRepository.save(MemberBadge.builder()
                .badge(badge)
                .member(member)
                .build());
    }

    /* 거리 배지 저장 */
    @Transactional
    @Override
    public void saveMemberBadgeByDistance(BadgeCode code, Long memberKey) {
        Badge badge = badgeRepository.findByCode(code)
                .orElseThrow(() -> new CustomException(BADGE_NOT_FOUND));

        // 기존에 배지가 있는지 확인
        Optional<Long> badgeId = memberBadgeRepository.existById(badge.getId());
        if (badgeId.isEmpty()) {
            Member member = memberRepository.findById(memberKey)
                    .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

            memberBadgeRepository.save(MemberBadge.builder()
                    .badge(badge)
                    .member(member)
                    .build());
        }
    }

    /* 랭킹 */
    @Transactional
    @Override
    public void saveCrewBadge(BadgeCode code, Long crewId) {
        Badge badge = badgeRepository.findByCode(BadgeCode.COUNT_1)
                .orElseThrow(() -> new CustomException(BADGE_NOT_FOUND));

        Crew crew = crewRepository.findById(crewId)
                .orElseThrow(() -> new CustomException(CREW_NOT_FOUND));

        crewBadgeRepository.save(CrewBadge.builder()
                .badge(badge)
                .crew(crew)
                .build());
    }

}
