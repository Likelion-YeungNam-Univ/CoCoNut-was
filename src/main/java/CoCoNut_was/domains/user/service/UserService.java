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
import org.springframework.security.core.userdetails.UserDetails;
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
    @Transactional
    public void signUp(CreateUserDto dto) {

        if(existsByEmail(dto.getEmail()))
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXIST);
        if(existsByNickname(dto.getNickname()))
            throw new CustomException(ErrorCode.NICKNAME_ALREADY_EXIST);

        User user = dto.toEntity(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }

    // 아이디 중복확인
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // 닉네임 중복확인
    public boolean existsByNickname(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    // 마이페이지 상세 조회
    @Transactional(readOnly = true)
    public UserInfoDto me(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        return UserInfoDto.fromEntity(user);
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        userRepository.delete(user);
    }

    // 로그인
    @Transactional
    public TokenResDto login(LoginUserDto dto, HttpServletResponse res) {
        // 1. 유저찾기
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        // 2. 비밀번호 검증
        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);

        // 3. 인증
        try{
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
            authenticationManager.authenticate(token);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        // 4. 토큰 발급
        String accessToken = jwtUtil.createAccessToken(user.getId(), user.getEmail(), user.getRole().toString());
        String refreshToken = jwtUtil.createRefreshToken(user.getId(), user.getEmail(), user.getRole().toString());

        // 5. Refresh 토큰 헤더 붙이기
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 일주일
                .build();
        res.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        // 6. Access 토큰 헤더 붙이기
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .path("/")
                .maxAge(2 * 60 * 60) // 두시간
                .build();
        res.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

        // 7. 기본 정보가 담긴 DTO 반환
        return TokenResDto.builder()
                .accessToken(accessToken)
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole().toString())
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
        res.addHeader(HttpHeaders.SET_COOKIE, deleteRefreshCookie.toString());

        ResponseCookie deleteAccessCookie = ResponseCookie.from("accessToken", "")
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();
        res.addHeader(HttpHeaders.SET_COOKIE, deleteAccessCookie.toString());

    }

    // 이메일을 통한 유저조회
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
    }

    // 이메일 중복 시 예외 발생
    public void checkEmailDuplication(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXIST);
        }
    }

    // 닉네임 중복 시 예외 발생
    public void checkNicknameDuplication(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new CustomException(ErrorCode.NICKNAME_ALREADY_EXIST);
        }
    }

}
