package hexlet.trainee.springtrainee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MyApplication {
    @RequestMapping("/")
    public String home() {
        return "Hello World and Bye!";
    }

    static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
