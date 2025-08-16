package CoCoNut_was.domains.vote.service;

import CoCoNut_was.domains.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;

    @Transactional
    public void vote(
            Long projectId,
            Long submissionId,
            UserDetails userDetails
    ){
        // 1. 로그인 토큰 확인



        // 2. 프로젝트 존재 확인



        // 3. 작품 존재 확인



        // 4. 투표 가능한 사람인지
        // 4-1. 해당 프로젝트에 투표한 이력이 있는 사람인지



        // 4-2. 공모전을 만든 소상공인은 투표할 수 없음



        // 4-3. 참여자는 자기 작품에 투표할 수 없음



        // 5. 투표 처리
    }
}
