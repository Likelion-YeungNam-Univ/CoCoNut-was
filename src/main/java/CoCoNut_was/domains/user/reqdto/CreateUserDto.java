package CoCoNut_was.domains.user.reqdto;

import CoCoNut_was.domains.user.entity.Role;
import CoCoNut_was.domains.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "회원가입 요청 API")
public class CreateUserDto {
    @Schema(description = "사용자 이메일", example = "likelion13@gmail.com")
    @Email(message = "이메일 형식을 맞춰주세요.")
    @NotBlank(message = "이메일은 필수 입력입니다.")
    private String email;

    @Schema(description = "사용자 이름", example = "홍길동")
    @NotBlank(message = "이름은 필수 입력입니다.")
    private String name;

    @Schema(description = "사용자 닉네임", example = "열정있는 아기사자")
    @NotBlank(message = "닉네임은 필수 입력입니다.")
    @Size(max = 10, message = "닉네임은 최대 10글자 까지 가능합니다.")
    private String nickname;

    @Schema(description = "비밀번호", example = "abcd1234")
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    private String password;

    @Schema(description = "역할", example = "ROLE_USER | ROLE_BUSINESS")
    @NotBlank(message = "역할 지정은 필수 사항입니다.")
    private String role;

    public User toEntity(String encodedPassword) {
        return User.builder()
                .email(this.email)
                .name(this.name)
                .nickname(this.nickname)
                .password(encodedPassword)
                .role(Role.valueOf(this.role))
                .build();
    }
}
