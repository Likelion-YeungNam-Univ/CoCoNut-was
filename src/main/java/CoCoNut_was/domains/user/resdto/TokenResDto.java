package CoCoNut_was.domains.user.resdto;

import lombok.Builder;
import lombok.Data;

@Data
public class TokenResDto {
    private String accessToken;
    private Long userId;
    private String email;
    private String role;

    @Builder public TokenResDto(String accessToken, Long userId, String email, String role) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.email = email;
        this.role = role;
    }
}
