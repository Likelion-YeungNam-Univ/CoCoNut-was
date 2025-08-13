package CoCoNut_was.domains.project.controller;

import CoCoNut_was.domains.project.dto.ProjectAiAssistanceDto;
import CoCoNut_was.domains.project.service.ProjectAiAssistanceService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class ProjectAiAssistanceController implements ProjectAiAssistanceApi {

    private final ProjectAiAssistanceService projectAiAssistanceService;

    @Override
    @PostMapping("/assist")
    public ResponseEntity<?> getAssistanceForProject(
            @RequestBody Map<String, String> userReq
    ) throws JsonProcessingException {
        String userPrompt = userReq.get("prompt");
        if (userPrompt == null || userPrompt.isBlank())
            return ResponseEntity.badRequest().body("프롬프트 내용이 없습니다.");

        ProjectAiAssistanceDto result = projectAiAssistanceService.getAssistance(userPrompt);
        return ResponseEntity.ok(result);
    }
}