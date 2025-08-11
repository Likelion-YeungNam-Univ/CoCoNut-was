package CoCoNut_was.domains.project.controller;

import CoCoNut_was.domains.project.dto.ProjectRequestDto;
import CoCoNut_was.domains.project.service.ProjectService;
import CoCoNut_was.domains.user.entity.User;
import CoCoNut_was.domains.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;

    // 공모전 생성
    @PostMapping
    public ResponseEntity<?> createProject(
            @RequestBody ProjectRequestDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        User currentUser = userService.findUserByEmail(userDetails.getUsername());
        Long projectId = projectService.createProject(dto, currentUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(projectId);
    }
}
