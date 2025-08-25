package CoCoNut_was.domains.project.repository;

import CoCoNut_was.domains.project.dto.ProjectListResponseDto;
import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.project.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // 공모전 조회할 때 User 엔티티 함께 조회 (N+1 방지)
    @Query("SELECT p FROM Project p JOIN FETCH p.user u ORDER BY p.createdAt DESC")
    List<Project> findAllWithUser();

    @Query("SELECT new CoCoNut_was.domains.project.dto.ProjectListResponseDto(p.user.id, p.id, p.user.nickname, p.title, p.merchantName, p.category, p.businessType, p.createdAt, p.deadline, p.rewardAmount, p.summary, p.status, size(p.submissions)) FROM Project p JOIN p.user u ORDER BY p.createdAt DESC")
    List<ProjectListResponseDto> findAllWithSubmissionCount();

    List<Project> findAllByDeadlineBeforeAndStatus(LocalDate date, Status status);

    List<Project> findAllByStatusAndVotingStartDateBefore(Status status, LocalDate date);
}
