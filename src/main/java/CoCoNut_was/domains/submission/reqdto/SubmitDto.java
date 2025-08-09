package CoCoNut_was.domains.submission.reqdto;

import CoCoNut_was.domains.project.entitiy.Project;
import CoCoNut_was.domains.submission.entity.Submission;
import CoCoNut_was.domains.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SubmitDto {

    @NotBlank(message = "작품제목은 필수 입력입니다.")
    private String title;

    private String description;

    private String imageUrl;

    @NotBlank(message = "제출일자는 필수 입력입니다.")
    private String submittedAt;
    // "2002-08-03" 형식의 문자열이 입력되어야함

    @NotBlank(message = "대상 프로젝트는 필수 사항입니다.")
    private Long projectId;

    @NotBlank(message = "유저 지정은 필수 사항입니다.")
    private Long userId;

    public Submission toEntity(Project project, User user){
        return Submission.builder()
                .description(this.description)
                .imageUrl(this.imageUrl)
                .submittedAt(LocalDate.parse(this.submittedAt))
                .project(project)
                .user(user)
                .build();
    }
}
