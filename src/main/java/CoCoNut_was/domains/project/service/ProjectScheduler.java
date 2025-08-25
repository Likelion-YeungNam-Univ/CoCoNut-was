package CoCoNut_was.domains.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectScheduler {
    private final ProjectService projectService;

    // 자정에 실행
    @Scheduled(cron = "0 0 0 * * *")
    public void updateProjectStatuses() {
        projectService.startVotingForExpiredProjects(); // 마감일 지난 공모전을 투표중 으로 변경

        projectService.closeExpiredVotingProjects(); //투표 시작 후 7일 지난 공모전을 마감 으로 변경
    }
}
