package CoCoNut_was.domains.project.dto;

import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.project.entity.Category;
import CoCoNut_was.domains.project.entity.BusinessType;
import CoCoNut_was.domains.project.entity.Status;
import CoCoNut_was.domains.project.entity.ProjectColor;
import CoCoNut_was.domains.project.entity.ProjectStyle;
import CoCoNut_was.domains.project.entity.ProjectTarget;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ProjectDetailResponseDto { // 공모전 상세 조회 DTO (응답)
    private final Long projectId;
    private final String writerNickname;
    private final String title;
    private final String merchantName;
    private final Category category;
    private final BusinessType businessType;
    private final String description;
    private final LocalDate createdAt;
    private final LocalDate deadline;
    private final int rewardAmount;
    private final String summary;
    private final Status status;
    private final List<String> colors;
    private final List<String> styles;
    private final List<String> targets;

    
    public static ProjectDetailResponseDto fromEntity(Project project) {
        return ProjectDetailResponseDto.builder()
                .projectId(project.getId())
                .writerNickname(project.getUser().getNickname())
                .title(project.getTitle())
                .merchantName(project.getMerchantName())
                .category(project.getCategory())
                .businessType(project.getBusinessType())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .deadline(project.getDeadline())
                .rewardAmount(project.getRewardAmount())
                .summary(project.getSummary())
                .status(project.getStatus())
                .colors(project.getProjectColors().stream()
                        .map(ProjectColor::getColor)
                        .collect(Collectors.toList()))
                .styles(project.getProjectStyles().stream()
                        .map(ProjectStyle::getStyle)
                        .collect(Collectors.toList()))
                .targets(project.getProjectTargets().stream()
                        .map(ProjectTarget::getTarget)
                        .collect(Collectors.toList()))
                .build();
    }
}
