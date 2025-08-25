package CoCoNut_was.domains.submission.reqdto;

import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.submission.entity.Submission;
import CoCoNut_was.domains.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SubmitDto {

    @Schema(description = "공모전 작품 제목", example = "아기사자 디자인 브런치 카페 메뉴판")
    @NotBlank(message = "작품제목은 필수 입력입니다.")
    private String title;

    @Schema(description = "공모전 작품 설명", example = "아기사자의 그림이 그려져있고, 아이들이 좋아할만한 캐릭터 디자인을 채택하였습니다.")
    private String description;

    @Schema(description = "작품에 대한 외부링크", example = "https://깃허브주소.com")
    private String relatedUrl;

    public Submission toEntity(Project project, User user, String imageUrl){
        return Submission.builder()
                .title(this.title)
                .description(this.description)
                .relatedUrl(this.relatedUrl)
                .imageUrl(imageUrl)
                .submittedAt(LocalDate.now())
                .project(project)
                .user(user)
                .build();
    }
}