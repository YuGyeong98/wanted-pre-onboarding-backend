package wanted.wanted_pre_onboarding_backend.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.wanted_pre_onboarding_backend.domain.Notice;
import wanted.wanted_pre_onboarding_backend.repository.NoticeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final NoticeRepository noticeRepository;

    /**
     * 채용공고 목록 조회
     *
     * @return 채용공고 목록
     */
    public List<Notice> findNotices() {
        return noticeRepository.findAll();
    }
}
