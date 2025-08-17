package CoCoNut_was.domains.reward.controller;

import CoCoNut_was.domains.project.dto.ProjectListResponseDto;
import CoCoNut_was.domains.reward.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rewards")
public class RewardController {
    private final RewardService rewardService;

    @PostMapping("/award/project/{projectId}/submission/{submissionId}")
    public ResponseEntity<?> awardWinner(
            @PathVariable Long projectId,
            @PathVariable Long submissionId) {
        rewardService.awardWinner(projectId, submissionId);

        return ResponseEntity.ok("수상작 선정 및 공모전 마감이 완료되었습니다.");
    }

    @GetMapping("/me/awards")
    public ResponseEntity<List<ProjectListResponseDto>> getMyAwards(@AuthenticationPrincipal UserDetails userDetails) {
        List<ProjectListResponseDto> awardedProjects = rewardService.getMyAwardWinningProjects(userDetails);
        return ResponseEntity.ok(awardedProjects);
    }

    @GetMapping("/me/awards/count")
    public ResponseEntity<Long> getMyAwardsCount(@AuthenticationPrincipal UserDetails userDetails) {
        long awardsCount = rewardService.getMyAwardsCount(userDetails);
        return ResponseEntity.ok(awardsCount);
    }
}
