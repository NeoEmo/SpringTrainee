package hexlet.trainee.springtrainee.hexletSpringBlog.api.controller;

import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.PostCreateDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.PostPatchDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.mappers.PostMapper;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.Post;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.User;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.PostsRepository;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.UsersRepository;
import hexlet.trainee.springtrainee.hexletSpringBlog.utils.JWTUtils;

import jakarta.transaction.Transactional;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
//@Transactional Каждый тест как транзакция и затем Rollback, но данная реализация тестов ломает его, поэтому отключен
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private Faker faker;

    private User testUser;
    private Post testPost;
    private String token;

    @BeforeEach
    public void setup() {
        testUser = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .ignore(Select.field(User::getPosts))
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .supply(Select.field(User::getPassword), () -> faker.credentials().password(10, 100))
                .supply(Select.field(User::getUsername), () -> faker.credentials().username())
                .create();

        testUser = userRepository.save(testUser);
        token = jwtUtils.generateToken(testUser.getUsername());

        testPost = Instancio.of(Post.class)
                .ignore(Select.field(Post::getId))
                .ignore(Select.field(Post::getTags))
//                .supply(Select.field(Post::getTags), () -> faker.lorem().word())
                .supply(Select.field(Post::getAuthorName), () -> testUser.getUsername())
                .supply(Select.field(Post::getContent), () -> faker.gameOfThrones().quote())
                .supply(Select.field(Post::getAuthor), () -> testUser)
                .create();

        testPost = postsRepository.save(testPost);
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/api/posts")).andExpect(status().isOk()).andReturn();
        var body = result.getResponse().getContentAsString();

        assertThatJson(body).node("content").isArray();
    }

    @Test
    public void testCreate() throws Exception {
        var dto = new PostCreateDTO();
        dto.setSlug("Another great day 13.11.2026");
        dto.setName("The Post name");
        dto.setContent("The Post content. This is a long enough content for validation.");

        var request = post("/api/posts")
                .headers(headers -> headers.add("Authorization", "Bearer " + token))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto));

        mockMvc.perform(request).andExpect(status().isCreated());

        var savedPost = postsRepository.findBySlug(dto.getSlug()).get();
        assertNotNull(savedPost);
        assertThat(savedPost.getAuthorName()).isEqualTo(testUser.getUsername());
    }

    @Test
    public void testUpdate() throws Exception {
        var dto = new PostPatchDTO();
        dto.setContent(JsonNullable.of("New content"));
        dto.setSlug(JsonNullable.of("Another great day 14.11.2026"));
        dto.setName(JsonNullable.of("The new Post name"));

        var request = patch("/api/posts/" + testPost.getId())
                .headers(headers -> headers.add("Authorization", "Bearer " + token))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto));

        mockMvc.perform(request).andExpect(status().isOk());

        var updatedPost = postsRepository.findById(testPost.getId()).get();
        assertThat(updatedPost.getName()).isEqualTo(dto.getName().get());
    }

    @Test
    public void testShow() throws Exception {
        postsRepository.save(testPost);

        var request = get("/api/posts/" + testPost.getId());
        var result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        var body = result.getResponse().getContentAsString();
        assertThatJson(body)
                .and(
                        v -> v.node("slug").isEqualTo(testPost.getSlug()),
                        v -> v.node("authorName").isEqualTo(testPost.getAuthorName()),
                        v -> v.node("content").isEqualTo(testPost.getContent()));
    }

    @Test
    public void testDelete() throws Exception {
        postsRepository.save(testPost);
        var request = delete("/api/posts/" + testPost.getId())
                .headers(headers -> headers.add("Authorization", "Bearer " + token));
        mockMvc.perform(request).andExpect(status().isNoContent());

        assertThat(postsRepository.existsById(testPost.getId())).isFalse();
    }
}