package CoCoNut_was.domains.user.controller;

/*
회원가입(추가), 회원조회, 회원수정, 회원삭제를 다루는 컨트롤러
 */

import CoCoNut_was.domains.user.dto.UserReqDto;
import CoCoNut_was.domains.user.dto.UserResDto;
import CoCoNut_was.domains.user.entity.User;
import CoCoNut_was.domains.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    // 1. 회원가입(회원추가)
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserReqDto dto){
        User created = userService.signUp(dto); // 새로만들기
        return ResponseEntity.ok(created.getIdentifier());
    }


    // 2. 회원조회
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUser(@PathVariable String user_id){
        UserResDto userInfo = userService.getUser(user_id);

        return ResponseEntity.ok(userInfo);
    }


    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable String user_id){
        userService.deleteUser(user_id);
        return ResponseEntity.ok("성공적으로 탈퇴하였습니다.");
    }

}
