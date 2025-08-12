package CoCoNut_was.domains.user.controller;

import CoCoNut_was.domains.user.reqdto.LoginUserDto;
import CoCoNut_was.domains.user.resdto.TokenResDto;
import CoCoNut_was.domains.user.reqdto.CreateUserDto;
import CoCoNut_was.domains.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController implements UserApi {
    private final UserService userService;

    // 1. 회원가입(회원추가)
    @Override
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody CreateUserDto dto){
        userService.signUp(dto);
        return ResponseEntity.ok().build();
    }

//    // 2. 회원조회 (관리자) 임시 폐기
//    @GetMapping("/{user_id}")
//    public ResponseEntity<?> getUser(@PathVariable Long user_id){
//        return ResponseEntity.ok(userService.getUser(user_id));
//    }

    // 2. 내 정보 조회(마이페이지)
    @GetMapping
    public ResponseEntity<?> me(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(userService.me(userDetails));
    }

    // 3. 회원탈퇴(삭제)
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        userService.deleteUser(userDetails);
        return ResponseEntity.ok().build();
    }

    // 4. 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserDto dto, HttpServletResponse res){
        TokenResDto tokenResDto = userService.login(dto, res);
        return ResponseEntity.ok(tokenResDto);
    }

    // 5. 로그아웃 : 단순 토큰인증, 액세스 토큰은 프론트엔드에서 제거해줘야함
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse res) {
        userService.logout(res);
        return ResponseEntity.ok().build();
    }

}
