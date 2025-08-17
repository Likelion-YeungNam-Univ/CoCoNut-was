package CoCoNut_was.domains.reward.entity;

import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.submission.entity.Submission;
import CoCoNut_was.domains.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int rewardAmount; // 보상 금액

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_jd", nullable = false)
    private User user; // 보상을 받는 유저

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project; // 해당 공모전

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id", nullable = false)
    private Submission submission; // 수상한 작품

    @Builder
    public Reward(User user, Project project, Submission submission, int rewardAmount) {
        this.user = user;
        this.project = project;
        this.submission = submission;
        this.rewardAmount = rewardAmount;
    }
}
