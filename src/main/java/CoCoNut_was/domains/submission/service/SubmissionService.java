package CoCoNut_was.domains.submission.service;

import CoCoNut_was.domains.project.Repository.ProjectRepository;
import CoCoNut_was.domains.project.entity.Project;
import CoCoNut_was.domains.submission.entity.Submission;
import CoCoNut_was.domains.submission.repository.SubmissionRepository;
import CoCoNut_was.domains.submission.reqdto.SubmitDto;
import CoCoNut_was.domains.submission.resdto.SubmissionDetailDto;
import CoCoNut_was.domains.submission.resdto.SubmissionListDto;
import CoCoNut_was.domains.user.entity.Role;
import CoCoNut_was.domains.user.entity.User;
import CoCoNut_was.domains.user.repository.UserRepository;
import CoCoNut_was.exception.CustomException;
import CoCoNut_was.exception.ErrorCode;
import CoCoNut_was.gcs.ImageUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

        // 2. 사용자 역할 검증
        if(user.getRole() != Role.ROLE_USER)
            throw new CustomException(ErrorCode.ROLE_NOT_MATCHED);

        // 3. 프로젝트 존재 확인
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new CustomException(ErrorCode.PROJECT_NOT_FOUND)
        );

        // 4. 프로젝트 제출기한 내 제출 시도인지
        if(project.getDeadline().isBefore(LocalDate.now()))
            throw new CustomException(ErrorCode.DEADLINE_EXPIRED);

        // 5. 프로젝트 중복 참여 검사(접속 전 자격 검증을 했다면 일어나지 않을 예외, 방지용으로 추가)
        if(submissionRepository.existsByUserAndProject(user, project))
            throw new CustomException(ErrorCode.NOT_POSSIBLE_MORE_SUBMISSION);

        // 6. 이미지 업로드
        String imageUrl = null;
        try{
            if(image != null && !image.isEmpty())
                imageUrl = imageUploadService.uploadImage(image);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }

        // 7. Submission 엔티티 생성
        Submission submission = dto.toEntity(project, user, imageUrl);

        // 8. 저장
        submissionRepository.save(submission);

    }

    // 한 공모전에 대한 작품 목록 조회
    @Transactional(readOnly = true)
    public List<SubmissionListDto> getProjectSubmissions(Long projectId) {
        // 1. 프로젝트 존재 확인
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new CustomException(ErrorCode.PROJECT_NOT_FOUND)
        );

        // 2. 작품 불러오기
        List<Submission> submissions = submissionRepository.findByProjectId(project.getId());
        List<SubmissionListDto> dtos = new ArrayList<>();

        // 3. dto로 모두 변환
        for(Submission submission : submissions) {
            dtos.add(SubmissionListDto.fromEntity(submission));
        }

        return dtos;
    }

    // 참여자가 제출한 작품 목록 조회
    @Transactional(readOnly = true)
    public List<SubmissionListDto> getMySubmissions(UserDetails userDetails) {
        // 1. 사용자 확인
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        if(user.getRole() != Role.ROLE_USER)
            throw new CustomException(ErrorCode.ROLE_NOT_MATCHED);

        // 2. 작품 불러오기
        List<Submission> submissions = submissionRepository.findByUserId(user.getId());
        List<SubmissionListDto> dtos = new ArrayList<>();

        // 3. dto로 모두 변환
        for(Submission submission : submissions) {
            dtos.add(SubmissionListDto.fromEntity(submission));
        }

        return dtos;
    }

    // 공모전 작품 상세 조회
    @Transactional(readOnly = true)
    public SubmissionDetailDto getSubmissionDetails(Long submissionId, UserDetails userDetails) {
        // 1. 사용자 존재 확인 (같은 유저 아니어도 됨
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        // 2. 작품 존재 확인
        Submission submission = submissionRepository.findById(submissionId).orElseThrow(
                () -> new CustomException(ErrorCode.SUBMISSION_NOT_FOUND)
        );

        // 3. dto로 변환후 반환
        return SubmissionDetailDto.fromEntity(submission);

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

        submission.setSubmittedAt(LocalDate.now());

        submissionRepository.save(submission);
    }

    public void checkSubmitValidation(Long projectId, UserDetails userDetails) {
        // 1. 공모전 및 유저의 유효성 여부 검사
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        // 2. 사용자 역할 검증
        if(user.getRole() != Role.ROLE_USER)
            throw new CustomException(ErrorCode.ROLE_NOT_MATCHED);


        // 3. 프로젝트 존재 검사
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new CustomException(ErrorCode.PROJECT_NOT_FOUND)
        );

        // 4. 이미 해당 공모전에 지원한경우
        if(submissionRepository.existsByUserAndProject(user, project))
            throw new CustomException(ErrorCode.NOT_POSSIBLE_MORE_SUBMISSION);


        // 해당 예외들을 모두 통과하면 지원자격이 있는 것
    }
}
