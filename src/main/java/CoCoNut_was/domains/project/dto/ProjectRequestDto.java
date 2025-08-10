package CoCoNut_was.domains.project.dto;

import CoCoNut_was.domains.project.entity.BusinessType;
import CoCoNut_was.domains.project.entity.Category;
import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProjectRequestDto { // 공모전 생성 DTO (요청)
    private String title;
    private String merchantName;
    private Category category;
    private BusinessType businessType;
    private String description;
    private int durationDays;
    private int rewardAmount;
    private String summary;

    // 중복 선택 항목
    private List<String> colors;
    private List<String> styles;
    private List<String> targets;

    public Project toEntity(User user) {
        LocalDateTime deadline = LocalDateTime.now().plusDays(this.durationDays);

        return Project.builder()
                .user(user) // 공모전 생성 유저
                .title(this.title)
                .merchantName(this.merchantName)
                .category(this.category)
                .businessType(this.businessType)
                .description(this.description)
                .rewardAmount(this.rewardAmount)
                .summary(this.summary)
                .build();
    }
}
