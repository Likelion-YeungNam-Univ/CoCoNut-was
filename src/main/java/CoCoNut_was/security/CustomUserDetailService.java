package CoCoNut_was.security;

import CoCoNut_was.domains.user.entity.User;
import CoCoNut_was.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user = userRepository.findByIdentifier(identifier).orElseThrow(
                () -> new UsernameNotFoundException("아이디로 사용자를 찾을 수 없음"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getIdentifier())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

}
