package CoCoNut_was.domains.vote.resdto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteResultDto {
    private Long submissionId;
    private String title;
    private String imageUrl;
    private Long voteCount;

    @Builder
    public VoteResultDto(Long submissionId, String title, String imageUrl, Long voteCount) {
        this.submissionId = submissionId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.voteCount = voteCount;
    }
}
