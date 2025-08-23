package CoCoNut_was.domains.vote.resdto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteCountDto {
    private Long projectId;
    private Long submissionId;
    private Long voteCount;
    private boolean isVoted;

    @Builder public VoteCountDto(Long projectId, Long submissionId, Long voteCount, boolean isVoted) {
        this.projectId = projectId;
        this.submissionId = submissionId;
        this.voteCount = voteCount;
        this.isVoted = isVoted;
    }
}
