package CoCoNut_was.domains.submission.reqdto;

import CoCoNut_was.domains.project.entity.Project;
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

    // 제출 일자는 그냥 LocalDate.now()를 쓰면 되지 않을까 라는 생각입니다.

//    @NotBlank(message = "제출일자는 필수 입력입니다.")
//    private String submittedAt;
//    // "2002-08-03" 형식의 문자열이 입력되어야함

    public Submission toEntity(Project project, User user){
        return Submission.builder()
                .description(this.description)
                .imageUrl(this.imageUrl)
                .submittedAt(LocalDate.now())
                .project(project)
                .user(user)
                .build();
    }
}