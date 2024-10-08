package wanted.wanted_pre_onboarding_backend.api.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import wanted.wanted_pre_onboarding_backend.api.user.response.ApplyNoticeResponse;
import wanted.wanted_pre_onboarding_backend.api.user.response.FindNoticeDetailResponse;
import wanted.wanted_pre_onboarding_backend.api.user.response.FindNoticeResponse;
import wanted.wanted_pre_onboarding_backend.common.exception.CustomException;
import wanted.wanted_pre_onboarding_backend.common.exception.CustomExceptionHandler;
import wanted.wanted_pre_onboarding_backend.domain.ApplyHistory;
import wanted.wanted_pre_onboarding_backend.domain.Company;
import wanted.wanted_pre_onboarding_backend.domain.Notice;
import wanted.wanted_pre_onboarding_backend.domain.User;
import wanted.wanted_pre_onboarding_backend.service.user.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static wanted.wanted_pre_onboarding_backend.common.constant.ErrorCode.*;

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
                get("/api/users/notices")
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].noticeId").value(response.getNoticeId()))
                .andExpect(jsonPath("$.[0].name").value(response.getName()))
                .andExpect(jsonPath("$.[0].country").value(response.getCountry()))
                .andExpect(jsonPath("$.[0].region").value(response.getRegion()))
                .andExpect(jsonPath("$.[0].position").value(response.getPosition()))
                .andExpect(jsonPath("$.[0].reward").value(response.getReward()))
                .andExpect(jsonPath("$.[0].techStack").value(response.getTechStack()));
    }

    @DisplayName("사용자가 채용공고 검색을 성공하면 200을 반환한다.")
    @Test
    void userSearchNoticesSuccessReturn200() throws Exception {
        // given
        String searchCondition = "원티드";

        Company company = new Company("원티드랩", "한국", "서울");
        Notice notice = new Notice("백엔드 주니어 개발자", 1000000, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");
        notice.setCompany(company);

        FindNoticeResponse response = new FindNoticeResponse(notice);

        when(userService.searchNotices(any())).thenReturn(List.of(notice, notice));

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/api/users/notices/search")
                        .param("searchCondition", searchCondition)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].noticeId").value(response.getNoticeId()))
                .andExpect(jsonPath("$.[0].name").value(response.getName()))
                .andExpect(jsonPath("$.[0].country").value(response.getCountry()))
                .andExpect(jsonPath("$.[0].region").value(response.getRegion()))
                .andExpect(jsonPath("$.[0].position").value(response.getPosition()))
                .andExpect(jsonPath("$.[0].reward").value(response.getReward()))
                .andExpect(jsonPath("$.[0].techStack").value(response.getTechStack()));
    }

    @DisplayName("사용자가 채용공고 상세페이지 조회를 성공하면 200을 반환한다.")
    @Test
    void userFindNoticeSuccessReturn200() throws Exception {
        // given
        Long id = 1L;

        Company company = new Company("원티드랩", "한국", "서울");
        Notice notice = new Notice(id, "백엔드 주니어 개발자", 1000000, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");
        notice.setCompany(company);

        FindNoticeDetailResponse response = new FindNoticeDetailResponse(notice);

        when(userService.findNotice(any())).thenReturn(notice);

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/api/users/notices/{noticeId}", id)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.noticeId").value(response.getNoticeId()))
                .andExpect(jsonPath("$.name").value(response.getName()))
                .andExpect(jsonPath("$.country").value(response.getCountry()))
                .andExpect(jsonPath("$.region").value(response.getRegion()))
                .andExpect(jsonPath("$.position").value(response.getPosition()))
                .andExpect(jsonPath("$.reward").value(response.getReward()))
                .andExpect(jsonPath("$.techStack").value(response.getTechStack()))
                .andExpect(jsonPath("$.content").value(response.getContent()));
    }

    @DisplayName("사용자가 채용공고 지원을 성공하면 201을 반환한다.")
    @Test
    void userApplyNoticeSuccessReturn201() throws Exception {
        // given
        Long userId = 1L;
        Long noticeId = 1L;

        User user = new User();
        Notice notice = new Notice("백엔드 주니어 개발자", 1000000, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");

        ApplyHistory applyHistory = new ApplyHistory(user, notice);
        ApplyNoticeResponse response = new ApplyNoticeResponse(applyHistory);

        when(userService.applyNotice(any(), any())).thenReturn(applyHistory);

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/users/{userId}/notices/{noticeId}", userId, noticeId)
        );

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.noticeId").value(response.getNoticeId()))
                .andExpect(jsonPath("$.userId").value(response.getUserId()));
    }

    @DisplayName("등록되지 않은 사용자가 채용공고를 지원하면 404를 반환한다.")
    @Test
    void notFoundUserApplyNoticeReturn404() throws Exception {
        // given
        Long userId = 1L;
        Long noticeId = 1L;

        when(userService.applyNotice(any(), any())).thenThrow(new CustomException(USER_NOT_FOUND));

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/users/{userId}/notices/{noticeId}", userId, noticeId)
        );

        // then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("해당 id를 가진 사용자가 없습니다."));
    }

    @DisplayName("등록되지 않은 채용공고를 지원하면 404를 반환한다.")
    @Test
    void notFoundNoticeApplyNoticeReturn404() throws Exception {
        // given
        Long userId = 1L;
        Long noticeId = 1L;

        when(userService.applyNotice(any(), any())).thenThrow(new CustomException(NOTICE_NOT_FOUND));

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/users/{userId}/notices/{noticeId}", userId, noticeId)
        );

        // then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("해당 id를 가진 채용공고가 없습니다."));
    }

    @DisplayName("채용공고에 2번 이상 지원하면 400을 반환한다.")
    @Test
    void userApplyTwiceApplyNoticeReturn400() throws Exception {
        // given
        Long userId = 1L;
        Long noticeId = 1L;

        when(userService.applyNotice(any(), any())).thenThrow(new CustomException(USER_APPLY_ONCE));

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/users/{userId}/notices/{noticeId}", userId, noticeId)
        );

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("사용자는 1회만 지원 가능합니다."));
    }
}