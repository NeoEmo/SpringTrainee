package hexlet.trainee.springtrainee;

import hexlet.trainee.springtrainee.HelloWorld.MyApplication;
import hexlet.trainee.springtrainee.hexletSpringBlog.HomeController;
import org.springframework.boot.SpringApplication;

public class Main {
    static void main(String[] args) {
        SpringApplication.run(HomeController.class, args);
    }
}
