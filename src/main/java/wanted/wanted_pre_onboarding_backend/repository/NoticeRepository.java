package wanted.wanted_pre_onboarding_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.wanted_pre_onboarding_backend.domain.Notice;
import wanted.wanted_pre_onboarding_backend.repository.custom.NoticeCustomRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeCustomRepository {
}
