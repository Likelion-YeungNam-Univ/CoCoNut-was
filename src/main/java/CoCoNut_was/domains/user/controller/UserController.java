package CoCoNut_was.domains.user.controller;

/*
회원가입(추가), 회원조회, 회원수정, 회원삭제를 다루는 컨트롤러
 */

import CoCoNut_was.domains.user.dto.UserReqDto;
import CoCoNut_was.domains.user.dto.UserResDto;
import CoCoNut_was.domains.user.entity.User;
import CoCoNut_was.domains.user.service.UserService;
import CoCoNut_was.security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // 1. 회원가입(회원추가)
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserReqDto dto){
        return ResponseEntity.ok(userService.signUp(dto).getIdentifier());
    }

    // 2. 회원조회
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUser(@PathVariable String user_id){
        return ResponseEntity.ok(userService.getUser(user_id));
    }

    // 3. 회원탈퇴(삭제)
    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable String user_id){
        userService.deleteUser(user_id);
        return ResponseEntity.ok("성공적으로 탈퇴하였습니다.");
    }

    // 4. 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserReqDto dto, HttpServletResponse res){
        return ResponseEntity.ok().body(Map.of("accessToken", userService.login(dto, res)));
    }

}
