package CoCoNut_was.domains.project.service;

import CoCoNut_was.domains.project.Repository.ProjectColorRepository;
import CoCoNut_was.domains.project.Repository.ProjectRepository;
import CoCoNut_was.domains.project.Repository.ProjectStyleRepository;
import CoCoNut_was.domains.project.Repository.ProjectTargetRepository;
import CoCoNut_was.domains.project.dto.ProjectDetailResponseDto;
import CoCoNut_was.domains.project.dto.ProjectListResponseDto;
import CoCoNut_was.domains.project.dto.ProjectRequestDto;
import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.project.entity.ProjectColor;
import CoCoNut_was.domains.project.entity.ProjectStyle;
import CoCoNut_was.domains.project.entity.ProjectTarget;
import CoCoNut_was.domains.user.entity.User;
import CoCoNut_was.exception.CustomException;
import CoCoNut_was.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectColorRepository projectColorRepository;
    private final ProjectStyleRepository projectStyleRepository;
    private final ProjectTargetRepository projectTargetRepository;

    @Transactional
    public void createProject(ProjectRequestDto projectRequestDto, User user) { // 공모전 생성
        Project project = projectRequestDto.toEntity(user);
        Project saveProject = projectRepository.save(project);

        saveProjectDetails(projectRequestDto.getColors(), projectRequestDto.getStyles(),
                            projectRequestDto.getTargets(), saveProject);
    }

    public List<ProjectListResponseDto> findAllProjects() { // 공모전 목록 조회
        return projectRepository.findAllWithUser().stream()
                .map(ProjectListResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public ProjectDetailResponseDto findProjectById(Long projectId) { // 공모전 상세 조회
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new CustomException(ErrorCode.PROJECT_NOT_FOUND)
        );
        return ProjectDetailResponseDto.fromEntity(project);
    }

    private void saveProjectDetails(List<String> colors, List<String> styles, List<String> targets, Project project) {
        // 색상 저장
        if (colors != null && !colors.isEmpty()) {
            List<ProjectColor> projectColors = colors.stream()
                    .map(color -> new ProjectColor(color, project))
                    .collect(Collectors.toList());
            projectColorRepository.saveAll(projectColors);
        }
        // 스타일 저장
        if (styles != null && !styles.isEmpty()) {
            List<ProjectStyle> projectStyles = styles.stream()
                    .map(style -> new ProjectStyle(style, project))
                    .collect(Collectors.toList());
            projectStyleRepository.saveAll(projectStyles);
        }
        // 타겟 저장
        if (targets != null && !targets.isEmpty()) {
            List<ProjectTarget> projectTargets = targets.stream()
                    .map(target -> new ProjectTarget(target, project))
                    .collect(Collectors.toList());
            projectTargetRepository.saveAll(projectTargets);
        }
    }
}
