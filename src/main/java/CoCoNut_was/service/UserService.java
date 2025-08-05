package CoCoNut_was.service;

import CoCoNut_was.dto.UserReqDto;
import CoCoNut_was.entity.User;
import CoCoNut_was.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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


}
