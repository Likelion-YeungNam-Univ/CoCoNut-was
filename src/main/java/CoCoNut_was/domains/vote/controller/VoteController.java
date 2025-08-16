package CoCoNut_was.domains.vote.controller;

import CoCoNut_was.domains.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/votes/projects/{project_id}")
public class VoteController {
    private final VoteService voteService;


    // 1. 투표 등록
    @PostMapping("/submission/{submission_id}")
    public ResponseEntity<?> vote(
            @PathVariable Long project_id,
            @PathVariable Long submission_id,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        voteService.vote(project_id, submission_id, userDetails);
        return ResponseEntity.ok().build();
    }


    // 2. 투표 결과 전체 조회


    // 3. 한 작품에 대한 투표 개수


    // 4. 투표 삭제 (이거 필요할까요)


}
