package CoCoNut_was.domains.project.entity;

import jakarta.persistence.*;

@Entity
public class ProjectStyle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectStyleId;

    @Column(nullable = false, length = 10)
    private String style; // 스타일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
