package CoCoNut_was.domains.project.dto;

import CoCoNut_was.domains.project.entity.BusinessType;
import CoCoNut_was.domains.project.entity.Category;
import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.project.entity.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ProjectListResponseDto { // 공모전 리스트 조회 DTO (응답)
    private final Long userId;
    private final Long projectId;
    private final String writerNickname;
    private final String title;
    private final String merchantName;
    private final Category category;
    private final BusinessType businessType;
    private final LocalDate createdAt;
    private final LocalDate deadline;
    private final int rewardAmount;
    private final String summary;
    private final Status status;
    private final int submissionCount;

    public ProjectListResponseDto(Long userId, Long projectId, String writerNickname, String title, String merchantName, Category category, BusinessType businessType, LocalDate createdAt, LocalDate deadline, int rewardAmount, String summary, Status status, long submissionCount) {
        this.userId = userId;
        this.projectId = projectId;
        this.writerNickname = writerNickname;
        this.title = title;
        this.merchantName = merchantName;
        this.category = category;
        this.businessType = businessType;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.rewardAmount = rewardAmount;
        this.summary = summary;
        this.status = status;
        this.submissionCount = (int) submissionCount;
    }

    public static ProjectListResponseDto fromEntity(Project project) {
        return ProjectListResponseDto.builder()
                .userId(project.getUser().getId())
                .projectId(project.getId())
                .writerNickname(project.getUser().getNickname())
                .title(project.getTitle())
                .merchantName(project.getMerchantName())
                .category(project.getCategory())
                .businessType(project.getBusinessType())
                .createdAt(project.getCreatedAt())
                .deadline(project.getDeadline())
                .rewardAmount(project.getRewardAmount())
                .summary(project.getSummary())
                .status(project.getStatus())
                .submissionCount(project.getSubmissions().size())
                .build();
    }
}
