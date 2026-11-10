package hexlet.trainee.springtrainee.hexletSpringBlog.api.controller;

import hexlet.trainee.springtrainee.hexletSpringBlog.handler.GlobalExceptionsHandler;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.Post;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.PostsRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PostsController.class)
@Import(GlobalExceptionsHandler.class)
public class WebPostControllerTest {

    private final Faker faker = new Faker();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PostsRepository postsRepository;

    @MockitoBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    private Post buildPost(Long id) {
        Post post = new Post();
        post.setId(id);
        post.setTitle(faker.book().title());
        post.setAuthor(faker.book().author());
        post.setContent(faker.lorem().sentence(5));

        return post;
    }

    @Test
    public void testGet() throws Exception {
        var post = buildPost(1L);

        when(postsRepository.findById(post.getId())).thenReturn(Optional.of(post));

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(post.getAuthor()));
    }
}
