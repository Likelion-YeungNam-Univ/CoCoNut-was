package CoCoNut_was.domains.project.service;

import CoCoNut_was.domains.project.repository.ProjectColorRepository;
import CoCoNut_was.domains.project.repository.ProjectRepository;
import CoCoNut_was.domains.project.repository.ProjectStyleRepository;
import CoCoNut_was.domains.project.repository.ProjectTargetRepository;
import CoCoNut_was.domains.project.dto.ProjectDetailResponseDto;
import CoCoNut_was.domains.project.dto.ProjectListResponseDto;
import CoCoNut_was.domains.project.dto.ProjectRequestDto;
import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.project.entity.ProjectColor;
import CoCoNut_was.domains.project.entity.ProjectStyle;
import CoCoNut_was.domains.project.entity.ProjectTarget;
import CoCoNut_was.domains.project.entity.Status;
import CoCoNut_was.domains.submission.repository.SubmissionRepository;
import CoCoNut_was.domains.user.entity.Role;
import CoCoNut_was.domains.user.entity.User;
import CoCoNut_was.domains.user.repository.UserRepository;
import CoCoNut_was.exception.CustomException;
import CoCoNut_was.exception.ErrorCode;
import CoCoNut_was.gcs.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectColorRepository projectColorRepository;
    private final ProjectStyleRepository projectStyleRepository;
    private final ProjectTargetRepository projectTargetRepository;
    private final ImageUploadService imageUploadService;
    private final UserRepository userRepository;
    private final SubmissionRepository submissionRepository;

    @Transactional
    public Long createProject(ProjectRequestDto projectRequestDto, User user, MultipartFile image) { // 공모전 생성
        // 사용자 역할 검증
        if (user.getRole() != Role.ROLE_BUSINESS) {
            throw new CustomException(ErrorCode.ROLE_NOT_MATCHED);
        }

        // 이미지 업로드
        String imageUrl = null;
        try {
            if (image != null && !image.isEmpty())
                imageUrl = imageUploadService.uploadImage(image);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }

        // 업로드된 이미지 URL을 포함하여 Project 엔티티를 생성
        Project project = projectRequestDto.toEntity(user, imageUrl);
        Project saveProject = projectRepository.save(project);

        // 색상, 스타일, 타겟 등 세부 정보를 저장
        saveProjectDetails(projectRequestDto.getColors(), projectRequestDto.getStyles(),
                projectRequestDto.getTargets(), saveProject);

        return saveProject.getId();
    }

    public List<ProjectListResponseDto> findAllProjects() { // 공모전 목록 조회
        return projectRepository.findAllWithSubmissionCount();
    }

    public ProjectDetailResponseDto findProjectById(Long projectId) { // 공모전 상세 조회
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new CustomException(ErrorCode.PROJECT_NOT_FOUND)
        );
        return ProjectDetailResponseDto.fromEntity(project);
    }

    public void deleteProjectById(Long projectId, User currentUser) { // 공모전 삭제
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new CustomException(ErrorCode.PROJECT_NOT_FOUND)
        );

        // 공모전 작성자와 현재 로그인한 유저가 같은지 확인
        if (!project.getUser().getId().equals(currentUser.getId())) {
            throw new CustomException(ErrorCode.PROJECT_USER_NOT_MATCHED);
        }

        projectRepository.delete(project);
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

    @Transactional
    public void startVotingForExpiredProjects() {
        List<Project> projects = projectRepository.findAllByDeadlineBeforeAndStatus(LocalDate.now(), Status.IN_PROGRESS);
        for (Project project : projects) {
            project.startVoting();
        }
    }

    @Transactional
    public void closeExpiredVotingProjects() {
        // 오늘로부터 7일 이전 날짜를 계산
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
        // 투표중 상태가 된 지 7일이 지난 공모전들을 조회
        List<Project> projects = projectRepository.findAllByStatusAndVotingStartDateBefore(Status.VOTING, sevenDaysAgo);
        for (Project project : projects) {
            project.close();
        }
    }
}
