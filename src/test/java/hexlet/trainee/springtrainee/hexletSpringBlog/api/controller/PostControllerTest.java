package hexlet.trainee.springtrainee.hexletSpringBlog.api.controller;

import hexlet.trainee.springtrainee.hexletSpringBlog.model.Post;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.PostsRepository;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/api/posts")).andExpect(status().isOk()).andReturn();

        var body = result.getResponse().getContentAsString();

        assertThatJson(body).node("content").isArray();
    }

    @Test
    public void TestUpdate() throws Exception {
        var post = Instancio.of(Post.class)
                .ignore(Select.field(Post::getId))
                .supply(Select.field(Post::getTitle), () -> faker.book().title())
                .supply(Select.field(Post::getAuthor), () -> faker.book().author())
                .supply(Select.field(Post::getContent), () -> faker.lorem().paragraph())
                .create();

        postsRepository.save(post);

        var data = new HashMap<>();
        data.put("author", "ThisNotMe");

        var request = patch("/api/posts/" + post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isOk());
        post = postsRepository.findById(post.getId()).get();
        assertThat(post.getAuthor()).isEqualTo(data.get("author"));
    }

    @Test
    public void TestDelete() throws Exception {
        var post = Instancio.of(Post.class)
                .ignore(Select.field(Post::getId))
                .supply(Select.field(Post::getTitle), () -> faker.book().title())
                .supply(Select.field(Post::getAuthor), () -> faker.book().author())
                .supply(Select.field(Post::getContent), () -> faker.lorem().paragraph())
                .create();

        postsRepository.save(post);

        var request = delete("/api/posts/" + post.getId());
        mockMvc.perform(request).andExpect(status().isNoContent());

        var request2 = delete("/api/posts/" + post.getId());
        mockMvc.perform(request2).andExpect(status().isNotFound());

        var request3 = get("/api/posts/" + post.getId());
        mockMvc.perform(request3).andExpect(status().isNotFound());
    }

    @Test
    public void TestCreate() throws Exception {
        var post = Instancio.of(Post.class)
                .ignore(Select.field(Post::getId))
                .supply(Select.field(Post::getTitle), () -> faker.book().title())
                .supply(Select.field(Post::getAuthor), () -> faker.book().author())
                .supply(Select.field(Post::getContent), () -> faker.lorem().paragraph())
                .create();

        var request = post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post));

        mockMvc.perform(request).andExpect(status().isCreated());

        var request2 = post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post));

        mockMvc.perform(request2).andExpect(status().isConflict());
    }

    @Test
    public void TestShow() throws Exception {
        var post = Instancio.of(Post.class)
                .ignore(Select.field(Post::getId))
                .supply(Select.field(Post::getTitle), () -> faker.book().title())
                .supply(Select.field(Post::getAuthor), () -> faker.book().author())
                .supply(Select.field(Post::getContent), () -> faker.lorem().paragraph())
                .create();

        postsRepository.save(post);

        var request = get("/api/posts/" + post.getId());
        mockMvc.perform(request).andExpect(status().isOk());

        var request2 = get("/api/posts/999");
        mockMvc.perform(request2).andExpect(status().isNotFound());
    }
}
