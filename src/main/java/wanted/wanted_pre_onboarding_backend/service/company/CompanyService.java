package wanted.wanted_pre_onboarding_backend.service.company;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.wanted_pre_onboarding_backend.common.CustomException;
import wanted.wanted_pre_onboarding_backend.domain.Company;
import wanted.wanted_pre_onboarding_backend.domain.Notice;
import wanted.wanted_pre_onboarding_backend.repository.CompanyRepository;
import wanted.wanted_pre_onboarding_backend.repository.NoticeRepository;
import wanted.wanted_pre_onboarding_backend.service.company.dto.CreateNoticeDto;

import static wanted.wanted_pre_onboarding_backend.common.ErrorCode.COMPANY_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final NoticeRepository noticeRepository;

    public Notice createNotice(Long id, CreateNoticeDto dto) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new CustomException(COMPANY_NOT_FOUND));
        Notice notice = Notice.createNotice(company, dto.position(), dto.reward(), dto.content(), dto.techStack());
        return noticeRepository.save(notice);
    }
}
