package CoCoNut_was.domains.submission.service;

import CoCoNut_was.domains.project.entitiy.Project;
import CoCoNut_was.domains.submission.entity.Submission;
import CoCoNut_was.domains.submission.repository.SubmissionRepository;
import CoCoNut_was.domains.submission.reqdto.SubmitDto;
import CoCoNut_was.domains.user.entity.User;
import CoCoNut_was.domains.user.repository.UserRepository;
import CoCoNut_was.exception.CustomException;
import CoCoNut_was.exception.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final UserRepository userRepository;
    private final SubmissionRepository submissionRepository;
//    private final ProjectRepository projectRepository;
    // 프로젝트 코드 미완성으로 인한 주석처리

    // 임시 로컬 저장
    private final String uploadDir = "upload/";


    public void submit(Long projectId, @Valid SubmitDto dto, MultipartFile image) {

        User user = userRepository.findById(dto.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        // 프로젝트 코드 미완성으로 인해 주석처리
//        Project project = projectRepository.findById(dto.getProjectId()).orElseThrow(
//                () -> new CustomException(ErrorCode)
//        )
        Project project = new Project(); // 임시

        String imageUrl = null;
        if(!image.isEmpty()){
            try{
                String savedFilename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists())
                    if(!uploadPath.mkdirs())
                        throw new CustomException(ErrorCode.DIRECTORY_MAKE_FAIL);

                File dest = new File(uploadPath, savedFilename);
                image.transferTo(dest);

                imageUrl = "/" + uploadDir + savedFilename;
            } catch (IOException e){
                throw new CustomException(ErrorCode.IMAGE_SAVE_FAIL);
            }
        }

        Submission submission = dto.toEntity(project, user);

        submissionRepository.save(submission);
    }


}
