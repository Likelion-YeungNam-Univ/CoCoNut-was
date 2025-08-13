package CoCoNut_was.domains.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProjectPromptDto {
    @Schema(description = "AI에게 전달할 프롬프트 내용", example = "우리는 카페 아기사자의 메뉴판을 만들어주는 공모전에 지원했어. 아기사자의 밝은 갈색과 초원 색상을 잘 섞었어. 이러한 설명을 좀 자세히 적어줘")
    @NotBlank(message = "프롬프트 내용은 비워둘 수 없습니다.")
    private String prompt;
}
