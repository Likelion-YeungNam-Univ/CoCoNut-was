package CoCoNut_was.domains.project.controller;

import CoCoNut_was.domains.project.dto.ProjectAiAssistanceDto;
import CoCoNut_was.domains.project.dto.ProjectPromptDto;
import CoCoNut_was.domains.project.service.ProjectAiAssistanceService;
import CoCoNut_was.exception.CustomException;
import CoCoNut_was.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectAiAssistanceController implements ProjectAiAssistanceApi {

    private final ProjectAiAssistanceService projectAiAssistanceService;

    @Override
    @PostMapping("/assist")
    public ResponseEntity<?> getAssistanceForProject(
            @RequestBody ProjectPromptDto userReq
    ) throws JsonProcessingException {
        String userPrompt = userReq.getPrompt();
        if (userPrompt == null || userPrompt.isBlank())
            throw new CustomException(ErrorCode.PROMPT_IS_BLANK);

        ProjectAiAssistanceDto result = projectAiAssistanceService.getAssistance(userPrompt);
        return ResponseEntity.ok(result);
    }
}