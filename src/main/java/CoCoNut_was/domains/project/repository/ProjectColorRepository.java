package CoCoNut_was.domains.project.repository;

import CoCoNut_was.domains.project.entity.ProjectColor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectColorRepository extends JpaRepository<ProjectColor, Long> {
    void deleteAllByProjectId(Long projectId);
}
