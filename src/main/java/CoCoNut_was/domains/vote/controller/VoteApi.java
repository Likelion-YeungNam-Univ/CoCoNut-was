package CoCoNut_was.domains.vote.controller;

import CoCoNut_was.domains.vote.resdto.VoteResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Vote API", description = "투표 관련 API")
public interface VoteApi {

    @Operation(summary = "투표하기", description = "일부 조건을 제외한 모든 서비스 이용자는 작품에 대해 투표할 수 있는 권한을 갖습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투표 성공"),
            @ApiResponse(responseCode = "400", description = "투표 권한 없음",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "사용자가 자신의 작품에 투표할 경우" , value = """
                                    {
                                        "status": 400,
                                        "message": "참여자는 자신이 제출한 작품에 투표할 수 없습니다."
                                    }
                                    """),
                            @ExampleObject(name = "공모전 주인이 투표할 경우" , value = """
                                    {
                                        "status": 400,
                                        "message": "공모전을 올린 소상공인은 해당 작품들에 대해 투표할 수 없습니다."
                                    }
                                    """),
                            @ExampleObject(name = "이미 투표한 사용자일 경우", value = """
                                    {
                                        "status": 400,
                                        "message": "한 공모전에 대한 작품에 이미 투표하셨습니다."
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
            @ApiResponse(responseCode = "404", description = "존재하지 않는 정보",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "로그인 사용자가 존재하지 않는 경우" , value = """
                                    {
                                        "status": 404,
                                        "message": "해당 유저를 찾을 수 없습니다."
                                    }
                                    """),
                            @ExampleObject(name = "해당 작품이 없는 경우" , value = """
                                    {
                                        "status": 404,
                                        "message": "해당 작품은 존재하지 않습니다."
                                    }
                                    """)
                    })),
            @ApiResponse(responseCode = "409", description = "중복 투표 방지",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "status" : 409,
                                        "message" : "해당 공모전에 대한 작품에 이미 투표하셨습니다."
                                    }
                                    """),
                    }))
    })
    ResponseEntity<?> vote(
            @PathVariable Long submission_id,
            @AuthenticationPrincipal UserDetails userDetails
    );


    @Operation(summary = "투표조회", description = "프론트엔드에서 작품에 대한 투표수와 Project 번호까지 추적할 수 있게 만든 DB조회용 API입니다.(필요없으시면 삭제 요청 바랍니다)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투표 조회 성공",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "projectId": 3,
                                        "submissionId": 2,
                                        "voteCount": 1
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
            @ApiResponse(responseCode = "404", description = "해당 작품이 없는 경우",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "status": 404,
                                        "message": "해당 작품은 존재하지 않습니다."
                                    }
                                    """)
                    })),
    })
    ResponseEntity<?> voteCount(
            @Parameter(description = "작품 고유 ID")
            @PathVariable Long submission_id,
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserDetails userdetails
    );


    @Operation(summary = "투표결과 상세조회", description = "특정 공모전에 대한 모든 작품의 이미지, 제목, 투표수를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VoteResultDto.class)),
                            examples = @ExampleObject(
                                    name = "조회 성공 예시",
                                    value = """
                            [
                                {
                                    "submissionId": 123,
                                    "title": "초콜릿 카페 메뉴판",
                                    "imageUrl": "https://storage.googleapis.com/example_bucket/chocolate_menu.jpeg",
                                    "voteCount": 1
                                },
                                {
                                    "submissionId": 124,
                                    "title": "여름 시즌 특별 음료 포스터",
                                    "imageUrl": "https://storage.googleapis.com/example_bucket/summer_ade_poster.png",
                                    "voteCount": 3
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
            @ApiResponse(responseCode = "401", description = "액세스 토큰 미입력/만료",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "status" : 401,
                                        "message" : "토큰이 없거나 만료되었습니다."
                                    }
                                    """)
                    }))
    })
    ResponseEntity<?> voteResult(
            @Parameter(description = "작품 고유 ID")
            @PathVariable Long project_id
    );

}
