package CoCoNut_was.domains.user.dto;

import CoCoNut_was.domains.user.entity.Role;
import CoCoNut_was.domains.user.entity.User;
import lombok.Data;

@Data
public class CreateUserDto {
    private String email;
    private String name;
    private String nickname;
    private String password;
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
