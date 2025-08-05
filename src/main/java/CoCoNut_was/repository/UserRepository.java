package CoCoNut_was.repository;

import CoCoNut_was.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByIdentifier(String identifier);
    boolean existsByNickname(String nickname);
}
