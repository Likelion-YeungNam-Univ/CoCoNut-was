package CoCoNut_was.domains.user.service;

import CoCoNut_was.domains.user.reqdto.LoginUserDto;
import CoCoNut_was.domains.user.resdto.TokenResDto;
import CoCoNut_was.domains.user.reqdto.CreateUserDto;
import CoCoNut_was.domains.user.resdto.UserInfoDto;
import CoCoNut_was.domains.user.entity.User;
import CoCoNut_was.domains.user.repository.UserRepository;
import CoCoNut_was.exception.CustomException;
import CoCoNut_was.exception.ErrorCode;
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

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // 1. 회원가입
    public User signUp(CreateUserDto dto) {
        // 아이디 및 닉네임 중복확인 검증
//        if(existsByEmail(dto.getEmail()) || existsByNickname(dto.getNickname()))
//            throw new IllegalArgumentException("이메일 또는 닉네임이 중복되었습니다.");
        if(existsByEmail(dto.getEmail()))
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXIST);
        if(existsByNickname(dto.getNickname()))
            throw new CustomException(ErrorCode.NICKNAME_ALREADY_EXIST);

        User user = dto.toEntity(passwordEncoder.encode(dto.getPassword()));

        return userRepository.save(user);
    }

    // 아이디 중복확인
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // 닉네임 중복확인
    public boolean existsByNickname(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    // 유저 상세조회
    public UserInfoDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("DB id : " + id + " 를 가진 유저가 존재하지 않습니다.")
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        return UserInfoDto.fromEntity(user);
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("DB id : " + id + " 를 가진 유저가 존재하지 않습니다.")
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        userRepository.delete(user);
    }

    // 로그인
    @Transactional
    public TokenResDto login(LoginUserDto dto, HttpServletResponse res) {
        // 1. 인증
        try{
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
            authenticationManager.authenticate(token);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        // 2. 유저찾기
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        // 3. 토큰 발급
        String accessToken = jwtUtil.createAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtUtil.createRefreshToken(user.getId(), user.getEmail());

        // 4. Refresh 토큰 보호
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 일주일
                .build();
        res.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

//        // 5. Access 토큰 임시 할당
//        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
//                .httpOnly(true)
//                .path("/")
//                .maxAge(60 * 60) // 한시간
//                .build();
//        res.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

        // 6. 기본 정보가 담긴 DTO 반환
        return TokenResDto.builder()
                .accessToken(accessToken)
                .userId(user.getId())
                .email(user.getEmail())
                .build();
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
