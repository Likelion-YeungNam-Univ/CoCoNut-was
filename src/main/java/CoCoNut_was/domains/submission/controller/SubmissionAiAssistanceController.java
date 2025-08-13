package CoCoNut_was.domains.submission.controller;

import CoCoNut_was.domains.submission.resdto.SubmissionAiAssistanceDto;
import CoCoNut_was.domains.submission.service.SubmissionAiAssistanceService;
import CoCoNut_was.exception.CustomException;
import CoCoNut_was.exception.ErrorCode;
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
@RequestMapping("/api/v1/submissions")
public class SubmissionAiAssistanceController implements SubmissionAiAssistanceApi {

    private final SubmissionAiAssistanceService submissionService;

    @PostMapping("/assist")
    public ResponseEntity<?> getAssistanceForSubmission(
            @RequestBody Map<String, String> userReq
    ) throws JsonProcessingException {
        String userPrompt = userReq.get("prompt");
        if (userPrompt == null || userPrompt.isBlank())
            throw new CustomException(ErrorCode.PROMPT_IS_BLANK);

        SubmissionAiAssistanceDto result = submissionService.getAssistance(userPrompt);
        return ResponseEntity.ok(result);
    }

}
