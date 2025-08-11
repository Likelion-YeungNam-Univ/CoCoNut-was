package CoCoNut_was.domains.project.controller;

import CoCoNut_was.domains.project.dto.ProjectAiAssistanceDto;
import CoCoNut_was.domains.project.service.ProjectAiAssistanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectAiAssistanceController {

    private final ProjectAiAssistanceService projectAiAssistanceService;

    @PostMapping("/assist")
    public ResponseEntity<?> getAssistanceForProject(
            @RequestBody Map<String, String> userReq
    ) {
        try {
            String userPrompt = userReq.get("prompt");
            if (userPrompt == null || userPrompt.isBlank()) {
                return ResponseEntity.badRequest().body("프롬프트 내용이 없습니다.");
            }

            ProjectAiAssistanceDto result = projectAiAssistanceService.getAssistance(userPrompt);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("AI 어시스턴트 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}