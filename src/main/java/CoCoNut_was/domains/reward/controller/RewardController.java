package CoCoNut_was.domains.reward.controller;

import CoCoNut_was.domains.project.dto.ProjectListResponseDto;
import CoCoNut_was.domains.reward.dto.RewardResponseDto;
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
public class RewardController implements RewardApi{
    private final RewardService rewardService;

    // 수상작 선정
    @Override
    @PostMapping("/award/project/{projectId}/submission/{submissionId}")
    public ResponseEntity<?> awardWinner(
            @PathVariable Long projectId,
            @PathVariable Long submissionId) {
        RewardResponseDto rewardResponseDto = rewardService.awardWinner(projectId, submissionId);

        return ResponseEntity.ok(rewardResponseDto);
    }

    // 사용자의 작품이 선정된 공모전 목록 조회
    @Override
    @GetMapping("/me/awards")
    public ResponseEntity<List<ProjectListResponseDto>> getMyAwards(@AuthenticationPrincipal UserDetails userDetails) {
        List<ProjectListResponseDto> awardedProjects = rewardService.getMyAwardWinningProjects(userDetails);
        return ResponseEntity.ok(awardedProjects);
    }

    // 사용자의 작품이 선정된 횟수 조회
    @Override
    @GetMapping("/me/awards/count")
    public ResponseEntity<Long> getMyAwardsCount(@AuthenticationPrincipal UserDetails userDetails) {
        long awardsCount = rewardService.getMyAwardsCount(userDetails);
        return ResponseEntity.ok(awardsCount);
    }

    // 공모전 수상자 정보 가져오기
    @GetMapping("/winner/project/{project_id}")
    public ResponseEntity<?> getProjectWinner(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long project_id) {
        return ResponseEntity.ok().body(rewardService.getProjectWinner(userDetails, project_id));
    }

    @GetMapping("/finish/project/{project_id}/image")
    public ResponseEntity<?> getProjectListWinner(@PathVariable Long project_id){
        return ResponseEntity.ok().body(rewardService.getProjectListWinner(project_id));
    }
}
