package CoCoNut_was.gcs;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket-name}")
    private String bucketName;

    public String uploadImage(MultipartFile file) throws IOException {
        // 1. 파일 이름이 중복되지 않도록 UUID로 새로운 파일명을 생성합니다.
        String originalFilename = file.getOriginalFilename();
        String ext = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + ext;

        // 2. GCS에 파일을 업로드합니다.
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, fileName)
                        .setContentType(file.getContentType())
                        .build(),
                file.getInputStream()
        );

        // 3. 업로드된 이미지의 공개 URL을 만들어 반환합니다.
        return "https://storage.googleapis.com/" + bucketName + "/" + fileName;
    }
}
