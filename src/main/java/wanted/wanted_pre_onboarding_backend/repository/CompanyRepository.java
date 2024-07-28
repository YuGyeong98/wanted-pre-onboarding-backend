package wanted.wanted_pre_onboarding_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.wanted_pre_onboarding_backend.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
