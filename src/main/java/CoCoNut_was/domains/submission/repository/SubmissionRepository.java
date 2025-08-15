package CoCoNut_was.domains.submission.repository;

import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.submission.entity.Submission;
import CoCoNut_was.domains.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByProject(Project project);

    Optional<Submission> findByUser(User user);

    boolean existsByUser(User user);
}
