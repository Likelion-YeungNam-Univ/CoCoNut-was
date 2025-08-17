package CoCoNut_was.domains.reward.repository;

import CoCoNut_was.domains.reward.entity.Reward;
import CoCoNut_was.domains.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardRepository extends JpaRepository<Reward, Long> {
    // 특정 사용자의 모든 보상 내역을 최신순으로 조회
    List<Reward> findAllByUserOrderByRewardedAtDesc(User user);

    // 사용자의 수상 횟수를 세는 메서드 추가
    long countByUser(User user);
}
