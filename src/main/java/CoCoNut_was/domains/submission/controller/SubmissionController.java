package CoCoNut_was.domains.submission.controller;

import CoCoNut_was.domains.submission.reqdto.SubmitDto;
import CoCoNut_was.domains.submission.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SubmissionController implements SubmissionApi {
    private final SubmissionService submissionService;


    // 1. 제출물 등록
    @PostMapping(value = "/projects/{project_id}/submissions", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submit(
            @PathVariable Long project_id,
            @Valid @RequestPart(value = "info", required = true) SubmitDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @AuthenticationPrincipal UserDetails userDetails
    ){
            submissionService.submit(project_id, dto, image, userDetails);
            return ResponseEntity.ok().build();
        }

    // 2. 제출물 목록 조회(한 공모전에 대한 모든 제출물 조회)
    @GetMapping("/projects/{project_id}/submissions")
    public ResponseEntity<?> getSubmissions(@PathVariable Long project_id){
        return ResponseEntity.ok().body(submissionService.getSubmissions(project_id));
    }

    // 3. 제출물 단일 상세조회
    @GetMapping("/submissions/{submission_id}")
    public ResponseEntity<?> getSubmissionById(
            @PathVariable Long submission_id,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        return ResponseEntity.ok().body(submissionService.getSubmissionDetails(submission_id, userDetails));
    }

    // 4. 제출물 수정
    @PutMapping("/submissions/{submission_id}")
    public ResponseEntity<?> updateSubmission(
            @PathVariable Long submission_id,
            @RequestBody SubmitDto dto,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        submissionService.updateSubmission(submission_id, dto, userDetails);
        return ResponseEntity.ok().build();
    }

}
