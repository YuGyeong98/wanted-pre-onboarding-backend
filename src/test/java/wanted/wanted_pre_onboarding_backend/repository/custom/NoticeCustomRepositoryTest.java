package wanted.wanted_pre_onboarding_backend.repository.custom;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wanted.wanted_pre_onboarding_backend.domain.Company;
import wanted.wanted_pre_onboarding_backend.domain.Notice;
import wanted.wanted_pre_onboarding_backend.repository.CompanyRepository;
import wanted.wanted_pre_onboarding_backend.repository.NoticeRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NoticeCustomRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @DisplayName("사용자가 검색하면 검색 조건을 포함하는 채용공고를 반환한다.")
    @Test
    void userSearchReturnSearchConditionNotices() {
        // given
        String searchCondition = "원티드";

        Company company1 = new Company("원티드랩", "한국", "서울");
        Notice notice1 = Notice.createNotice(company1, "백엔드 주니어 개발자", 1000000, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");

        Company company2 = new Company("원티드코리아", "한국", "부산");
        Notice notice2 = Notice.createNotice(company2, "프론트엔드 개발자", 500000, "원티드랩에서 프론트엔드 개발자를 채용합니다. 자격요건은..", "Javascript");

        Company company3 = new Company("네이버", "한국", "판교");
        Notice notice3 = Notice.createNotice(company3, "안드로이드 주니어 개발자", 1000000, "네이버에서 안드로이드 주니어 개발자를 채용합니다. 자격요건은..", "Kotlin");

        companyRepository.save(company1);
        companyRepository.save(company2);
        companyRepository.save(company3);

        noticeRepository.save(notice1);
        noticeRepository.save(notice2);
        noticeRepository.save(notice3);

        // when
        List<Notice> result = noticeRepository.search(searchCondition);

        // then
        assertThat(result.size()).isEqualTo(2);
    }
}