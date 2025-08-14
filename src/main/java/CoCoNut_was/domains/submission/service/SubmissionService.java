package CoCoNut_was.domains.submission.service;

import CoCoNut_was.domains.project.Repository.ProjectRepository;
import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.submission.entity.Submission;
import CoCoNut_was.domains.submission.repository.SubmissionRepository;
import CoCoNut_was.domains.submission.reqdto.SubmitDto;
import CoCoNut_was.domains.submission.resdto.SubmissionDetailDto;
import CoCoNut_was.domains.submission.resdto.SubmissionListDto;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ImageUploadService imageUploadService;
    private final SubmissionRepository submissionRepository;

    // 공모전 작품 제출
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
        Submission submission = dto.toEntity(project, user, imageUrl);

        // 5. 저장
        submissionRepository.save(submission);

    }

    // 공모전 작품 조회(로그인 X)
    public List<SubmissionListDto> getSubmissions(Long projectId) {
        // 1. 프로젝트 존재 확인
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new CustomException(ErrorCode.PROJECT_NOT_FOUND)
        );

        // 2. 제출물 불러오기
        List<Submission> submissions = submissionRepository.findByProject(project);
        List<SubmissionListDto> dtos = new ArrayList<>();

        // 3. dto로 모두 변환
        for(Submission submission : submissions) {
            dtos.add(SubmissionListDto.fromEntity(submission));
        }

        return dtos;
    }

    public SubmissionDetailDto getSubmissionDetails(Long submissionId, UserDetails userDetails) {
    }

    // 공모전 작품 수정
    @Transactional
    public void updateSubmission(Long submissionId, SubmitDto dto, UserDetails userDetails) {
        // 1. 사용자 확인
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        // 2. 해당 제출물이 존재하는지
        Submission submission = submissionRepository.findById(submissionId).orElseThrow(
                () -> new CustomException(ErrorCode.SUBMISSION_NOT_FOUND)
        );

        // 3. 제출물의 소유자와 일치하는지
        if(!submission.getUser().getId().equals(user.getId()))
            throw new CustomException(ErrorCode.SUBMISSION_USER_NOT_MATCHED);


        if(dto.getTitle() != null && !dto.getTitle().isBlank())
            submission.setTitle(dto.getTitle());
        if(dto.getDescription() != null && !dto.getDescription().isBlank())
            submission.setDescription(dto.getDescription());

        submissionRepository.save(submission);
    }
}
