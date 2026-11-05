package hexlet.trainee.springtrainee.helloWorld;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloWorld")
public class MyApplication {
    @RequestMapping("/")
    public String home() {
        return "Hello World and Bye!";
    }
}
