package CoCoNut_was.domains.project.repository;

import CoCoNut_was.domains.project.entity.ProjectTarget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTargetRepository extends JpaRepository<ProjectTarget, Long> {
    void deleteAllByProjectId(Long projectId);
}
