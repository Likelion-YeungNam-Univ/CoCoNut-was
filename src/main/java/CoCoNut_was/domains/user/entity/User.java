package CoCoNut_was.domains.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String identifier;

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
    public User(String identifier, String name, String nickname, String password, Role role) {
        this.identifier = identifier;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

}

