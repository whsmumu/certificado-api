package github.whsmumu.certificadoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CertificadoapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CertificadoapiApplication.class, args);
	}

}
