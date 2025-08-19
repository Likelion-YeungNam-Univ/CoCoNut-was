package CoCoNut_was.domains.vote.resdto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteResultDto {
    private Long submissionId;
    private Long projectId;
    private String voteStartTime;
    private String voteDeadline;
    private String title;
    private String imageUrl;
    private Long voteCount;

    @Builder
    public VoteResultDto(Long submissionId, Long projectId, String voteStartTime, String voteDeadline ,String title, String imageUrl, Long voteCount) {
        this.submissionId = submissionId;
        this.projectId = projectId;
        this.voteStartTime = voteStartTime;
        this.voteDeadline = voteDeadline;
        this.title = title;
        this.imageUrl = imageUrl;
        this.voteCount = voteCount;
    }
}
