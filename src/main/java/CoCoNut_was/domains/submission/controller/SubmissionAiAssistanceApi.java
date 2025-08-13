package CoCoNut_was.domains.submission.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Tag(name = "SubmissionAiAssistance API", description = "공모전 작품설명 작성 도우미 AI API")
public interface SubmissionAiAssistanceApi {

    ResponseEntity<?> getAssistanceForSubmission(
            @RequestBody Map<String, String> userReq
    ) throws JsonProcessingException;
    
}
