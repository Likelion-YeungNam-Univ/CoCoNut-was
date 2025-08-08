package CoCoNut_was.domains.user.resdto;

import lombok.Builder;
import lombok.Data;

@Data
public class TokenResDto {
    private String accessToken;
    private Long userId;
    private String email;

    @Builder public TokenResDto(String accessToken, Long userId, String email) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.email = email;
    }
}
