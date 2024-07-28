package wanted.wanted_pre_onboarding_backend.api.user.response;

import lombok.Data;
import wanted.wanted_pre_onboarding_backend.domain.Notice;

import java.util.List;

@Data
public class FindNoticeDetailResponse {

    private Long noticeId;
    private String name;
    private String country;
    private String region;
    private String position;
    private int reward;
    private String techStack;
    private String content;
    private List<Long> otherNoticeIds;

    public FindNoticeDetailResponse(Notice notice) {
        this.noticeId = notice.getId();
        this.name = notice.getCompany().getName();
        this.country = notice.getCompany().getCountry();
        this.region = notice.getCompany().getRegion();
        this.position = notice.getPosition();
        this.reward = notice.getReward();
        this.techStack = notice.getTechStack();
        this.content = notice.getContent();
        this.otherNoticeIds = notice.getCompany().getNotices()
                .stream()
                .map(Notice::getId)
                .filter(id -> !this.noticeId.equals(id))
                .toList();
    }
}
