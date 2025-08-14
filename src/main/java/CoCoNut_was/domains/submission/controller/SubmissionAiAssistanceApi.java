package CoCoNut_was.domains.submission.controller;

import CoCoNut_was.domains.submission.reqdto.SubmissionPromptDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "SubmissionAiAssistance API", description = "AI를 이용한 작품설명 작성 지원 API")
public interface SubmissionAiAssistanceApi {

    @Operation(summary = "AI 작품 설명작성 지원", description = "사용자가 입력한 프롬프트를 기반으로 AI가 작품의 상세설명에 대해서 깔끔하게 작성해줍니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "AI 응답 성공",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "description": "아기사자 카페 메뉴판은 밝은 갈색과 초원 색상을 조화롭게 사용하여 따뜻하면서도 생동감 있는 디자인을 구현했습니다. 아기사자의 친근한 이미지를 살리고, 자연의 신선함을 담아 고객에게 편안한 분위기를 선사하는 메뉴판입니다."
                                    }
                                    """)
                    })),
            @ApiResponse(responseCode = "400", description = "프롬프트 정보 입력 오류",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "의도에 맞지않는 프롬프트 작성" , value = """
                                    {
                                        "status": 400,
                                        "message": "AI Assistance 응답 생성에 실패하였습니다. 입력한 정보가 정확한지 확인해주세요"
                                    }
                                    """),
                            @ExampleObject(name = "프롬프트 내용 없음" , value = """
                                    {
                                        "status": 400,
                                        "message": "프롬프트 내용이 없습니다. 제대로 입력해주세요."
                                    }
                                    """)
                    })),
            @ApiResponse(responseCode = "401", description = "액세스 토큰 미입력/만료",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "status" : 401,
                                        "message" : "토큰이 없거나 만료되었습니다."
                                    }
                                    """),
                    }))
    })
    ResponseEntity<?> getAssistanceForSubmission(
            @Parameter(description = "AI 프롬프트 입력")
            @RequestBody SubmissionPromptDto userReq
    ) throws JsonProcessingException;

}
