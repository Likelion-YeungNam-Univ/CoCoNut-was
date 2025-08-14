package CoCoNut_was.domains.submission.resdto;

import CoCoNut_was.domains.submission.entity.Submission;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmissionListDto {
    private String title;
    private String description;
    private String imageUrl;

    @Builder public SubmissionListDto(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public static SubmissionListDto fromEntity(Submission submission) {
        return SubmissionListDto.builder()
                .title(submission.getTitle())
                .description(submission.getDescription())
                .imageUrl(submission.getImageUrl())
                .build();
    }
}