package CoCoNut_was.domains.project.entity;

import CoCoNut_was.domains.submission.entity.Submission;
import CoCoNut_was.domains.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    @Column(nullable = false, length = 50)
    private String title; // 공모전 제목

    @Column(nullable = false)
    private String merchantName; // 가게명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category; // 공모전 카테고리

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusinessType businessType; // 공모전 업종

    @Column(nullable=false, columnDefinition="TEXT")
    private String description; // 공모전 상세 설명

    @Column(nullable = false)
    private int rewardAmount; // 공모전 상금

    @Column
    private String summary; // 공모전 한 줄 소개

    @Column(nullable = false)
    private LocalDate createdAt; // 공모전 생성일자

    @Column(nullable = false)
    private LocalDate deadline; // 공모전 마감일자

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status; // 공모전 상태

    @Column
    private String imageUrl; // 이미지 경로

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectColor> projectColors = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectStyle> projectStyles = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectTarget> projectTargets = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Submission> submissions = new ArrayList<>();

    @PrePersist
    public void onCreate() { // 생성일자 자동 생성
        this.createdAt = LocalDate.now();
        this.status = Status.IN_PROGRESS; // 진행중 상태로 시작
    }

    public void close() { // 공모전을 마감 상태로 변경하는 메서드
        this.status = Status.CLOSED;
    }

    @Builder
    public Project(User user, String title, String merchantName, Category category,
                   BusinessType businessType, String description, int rewardAmount,
                   String summary, LocalDate deadline, String imageUrl) {
        this.user = user;
        this.title = title;
        this.merchantName = merchantName;
        this.category = category;
        this.businessType = businessType;
        this.description = description;
        this.rewardAmount = rewardAmount;
        this.summary = summary;
        this.deadline = deadline;
        this.imageUrl = imageUrl;
    }
}
