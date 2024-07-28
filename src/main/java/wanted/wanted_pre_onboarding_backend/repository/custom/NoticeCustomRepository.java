package wanted.wanted_pre_onboarding_backend.repository.custom;

import wanted.wanted_pre_onboarding_backend.domain.Notice;

import java.util.List;

public interface NoticeCustomRepository {

    List<Notice> search(String searchCondition);
}
