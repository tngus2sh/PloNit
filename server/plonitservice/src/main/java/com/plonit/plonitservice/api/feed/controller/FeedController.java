package com.plonit.plonitservice.api.feed.controller;

import com.plonit.plonitservice.api.feed.controller.request.SaveCommentReq;
import com.plonit.plonitservice.api.feed.controller.request.SaveFeedReq;
import com.plonit.plonitservice.api.feed.controller.response.FindFeedRes;
import com.plonit.plonitservice.api.feed.service.FeedQueryService;
import com.plonit.plonitservice.api.feed.service.FeedService;
import com.plonit.plonitservice.api.feed.service.dto.SaveCommentDto;
import com.plonit.plonitservice.api.feed.service.dto.SaveFeedDto;
import com.plonit.plonitservice.common.CustomApiResponse;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.util.RequestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.plonit.plonitservice.common.exception.ErrorCode.INVALID_FIELDS_REQUEST;
import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.END;

@Tag(name = "Feed API Controller", description = "Feed API Document")
@Slf4j
@RequestMapping("/api/plonit-service/v1/feed")
@RestController
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;
    private final FeedQueryService feedQueryService;

    @Operation(summary = "피드 등록", description = "사용자는 피드를 등록한다.")
    @PostMapping
    public CustomApiResponse<Object> saveFeed(@Validated @ModelAttribute SaveFeedReq saveFeedReq, HttpServletRequest request, Errors errors) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info(saveFeedReq.toString());

        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(e -> {
                log.info("error message : " + e.getDefaultMessage());
            });
            log.info(logCurrent(getClassName(), getMethodName(), END));
            throw new CustomException(INVALID_FIELDS_REQUEST);
        }

        Long memberKey = RequestUtils.getMemberKey(request);
        feedService.saveFeed(SaveFeedDto.of(memberKey, saveFeedReq));

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok("", "피드 등록 성공했습니다.");
    }

    @Operation(summary = "피드 조회", description = "피드를 조회한다.")
    @GetMapping("/{crew-id}")
    public CustomApiResponse<Object> findFeeds(HttpServletRequest request, @PathVariable("crew-id") long crewId) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        Long memberKey = RequestUtils.getMemberKey(request); // 내가 작성한 피드 인지 확인
        List<FindFeedRes> findFeedRes = feedQueryService.findFeeds(memberKey, crewId);

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(findFeedRes, "피드 조회에 성공했습니다.");
    }

    @Operation(summary = "피드 삭제", description = "피드를 삭제한다.")
    @DeleteMapping("/{feed-id}")
    public CustomApiResponse<Object> deleteFeed(@PathVariable("feed-id") long feedId, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        Long memberKey = RequestUtils.getMemberKey(request);
        feedService.deleteFeed(memberKey, feedId);

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok("", "피드 삭제에 성공했습니다.");
    }

    @Operation(summary = "피드 댓글 등록", description = "피드 댓글을 등록한다.")
    @PostMapping("/comment")
    public CustomApiResponse<Object> creadComment(@Validated @RequestBody SaveCommentReq saveCommentReq, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info(saveCommentReq.toString());

        Long memberKey = RequestUtils.getMemberKey(request);
        feedService.saveComment(SaveCommentDto.of(memberKey, saveCommentReq));

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok("", "피드 댓글 등록에 성공했습니다.");
    }

    @Operation(summary = "피드 댓글 삭제", description = "피드 댓글을 삭제한다.")
    @DeleteMapping("/comment/{comment-id}")
    public CustomApiResponse<Object> deleteComment(@PathVariable("comment-id") long commentId, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        Long memberKey = RequestUtils.getMemberKey(request);
        feedService.deleteComment(memberKey, commentId);

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok("", "피드 댓글 삭제에 성공했습니다.");
    }

    @Operation(summary = "피드 좋아요", description = "피드 좋아요를 등록하거나, 좋아요를 취소한다.")
    @PostMapping("/like/{feed-id}")
    public CustomApiResponse<Object> saveFeedLike(@PathVariable("feed-id") long feedId) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("saveFeedLike = {}", feedId);

        boolean flag = feedService.saveFeedLike(feedId);

        if(flag) {
            return CustomApiResponse.ok(null, "피드 좋아요 등록에 성공했습니다.");
        }
        else {
            return CustomApiResponse.ok(null, "피드 좋아요 등록을 취소했습니다.");
        }
    }

    @Operation(summary = "피드 좋아요 삭제", description = "피드 좋아요를 삭제한다.")
    @DeleteMapping("/like/{feed-id}")
    public CustomApiResponse<Object> deleteFeedLike(@PathVariable("feed-id") long feedId, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        Long memberKey = RequestUtils.getMemberKey(request);
        //todo : 피드 좋아요 삭제

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok("", "피드 좋아요 삭제에 성공했습니다.");
    }
}
