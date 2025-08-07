package CoCoNut_was.controller;

/*
회원가입(추가), 회원조회, 회원수정, 회원삭제를 다루는 컨트롤러
 */

import CoCoNut_was.dto.UserReqDto;
import CoCoNut_was.dto.UserResDto;
import CoCoNut_was.entity.User;
import CoCoNut_was.service.UserService;
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
        return ResponseEntity.ok().build(); // 생성 시 반환 미정
    }


    // 2. 회원조회
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUser(@PathVariable String user_id){
        UserResDto userInfo = userService.getUser(user_id);

        return ResponseEntity.ok().build();
    }

}
