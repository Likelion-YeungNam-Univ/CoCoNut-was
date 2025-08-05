package CoCoNut_was.dto;

import CoCoNut_was.entity.Role;
import CoCoNut_was.entity.User;
import lombok.Data;

@Data
public class UserReqDto {
    private String identifier;
    private String name;
    private String nickname;
    private String password;
    private String role;

    public User toEntity() {
        return User.builder()
                .identifier(identifier)
                .name(name)
                .nickname(nickname)
                .password(password)
                .role(Role.valueOf(role))
                .build();
    }
}
