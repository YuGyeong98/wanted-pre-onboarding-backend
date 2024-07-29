package wanted.wanted_pre_onboarding_backend.api.user;

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
import wanted.wanted_pre_onboarding_backend.api.user.response.FindNoticeResponse;
import wanted.wanted_pre_onboarding_backend.common.exception.CustomExceptionHandler;
import wanted.wanted_pre_onboarding_backend.domain.Company;
import wanted.wanted_pre_onboarding_backend.domain.Notice;
import wanted.wanted_pre_onboarding_backend.service.user.UserService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class UserApiControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserApiController userApiController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(userApiController)
                .setControllerAdvice(CustomExceptionHandler.class)
                .alwaysDo(print())
                .build();
    }

    @DisplayName("사용자가 채용공고 목록 조회를 성공하면 200을 반환한다.")
    @Test
    void userFindNoticesSuccessReturn200() throws Exception {
        // given
        Company company = new Company("원티드랩", "한국", "서울");
        Notice notice = new Notice("백엔드 주니어 개발자", 1000000, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");
        notice.setCompany(company);

        FindNoticeResponse response = new FindNoticeResponse(notice);

        when(userService.findNotices()).thenReturn(List.of(notice, notice));

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/api/notices")
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("채용공고 목록 조회가 완료되었습니다."))
                .andExpect(jsonPath("$.data[0].noticeId").value(response.getNoticeId()))
                .andExpect(jsonPath("$.data[0].name").value(response.getName()))
                .andExpect(jsonPath("$.data[0].country").value(response.getCountry()))
                .andExpect(jsonPath("$.data[0].region").value(response.getRegion()))
                .andExpect(jsonPath("$.data[0].position").value(response.getPosition()))
                .andExpect(jsonPath("$.data[0].reward").value(response.getReward()))
                .andExpect(jsonPath("$.data[0].techStack").value(response.getTechStack()));
    }
}