package sebastien.andreu.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@SpringBootApplication
@RestController
@EnableJpaRepositories("sebastien.andreu.spring.repository")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);


//		Path path = Path.of("C:\\Users\\andre\\Images\\2291.jpg");
//		byte[] fileContent = new byte[0];
//		try {
//			fileContent = Files.readAllBytes(path);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//
//		String base64Encoded = Base64.getEncoder().encodeToString(fileContent);
//		System.out.println(base64Encoded);
	}
}