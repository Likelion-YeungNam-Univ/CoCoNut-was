package CoCoNut_was.domains.reward.dto;

import CoCoNut_was.domains.reward.entity.Reward;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RewardResponseDto {
    private final Long rewardId;
    private final Long userId;
    private final Long projectId;
    private final Long submissionId;
    private final int rewardAmount;

    public static RewardResponseDto fromEntity(Reward reward) {
        return RewardResponseDto.builder()
                .rewardId(reward.getId())
                .userId(reward.getUser().getId())
                .projectId(reward.getProject().getId())
                .submissionId(reward.getSubmission().getId())
                .rewardAmount(reward.getRewardAmount())
                .build();
    }
}
