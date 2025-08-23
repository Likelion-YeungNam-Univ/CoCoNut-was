package CoCoNut_was.domains.reward.controller;

import CoCoNut_was.domains.project.dto.ProjectListResponseDto;
import CoCoNut_was.domains.reward.dto.RewardResponseDto;
import CoCoNut_was.domains.reward.dto.WinnerInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

import java.util.List;

@Tag(name = "Reward API", description = "보상 관련 API")
public interface RewardApi {
    @Operation(summary = "우승자 선정", description = "우승자를 선정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "우승자 선정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RewardResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 공모전 및 제출물 비일치",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "공모전이 존재하지 않는 경우", value = """
                                    {
                                        "status": 404,
                                        "message": "해당 공모전을 찾을 수 없습니다."
                                    }
                                    """),
                            @ExampleObject(name = "작품이 존재하지 않는 경우", value = """
                                    {
                                        "status": 404,
                                        "message": "해당 작품은 존재하지 않습니다."
                                    }
                                    """)
                    }))
    })
    ResponseEntity<?> awardWinner(
            @Parameter(description = "우승자를 선정할 공모전의 ID", required = true, example = "1")
            @PathVariable Long projectId,
            @Parameter(description = "우승자로 선택할 작품의 ID", required = true, example = "1")
            @PathVariable Long submissionId
    );


    @Operation(summary = "사용자의 작품이 선정된 공모전 리스트", description = "사용자가 선정된 공모전을 최신순으로 나열")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "선정된 공모전 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CoCoNut_was.domains.project.dto.ProjectListResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 유저가 존재하지않을 경우",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "유저가 존재하지 않는 경우", value = """
                                    {
                                        "status": 404,
                                        "message": "해당 유저를 찾을 수 없습니다."
                                    }
                                    """)
                    }))

    })
    ResponseEntity<List<ProjectListResponseDto>> getMyAwards(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails
    );


    @Operation(summary = "사용자의 작품이 선정된 횟수", description = "사용자가 선정된 횟수를 조회.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "선정된 횟수 조회 성공",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = "0")
                    })),
            @ApiResponse(responseCode = "404", description = "해당 유저가 존재하지않을 경우",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "유저가 존재하지 않는 경우", value = """
                                    {
                                        "status": 404,
                                        "message": "해당 유저를 찾을 수 없습니다."
                                    }
                                    """)
                    }))
    })
    ResponseEntity<Long> getMyAwardsCount(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails
    );



    @Operation(summary = "공모전의 우승자 정보 조회", description = "공모전 우승자 정보 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "공모전 우승자 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WinnerInfoDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않음",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "유저가 존재하지 않는 경우", value = """
                                    {
                                        "status": 404,
                                        "message": "해당 유저를 찾을 수 없습니다."
                                    }
                                    """),
                            @ExampleObject(name = "해당 공모전을 찾을 수 없습니다.", value = """
                                    {
                                        "status": 404,
                                        "message": "해당 공모전을 찾을 수 없습니다."
                                    }
                                    """),
                            @ExampleObject(name = "수상한 사람이 없는 경우", value = """
                                    {
                                        "status": 404,
                                        "message": "이 공모전엔 수상자가 없습니다."
                                    }
                                    """)
                    }))
    })
    ResponseEntity<?> getProjectWinner(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description = "프로젝트 고유 ID")
            @PathVariable Long project_id
    );
}
