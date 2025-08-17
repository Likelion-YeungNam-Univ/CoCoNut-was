package CoCoNut_was.domains.project.dto;

import CoCoNut_was.domains.project.entity.BusinessType;
import CoCoNut_was.domains.project.entity.Category;
import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "공모전 생성 요청 API")
public class ProjectRequestDto { // 공모전 생성 DTO (요청)
    @Schema(description = "공모전 제목", example = "경산시 로컬 카페 '코코넛' 로고 디자인 의뢰")
    @NotBlank(message = "공모전 제목은 필수 입력입니다.")
    private String title;

    @Schema(description = "가게명", example = "코코넛 카페")
    @NotBlank(message = "가게명은 필수 입력입니다.")
    private String merchantName;

    @Schema(description = "공모전 카테고리", example = "BRANDING_LOGO")
    @NotNull(message = "카테고리는 필수 선택입니다.")
    private Category category;

    @Schema(description = "공모전 업종 ", example = "FOOD_BEVERAGE")
    @NotNull(message = "공모전 업종은 필수 선택입니다.")
    private BusinessType businessType;

    @Schema(description = "공모전 설명", example = "안녕하세요! 경산시에 새로 오픈하는 '코코넛 카페'입니다. " +
            "따뜻하고 아늑한 분위기를 잘 살릴 수 있는 로고 디자인을 찾고 있습니다. 코코넛과 커피 원두를 모티브로 한 디자인을 선호합니다. 자유롭게 제안해주세요!")
    @NotBlank(message = "공모전 설명은 필수 입력입니다.")
    private String description;

    @Schema(description = "공모 마감날짜", example = "2025-08-08")
    @NotNull(message = "공모 마감날짜는 필수 입력입니다.")
    private LocalDate deadline;

    @Schema(description = "공모전 상금", example = "500000")
    @Min(value = 0, message = "상금은 0원 이상이어야 합니다.")
    private int rewardAmount;

    @Schema(description = "공모전 한 줄 소개", example = "경산시 신규 오픈 '코코넛 카페'의 새 얼굴을 만들어주세요!")
    @NotBlank(message = "공모전 한 줄 소개는 필수 입력입니다.")
    private String summary;

    // 중복 선택 항목
    @Schema(description = "색상", example = "[\n" +
            "        \"#FFFFFF\",\n" +
            "        \"#3B2E2E\",\n" +
            "        \"#FFDAB9\"\n" +
            "    ]")
    private List<String> colors;

    @Schema(description = "스타일", example = "[\n" +
            "        \"미니멀\",\n" +
            "        \"따뜻한\",\n" +
            "        \"귀여운\"\n" +
            "    ]")
    private List<String> styles;

    @Schema(description = "타겟", example = "[\n" +
            "        \"20대 여성\",\n" +
            "        \"대학생\",\n" +
            "        \"인스타그래머\"\n" +
            "    ]")
    private List<String> targets;

    public Project toEntity(User user) {
        return Project.builder()
                .user(user) // 공모전 생성 유저
                .title(this.title)
                .merchantName(this.merchantName)
                .category(this.category)
                .businessType(this.businessType)
                .description(this.description)
                .rewardAmount(this.rewardAmount)
                .summary(this.summary)
                .deadline(this.deadline)
                .build();
    }
}
