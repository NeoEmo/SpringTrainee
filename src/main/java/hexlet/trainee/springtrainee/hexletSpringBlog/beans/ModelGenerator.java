package hexlet.trainee.springtrainee.hexletSpringBlog.beans;

import hexlet.trainee.springtrainee.hexletSpringBlog.model.Post;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.User;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.PostsRepository;


import hexlet.trainee.springtrainee.hexletSpringBlog.repository.UsersRepository;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

//@Component
//public class ModelGenerator {
//    private final Faker faker;
//    private final UsersRepository userRepository;
//    private final PostsRepository postsRepository;
//
//    public ModelGenerator(Faker faker, UsersRepository userRepository, PostsRepository postsRepository) {
//        this.faker = faker;
//        this.userRepository = userRepository;
//        this.postsRepository = postsRepository;
//    }
//
//    @PostConstruct
//    public void generateData() {
//        for (int i = 0; i < 10; i++) {
//            var user = new User();
//            user.setUsername(faker.name().fullName());
//            user.setEmail(faker.internet().emailAddress());
//            user.setPassword(faker.credentials().password(10, 100));
//            userRepository.save(user);
//
//            for (int j = 0; j < 3; j++) {
//                var post = new Post();
//                post.setTitle(faker.book().title() + j+i);
//                post.setContent(faker.lorem().paragraph(1));
//                post.setAuthor(user.getUsername());
//                postsRepository.save(post);
//            }
//        }
//    }
//}
