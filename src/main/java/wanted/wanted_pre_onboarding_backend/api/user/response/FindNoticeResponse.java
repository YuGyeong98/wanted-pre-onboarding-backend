package wanted.wanted_pre_onboarding_backend.api.user.response;

import lombok.Data;
import wanted.wanted_pre_onboarding_backend.domain.Notice;

@Data
public class FindNoticeResponse {

    private Long noticeId;
    private String name;
    private String country;
    private String region;
    private String position;
    private int reward;
    private String techStack;

    public FindNoticeResponse(Notice notice) {
        this.noticeId = notice.getId();
        this.name = notice.getCompany().getName();
        this.country = notice.getCompany().getCountry();
        this.region = notice.getCompany().getRegion();
        this.position = notice.getPosition();
        this.reward = notice.getReward();
        this.techStack = notice.getTechStack();
    }
}
