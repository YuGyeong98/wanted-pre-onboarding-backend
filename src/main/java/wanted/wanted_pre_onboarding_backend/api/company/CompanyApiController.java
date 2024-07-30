package wanted.wanted_pre_onboarding_backend.api.company;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wanted.wanted_pre_onboarding_backend.api.company.request.CreateNoticeRequest;
import wanted.wanted_pre_onboarding_backend.api.company.request.UpdateNoticeRequest;
import wanted.wanted_pre_onboarding_backend.api.company.response.CreateNoticeResponse;
import wanted.wanted_pre_onboarding_backend.api.company.response.UpdateNoticeResponse;
import wanted.wanted_pre_onboarding_backend.domain.Notice;
import wanted.wanted_pre_onboarding_backend.service.company.CompanyService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@Validated
public class CompanyApiController {

    private final CompanyService companyService;

    /**
     * 채용공고 등록
     *
     * @param companyId 회사 id
     * @param request   채용공고 등록 요청 객체
     * @return 201 CREATED, 400 BAD_REQUEST, 404 NOT_FOUND
     */
    @PostMapping("/api/notices/{companyId}")
    public ResponseEntity<CreateNoticeResponse> createNotice(@PathVariable Long companyId, @RequestBody @Valid CreateNoticeRequest request) {
        Notice notice = companyService.createNotice(companyId, request.toServiceDto());
        CreateNoticeResponse response = new CreateNoticeResponse(notice);
        return ResponseEntity.status(CREATED)
                .body(response);
    }

    /**
     * 채용공고 수정
     *
     * @param noticeId 채용공고 id
     * @param request  채용공고 수정 요청 객체
     * @return 200 OK, 400 BAD_REQUEST, 404 NOT_FOUND
     */
    @PutMapping("/api/notices/{noticeId}")
    public ResponseEntity<UpdateNoticeResponse> updateNotice(@PathVariable Long noticeId, @RequestBody @Valid UpdateNoticeRequest request) {
        Notice notice = companyService.updateNotice(noticeId, request.toServiceDto());
        UpdateNoticeResponse response = new UpdateNoticeResponse(notice);
        return ResponseEntity.ok(response);
    }

    /**
     * 채용공고 삭제
     *
     * @param noticeId 채용공고 id
     * @return 200 OK, 404 NOT_FOUND
     */
    @DeleteMapping("/api/notices/{noticeId}")
    public ResponseEntity<String> deleteNotice(@PathVariable Long noticeId) {
        companyService.deleteNotice(noticeId);
        return ResponseEntity.ok("채용공고가 삭제되었습니다.");
    }
}
