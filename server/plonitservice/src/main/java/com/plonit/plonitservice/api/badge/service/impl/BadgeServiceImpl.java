package com.plonit.plonitservice.api.badge.service.impl;

import com.plonit.plonitservice.api.badge.service.BadgeService;
import com.plonit.plonitservice.api.badge.service.dto.BadgeDto;
import com.plonit.plonitservice.api.badge.service.dto.CrewBadgeDto;
import com.plonit.plonitservice.api.badge.service.dto.MembersBadgeDto;
import com.plonit.plonitservice.common.AwsS3Uploader;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.exception.ErrorCode;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.plonit.plonitservice.common.exception.ErrorCode.INVALID_FIELDS_REQUEST;

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
}
