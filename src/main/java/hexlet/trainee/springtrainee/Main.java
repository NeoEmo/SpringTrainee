package hexlet.trainee.springtrainee;

import hexlet.trainee.springtrainee.hexletSpringBlog.controller.HomeController;
import hexlet.trainee.springtrainee.hexletSpringBlog.controller.PostsController;
import hexlet.trainee.springtrainee.responseEntity.Application;
import org.springframework.boot.SpringApplication;

public class Main {
    static void main(String[] args) {
        SpringApplication.run(HomeController.class, args);
    }
}
