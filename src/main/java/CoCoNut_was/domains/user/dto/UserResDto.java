package CoCoNut_was.domains.user.dto;

import CoCoNut_was.domains.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
public class UserResDto {
    private String email;
    private String name;
    private String nickname;
    private String role;

    @Builder public UserResDto(String email, String name, String nickname, String role) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.role = role;
    }

    public static UserResDto fromEntity(User user){
        return UserResDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .role(user.getRole().toString()) // 일단 toString 처리
                .build();
    }
}
