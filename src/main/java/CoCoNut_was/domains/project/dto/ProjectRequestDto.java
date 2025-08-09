package CoCoNut_was.domains.project.dto;

import CoCoNut_was.domains.project.entitiy.Project;

import java.util.List;

public class ProjectRequestDto {
    private String title;
    private String merchantName;
    private String category;
    private String description;
    private String period;
    private int rewardAmount;
    private String summary;

    // 중복 선택 항목
    private List<String> colors;
    private List<String> styles;
    private List<String> targets;
}
