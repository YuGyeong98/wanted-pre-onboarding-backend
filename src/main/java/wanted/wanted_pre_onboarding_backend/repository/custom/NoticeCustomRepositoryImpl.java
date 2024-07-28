package wanted.wanted_pre_onboarding_backend.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import wanted.wanted_pre_onboarding_backend.domain.Notice;

import java.util.List;

import static wanted.wanted_pre_onboarding_backend.domain.QNotice.notice;

@RequiredArgsConstructor
public class NoticeCustomRepositoryImpl implements NoticeCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Notice> search(String searchCondition) {
        return queryFactory.selectFrom(notice)
                .join(notice.company).fetchJoin()
                .where(
                        notice.company.name.contains(searchCondition)
                                .or(notice.company.country.contains(searchCondition))
                                .or(notice.company.region.contains(searchCondition))
                                .or(notice.position.contains(searchCondition))
                                .or(rewardGoe(searchCondition))
                                .or(notice.techStack.contains(searchCondition))
                )
                .fetch();
    }

    private BooleanExpression rewardGoe(String reward) {
        try {
            int toIntReward = Integer.parseInt(reward);
            return notice.reward.goe(toIntReward);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
