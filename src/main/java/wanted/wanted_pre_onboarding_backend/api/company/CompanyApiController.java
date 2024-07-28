package wanted.wanted_pre_onboarding_backend.api.company;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wanted.wanted_pre_onboarding_backend.api.company.request.CreateNoticeRequest;
import wanted.wanted_pre_onboarding_backend.api.company.response.CreateNoticeResponse;
import wanted.wanted_pre_onboarding_backend.domain.Notice;
import wanted.wanted_pre_onboarding_backend.service.company.CompanyService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class CompanyApiController {

    private final CompanyService companyService;

    @PostMapping("/api/notices/{id}")
    public ResponseEntity<CreateNoticeResponse> createNotice(@PathVariable Long id, @RequestBody CreateNoticeRequest request) {
        Notice notice = companyService.createNotice(id, request.toServiceDto());
        CreateNoticeResponse response = new CreateNoticeResponse(notice);
        return ResponseEntity.status(CREATED)
                .body(response);
    }
}
