package wanted.wanted_pre_onboarding_backend.api.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import wanted.wanted_pre_onboarding_backend.api.company.request.CreateNoticeRequest;
import wanted.wanted_pre_onboarding_backend.api.company.request.UpdateNoticeRequest;
import wanted.wanted_pre_onboarding_backend.api.company.response.CreateNoticeResponse;
import wanted.wanted_pre_onboarding_backend.api.company.response.UpdateNoticeResponse;
import wanted.wanted_pre_onboarding_backend.common.exception.CustomException;
import wanted.wanted_pre_onboarding_backend.common.exception.CustomExceptionHandler;
import wanted.wanted_pre_onboarding_backend.domain.Company;
import wanted.wanted_pre_onboarding_backend.domain.Notice;
import wanted.wanted_pre_onboarding_backend.service.company.CompanyService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static wanted.wanted_pre_onboarding_backend.common.constant.ErrorCode.COMPANY_NOT_FOUND;
import static wanted.wanted_pre_onboarding_backend.common.constant.ErrorCode.NOTICE_NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class CompanyApiControllerTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyApiController companyApiController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(companyApiController)
                .setControllerAdvice(CustomExceptionHandler.class)
                .alwaysDo(print())
                .build();
    }

    @DisplayName("회사가 채용공고 등록을 성공하면 201을 반환한다.")
    @Test
    public void companyCreateNoticeSuccessReturn201() throws Exception {
        // given
        Long id = 1L;

        CreateNoticeRequest request = new CreateNoticeRequest();
        request.setPosition("백엔드 주니어 개발자");
        request.setReward(1000000);
        request.setContent("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..");
        request.setTechStack("Python");

        Company company = new Company("원티드랩", "한국", "서울");
        Notice notice = new Notice("백엔드 주니어 개발자", 1000000, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");
        notice.setCompany(company);

        CreateNoticeResponse response = new CreateNoticeResponse(notice);

        when(companyService.createNotice(any(), any())).thenReturn(notice);

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/notices/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyId").value(response.getCompanyId()))
                .andExpect(jsonPath("$.position").value(response.getPosition()))
                .andExpect(jsonPath("$.reward").value(response.getReward()))
                .andExpect(jsonPath("$.content").value(response.getContent()))
                .andExpect(jsonPath("$.techStack").value(response.getTechStack()));
    }

    @DisplayName("등록되지 않은 회사가 채용공고를 등록하면 404를 반환한다.")
    @Test
    void notFoundCompanyCreateNoticeReturn404() throws Exception {
        // given
        Long id = 1L;

        CreateNoticeRequest request = new CreateNoticeRequest();
        request.setPosition("백엔드 주니어 개발자");
        request.setReward(1000000);
        request.setContent("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..");
        request.setTechStack("Python");

        when(companyService.createNotice(any(), any())).thenThrow(new CustomException(COMPANY_NOT_FOUND));

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/notices/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("해당 id를 가진 회사가 없습니다."));
    }

    @DisplayName("회사가 채용공고 수정을 성공하면 200을 반환한다.")
    @Test
    void companyUpdateNoticeSuccessReturn200() throws Exception {
        // given
        Long id = 1L;

        UpdateNoticeRequest request = new UpdateNoticeRequest();
        request.setPosition("백엔드 주니어 개발자");
        request.setReward(1000000);
        request.setContent("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..");
        request.setTechStack("Python");

        Notice notice = new Notice("백엔드 주니어 개발자", 1000000, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");

        UpdateNoticeResponse response = new UpdateNoticeResponse(notice);

        when(companyService.updateNotice(any(), any())).thenReturn(notice);

        // when
        ResultActions resultActions = mockMvc.perform(
                put("/api/notices/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.position").value(response.getPosition()))
                .andExpect(jsonPath("$.reward").value(response.getReward()))
                .andExpect(jsonPath("$.content").value(response.getContent()))
                .andExpect(jsonPath("$.techStack").value(response.getTechStack()));
    }

    @DisplayName("등록되지 않은 채용공고를 수정하면 404를 반환한다.")
    @Test
    void notFoundNoticeUpdateNoticeReturn404() throws Exception {
        // given
        Long id = 1L;

        CreateNoticeRequest request = new CreateNoticeRequest();
        request.setPosition("백엔드 주니어 개발자");
        request.setReward(1000000);
        request.setContent("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..");
        request.setTechStack("Python");

        when(companyService.updateNotice(any(), any())).thenThrow(new CustomException(NOTICE_NOT_FOUND));

        // when
        ResultActions resultActions = mockMvc.perform(
                put("/api/notices/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("해당 id를 가진 채용공고가 없습니다."));
    }

    @DisplayName("회사가 채용공고 삭제를 성공하면 200을 반환한다.")
    @Test
    void companyDeleteNoticeSuccessReturn200() throws Exception {
        // given
        Long id = 1L;

        doNothing().when(companyService).deleteNotice(any());

        // when
        ResultActions resultActions = mockMvc.perform(
                delete("/api/notices/{id}", id)
        );

        // then
        resultActions.andExpect(status().isOk());
    }

    @DisplayName("등록되지 않은 채용공고를 삭제하면 404를 반환한다.")
    @Test
    void notFoundNoticeDeleteNoticeReturn404() throws Exception {
        // given
        Long id = 1L;

        doThrow(new CustomException(NOTICE_NOT_FOUND)).when(companyService).deleteNotice(any());

        // when
        ResultActions resultActions = mockMvc.perform(
                delete("/api/notices/{id}", id)
        );

        // then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("해당 id를 가진 채용공고가 없습니다."));
    }
}