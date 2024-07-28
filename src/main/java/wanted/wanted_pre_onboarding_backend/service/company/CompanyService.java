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
import wanted.wanted_pre_onboarding_backend.service.company.dto.UpdateNoticeDto;

import static wanted.wanted_pre_onboarding_backend.common.ErrorCode.COMPANY_NOT_FOUND;
import static wanted.wanted_pre_onboarding_backend.common.ErrorCode.NOTICE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final NoticeRepository noticeRepository;

    /**
     * 채용공고 등록
     *
     * @param id  회사 id
     * @param dto 채용공고 등록 dto 객체
     * @return 저장된 채용공고
     * @throws CustomException COMPANY_NOT_FOUND
     */
    public Notice createNotice(Long id, CreateNoticeDto dto) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new CustomException(COMPANY_NOT_FOUND));
        Notice notice = Notice.createNotice(company, dto.position(), dto.reward(), dto.content(), dto.techStack());
        return noticeRepository.save(notice);
    }

    /**
     * 채용공고 수정
     *
     * @param id  채용공고 id
     * @param dto 채용공고 수정 dto 객체
     * @return 수정된 채용공고
     * @throws CustomException NOTICE_NOT_FOUND
     */
    public Notice updateNotice(Long id, UpdateNoticeDto dto) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new CustomException(NOTICE_NOT_FOUND));
        notice.updateNotice(dto.getPosition(), dto.getReward(), dto.getContent(), dto.getTechStack());
        return notice;
    }
}
