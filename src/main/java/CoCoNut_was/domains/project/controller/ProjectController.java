package CoCoNut_was.domains.project.controller;

import CoCoNut_was.domains.project.dto.ProjectRequestDto;
import CoCoNut_was.domains.project.service.ProjectService;
import CoCoNut_was.domains.user.entity.Role;
import CoCoNut_was.domains.user.entity.User;
import CoCoNut_was.domains.user.service.UserService;
import CoCoNut_was.exception.CustomException;
import CoCoNut_was.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/projects")
public class ProjectController implements ProjectApi {
    private final ProjectService projectService;
    private final UserService userService;

    // 공모전 생성
    @Override
    @PostMapping
    public ResponseEntity<?> createProject(
            @RequestBody ProjectRequestDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        User currentUser = userService.findUserByEmail(userDetails.getUsername());
        if(currentUser.getRole() != Role.ROLE_BUSINESS)
            throw new CustomException(ErrorCode.WRITE_ROLE_NOT_MATCHED);
        Long projectId = projectService.createProject(dto, currentUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(projectId);
    }

    // 공모전 목록 조회 (모든 공모전)
    @Override
    @GetMapping
    public ResponseEntity<?> findAllProjects() {
        return ResponseEntity.ok(projectService.findAllProjects());
    }

    // 공모전 상세 조회
    @Override
    @GetMapping("{project_id}")
    public ResponseEntity<?> findProjectById(@PathVariable Long project_id) {
        return ResponseEntity.ok(projectService.findProjectById(project_id));
    }

    // 공모전 삭제
    @Override
    @DeleteMapping("{project_id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long project_id) {
        projectService.deleteProjectById(project_id);
        return ResponseEntity.ok().build();
    }
}
