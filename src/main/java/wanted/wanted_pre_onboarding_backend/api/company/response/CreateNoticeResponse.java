package wanted.wanted_pre_onboarding_backend.api.company.response;

import lombok.Data;
import wanted.wanted_pre_onboarding_backend.domain.Notice;

@Data
public class CreateNoticeResponse {

    private Long companyId;
    private String position;
    private int reward;
    private String content;
    private String techStack;

    public CreateNoticeResponse(Notice notice) {
        companyId = notice.getCompany().getId();
        position = notice.getPosition();
        reward = notice.getReward();
        content = notice.getContent();
        techStack = notice.getTechStack();
    }
}
