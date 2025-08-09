package CoCoNut_was.domains.submission.entity;

import CoCoNut_was.domains.project.entitiy.Project;
import CoCoNut_was.domains.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private LocalDate submittedAt;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder public Submission(String title, String description, String imageUrl, LocalDate submittedAt, Project project, User user) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.submittedAt = submittedAt;
        this.project = project;
        this.user = user;
    }
}
