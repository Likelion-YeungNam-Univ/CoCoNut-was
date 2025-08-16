package CoCoNut_was.domains.vote.controller;

import CoCoNut_was.domains.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects/{project_id}/votes")
public class VoteController {
    private final VoteService voteService;


    // 1. 투표 등록


    // 2. 투표 결과 전체 조회


    // 3. 한 작품에 대한 투표 개수


    // 4. 투표 삭제 (이거 필요할까요)


}
