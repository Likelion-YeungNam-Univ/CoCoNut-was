package CoCoNut_was.domains.user.service;

import CoCoNut_was.domains.user.dto.UserReqDto;
import CoCoNut_was.domains.user.dto.UserResDto;
import CoCoNut_was.domains.user.entity.User;
import CoCoNut_was.domains.user.repository.UserRepository;
import CoCoNut_was.security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // 1. 회원가입
    public User signUp(UserReqDto dto) {
        // 아이디 및 닉네임 중복확인 검증
        if(existsByIdentifier(dto.getIdentifier()) || existByNickname(dto.getNickname()))
            throw new IllegalArgumentException("아이디 또는 닉네임이 중복되었습니다.");

        User user = dto.toEntity(passwordEncoder.encode(dto.getPassword()));

        return userRepository.save(user);
    }

    // 아이디 중복확인
    public boolean existsByIdentifier(String identifier) {
        return userRepository.existsByIdentifier(identifier);
    }

    // 닉네임 중복확인
    public boolean existByNickname(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    // 유저 상세조회
    public UserResDto getUser(String identifier) {
        User user = userRepository.findByIdentifier(identifier).orElseThrow(
                () -> new IllegalArgumentException("아이디 : " + identifier + " 를 가진 유저가 존재하지 않습니다.")
        );
        return UserResDto.fromEntity(user);
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(String identifier) {
        User user = userRepository.findByIdentifier(identifier).orElseThrow(
                () -> new IllegalArgumentException("아이디 : " + identifier + " 는 유효하지 않습니다.")
        );
        userRepository.delete(user);
    }

    // 로그인
    @Transactional
    public String login(UserReqDto dto, HttpServletResponse res) {
        // 1. 인증
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(dto.getIdentifier(), dto.getPassword());
        authenticationManager.authenticate(token);

        // 2. 토큰 발급
        String accessToken = jwtUtil.createAccessToken(dto.getIdentifier());
        String refreshToken = jwtUtil.createRefreshToken(dto.getIdentifier());

        // 3. Refresh 토큰 보호
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 일주일
                .build();
        res.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // 3.A. Access 토큰 임시 할당
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .path("/")
                .maxAge(60 * 60) // 한시간
                .build();
        res.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

        // 4. Access 토큰은 응답 바디나 헤더에 담기
        return accessToken;
    }

    // 로그아웃
    @Transactional
    public void logout(HttpServletResponse res){
        ResponseCookie deleteRefreshCookie = ResponseCookie.from("refreshToken", "")
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();

//        ResponseCookie deleteAccessCookie = ResponseCookie.from("accessToken", "")
//                .path("/")
//                .httpOnly(true)
//                .maxAge(0)
//                .build();

        res.addHeader(HttpHeaders.SET_COOKIE, deleteRefreshCookie.toString());
//        res.addHeader(HttpHeaders.SET_COOKIE, deleteAccessCookie.toString());
    }
}
