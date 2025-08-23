package CoCoNut_was.domains.reward.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageDto {
    private String imageUrl;

    @Builder public ImageDto(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
