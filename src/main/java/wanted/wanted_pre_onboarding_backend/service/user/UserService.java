package wanted.wanted_pre_onboarding_backend.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.wanted_pre_onboarding_backend.common.exception.CustomException;
import wanted.wanted_pre_onboarding_backend.domain.ApplyHistory;
import wanted.wanted_pre_onboarding_backend.domain.Notice;
import wanted.wanted_pre_onboarding_backend.domain.User;
import wanted.wanted_pre_onboarding_backend.repository.ApplyHistoryRepository;
import wanted.wanted_pre_onboarding_backend.repository.NoticeRepository;
import wanted.wanted_pre_onboarding_backend.repository.UserRepository;

import java.util.List;

import static wanted.wanted_pre_onboarding_backend.common.constant.ErrorCode.NOTICE_NOT_FOUND;
import static wanted.wanted_pre_onboarding_backend.common.constant.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;
    private final ApplyHistoryRepository applyHistoryRepository;

    /**
     * 채용공고 목록 조회
     *
     * @return 채용공고 목록
     */
    public List<Notice> findNotices() {
        return noticeRepository.findAll();
    }

    /**
     * 채용공고 상세페이지 조회
     *
     * @param id 채용공고 id
     * @return 채용공고 상세페이지
     * @throws CustomException NOTICE_NOT_FOUND
     */
    public Notice findNotice(Long id) {
        return noticeRepository.findById(id).orElseThrow(() -> new CustomException(NOTICE_NOT_FOUND));
    }

    /**
     * 채용공고 지원
     *
     * @param userId   사용자 id
     * @param noticeId 채용공고 id
     * @return 채용공고 지원 내역
     * @throws CustomException USER_NOT_FOUND, NOTICE_NOT_FOUND
     */
    @Transactional
    public ApplyHistory applyNotice(Long userId, Long noticeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new CustomException(NOTICE_NOT_FOUND));
        ApplyHistory applyHistory = new ApplyHistory(user, notice);
        return applyHistoryRepository.save(applyHistory);
    }
}
