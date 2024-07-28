package wanted.wanted_pre_onboarding_backend.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import wanted.wanted_pre_onboarding_backend.api.user.response.FindNoticeDetailResponse;
import wanted.wanted_pre_onboarding_backend.api.user.response.FindNoticeResponse;
import wanted.wanted_pre_onboarding_backend.common.response.SuccessResponse;
import wanted.wanted_pre_onboarding_backend.domain.Notice;
import wanted.wanted_pre_onboarding_backend.service.user.UserService;

import java.util.List;

import static wanted.wanted_pre_onboarding_backend.common.constant.SuccessCode.NOTICES_FOUND;
import static wanted.wanted_pre_onboarding_backend.common.constant.SuccessCode.NOTICE_FOUND;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    /**
     * 채용공고 목록 조회
     *
     * @return 200 OK
     */
    @GetMapping("/api/notices")
    public ResponseEntity<SuccessResponse<List<FindNoticeResponse>>> findNotices() {
        List<Notice> notices = userService.findNotices();
        List<FindNoticeResponse> response = notices.stream().map(FindNoticeResponse::new).toList();
        return SuccessResponse.toResponseEntity(NOTICES_FOUND, response);
    }

    /**
     * 채용공고 상세페이지 조회
     *
     * @param id 채용공고 id
     * @return 200 OK, 404 NOT_FOUND
     */
    @GetMapping("/api/notices/{id}")
    public ResponseEntity<SuccessResponse<FindNoticeDetailResponse>> findNotice(@PathVariable Long id) {
        Notice notice = userService.findNotice(id);
        FindNoticeDetailResponse response = new FindNoticeDetailResponse(notice);
        return SuccessResponse.toResponseEntity(NOTICE_FOUND, response);
    }
}
