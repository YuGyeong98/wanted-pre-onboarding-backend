package wanted.wanted_pre_onboarding_backend;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wanted.wanted_pre_onboarding_backend.domain.Company;
import wanted.wanted_pre_onboarding_backend.domain.User;
import wanted.wanted_pre_onboarding_backend.repository.CompanyRepository;
import wanted.wanted_pre_onboarding_backend.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @PostConstruct
    private void init() {
        Company wantedLab = new Company("원티드랩", "한국", "서울");
        Company wantedKorea = new Company("원티드코리아", "한국", "부산");
        Company naver = new Company("네이버", "한국", "판교");
        Company kakao = new Company("카카오", "한국", "판교");

        User userA = new User();
        User userB = new User();

        companyRepository.save(wantedLab);
        companyRepository.save(wantedKorea);
        companyRepository.save(naver);
        companyRepository.save(kakao);

        userRepository.save(userA);
        userRepository.save(userB);
    }
}
