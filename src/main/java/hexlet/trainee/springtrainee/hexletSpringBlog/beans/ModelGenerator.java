package hexlet.trainee.springtrainee.hexletSpringBlog.beans;

import hexlet.trainee.springtrainee.authentication.repository.AuthUserRepository;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.Post;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.Tag;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.User;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.PostsRepository;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Profile("test")
@Component
@RequiredArgsConstructor // Lombok сам создаст конструктор для final-полей
public class ModelGenerator implements CommandLineRunner {

    private final Faker faker;
    private final UsersRepository userRepository;
    private final PostsRepository postsRepository;
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Защита от дублирования данных при перезапуске приложения
        if (userRepository.count() > 0) {
            return;
        }

        System.out.println("Генерация тестовых данных...");

        for (int i = 0; i < 10; i++) {
            // 1. Создаем пользователя
            var user = new User();
            user.setUsername(faker.name().fullName());
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(faker.credentials().password(10, 100));

            // 2. Создаем для него посты
            List<Post> userPosts = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                var post = new Post();
                // Генерируем уникальный slug, чтобы не сработало ограничение unique = true
                post.setSlug(faker.lorem().word() + "-" + i + "-" + j);
                post.setName(faker.book().title());
                post.setContent(faker.lorem().paragraph(3));
                post.setAuthorName(user.getUsername());
                post.setAuthor(user); // <--- Привязываем автора

                // 3. Создаем теги для поста
                List<Tag> postTags = new ArrayList<>();
                for (int k = 0; k < 2; k++) {
                    var tag = new Tag();
                    tag.setName(faker.programmingLanguage().name());
                    tag.setPost(post); // <--- Привязываем тег к посту
                    postTags.add(tag);
                }
                post.setTags(postTags); // <--- Кладем теги в пост
                userPosts.add(post);
            }
            user.setPosts(userPosts); // <--- Кладем посты в юзера (хотя это не обязательно, CascadeType.ALL сделает работу)

            // 4. Сохраняем юзера. Благодаря CascadeType.ALL в сущности User,
            // Hibernate сам сохранит всех его постов и все теги этих постов!
            userRepository.save(user);
        }

        System.out.println("Генерация тестовых данных завершена!");
        System.out.println("Создание Auth пользователя:");
        var authUser = new User();
        authUser.setUsername("testuser");
        authUser.setEmail("test@example.com");
        authUser.setPassword(passwordEncoder.encode("secret_password123")); // Хэшируем!
        userRepository.save(authUser);
        System.out.println("Auth пользователь создан");
    }
}
