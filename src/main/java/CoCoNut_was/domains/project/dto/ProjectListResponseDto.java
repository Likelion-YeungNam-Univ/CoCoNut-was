package CoCoNut_was.domains.project.dto;

import CoCoNut_was.domains.project.entity.BusinessType;
import CoCoNut_was.domains.project.entity.Category;
import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.project.entity.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class ProjectListResponseDto { // 공모전 리스트 조회 DTO (응답)
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


    public static ProjectListResponseDto fromEntity(Project project) {
        return ProjectListResponseDto.builder()
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
                .build();
    }
}
