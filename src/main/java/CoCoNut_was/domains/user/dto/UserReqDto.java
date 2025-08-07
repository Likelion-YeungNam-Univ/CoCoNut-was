package CoCoNut_was.domains.user.dto;

import CoCoNut_was.domains.user.entity.Role;
import CoCoNut_was.domains.user.entity.User;
import lombok.Data;

@Data
public class UserReqDto {
    private String identifier;
    private String name;
    private String nickname;
    private String password;
    private String role;

    public User toEntity(String encodedPassword) {
        return User.builder()
                .identifier(identifier)
                .name(name)
                .nickname(nickname)
                .password(encodedPassword)
                .role(Role.valueOf(role))
                .build();
    }
}
