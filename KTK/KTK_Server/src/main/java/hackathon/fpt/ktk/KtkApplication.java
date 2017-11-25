package hackathon.fpt.ktk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "hackathon.fpt.ktk.service")
@EnableJpaRepositories(basePackages = "hackathon.fpt.ktk.repository")
@EntityScan(basePackages = "hackathon.fpt.ktk.entity")
public class KtkApplication {

	public static void main(String[] args) {
		SpringApplication.run(KtkApplication.class, args);
	}
}
