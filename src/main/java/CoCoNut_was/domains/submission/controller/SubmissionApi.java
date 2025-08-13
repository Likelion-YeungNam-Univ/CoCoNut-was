package CoCoNut_was.domains.submission.controller;

import CoCoNut_was.domains.submission.reqdto.SubmitDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Submission API", description = "공모전 작품 API")
public interface SubmissionApi {

    @Operation(summary = "작품 제출", description = "작품 제출 시도")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "제출성공"),
            @ApiResponse(responseCode = "400", description = "입력 누락 및 형식 비일치",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "작품 정보에 대한 누락 발생", value = """
                                    {
                                        "status": 400,
                                        "message": "제출물 형태에 대해 누락이 있습니다. 반드시 KEY에 info를 포함하고, VALUE에 JSON값을, Content-Type를 application/json으로 설정해주세요."
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
    ResponseEntity<?> submit(
            @PathVariable Long project_id,
            @Valid @RequestPart(value = "info", required = true) SubmitDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @AuthenticationPrincipal UserDetails userDetails
    );



    ResponseEntity<?> getSubmissions(@PathVariable Long project_id);



    ResponseEntity<?> updateSubmission(
            @PathVariable Long submission_id,
            @RequestBody SubmitDto dto,
            @AuthenticationPrincipal UserDetails userDetails
    );
}
