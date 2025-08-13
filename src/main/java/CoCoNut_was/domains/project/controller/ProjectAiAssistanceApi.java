package CoCoNut_was.domains.project.controller;

import CoCoNut_was.domains.project.dto.ProjectAiAssistanceDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Tag(name = "Project AI Assistance API", description = "AI를 이용한 공모전 생성 지원 API")
public interface ProjectAiAssistanceApi {

    @Operation(summary = "AI 공모전 기획 지원", description = "사용자가 입력한 프롬프트를 기반으로 AI가 공모전의 상세 설명, 상금, 기간, 요약을 추천합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "AI 추천 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectAiAssistanceDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "프롬프트 내용 없음" , value = """
                                    {
                                        "status": 400,
                                        "message": "프롬프트 내용이 없습니다. 제대로 입력해주세요."
                                    }
                                    """),
                            @ExampleObject(name = "AI 생성 실패", value = """
                                    {
                                        "status": 400,
                                        "message": "AI Assistance 응답 생성에 실패하였습니다. 입력한 정보가 정확한지 확인해주세요"
                                    }
                                    """)
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
    ResponseEntity<?> getAssistanceForProject(
            @Parameter(description = "AI에게 전달할 프롬프트", required = true,
                    examples = @ExampleObject(value = """
                            {
                                "prompt": "대학생들을 타겟으로 하는 카페 로고 디자인 공모전을 열고 싶어"
                            }
                            """))
            @RequestBody Map<String, String> userReq
    ) throws JsonProcessingException;
}