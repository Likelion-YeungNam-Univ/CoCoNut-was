package CoCoNut_was.domains.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role;

    // 임시 빌더
    @Builder
    public User(String email, String name, String nickname, String password, Role role) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

}

