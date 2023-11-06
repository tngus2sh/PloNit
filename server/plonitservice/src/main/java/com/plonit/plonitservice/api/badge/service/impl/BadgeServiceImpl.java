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
import com.plonit.plonitservice.domain.badge.repository.BadgeConditionRepository;
import com.plonit.plonitservice.domain.badge.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BadgeServiceImpl implements BadgeService {
    
    private final BadgeRepository badgeRepository;
    private final BadgeConditionRepository badgeConditionRepository;
    private final AwsS3Uploader awsS3Uploader;

    @Override
    public void saveBadge(List<BadgeDto> badgeDtos) {

        List<BadgeCondition> badgeConditions = new ArrayList<>();
        List<Badge> badges = new ArrayList<>();
        
        for (BadgeDto badgeDto : badgeDtos) {
            String badgeImage = null;
            if (badgeDto.getImage() != null) {
                try {
                    badgeImage = awsS3Uploader.uploadFile(badgeDto.getImage(), "badge/badgeImage");
                } catch (IOException e) {
                    throw new CustomException(ErrorCode.INVALID_FIELDS_REQUEST);
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
    public void saveBadgeByIndividual(List<MembersBadgeDto> membersBadgeDtos) {

    }

    @Override
    public void saveBadgeByCrew(List<CrewBadgeDto> crewBadgeDtos) {

    }
}
