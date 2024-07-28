package wanted.wanted_pre_onboarding_backend.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.wanted_pre_onboarding_backend.api.user.response.FindNoticeResponse;
import wanted.wanted_pre_onboarding_backend.common.response.SuccessResponse;
import wanted.wanted_pre_onboarding_backend.domain.Notice;
import wanted.wanted_pre_onboarding_backend.service.user.UserService;

import java.util.List;

import static wanted.wanted_pre_onboarding_backend.common.constant.SuccessCode.NOTICES_FOUND;

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
}
