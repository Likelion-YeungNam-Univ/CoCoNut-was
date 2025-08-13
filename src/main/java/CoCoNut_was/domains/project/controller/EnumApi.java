package CoCoNut_was.domains.project.controller;

import CoCoNut_was.domains.project.dto.EnumResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Enum API", description = "공모전 생성에 필요한 Enum 타입 데이터 조회 API")
public interface EnumApi {

    @Operation(summary = "공모전 카테고리 목록 조회", description = "공모전 생성 시 선택 가능한 모든 카테고리 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EnumResponseDto.class)))
    })
    ResponseEntity<?> getCategories();

    @Operation(summary = "공모전 업종 목록 조회", description = "공모전 생성 시 선택 가능한 모든 업종 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "업종 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EnumResponseDto.class)))
    })
    ResponseEntity<?> getBusinessType();
}