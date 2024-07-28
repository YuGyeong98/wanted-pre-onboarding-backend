package wanted.wanted_pre_onboarding_backend.api.company.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import wanted.wanted_pre_onboarding_backend.service.company.dto.CreateNoticeDto;

@Data
public class CreateNoticeRequest {

    @NotBlank(message = "채용포지션은 필수 입력입니다.")
    private String position;

    @NotNull(message = "채용보상금은 필수 입력입니다.")
    private Integer reward;

    @NotBlank(message = "채용내용은 필수 입력입니다.")
    private String content;

    @NotBlank(message = "사용기술은 필수 입력입니다.")
    private String techStack;

    public CreateNoticeDto toServiceDto() {
        return new CreateNoticeDto(position, reward, content, techStack);
    }
}
