package CoCoNut_was.domains.user.controller;

import CoCoNut_was.domains.user.reqdto.CreateUserDto;
import CoCoNut_was.domains.user.reqdto.LoginUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
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


    @Operation(summary = "내 정보 조회", description = "조회 시도")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "email": "<email>",
                                        "name": "<name>",
                                        "nickname": "<nickname>",
                                        "role": "ROLE_USER | ROLE_BUSINESS"
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
    ResponseEntity<?> me(
            @Parameter(description = "JWT 토큰기반 유저 조회")
            @AuthenticationPrincipal UserDetails userDetails
    );


    @Operation(summary = "회원탈퇴(삭제)", description = "탈퇴(삭제) 시도")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "status" : 401,
                                        "message" : "토큰이 없거나 만료되었습니다."
                                    }
                                    """)
                    }))
    })
    ResponseEntity<?> deleteUser(
            @Parameter(description = "JWT 토큰기반 유저 탈퇴")
            @AuthenticationPrincipal UserDetails userDetails
    );


    @Operation(summary = "로그인", description = "로그인 시도")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "accessToken" : "<accessToken>",
                                        "userId" : "<userId>",
                                        "email" : "<email>"
                                    }
                                    """)
                    })),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "액세스 토큰 없음 / 만료", value = """
                                    {
                                        "status" : 401,
                                        "message" : "토큰이 없거나 만료되었습니다."
                                    }
                                    """),
                            @ExampleObject(name = "비밀번호 불일치", value = """
                                    {
                                        "status" : 401,
                                        "message" : "비밀번호가 일치하지 않습니다."
                                    }
                                    """)
                    }))
    })
    ResponseEntity<?> login(
            @Parameter(description = "로그인 정보(이메일, 비밀번호)")
            @Valid @RequestBody LoginUserDto dto,
            HttpServletResponse res
    );


    @Operation(summary = "로그아웃", description = "로그아웃 시도")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "액세스 토큰 미입력/만료",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "status" : 401,
                                        "message" : "액세스 토큰이 유효하지 않습니다."
                                    }
                                    """),
                    }))
    })
    ResponseEntity<?> logout(HttpServletResponse res);

}
