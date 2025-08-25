package CoCoNut_was.domains.submission.resdto;

import CoCoNut_was.domains.submission.entity.Submission;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmissionDetailDto {
    // 작품 ID
    private Long submissionId;

    // 공모전 ID
    private Long projectId;

    // 작성자 ID
    private Long userId;

    // 작품명
    private String title;

    // 작품설명
    private String description;

    // 작품 관련 링크
    private String relatedUrl;

    // 작품 이미지 링크(프론트엔드에서 렌더링)
    private String imageUrl;

    // 제출일자
    private String submittedAt;

    // 작성자(제출팀 대표자)
    private String writer;

    @Builder public SubmissionDetailDto(Long submissionId, Long projectId, Long userId, String title, String description, String relatedUrl, String imageUrl, String submittedAt, String writer) {
        this.submissionId = submissionId;
        this.projectId = projectId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.relatedUrl = relatedUrl;
        this.imageUrl = imageUrl;
        this.submittedAt = submittedAt;
        this.writer = writer;
    }

    public static SubmissionDetailDto fromEntity(Submission submission) {
        return SubmissionDetailDto.builder()
                .submissionId(submission.getId())
                .projectId(submission.getProject().getId())
                .userId(submission.getUser().getId())
                .title(submission.getTitle())
                .description(submission.getDescription())
                .relatedUrl(submission.getRelatedUrl())
                .imageUrl(submission.getImageUrl())
                .submittedAt(submission.getSubmittedAt().toString())
                .writer(submission.getUser().getNickname())
                .build();
    }
}
