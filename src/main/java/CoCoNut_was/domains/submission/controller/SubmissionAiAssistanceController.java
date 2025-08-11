package CoCoNut_was.domains.submission.controller;

import CoCoNut_was.domains.submission.resdto.SubmissionAiAssistanceDto;
import CoCoNut_was.domains.submission.service.SubmissionAiAssistanceService;
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
public class SubmissionAiAssistanceController {

    private final SubmissionAiAssistanceService submissionService;

    @PostMapping("/assist")
    public ResponseEntity<?> getAssistanceForSubmission(
            @RequestBody Map<String, String> userReq
    ) {
        try {
            String userPrompt = userReq.get("prompt");
            if (userPrompt == null || userPrompt.isBlank())
                return ResponseEntity.badRequest().body("프롬프트 내용이 없습니다.");

            SubmissionAiAssistanceDto result = submissionService.getAssistance(userPrompt);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("AI 어시스턴트 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

}
