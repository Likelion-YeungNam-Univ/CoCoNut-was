package CoCoNut_was.domains.reward.service;

import CoCoNut_was.domains.project.Repository.ProjectRepository;
import CoCoNut_was.domains.project.dto.ProjectListResponseDto;
import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.reward.dto.RewardResponseDto;
import CoCoNut_was.domains.reward.entity.Reward;
import CoCoNut_was.domains.reward.repository.RewardRepository;
import CoCoNut_was.domains.submission.entity.Submission;
import CoCoNut_was.domains.submission.repository.SubmissionRepository;
import CoCoNut_was.domains.user.entity.User;
import CoCoNut_was.domains.user.repository.UserRepository;
import CoCoNut_was.exception.CustomException;
import CoCoNut_was.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardService {
    private final ProjectRepository projectRepository;
    private final RewardRepository rewardRepository;
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;

    @Transactional
    public RewardResponseDto awardWinner(Long projectId, Long submissionId) { // 소상공인이 보상을 지급하고, 공모전 마감 처리
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new CustomException(ErrorCode.PROJECT_NOT_FOUND)
        );
        Submission winningSubmission = submissionRepository.findById(submissionId).orElseThrow(
                () -> new CustomException(ErrorCode.SUBMISSION_NOT_FOUND)
        );
        project.close();
        projectRepository.save(project);

        Reward reward = Reward.builder()
                .user(winningSubmission.getUser())
                .project(project)
                .submission(winningSubmission)
                .rewardAmount(project.getRewardAmount())
                .build();

        Reward savedReward = rewardRepository.save(reward);

        return RewardResponseDto.fromEntity(savedReward);
    }

    @Transactional(readOnly = true)
    public List<ProjectListResponseDto> getMyAwardWinningProjects(UserDetails userDetails) { // 로그인한 사용자가 수상한 공모전 목록 조회
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Reward> myRewards = rewardRepository.findAllByUserOrderByRewardedAtDesc(user);

        return myRewards.stream()
                .map(reward -> ProjectListResponseDto.fromEntity(reward.getProject()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long getMyAwardsCount(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return rewardRepository.countByUser(user);
    }
}
