package CoCoNut_was.domains.vote.service;

import CoCoNut_was.domains.project.Repository.ProjectRepository;
import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.submission.entity.Submission;
import CoCoNut_was.domains.submission.repository.SubmissionRepository;
import CoCoNut_was.domains.user.entity.User;
import CoCoNut_was.domains.user.repository.UserRepository;
import CoCoNut_was.domains.vote.repository.VoteRepository;
import CoCoNut_was.exception.CustomException;
import CoCoNut_was.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final SubmissionRepository submissionRepository;

    @Transactional
    public void vote(
            Long projectId,
            Long submissionId,
            UserDetails userDetails
    ){
        // 1. 로그인 토큰 확인
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        // 2. 프로젝트 존재 확인
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new CustomException(ErrorCode.PROJECT_NOT_FOUND)
        );

        // 3. 작품 존재 확인
        Submission submission = submissionRepository.findById(submissionId).orElseThrow(
                () -> new CustomException(ErrorCode.SUBMISSION_NOT_FOUND)
        );


        // 4. 투표 가능한 사람인지
        // 4-1. 해당 프로젝트에 투표한 이력이 있는 사람인지



        // 4-2. 공모전을 만든 소상공인은 투표할 수 없음



        // 4-3. 참여자는 자기 작품에 투표할 수 없음



        // 5. 투표 처리
    }
}
