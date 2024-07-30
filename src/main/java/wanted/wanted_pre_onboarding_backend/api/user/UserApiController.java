package wanted.wanted_pre_onboarding_backend.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.wanted_pre_onboarding_backend.api.user.response.ApplyNoticeResponse;
import wanted.wanted_pre_onboarding_backend.api.user.response.FindNoticeDetailResponse;
import wanted.wanted_pre_onboarding_backend.api.user.response.FindNoticeResponse;
import wanted.wanted_pre_onboarding_backend.common.response.SuccessResponse;
import wanted.wanted_pre_onboarding_backend.domain.ApplyHistory;
import wanted.wanted_pre_onboarding_backend.domain.Notice;
import wanted.wanted_pre_onboarding_backend.service.user.UserService;

import java.util.List;

import static wanted.wanted_pre_onboarding_backend.common.constant.SuccessCode.*;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    /**
     * 채용공고 목록 조회
     *
     * @return 200 OK
     */
    @GetMapping("/api/users/notices")
    public ResponseEntity<SuccessResponse<List<FindNoticeResponse>>> findNotices() {
        List<Notice> notices = userService.findNotices();
        List<FindNoticeResponse> response = notices.stream().map(FindNoticeResponse::new).toList();
        return SuccessResponse.toResponseEntity(NOTICES_FOUND, response);
    }

    /**
     * 채용공고 검색
     *
     * @param searchCondition 검색 조건
     * @return 200 OK
     */
    @GetMapping("/api/users/notices/search")
    public ResponseEntity<SuccessResponse<List<FindNoticeResponse>>> searchNotices(@RequestParam String searchCondition) {
        List<Notice> notices = userService.searchNotices(searchCondition);
        List<FindNoticeResponse> response = notices.stream().map(FindNoticeResponse::new).toList();
        return SuccessResponse.toResponseEntity(NOTICE_SEARCH_FOUND, response);
    }

    /**
     * 채용공고 상세페이지 조회
     *
     * @param noticeId 채용공고 id
     * @return 200 OK, 404 NOT_FOUND
     */
    @GetMapping("/api/users/notices/{noticeId}")
    public ResponseEntity<SuccessResponse<FindNoticeDetailResponse>> findNotice(@PathVariable Long noticeId) {
        Notice notice = userService.findNotice(noticeId);
        FindNoticeDetailResponse response = new FindNoticeDetailResponse(notice);
        return SuccessResponse.toResponseEntity(NOTICE_FOUND, response);
    }

    /**
     * 채용공고 지원
     *
     * @param userId   사용자 id
     * @param noticeId 채용공고 id
     * @return 200 OK, 404 NOT_FOUND
     */
    @PostMapping("/api/users/{userId}/notices/{noticeId}")
    public ResponseEntity<SuccessResponse<ApplyNoticeResponse>> applyNotice(@PathVariable Long userId, @PathVariable Long noticeId) {
        ApplyHistory applyHistory = userService.applyNotice(userId, noticeId);
        ApplyNoticeResponse response = new ApplyNoticeResponse(applyHistory);
        return SuccessResponse.toResponseEntity(NOTICE_APPLIED, response);
    }
}
