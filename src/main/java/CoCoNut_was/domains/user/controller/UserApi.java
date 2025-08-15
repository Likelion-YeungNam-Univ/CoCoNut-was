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
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "User API", description = "유저 관련 API")
public interface UserApi {

    @Operation(summary = "회원가입", description = "서비스를 이용하기 위한 회원가입 입니다. role은 ROLE_USER, ROLE_BUSINESS 둘중 하나만 가능합니다.")
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


    @Operation(summary = "내 정보 조회", description = "자신의 정보를 조회하는 기능입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "user_id": "<user_id>",
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


    @Operation(summary = "회원탈퇴(삭제)", description = "회원탈퇴를 하는 기능입니다.")
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


    @Operation(summary = "로그인", description = "로그인을 하는 기능이며, 여기서 나온 토큰으로 서비스에서 유저 정보를 조회합니다.")
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
            @ApiResponse(responseCode = "404", description = "이메일이 존재하지 않음",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    {
                                        "status": 404,
                                        "message": "해당 유저를 찾을 수 없습니다."
                                    }
                                    """)
                    })),
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
                                    """)
                    })),
            @ApiResponse(responseCode = "401", description = "비밀번호 불일치",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
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


    @Operation(summary = "로그아웃", description = "로그아웃을 하는 기능입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
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
    ResponseEntity<?> logout(HttpServletResponse res);


    @Operation(summary = "이메일 중복 확인", description = "사용 가능한 이메일인지 확인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용 가능한 이메일"),
            @ApiResponse(responseCode = "409", description = "이미 사용 중인 이메일",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                {
                                    "status" : 409,
                                    "message" : "해당 이메일은 이미 존재합니다."
                                }
                                """)
                    }))
    })
    ResponseEntity<?> checkEmail(
            @Parameter(description = "확인할 이메일", required = true, example = "likelion13@gmail.com")
            @RequestParam String email
    );


    @Operation(summary = "닉네임 중복 확인", description = "사용 가능한 닉네임인지 확인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용 가능한 닉네임"),
            @ApiResponse(responseCode = "409", description = "이미 사용 중인 닉네임",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                {
                                    "status" : 409,
                                    "message" : "해당 닉네임은 이미 존재합니다."
                                }
                                """)
                    }))
    })
    ResponseEntity<?> checkNickname(
            @Parameter(description = "확인할 닉네임", required = true, example = "코코넛")
            @RequestParam String nickname
    );
}
