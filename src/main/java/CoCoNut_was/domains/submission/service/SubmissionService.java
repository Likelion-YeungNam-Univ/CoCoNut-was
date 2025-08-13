package CoCoNut_was.domains.submission.service;

import CoCoNut_was.domains.project.Repository.ProjectRepository;
import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.submission.entity.Submission;
import CoCoNut_was.domains.submission.repository.SubmissionRepository;
import CoCoNut_was.domains.submission.reqdto.SubmitDto;
import CoCoNut_was.domains.user.entity.User;
import CoCoNut_was.domains.user.repository.UserRepository;
import CoCoNut_was.exception.CustomException;
import CoCoNut_was.exception.ErrorCode;
import CoCoNut_was.gcs.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ImageUploadService imageUploadService;
    private final SubmissionRepository submissionRepository;

    @Transactional
    public void submit(Long projectId, SubmitDto dto, MultipartFile image, UserDetails userDetails) {
        // 1. 사용자 확인
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        // 2. 프로젝트 존재 확인
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new CustomException(ErrorCode.PROJECT_NOT_FOUND)
        );


        // 3. 이미지 업로드
        String imageUrl = null;
        try{
            if(image != null && !image.isEmpty())
                imageUrl = imageUploadService.uploadImage(image);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }

        // 4. Submission 엔티티 생성
        Submission submission = dto.toEntity(project, user);

        // 5. 저장
        submissionRepository.save(submission);

    }


}
