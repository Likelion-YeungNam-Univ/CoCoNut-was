package CoCoNut_was.domains.vote.repository;

import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.user.entity.User;
import CoCoNut_was.domains.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    // 특정 유저, 특정 프로젝트에 대한 투표기록이 존재하는가를 표현할때 쓰는 JPA 메소드
    boolean existsByUserAndSubmission_Project(User user, Project project);

    Long countBySubmissionId(Long submissionId);
}
