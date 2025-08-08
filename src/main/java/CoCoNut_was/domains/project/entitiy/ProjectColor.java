package CoCoNut_was.domains.project.entitiy;

import jakarta.persistence.*;

@Entity
public class ProjectColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectColorId;

    @Column(nullable = false)
    private String color; // 색상

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
