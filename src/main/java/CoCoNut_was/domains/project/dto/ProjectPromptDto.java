package CoCoNut_was.domains.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProjectPromptDto {
    @Schema(description = "AI에게 전달할 프롬프트 내용", example = "대학생들을 타겟으로 하는 카페 로고 디자인 공모전을 열고 싶어")
    @NotBlank(message = "프롬프트 내용은 비워둘 수 없습니다.")
    private String prompt;
}
