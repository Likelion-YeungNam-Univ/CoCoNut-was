package CoCoNut_was.controller;

/*
회원가입, 회원조회, 회원수정, 회원삭제를 다루는 컨트롤러
현재 api명세 없이 일반적으로 작성
 */

import CoCoNut_was.dto.UserReqDto;
import CoCoNut_was.entity.User;
import CoCoNut_was.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    // 1. 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserReqDto dto){
        User created = userService.signUp(dto); // 새로만들기
        return ResponseEntity.ok(created);
    }



    // 2. 회원조회 : 유저들의 정보가 필요할때 설계

}
