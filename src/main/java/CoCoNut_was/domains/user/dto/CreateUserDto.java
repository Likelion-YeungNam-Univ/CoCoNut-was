package CoCoNut_was.domains.user.dto;

import CoCoNut_was.domains.user.entity.Role;
import CoCoNut_was.domains.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserDto {
    @Email(message = "이메일 형식을 맞춰주세요.")
    @NotBlank(message = "이메일은 필수 입력입니다.")
    private String email;

    @NotBlank(message = "이름은 필수 입력입니다.")
    private String name;

    @NotBlank(message = "닉네임은 필수 입력입니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    private String password;

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
