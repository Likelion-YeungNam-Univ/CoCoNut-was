package CoCoNut_was.domains.submission.controller;

import CoCoNut_was.domains.submission.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SubmissionController {
    private final SubmissionService submissionService;


    // 1. 제출물 등록
    @PostMapping("/projects/{project_id}/submissions")
    public ResponseEntity<?> submit(@PathVariable Long project_id){ // 제출할 dto도 보내야함
        return ResponseEntity.ok().build();
    }

    // 2. 제출물 목록 조회(한 공모전에 대한 모든 제출물 조회)
    @GetMapping("/projects/{project_id}/submissions")
    public ResponseEntity<?> getSubmissions(@PathVariable Long project_id){
        return ResponseEntity.ok().build();
    }

    // 3. 제출물 수정
    @PutMapping("/submissions/{submission_id}")
    public ResponseEntity<?> updateSubmission(@PathVariable Long submission_id){ // 수정할 dto도 보내야함
        return ResponseEntity.ok().build();
    }
}
