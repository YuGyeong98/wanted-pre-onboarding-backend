package wanted.wanted_pre_onboarding_backend.service.company.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdateNoticeDto {

    private final String position;
    private final int reward;
    private final String content;
    private final String techStack;
}
