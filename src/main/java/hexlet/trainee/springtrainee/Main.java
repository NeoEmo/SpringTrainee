package hexlet.trainee.springtrainee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Main {
    static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
