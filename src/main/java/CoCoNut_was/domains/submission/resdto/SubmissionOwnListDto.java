package CoCoNut_was.domains.submission.resdto;


import CoCoNut_was.domains.submission.entity.Submission;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmissionOwnListDto {

    private Long submissionId;

    private Long projectId;

    private Long userId;

    private String title;

    private String imageUrl;

    private Integer rewardAmount;

    private boolean isWinner;

    @Builder public SubmissionOwnListDto(Long submissionId, Long projectId, Long userId, String title, String imageUrl, Integer rewardAmount, boolean isWinner) {
        this.submissionId = submissionId;
        this.projectId = projectId;
        this.userId = userId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.rewardAmount = rewardAmount;
        this.isWinner = isWinner;
    }

    public static SubmissionOwnListDto fromEntity(Submission submission, boolean isWinner) {
        return SubmissionOwnListDto.builder()
                .submissionId(submission.getId())
                .projectId(submission.getProject().getId())
                .userId(submission.getUser().getId())
                .title(submission.getTitle())
                .imageUrl(submission.getImageUrl())
                .rewardAmount(submission.getProject().getRewardAmount())
                .isWinner(isWinner)
                .build();
    }
}
