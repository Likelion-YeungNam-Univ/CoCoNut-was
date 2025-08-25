package CoCoNut_was.domains.project.dto;

import lombok.Getter;

@Getter
public class EnumResponseDto {
    private final String code;
    private final String description;

    public EnumResponseDto(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
