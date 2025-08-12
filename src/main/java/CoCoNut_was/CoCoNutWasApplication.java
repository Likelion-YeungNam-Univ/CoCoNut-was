package CoCoNut_was;

import CoCoNut_was.gcs.ImageUploadService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CoCoNutWasApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoCoNutWasApplication.class, args);

		// 👇 클라우드 연결 확인용 코드, 기본 스프링 앱 주석 달고 실행
//		ConfigurableApplicationContext context = SpringApplication.run(CoCoNutWasApplication.class, args);
//		ImageUploadService imageUploadService = context.getBean(ImageUploadService.class);
//		imageUploadService.testGcsConnection();
	}

}
