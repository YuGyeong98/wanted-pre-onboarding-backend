package wanted.wanted_pre_onboarding_backend.api.user.response;

import lombok.Data;
import wanted.wanted_pre_onboarding_backend.domain.ApplyHistory;

@Data
public class ApplyNoticeResponse {

    private Long noticeId;
    private Long userId;

    public ApplyNoticeResponse(ApplyHistory applyHistory) {
        this.noticeId = applyHistory.getNotice().getId();
        this.userId = applyHistory.getUser().getId();
    }
}
