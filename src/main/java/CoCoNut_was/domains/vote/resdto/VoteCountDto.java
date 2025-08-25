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
    private Long voteUserId;
    private String voteUserNickname;
    private String voteUserEmail;
    private boolean isVoted;

    @Builder public VoteCountDto(Long projectId, Long submissionId, Long voteCount, Long voteUserId, String voteUserNickname, String voteUserEmail, boolean isVoted) {
        this.projectId = projectId;
        this.submissionId = submissionId;
        this.voteCount = voteCount;
        this.voteUserId = voteUserId;
        this.voteUserNickname = voteUserNickname;
        this.voteUserEmail = voteUserEmail;
        this.isVoted = isVoted;
    }
}
