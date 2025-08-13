package CoCoNut_was.domains.submission.resdto;

import CoCoNut_was.domains.submission.entity.Submission;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmissionResDto {
    private String title;
    private String description;
    private String imageUrl;

    @Builder public SubmissionResDto(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public static SubmissionResDto fromEntity(Submission submission) {
        return SubmissionResDto.builder()
                .title(submission.getTitle())
                .description(submission.getDescription())
                .imageUrl(submission.getImageUrl())
                .build();
    }
}