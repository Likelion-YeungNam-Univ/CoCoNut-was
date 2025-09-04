package CoCoNut_was;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoCoNutWasApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoCoNutWasApplication.class, args);
	}

}
