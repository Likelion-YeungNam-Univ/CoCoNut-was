package CoCoNut_was.domains.user.service;

import CoCoNut_was.domains.user.dto.UserReqDto;
import CoCoNut_was.domains.user.dto.UserResDto;
import CoCoNut_was.domains.user.entity.User;
import CoCoNut_was.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    // 1. 회원가입
    public User signUp(UserReqDto dto) {
        // 아이디 및 닉네임 중복확인 검증
        if(!existsByIdentifier(dto.getIdentifier()) && !existByNickname(dto.getNickname()))
            return null;

        User user = dto.toEntity();

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
}
