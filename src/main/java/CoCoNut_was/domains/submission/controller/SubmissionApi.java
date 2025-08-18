package CoCoNut_was.domains.submission.controller;

import CoCoNut_was.domains.submission.reqdto.SubmitDto;
import CoCoNut_was.domains.submission.resdto.SubmissionListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Submission API", description = "작품 관련 API")
public interface SubmissionApi {

    @Operation(summary = "작품 제출", description = "특정 공모전에 대한 작품을 제출합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "제출성공"),
            @ApiResponse(responseCode = "400", description = "입력 누락 및 형식 비일치",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "작품제목 누락", value = """
                                    {
                                        "title": "작품제목은 필수 입력입니다."
                                    }
                                    """),
                            @ExampleObject(name = "작품정보 JSON 또는 이미지 누락", value = """
                                    {
                                        "status": 400,
                                        "message": "작품 정보 JSON과 Image 파일 모두 제공되지 않았습니다. 누락된 부분이 있는지 확인해주세요."
                                    }
                                    """),
                            @ExampleObject(name = "중복 지원 방지", value = """
                                    {
                                        "status": 400,
                                        "message": "이미 지원하신 공모전에 다시 지원할 수 없습니다."
                                    }
                                    """),
                            @ExampleObject(name = "소상공인 계정이 공모전에 참여하려는 경우", value = """
                                    {
                                        "status": 400,
                                        "message": "로그인된 계정이 해당 작업을 수행할 수 있는 역할이 아닙니다."
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
    ResponseEntity<?> submit(
            @Parameter(description = "프로젝트 고유 ID")
            @PathVariable Long project_id,
            @Parameter(description = "작품 정보 (JSON 형식)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SubmitDto.class)))
            @Valid @RequestPart(value = "info", required = true) SubmitDto dto,
            @Parameter(description = "작품 대표 사진")
            @RequestPart(value = "image", required = false) MultipartFile image,
            @AuthenticationPrincipal UserDetails userDetails
    );


    @Operation(summary = "작품 목록 조회", description = "특정 공모전에 대한 모든 작품을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SubmissionListDto.class)),
                            examples = @ExampleObject(
                                    name = "조회 성공 예시",
                                    value = """
                            [
                                {
                                    "submissionId": 123,
                                    "projectId": 12,
                                    "userId": 1,
                                    "title": "초콜릿 카페 메뉴판",
                                    "imageUrl": "https://storage.googleapis.com/example_bucket/chocolate_menu.jpeg"
                                },
                                {
                                    "submissionId": 124,
                                    "projectId": 12,
                                    "userId": 2,
                                    "title": "여름 시즌 특별 음료 포스터",
                                    "imageUrl": "https://storage.googleapis.com/example_bucket/summer_ade_poster.png"
                                }
                            ]
                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 공모전을 찾을 수 없음",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "status": 404,
                                        "message": "해당 공모전을 찾을 수 없습니다."
                                    }
                                    """)
                    })),
//            @ApiResponse(responseCode = "401", description = "액세스 토큰 미입력/만료",
//                    content = @Content(mediaType = "application/json", examples = {
//                            @ExampleObject(value = """
//                                    {
//                                        "status" : 401,
//                                        "message" : "토큰이 없거나 만료되었습니다."
//                                    }
//                                    """)
//                    }))
    })
    ResponseEntity<?> getSubmissions(
            @Parameter(description = "프로젝트 고유 ID")
            @PathVariable Long project_id
    );

    @Operation(summary = "작품 상세 조회", description = "특정 작품에 대한 상세 정보를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "submissionId": 123,
                                        "projectId": 12,
                                        "userId": 1,
                                        "title": "초콜릿 카페",
                                        "description": "저희는 초콜릿을 직접 재배하여 판매합니다!",
                                        "relatedUrl": "https://github.com/java_spring_lover/chocolate_farmer",
                                        "imageUrl": "https://storage.googleapis.com/example_bucket/chocolate_menu.jpeg",
                                        "submittedAt": "2025-08-15",
                                        "writer": "자바칩프라푸치노"
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
                    })),
            @ApiResponse(responseCode = "404", description = "해당 작품을 찾을 수 없음",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                      "status": 404,
                                      "message": "해당 작품은 존재하지 않습니다."
                                    }
                                    """)
                    }))
    })
    ResponseEntity<?> getSubmissionById(
            @Parameter(description = "작품 고유 ID")
            @PathVariable Long submission_id,
            @AuthenticationPrincipal UserDetails userDetails
    );


    @Operation(summary = "작품 수정", description = "작품 수정 시도")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "401", description = "액세스 토큰 미입력/만료",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "status" : 401,
                                        "message" : "토큰이 없거나 만료되었습니다."
                                    }
                                    """),
                    })),
            @ApiResponse(responseCode = "403", description = "로그인 정보와 작성자 정보 불일치",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "status": 403,
                                        "message": "작품의 유저정보와 로그인 정보가 일치하지 않습니다."
                                    }
                                    """),
                    })),
            @ApiResponse(responseCode = "404", description = "해당 작품을 찾을 수 없음",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "status": 404,
                                        "message": "해당 작품은 존재하지 않습니다."
                                    }
                                    """)
                    }))
    })
    ResponseEntity<?> updateSubmission(
            @Parameter(description = "작품 고유 ID")
            @PathVariable Long submission_id,
            @Parameter(description = "작품 수정 정보")
            @RequestBody SubmitDto dto,
            @AuthenticationPrincipal UserDetails userDetails
    );



    @Operation(summary = "작품 제출 자격검증", description = "사용자가 이미 공모전에 작품을 제출했거나, 공모전 주인이 자신의 공모전에 참여해버리는 오류를 막기 위한 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "자격 있음"),
            @ApiResponse(responseCode = "400", description = "입력 누락 및 형식 비일치",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "소상공인 계정이 공모전에 참여하려는 경우", value = """
                                    {
                                        "status": 400,
                                        "message": "로그인된 계정이 해당 작업을 수행할 수 있는 역할이 아닙니다."
                                    }
                                    """),
                            @ExampleObject(name = "중복 지원 방지", value = """
                                    {
                                        "status": 400,
                                        "message": "이미 지원하신 공모전에 다시 지원할 수 없습니다."
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
    ResponseEntity<?> checkSubmitValidation(
            @Parameter(description = "작품 고유 ID")
            @PathVariable Long project_id,
            @AuthenticationPrincipal UserDetails userDetails
    );
}
