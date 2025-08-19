package CoCoNut_was.domains.submission.repository;

import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.submission.entity.Submission;
import CoCoNut_was.domains.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByProjectId(Long project_id);

    boolean existsByUserAndProject(User user, Project project);

    List<Submission> findByUserId(Long id);
}
