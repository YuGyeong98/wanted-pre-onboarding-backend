package wanted.wanted_pre_onboarding_backend.service.company;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wanted.wanted_pre_onboarding_backend.common.exception.CustomException;
import wanted.wanted_pre_onboarding_backend.domain.Company;
import wanted.wanted_pre_onboarding_backend.domain.Notice;
import wanted.wanted_pre_onboarding_backend.repository.CompanyRepository;
import wanted.wanted_pre_onboarding_backend.repository.NoticeRepository;
import wanted.wanted_pre_onboarding_backend.service.company.dto.CreateNoticeDto;
import wanted.wanted_pre_onboarding_backend.service.company.dto.UpdateNoticeDto;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static wanted.wanted_pre_onboarding_backend.common.constant.ErrorCode.COMPANY_NOT_FOUND;
import static wanted.wanted_pre_onboarding_backend.common.constant.ErrorCode.NOTICE_NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private NoticeRepository noticeRepository;

    @InjectMocks
    private CompanyService companyService;

    @DisplayName("회사가 채용공고 등록을 하면 저장된 채용공고를 반환한다.")
    @Test
    void companyCreateNoticeReturnSavedNotice() {
        // given
        Long id = 1L;
        CreateNoticeDto dto = new CreateNoticeDto("백엔드 주니어 개발자", 1000000, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");
        Company company = new Company("원티드랩", "한국", "서울");
        Notice notice = new Notice(dto.position(), dto.reward(), dto.content(), dto.techStack());

        when(companyRepository.findById(any())).thenReturn(Optional.of(company));
        when(noticeRepository.save(any())).thenReturn(notice);

        // when
        Notice result = companyService.createNotice(id, dto);

        // then
        assertThat(result).isEqualTo(notice);
    }

    @DisplayName("등록되지 않은 회사가 채용공고 등록을 하면 예외가 발생한다.")
    @Test
    void notFoundCompanyCreateNoticeThrowsException() {
        // given
        Long id = 1L;
        CreateNoticeDto dto = new CreateNoticeDto("백엔드 주니어 개발자", 1000000, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");

        when(companyRepository.findById(any())).thenReturn(Optional.empty());

        // when
        CustomException exception = assertThrows(CustomException.class, () -> companyService.createNotice(id, dto));

        // then
        assertThat(exception.getErrorCode()).isEqualTo(COMPANY_NOT_FOUND);
    }

    @DisplayName("회사가 채용공고 수정을 하면 수정된 채용공고를 반환한다.")
    @Test
    void companyUpdateNoticeReturnUpdatedNotice() {
        // given
        Long id = 1L;
        UpdateNoticeDto dto = new UpdateNoticeDto("프론트엔드 주니어 개발자", 1000000, "원티드랩에서 프론트엔드 주니어 개발자를 채용합니다. 자격요건은..", "Javascript");
        Notice notice = new Notice("백엔드 주니어 개발자", 1000000, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");

        when(noticeRepository.findById(any())).thenReturn(Optional.of(notice));

        // when
        Notice result = companyService.updateNotice(id, dto);

        // then
        assertThat(result.getPosition()).isEqualTo(notice.getPosition());
        assertThat(result.getReward()).isEqualTo(notice.getReward());
        assertThat(result.getContent()).isEqualTo(notice.getContent());
        assertThat(result.getTechStack()).isEqualTo(notice.getTechStack());
    }

    @DisplayName("등록되지 않은 채용공고를 수정하면 예외가 발생한다.")
    @Test
    void notFoundNoticeUpdateNoticeThrowsException() {
        // given
        Long id = 1L;
        UpdateNoticeDto dto = new UpdateNoticeDto("프론트엔드 주니어 개발자", 1000000, "원티드랩에서 프론트엔드 주니어 개발자를 채용합니다. 자격요건은..", "Javascript");

        when(noticeRepository.findById(any())).thenReturn(Optional.empty());

        // when
        CustomException exception = assertThrows(CustomException.class, () -> companyService.updateNotice(id, dto));

        // then
        assertThat(exception.getErrorCode()).isEqualTo(NOTICE_NOT_FOUND);
    }

    @DisplayName("회사 채용공고가 존재하면 삭제한다.")
    @Test
    void companyDeleteNoticeIfPresent() {
        // given
        Long id = 1L;
        Notice notice = new Notice("백엔드 주니어 개발자", 1000000, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");

        when(noticeRepository.findById(any())).thenReturn(Optional.of(notice));

        // when
        companyService.deleteNotice(id);

        // then
        verify(noticeRepository, times(1)).delete(notice);
    }

    @DisplayName("등록되지 않은 채용공고를 삭제하면 예외가 발생한다.")
    @Test
    void notFoundNoticeDeleteNoticeThrowsException() {
        // given
        Long id = 1L;

        when(noticeRepository.findById(any())).thenReturn(Optional.empty());

        // when
        CustomException exception = assertThrows(CustomException.class, () -> companyService.deleteNotice(id));

        // then
        assertThat(exception.getErrorCode()).isEqualTo(NOTICE_NOT_FOUND);
    }
}