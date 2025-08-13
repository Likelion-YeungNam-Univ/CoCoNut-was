package CoCoNut_was.domains.submission.controller;

import CoCoNut_was.domains.submission.reqdto.SubmitDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Submission API", description = "공모전 작품 API")
public interface SubmissionApi {


    ResponseEntity<?> submit(
            @PathVariable Long project_id,
            @RequestPart("info") SubmitDto dto,
            @RequestPart("image") MultipartFile image,
            @AuthenticationPrincipal UserDetails userDetails
    );



    ResponseEntity<?> getSubmissions(@PathVariable Long project_id);



    ResponseEntity<?> updateSubmission(
            @PathVariable Long submission_id,
            @RequestBody SubmitDto dto,
            @AuthenticationPrincipal UserDetails userDetails
    );
}
