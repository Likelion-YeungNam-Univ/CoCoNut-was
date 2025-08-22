package CoCoNut_was.domains.submission.resdto;

import CoCoNut_was.domains.submission.entity.Submission;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmissionListDto {

    private Long submissionId;

    private Long projectId;

    private Long userId;

    private String title;

    private String imageUrl;

    private boolean isWinner;

    @Builder public SubmissionListDto(Long submissionId, Long projectId, Long userId, String title, String imageUrl, boolean isWinner) {
        this.submissionId = submissionId;
        this.projectId = projectId;
        this.userId = userId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.isWinner = isWinner;
    }

    public static SubmissionListDto fromEntity(Submission submission, boolean isWinner) {
        return SubmissionListDto.builder()
                .submissionId(submission.getId())
                .projectId(submission.getProject().getId())
                .userId(submission.getUser().getId())
                .title(submission.getTitle())
                .imageUrl(submission.getImageUrl())
                .isWinner(isWinner)
                .build();
    }
}