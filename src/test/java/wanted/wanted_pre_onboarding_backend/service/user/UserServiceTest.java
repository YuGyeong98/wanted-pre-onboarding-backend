package wanted.wanted_pre_onboarding_backend.service.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wanted.wanted_pre_onboarding_backend.common.exception.CustomException;
import wanted.wanted_pre_onboarding_backend.domain.Notice;
import wanted.wanted_pre_onboarding_backend.repository.ApplyHistoryRepository;
import wanted.wanted_pre_onboarding_backend.repository.NoticeRepository;
import wanted.wanted_pre_onboarding_backend.repository.UserRepository;
import wanted.wanted_pre_onboarding_backend.service.company.dto.UpdateNoticeDto;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static wanted.wanted_pre_onboarding_backend.common.constant.ErrorCode.NOTICE_NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private NoticeRepository noticeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApplyHistoryRepository applyHistoryRepository;

    @InjectMocks
    private UserService userService;

    @DisplayName("사용자가 채용공고 목록을 조회한다.")
    @Test
    void userFindNotices() {
        // given
        Notice notice = new Notice("백엔드 주니어 개발자", 1000000, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");

        when(noticeRepository.findAll()).thenReturn(List.of(notice, notice));

        // when
        List<Notice> notices = userService.findNotices();

        // then
        assertThat(notices.size()).isEqualTo(2);
    }

    @DisplayName("사용자가 채용공고 상세페이지를 조회한다.")
    @Test
    void userFindNotice() {
        // given
        Long id = 1L;
        Notice notice = new Notice("백엔드 주니어 개발자", 1000000, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");

        when(noticeRepository.findById(any())).thenReturn(Optional.of(notice));

        // when
        Notice result = userService.findNotice(id);

        // then
        assertThat(result.getPosition()).isEqualTo(notice.getPosition());
        assertThat(result.getReward()).isEqualTo(notice.getReward());
        assertThat(result.getContent()).isEqualTo(notice.getContent());
        assertThat(result.getTechStack()).isEqualTo(notice.getTechStack());
    }

    @DisplayName("등록되지 않은 채용공고를 조회하면 예외가 발생한다.")
    @Test
    void notFoundNoticeFindNoticeThrowsException() {
        // given
        Long id = 1L;

        when(noticeRepository.findById(any())).thenReturn(Optional.empty());

        // when
        CustomException exception = assertThrows(CustomException.class, () -> userService.findNotice(id));

        // then
        assertThat(exception.getErrorCode()).isEqualTo(NOTICE_NOT_FOUND);
    }
}