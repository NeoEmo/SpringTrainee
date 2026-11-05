package hexlet.trainee.springtrainee.hexletSpringBlog.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "Добро пожаловать в Hexlet Spring Blog!";
    }

    @RequestMapping("/about")
    public String about() {
        return "This is simple Spring blog!";
    }
}
