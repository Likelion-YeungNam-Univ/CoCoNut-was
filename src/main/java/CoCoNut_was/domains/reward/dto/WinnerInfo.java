package CoCoNut_was.domains.reward.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WinnerInfo {
    private Long winnerId;
    private String winnerEmail;
    private String winnerNickname;
    private Long rewardId;
    private Long submissionId;
    private String submissionImageUrl;
    private Long projectId;
    private Long projectOwnerId;

    @Builder public WinnerInfo(Long winnerId, String winnerEmail, String winnerNickname, Long rewardId, Long submissionId, String submissionImageUrl, Long projectId, Long projectOwnerId) {
        this.winnerId = winnerId;
        this.winnerEmail = winnerEmail;
        this.winnerNickname = winnerNickname;
        this.rewardId = rewardId;
        this.submissionId = submissionId;
        this.submissionImageUrl = submissionImageUrl;
        this.projectId = projectId;
        this.projectOwnerId = projectOwnerId;
    }
}
