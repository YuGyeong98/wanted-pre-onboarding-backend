package wanted.wanted_pre_onboarding_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private int reward;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String techStack;

    public Notice(String position, int reward, String content, String techStack) {
        this.position = position;
        this.reward = reward;
        this.content = content;
        this.techStack = techStack;
    }

    // 연관관계 편의 메소드
    public void setCompany(Company company) {
        this.company = company;
        company.getNotices().add(this);
    }

    // 생성 메소드
    public static Notice createNotice(Company company, String position, int reward, String content, String techStack) {
        Notice notice = new Notice(position, reward, content, techStack);
        notice.setCompany(company);
        return notice;
    }
}
