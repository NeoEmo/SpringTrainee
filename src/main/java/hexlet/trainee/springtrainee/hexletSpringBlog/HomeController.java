package hexlet.trainee.springtrainee.hexletSpringBlog;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class HomeController {
    @GetMapping
    public String home() {
        return "Добро пожаловать в Hexlet Spring Blog!";
    }
}
