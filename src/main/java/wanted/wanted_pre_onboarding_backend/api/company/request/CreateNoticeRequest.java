package wanted.wanted_pre_onboarding_backend.api.company.request;

import lombok.Data;
import wanted.wanted_pre_onboarding_backend.service.company.dto.CreateNoticeDto;

@Data
public class CreateNoticeRequest {

    private String position;
    private int reward;
    private String content;
    private String techStack;

    public CreateNoticeDto toServiceDto() {
        return new CreateNoticeDto(position, reward, content, techStack);
    }
}
