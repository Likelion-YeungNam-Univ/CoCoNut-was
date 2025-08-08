package CoCoNut_was.domains.project.entitiy;

import jakarta.persistence.*;

@Entity
public class ProjectTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectTargetId;

    @Column(nullable = false, length = 10)
    private String target; // 타겟

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
