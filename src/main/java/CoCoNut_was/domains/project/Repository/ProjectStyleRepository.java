package CoCoNut_was.domains.project.Repository;

import CoCoNut_was.domains.project.entity.ProjectStyle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectStyleRepository extends JpaRepository<ProjectStyle, Long> {
    void deleteAllByProjectId(Long projectId);
}
