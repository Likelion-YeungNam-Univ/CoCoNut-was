package CoCoNut_was.domains.vote.controller;

import CoCoNut_was.domains.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/votes")
public class VoteController implements VoteApi {
    private final VoteService voteService;


    // 1. 투표 등록
    @PostMapping("/submissions/{submission_id}")
    public ResponseEntity<?> vote(
            @PathVariable Long submission_id,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        voteService.vote(submission_id, userDetails);
        return ResponseEntity.ok().build();
    }


    // 2. 한 작품에 대한 투표 개수
    @GetMapping("/submissions/{submission_id}")
    public ResponseEntity<?> voteCount(
            @PathVariable Long submission_id,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        return ResponseEntity.ok().body(voteService.voteCount(submission_id, userDetails));
    }


    // 3. 투표 결과 전체 조회(이름, 이미지가 모두 포함된 정보)
    @GetMapping("/projects/{project_id}")
    public ResponseEntity<?> voteResult(
            @PathVariable Long project_id
    ){
        return ResponseEntity.ok().body(voteService.voteResult(project_id));
    }


    // 4. 투표 삭제 (이거 필요할까요)


}
