package CoCoNut_was.domains.project.controller;

import CoCoNut_was.domains.project.dto.ProjectRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Project API", description = "공모전 관련 API")
public interface ProjectApi {

    @Operation(summary = "공모전 생성", description = "새로운 공모전을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "공모전 생성 성공",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = "1")
                    })),
            @ApiResponse(responseCode = "400", description = "입력 누락 및 형식 비일치",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "필드 누락", value = """
                                    {
                                        "title" : "제목은 필수 입력입니다."
                                    }
                                    """),
                    })),
            @ApiResponse(responseCode = "401", description = "인증 실패 (토큰 없음 또는 만료)",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "status" : 401,
                                        "message" : "토큰이 없거나 만료되었습니다."
                                    }
                                    """),
                    }))
    })
    ResponseEntity<?> createProject(
            @Parameter(description = "공모전 생성 정보", required = true,
                    schema = @Schema(implementation = ProjectRequestDto.class))
            @Valid @RequestBody ProjectRequestDto dto,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails
    );

    @Operation(summary = "공모전 목록 조회", description = "모든 공모전 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "공모전 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CoCoNut_was.domains.project.dto.ProjectListResponseDto.class)))
    })
    ResponseEntity<?> findAllProjects();


    @Operation(summary = "공모전 상세 조회", description = "특정 공모전의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "공모전 상세 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CoCoNut_was.domains.project.dto.ProjectDetailResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 공모전을 찾을 수 없음",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "status": 404,
                                        "message": "해당 공모전을 찾을 수 없습니다."
                                    }
                                    """)
                    }))
    })
    ResponseEntity<?> findProjectById(
            @Parameter(description = "조회할 공모전의 ID", required = true, example = "1")
            @PathVariable Long project_id
    );


    @Operation(summary = "공모전 삭제", description = "특정 공모전을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "공모전 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패 (토큰 없음 또는 만료)",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "status" : 401,
                                        "message" : "토큰이 없거나 만료되었습니다."
                                    }
                                    """),
                    })),
            @ApiResponse(responseCode = "404", description = "해당 공모전을 찾을 수 없음",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "status": 404,
                                        "message": "해당 공모전을 찾을 수 없습니다."
                                    }
                                    """)
                    }))
    })
    ResponseEntity<?> deleteProject(
            @Parameter(description = "삭제할 공모전의 ID", required = true, example = "1")
            @PathVariable Long project_id
    );
}