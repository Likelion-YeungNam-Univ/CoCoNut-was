package CoCoNut_was.domains.vote.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
    })
    ResponseEntity<?> vote(
            @PathVariable Long submission_id,
            @AuthenticationPrincipal UserDetails userDetails
    );

    ResponseEntity<?> voteCount(
            @PathVariable Long submission_id
    );

    ResponseEntity<?> voteResult(
            @PathVariable Long project_id
    );

}
