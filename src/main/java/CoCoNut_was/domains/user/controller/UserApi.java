package CoCoNut_was.domains.user.controller;

import CoCoNut_was.domains.user.reqdto.CreateUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User API", description = "유저 관련 API")
public interface UserApi {

    @Operation(summary = "회원가입", description = "회원가입 시도")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "입력 누락 및 형식 비일치",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "필드 누락", value = """
                                    {
                                        "<field>" : "<field>는 필수 입력입니다."
                                    }
                                    """),
                            @ExampleObject(name = "이메일 형식 비일치", value = """
                                    {
                                        "email": "이메일 형식을 맞춰주세요."
                                    }
                                    """),
                            @ExampleObject(name = "닉네임 길이 초과", value = """
                                    {
                                        "nickname": "닉네임은 최대 10글자 까지 가능합니다."
                                    }
                                    """)
                    })),
            @ApiResponse(responseCode = "409", description = "중복으로 인한 회원가입 실패",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "이메일 중복", value = """
                                    {
                                        "status" : 409,
                                        "message" : "해당 이메일은 이미 존재합니다."
                                    }
                                    """),
                            @ExampleObject(name = "닉네임 중복", value = """
                                    {
                                        "status" : 409,
                                        "message" : "해당 닉네임은 이미 존재합니다."
                                    }
                                    """)
                    }))
    })
    ResponseEntity<?> signUp(
            @Parameter(description = "회원가입 정보")
            @Valid @RequestBody CreateUserDto dto
    );


}
