package CoCoNut_was.domains.user.reqdto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUserDto {
    @Schema(description = "사용자 이메일", example = "likelion13@gmail.com")
    @Email(message = "이메일 형식을 맞춰주세요.")
    @NotBlank(message = "이메일은 필수 입력입니다.")
    private String email;

    @Schema(description = "비밀번호", example = "abcd1234")
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    private String password;
}
