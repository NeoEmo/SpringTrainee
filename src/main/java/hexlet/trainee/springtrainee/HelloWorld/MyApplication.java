package hexlet.trainee.springtrainee.HelloWorld;

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
}
