package com.plonit.plonitservice.domain.feed.repository;

import com.plonit.plonitservice.api.feed.service.dto.FeedCommentDto;
import com.plonit.plonitservice.api.feed.service.dto.FeedPictureDto;
import com.plonit.plonitservice.api.feed.controller.response.FindFeedRes;
import com.plonit.plonitservice.domain.feed.Feed;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.plonit.plonitservice.domain.feed.QFeed.feed;
import static com.plonit.plonitservice.domain.feed.QFeedPicture.feedPicture;
import static com.plonit.plonitservice.domain.feed.QComment.comment;
import static com.plonit.plonitservice.domain.feed.QLike.like;
import static com.plonit.plonitservice.domain.member.QMember.member;
import static com.plonit.plonitservice.domain.crew.QCrew.crew;
import static com.plonit.plonitservice.domain.crew.QCrewMember.crewMember;
import static com.querydsl.core.group.GroupBy.groupBy;

@Repository
public class FeedQueryRepository {

    private final JPAQueryFactory queryFactory;

    public FeedQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    public List<FindFeedRes> findFeedsWithPictureAndComment(Long memberKey, Long crewId) {
        // Feed 기본 정보와 연관된 Member 상세 정보를 조회
        List<Feed> feeds = queryFactory
                .selectFrom(feed)
                .join(feed.crew, crew)
                .join(feed.member, member).fetchJoin()
                .orderBy(feed.createdDate.desc())
                .where(feed.isDelete.eq(false), feed.crew.id.eq(crewId))
                .fetch();

        // 각 Feed ID를 기준으로 FeedPicture를 조회
        Map<Long, List<FeedPictureDto>> feedPicturesMap = queryFactory
                .selectFrom(feedPicture)
                .where(feedPicture.feed.id.in(feeds.stream().map(Feed::getId).collect(Collectors.toList())))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(
                        feedPicture -> feedPicture.getFeed().getId(),
                        Collectors.mapping(
                                picture -> FeedPictureDto.builder().feedPicture(picture.getImage()).build(),
                                Collectors.toList()
                        )
                ));

        // 각 Feed ID를 기준으로 FeedCommnets를 조회
        Map<Long, List<FeedCommentDto>> feedCommentMap = queryFactory
                .selectFrom(comment)
                .where(comment.feed.id.in(feeds.stream().map(Feed::getId).collect(Collectors.toList())))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(
                        feedComment -> feedComment.getFeed().getId(),
                        Collectors.mapping(
                                comment -> FeedCommentDto.builder().nickname(comment.getMember().getNickname())
                                        .commentId(comment.getId())
                                        .profileImage(comment.getMember().getProfileImage())
                                        .content(comment.getContent())
                                        .isMine(comment.getMember().getId().equals(memberKey))
                                        .build(),
                                Collectors.toList()
                        )
                ));

        // 각 Feed ID와 Member ID를 기준으로 Likes를 조회
        Map<Long, Boolean> likeMap = queryFactory
                .selectFrom(like)
                .where(like.feed.id.in(feeds.stream().map(Feed::getId).collect(Collectors.toList())), like.member.id.eq(memberKey))
                .transform(groupBy(like.feed.id).as(
                        like.isNotNull()
                ));

        // 조회된 Feed 엔티티 리스트를 기반으로 DTO 리스트를 생성합니다.
        return feeds.stream().map(feedEntity -> {
            List<FeedPictureDto> feedPictureDtos = feedPicturesMap.getOrDefault(feedEntity.getId(), Collections.emptyList());
            List<FeedCommentDto> feedCommentDtos = feedCommentMap.getOrDefault(feedEntity.getId(), Collections.emptyList());
            Boolean isLike = likeMap.getOrDefault(feedEntity.getId(), false);

            // FindFeedRes DTO 인스턴스를 생성합니다.
            return FindFeedRes.builder()
                    .id(feedEntity.getId())
                    .content(feedEntity.getContent())
                    .nickname(feedEntity.getMember().getNickname())
                    .profileImage(feedEntity.getMember().getProfileImage())
                    .isMine(feedEntity.getMember().getId().equals(memberKey))
                    .createdDate(feedEntity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .feedPictures(feedPictureDtos)
                    .likeCount(feedEntity.getLikeCount())
                    .isLike(isLike)
                    .comments(feedCommentDtos)
                    .build();

        }).collect(Collectors.toList());
    }
    public List<Feed> findFeeds() {
        return queryFactory
                .selectFrom(feed)
                .join(feed.member, member).fetchJoin()
                .orderBy(feed.createdDate.asc())
                .fetch();
    }

    public Optional<Feed> findFeedWithCrewMember(Long feedId, Long memberKey) {
        return Optional.ofNullable(queryFactory
                .selectFrom(feed)
                .join(feed.crew, crew)
                .join(crewMember).on(crewMember.crew.eq(crew)).fetchJoin()
                .where(feed.id.eq(feedId), crewMember.member.id.eq(memberKey))
                .fetchOne());
    }
}