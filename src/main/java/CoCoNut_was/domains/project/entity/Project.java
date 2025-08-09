package CoCoNut_was.domains.project.entity;

import CoCoNut_was.domains.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 공모전 고유 키 값

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 사용자와 N:1

    @Column(nullable = false, length = 100)
    private String title; // 공모전 제목

    @Column(nullable = false, length = 10)
    private String merchantName; // 가게명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category; // 공모전 카테고리

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusinessType businessType; // 공모전 업종

    @Column(nullable = false)
    private String description; // 공모전 상세 설명

    @Column(nullable = false, length = 10)
    private String period; // 공모전 기간

    @Column(nullable = false)
    private int rewardAmount; // 공모전 상금

    @Column(nullable = false)
    private String summary; // 공모전 한 줄 소개

    @Column(nullable = false)
    private LocalDateTime createdAt; // 공모전 생성일자

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status; // 공모전 상태

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectColor> projectColors = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectStyle> projectStyles = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectTarget> projectTargets = new ArrayList<>();

    @PrePersist
    public void onCreate() { // 생성일자 자동 생성
        this.createdAt = LocalDateTime.now();
        this.status = Status.IN_PROGRESS; // 진행중 상태로 시작
    }

    @Builder
    public Project(User user, String title, String merchantName, Category category,
                   BusinessType businessType, String description, String period, int rewardAmount,
                   String summary, LocalDateTime createdAt, Status status) {
        this.user = user;
        this.title = title;
        this.merchantName = merchantName;
        this.category = category;
        this.businessType = businessType;
        this.description = description;
        this.period = period;
        this.rewardAmount = rewardAmount;
        this.summary = summary;
        this.createdAt = createdAt;
        this.status = status;
    }

    @Builder
    public Project(String title, String merchantName, Category category, String description,
                   String period, int rewardAmount, String summary, LocalDateTime createdAt,
                   Status status) {
        this.title = title;
        this.merchantName = merchantName;
        this.category = category;
        this.description = description;
        this.period = period;
        this.rewardAmount = rewardAmount;
        this.summary = summary;
        this.createdAt = createdAt;
        this.status = status;
    }

}
